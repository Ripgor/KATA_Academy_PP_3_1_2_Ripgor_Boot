package com.ripgor.ripgor_boot.dao;

import com.ripgor.ripgor_boot.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findUserByName(String name);
}
