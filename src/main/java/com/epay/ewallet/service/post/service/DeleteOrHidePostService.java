package com.epay.ewallet.service.post.service;

import com.epay.ewallet.service.post.payloads.request.DeleteOfHidePostRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;

public interface DeleteOrHidePostService {

	CommonResponse<Object> deleteOrHidePost(String requestId, String logCategory, String phone, String language,
			DeleteOfHidePostRequest request) throws Exception;

}
