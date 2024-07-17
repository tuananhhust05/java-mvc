package com.epay.ewallet.service.post.model;

import java.util.Date;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "report_temp")
public class Report {

	@Id
	private String id;

	private String content;

	@NotBlank
	private String userId;

	@NotBlank
	private String report_temp;

	@NotBlank
	private String referenceId;

	@NotBlank
	private String type;

	@NotBlank
	private String status;

	@NotBlank
	private String isRead;

	@NotNull
	private Date createDate;

	@NotNull
	private Date editDate;

	public Report() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReport_temp() {
		return this.report_temp;
	}

	public void setReport_temp(String report_temp) {
		this.report_temp = report_temp;
	}

	public String getReferenceId() {
		return this.referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsRead() {
		return this.isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
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

	

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Report(String id, String content, String userId, String report_temp, String referenceId, String type, String status, String isRead, Date createDate, Date editDate) {
		this.id = id;
		this.content = content;
		this.userId = userId;
		this.report_temp = report_temp;
		this.referenceId = referenceId;
		this.type = type;
		this.status = status;
		this.isRead = isRead;
		this.createDate = createDate;
		this.editDate = editDate;
	}


}
