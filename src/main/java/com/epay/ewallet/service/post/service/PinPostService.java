package com.epay.ewallet.service.post.service;

import com.epay.ewallet.service.post.model.User;
import com.epay.ewallet.service.post.payloads.request.PinPostRequest;
import com.epay.ewallet.service.post.payloads.request.PostDetailRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;

public interface PinPostService {

	CommonResponse<Object> pinPost(PinPostRequest request, User user, String requestId);

	CommonResponse<Object> validate(PinPostRequest request);
}
