package com.epay.ewallet.service.post.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

@Document(collection = "reacts")
@QueryEntity
public class React {

	@Id
	private String id;

	@NotBlank
	private String userId;

	@NotBlank
	private String reactType;

	@NotBlank
	private String referenceId;

	@NotNull
	private Date createDate;

	@NotBlank
	private String status;

	@NotNull
	private String type;

	public React() {

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the reactType
	 */
	public String getReactType() {
		return reactType;
	}

	/**
	 * @param reactType the reactType to set
	 */
	public void setReactType(String reactType) {
		this.reactType = reactType;
	}

	/**
	 * @return the referenceId
	 */
	public String getReferenceId() {
		return referenceId;
	}

	/**
	 * @param referenceId the referenceId to set
	 */
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	public React(String id, @NotBlank String userId, @NotBlank String reactType, @NotBlank String referenceId,
			@NotNull Date createDate, @NotBlank String status, @NotNull String type) {
		super();
		this.id = id;
		this.userId = userId;
		this.reactType = reactType;
		this.referenceId = referenceId;
		this.createDate = createDate;
		this.status = status;
		this.type = type;
	}

}