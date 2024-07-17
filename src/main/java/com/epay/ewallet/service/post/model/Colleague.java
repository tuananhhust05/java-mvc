package com.epay.ewallet.service.post.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "TBL_USERS")
public class Colleague {

  private String userId;

  @NotNull
  private String name;

  private String nickname;
  
  private String avatar;

  private int status;

  private int userTypeId;

  private String phone;

  public Colleague() {
  }

  public Colleague(String userId, String name, String avatar) {
    this.userId = userId;
    this.name = name;
    this.avatar = avatar;
  }

  
  @Column(name = "PHONE", nullable = false)
  public String getPhone() {
	return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}

@Id
  @Column(name = "ID", nullable = false)
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Column(name = "NAME")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Column(name = "AVATAR")
  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  

  @Column(name = "STATUS")
  public int getStatus(){
    return status;
  }

  public void setStatus(int status){
    this.status = status;
  }

  @Column(name = "USER_TYPE_ID")
  public int getUserTypeId() {
    return userTypeId;
  }

  public void setUserTypeId(int userTypeId) {
    this.userTypeId = userTypeId;
  }

  @Column(name = "NICKNAME")
  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  @Override
  public String toString() {
    return "Colleague [userId=" + userId + ", name=" + name + ", nickname=" + nickname + ", avatar=" + avatar
        + ", status=" + status + ", userTypeId=" + userTypeId + "]";
  }

  



  
  
  
  
  
}
