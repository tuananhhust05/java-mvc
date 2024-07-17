package com.epay.ewallet.service.post.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.epay.ewallet.service.post.model.Post;
import com.epay.ewallet.service.post.model.SavedPost;

public interface SavedPostRepository extends MongoRepository<SavedPost, String> {
  
  Optional<SavedPost> findByPostIdAndUserId(String postId, String userId);
}