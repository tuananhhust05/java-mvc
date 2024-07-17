package com.epay.ewallet.service.post.service;

import com.epay.ewallet.service.post.model.User;
import com.epay.ewallet.service.post.payloads.request.TagColleagueRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;

public interface TagColleagueService {
    
    CommonResponse<Object> tagColleague(TagColleagueRequest request, User user, String requestId);

    CommonResponse<Object> validate(TagColleagueRequest request);

    
}