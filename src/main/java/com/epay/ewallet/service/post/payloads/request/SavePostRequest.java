package com.epay.ewallet.service.post.payloads.request;

import lombok.Data;

@Data
public class SavePostRequest {
    private String postId;

    private String type;
}