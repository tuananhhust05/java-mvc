package com.epay.ewallet.service.post.model;

import java.util.Date;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

@Document(collection = "saved_posts")
@QueryEntity
public class SavedPost {
    
  @Id
  private String id;

  private String postId;
  
  private String status;

  private String userId;

  private Date saveDate;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  
  
  public Date getSaveDate() {
    return saveDate;
  }
  
  public void setSaveDate(Date saveDate) {
    this.saveDate = saveDate;
  }

  
  public String getStatus() {
      return status;
  }
  
  public void setStatus(String status) {
      this.status = status;
  }
  
  
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  
  public String getPostId() {
    return postId;
  }
  
  public void setPostId(String postId) {
    this.postId = postId;
  }

  @Override
  public String toString() {
    return "SavedPost [id=" + id + ", postId=" + postId + ", status=" + status + ", userId=" + userId + ", saveDate="
        + saveDate + "]";
  }
  
 
}