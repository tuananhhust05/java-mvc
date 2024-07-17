package com.epay.ewallet.service.post.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

@Document(collection = "media")
@QueryEntity
public class Media {

	@Id
	private String id;

	@NotNull
	private String referenceId;

	@NotNull
	private String mediaUrl;

	@NotBlank
	private String status;

	private String type;

	private int seq;

	public Media() {

	}

	public Media(String id, String referenceId, String mediaUrl, String status, String type, int seq) {
		this.id = id;
		this.referenceId = referenceId;
		this.status = status;
		this.mediaUrl = mediaUrl;
		this.type = type;
		this.seq = seq;
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
	 * @return the objectId
	 */
	public String getReferenceId() {
		return referenceId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	/**
	 * @return the mediaUrl
	 */
	public String getMediaUrl() {
		return mediaUrl;
	}

	/**
	 * @param mediaUrl the mediaUrl to set
	 */
	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
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

}