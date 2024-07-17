package com.epay.ewallet.service.post.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

@Document(collection = "comments")
@QueryEntity
public class Comment {

	@Id
	private String id;

	@NotNull
	private int userId;

	@NotBlank
	private String referenceId;

	@NotBlank
	private String status;

	@NotNull
	private Date createDate;

	private Date editDate;

	@NotBlank
	private String level;

	@NotBlank
	private String content;

	private String reason;

	private String reportTypeId;

	public Comment() {

	}

	public Comment(String id, int userId, String referenceId, String status, Date createDate, Date editDate, String level, String content, String reason, String reportTypeId) {
		this.id = id;
		this.userId = userId;
		this.referenceId = referenceId;
		this.status = status;
		this.createDate = createDate;
		this.editDate = editDate;
		this.level = level;
		this.content = content;
		this.reason = reason;
		this.reportTypeId = reportTypeId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getReferenceId() {
		return this.referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getEditDate() {
		return this.editDate;
	}

	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

	public String getLevel() {
		return this.level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReportTypeId() {
		return this.reportTypeId;
	}

	public void setReportTypeId(String reportTypeId) {
		this.reportTypeId = reportTypeId;
	}

	

}