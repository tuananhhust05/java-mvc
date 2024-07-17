package com.epay.ewallet.service.post.model;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.querydsl.core.annotations.QueryEntity;

@Document(collection = "report_temp")
@QueryEntity
public class ReportTemp {

	@Id
	private String id;

	@NotBlank
	private String contentVN;

	@NotBlank
	private String contentEN;

	@NotBlank
	private String contentKR;

	@NotBlank
	private String status;

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
	 * @return the contentVN
	 */
	public String getContentVN() {
		return contentVN;
	}

	/**
	 * @param contentVN the contentVN to set
	 */
	public void setContentVN(String contentVN) {
		this.contentVN = contentVN;
	}

	/**
	 * @return the contentEN
	 */
	public String getContentEN() {
		return contentEN;
	}

	/**
	 * @param contentEN the contentEN to set
	 */
	public void setContentEN(String contentEN) {
		this.contentEN = contentEN;
	}

	/**
	 * @return the contentKR
	 */
	public String getContentKR() {
		return contentKR;
	}

	/**
	 * @param contentKR the contentKR to set
	 */
	public void setContentKR(String contentKR) {
		this.contentKR = contentKR;
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

	public ReportTemp(String id, @NotBlank String contentVN, @NotBlank String contentEN, @NotBlank String contentKR,
			@NotBlank String status) {
		super();
		this.id = id;
		this.contentVN = contentVN;
		this.contentEN = contentEN;
		this.contentKR = contentKR;
		this.status = status;
	}

}