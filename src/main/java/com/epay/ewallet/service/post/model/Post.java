package com.epay.ewallet.service.post.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

@Document(collection = "posts")
@QueryEntity
public class Post {

	@Id
	private String id;

	private String userId;

	private String groupId;

	private String content;

	private String status;

	private Date createDate;

	private Date editDate;

	private String byadmin;

	private String reason;

	private String marketType;

	private String category;

	private String price;

	private String postByRole;;
	
	private String reportTypeId;

	public Post() {

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
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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

	/**
	 * @return the byadmin
	 */
	public String getByadmin() {
		return byadmin;
	}

	/**
	 * @param byadmin the byadmin to set
	 */
	public void setByadmin(String byadmin) {
		this.byadmin = byadmin;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the marketType
	 */
	public String getMarketType() {
		return marketType;
	}

	/**
	 * @param marketType the marketType to set
	 */
	public void setMarketType(String marketType) {
		this.marketType = marketType;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getReportTypeId() {
		return reportTypeId;
	}

	public void setReportTypeId(String reportTypeId) {
		this.reportTypeId = reportTypeId;
	}

	/**
	 * @return the postByRole
	 */
	public String getPostByRole() {
		return postByRole;
	}

	/**
	 * @param postByRole the postByRole to set
	 */
	public void setPostByRole(String postByRole) {
		this.postByRole = postByRole;
	}

	public Post(String id, String userId, String groupId, String content, String status, Date createDate, Date editDate,
			String byadmin, String reason, String marketType, String category, String price, String reportTypeId,
			String postByRole) {
		super();
		this.id = id;
		this.userId = userId;
		this.groupId = groupId;
		this.content = content;
		this.status = status;
		this.createDate = createDate;
		this.editDate = editDate;
		this.byadmin = byadmin;
		this.reason = reason;
		this.marketType = marketType;
		this.category = category;
		this.price = price;
		this.postByRole = postByRole;
	}

}