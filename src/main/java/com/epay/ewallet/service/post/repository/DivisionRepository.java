package com.epay.ewallet.service.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.Division;

@Repository
public interface DivisionRepository extends JpaRepository<Division, String> {
    List<Division> findByName(String name);
    
    List<Division> findByPhonenumber(String phonenumber);
}
