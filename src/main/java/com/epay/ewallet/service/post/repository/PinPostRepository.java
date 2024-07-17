package com.epay.ewallet.service.post.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.PinPost;

@Repository
public interface PinPostRepository extends MongoRepository<PinPost, String>  {
    PinPost findByReferenceId(String referenceId);
}