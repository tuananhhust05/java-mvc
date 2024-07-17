package com.epay.ewallet.service.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.Colleague;

@Repository
public interface PostRepository extends JpaRepository<Colleague, String> {
    
}