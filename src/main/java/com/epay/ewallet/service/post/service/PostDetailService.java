package com.epay.ewallet.service.post.service;

import com.epay.ewallet.service.post.payloads.request.PostDetailRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;

public interface PostDetailService {

	CommonResponse<Object> getPostDetail(String requestId, String logCategory, String phone, String language,
			PostDetailRequest request) throws Exception;

//	CommonResponse<Object> getPostDetail(String requestId, String logCategory, String phone, String language,
//			PostDetailRequest request) throws Exception;

}
