package com.epay.ewallet.service.post.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

@Document(collection = "usersetting")
@QueryEntity
public class UserSetting {

	@Id
	private String id;

	@NotNull
	private String userId;

	private String disableTag;

	private String disableMention;

	private String list_social;

	@NotNull
	private Date createDate;

	@NotNull
	private Date editDate;

	public UserSetting() {

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
	 * @return the disableTag
	 */
	public String getDisableTag() {
		return disableTag;
	}

	/**
	 * @param disableTag the disableTag to set
	 */
	public void setDisableTag(String disableTag) {
		this.disableTag = disableTag;
	}

	/**
	 * @return the disableMention
	 */
	public String getDisableMention() {
		return disableMention;
	}

	/**
	 * @param disableMention the disableMention to set
	 */
	public void setDisableMention(String disableMention) {
		this.disableMention = disableMention;
	}

	/**
	 * @return the list_social
	 */
	public String getList_social() {
		return list_social;
	}

	/**
	 * @param list_social the list_social to set
	 */
	public void setList_social(String list_social) {
		this.list_social = list_social;
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
	 * @return the editDate
	 */
	public Date getEditDate() {
		return editDate;
	}

	/**
	 * @param editDate the editDate to set
	 */
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public UserSetting(String id, @NotNull String userId, String disableTag, String disableMention, String list_social,
			@NotNull Date createDate, @NotNull Date editDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.disableTag = disableTag;
		this.disableMention = disableMention;
		this.list_social = list_social;
		this.createDate = createDate;
		this.editDate = editDate;
	}

}