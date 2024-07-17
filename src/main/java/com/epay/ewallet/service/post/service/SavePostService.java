package com.epay.ewallet.service.post.service;

import com.epay.ewallet.service.post.model.User;
import com.epay.ewallet.service.post.payloads.request.SavePostRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;

public interface SavePostService {
	CommonResponse<Object> toggleSave(SavePostRequest request, User user, String requestId);

	CommonResponse<Object> validate(SavePostRequest request);
}
