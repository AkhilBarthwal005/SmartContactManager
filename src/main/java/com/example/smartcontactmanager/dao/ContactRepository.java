package com.example.smartcontactmanager.dao;

import com.example.smartcontactmanager.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Integer> {

    @Query(value = "select * from contact where user_id =:userId order by name", nativeQuery = true)
    public List<Contact> findAllContactsByUserId(@Param("userId") int userId);

}
