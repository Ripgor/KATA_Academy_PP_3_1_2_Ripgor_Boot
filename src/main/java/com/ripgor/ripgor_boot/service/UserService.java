package com.ripgor.ripgor_boot.service;

import com.ripgor.ripgor_boot.dao.UserDao;
import com.ripgor.ripgor_boot.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findUser(int id) {
        return userDao.findById(id).get();
    }

    public User findUserByName(String name) {
        return userDao.findUserByName(name);
    }

    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    public void deleteUser(int id) {
        userDao.deleteById(id);
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    public void updateUser(User user) {
        User userToUpdate = userDao.findById(user.getId()).get();
        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setRoles(user.getRoles());
        userDao.save(userToUpdate);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findUserByName(username);

        if (user == null) throw new UsernameNotFoundException("User Not Found");

        return new org.springframework.security.core.userdetails.User(
                user.getName(), user.getPassword(), user.getAuthorities()
        );
    }
}
