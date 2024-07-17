package com.epay.ewallet.service.post.payloads.request;

import java.util.Date;

import lombok.Data;

@Data
public class PinPostRequest {
    private String postId;
    private Date fromDate;
    private Date toDate;
}