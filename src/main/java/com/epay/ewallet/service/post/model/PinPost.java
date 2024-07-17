package com.epay.ewallet.service.post.model;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

@Document(collection = "pins")
@QueryEntity
public class PinPost {

	@Id
	private String id;

	@NotBlank
	private String userId;

	private String referenceId;

	private boolean isAdmin;

	private String groupId;

	private String checkPage;

	private String status;

	private Date fromDate;

	private Date toDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getCheckPage() {
		return checkPage;
	}

	public void setCheckPage(String checkPage) {
		this.checkPage = checkPage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public PinPost(String id, @NotNull String userId, String referenceId, boolean isAdmin, String checkPage,
			String status, Date fromDate, Date toDate) {
		this.id = id;
		this.userId = userId;
		this.referenceId = referenceId;
		this.isAdmin = isAdmin;
		this.checkPage = checkPage;
		this.status = status;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

	public PinPost() {
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Override
	public String toString() {
		return "PinPost [id=" + id + ", userId=" + userId + ", referenceId=" + referenceId + ", isAdmin=" + isAdmin
				+ ", checkPage=" + checkPage + ", status=" + status + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ "]";
	}
}