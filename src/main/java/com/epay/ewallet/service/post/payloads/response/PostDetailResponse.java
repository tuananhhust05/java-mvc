package com.epay.ewallet.service.post.payloads.response;

import org.bson.Document;

import lombok.Data;

@Data
public class PostDetailResponse {

	private String postId;
	private String nickname;
	private String postedDate;
	private String content;
	private String type;
	private String categoryId;
	private String category;
	private long price;
	private int likeCount;
	private int commentCount;
	private String[] images;
	private int tagCount;
	private Document[] tagList;
	private Document[] contactList;
	private Document poster;
	private String postByRole;
	private boolean hasReacted;

}
