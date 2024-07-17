package com.epay.ewallet.service.post.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TBL_COMPANY_USER")
public class Division {
    private String userId;

    private String phonenumber;
    
    @NotNull
    private String name;

    private String posittion;

    private String division;

    
    @Column(name = "PHONE_NUMBER")
    public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	@Id
    @Column(name = "ID", nullable = false)
    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    @Column(name = "FULL_NAME")
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Column(name = "POSITTION")
    public String getPosittion() {
      return posittion;
    }

    public void setPosittion(String posittion) {
      this.posittion = posittion;
    }

    @Column(name = "DIVISION")
    public String getDivision() {
      return division;
    }

    public void setDivision(String division) {
      this.division = division;
    }

    public Division() {
    }

//    public Division(String userId, @NotNull String name, String posittion, String division) {
//      this.userId = userId;
//      this.name = name;
//      this.posittion = posittion;
//      this.division = division;
//    }

    
}
