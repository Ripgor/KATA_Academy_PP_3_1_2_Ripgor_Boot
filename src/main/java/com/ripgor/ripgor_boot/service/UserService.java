package com.ripgor.ripgor_boot.service;

import com.ripgor.ripgor_boot.dao.UserDao;
import com.ripgor.ripgor_boot.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findUser(int id) {
        return userDao.findById(id).orElse(null);
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
}
