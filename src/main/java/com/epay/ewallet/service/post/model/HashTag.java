package com.epay.ewallet.service.post.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

@Document(collection = "hashtags")
@QueryEntity
public class HashTag {

	@Id
	private String id;

	@NotNull
	private String postId;

	@NotNull
	private String tagcontent;

	@NotBlank
	private String status;

	@NotBlank
	private Date createDate;

	@NotNull
	private int seq;

	public HashTag() {

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
	 * @return the postId
	 */
	public String getPostId() {
		return postId;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(String postId) {
		this.postId = postId;
	}

	/**
	 * @return the tagcontent
	 */
	public String getTagcontent() {
		return tagcontent;
	}

	/**
	 * @param tagcontent the tagcontent to set
	 */
	public void setTagcontent(String tagcontent) {
		this.tagcontent = tagcontent;
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
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	public HashTag(String id, @NotNull String postId, @NotNull String tagcontent, @NotBlank String status,
			@NotBlank Date createDate, @NotNull int seq) {
		super();
		this.id = id;
		this.postId = postId;
		this.tagcontent = tagcontent;
		this.status = status;
		this.createDate = createDate;
		this.seq = seq;
	}

}