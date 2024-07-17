package com.epay.ewallet.service.post.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

@Document(collection = "roles")
@QueryEntity
public class Role {

	@Id
	private int id;

	@NotNull
	private String name;

	private String description;

	@NotBlank
	private String status;

	@NotNull
	private Date createDate;

	private Date editDate;

	public Role() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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

	public Role(int id, @NotNull String name, String description, @NotBlank String status, @NotNull Date createDate,
			Date editDate) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.status = status;
		this.createDate = createDate;
		this.editDate = editDate;
	}

}