package com.example.smartcontactmanager.dao;

import com.example.smartcontactmanager.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Integer> {

}
