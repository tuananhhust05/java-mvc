package com.epay.ewallet.service.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.Colleague;

@Repository
public interface TagColleagueRepository extends JpaRepository<Colleague, String>{

    // @Query("SELECT NAME FROM TBL_USERS")
    // List<Colleague> search(String name);
    
    
    Page<Colleague> findByNameContainingIgnoreCase(String name, Pageable pageable);
   
    
    Page<Colleague> findByNameContainingIgnoreCaseAndStatusGreaterThanEqualAndUserTypeIdAndUserIdIsNot(String name,int status,int userTypeId,String userId, Pageable pageable);
}