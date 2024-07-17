package com.epay.ewallet.service.post.payloads.request;

import lombok.Data;

@Data
public class DeleteOfHidePostRequest {
	private String objectId;
	private String objectType;
	private String function;
	private String reportTypeId;
	private String content;

}
