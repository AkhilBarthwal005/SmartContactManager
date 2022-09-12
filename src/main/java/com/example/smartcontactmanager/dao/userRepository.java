package com.example.smartcontactmanager.dao;

import com.example.smartcontactmanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User,Integer> {

}
