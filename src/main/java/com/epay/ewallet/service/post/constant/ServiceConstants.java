package com.epay.ewallet.service.post.constant;

public class ServiceConstants {
	public static final String LOG_ADMIN_PIN = "GET_ADMIN_PIN_POST";
	public static final String LOG_NEWEST_POST = "GET_NEWEST_POSTS";
	public static final String LOG_GET_MY_POST = "GET_MY_POST";
	public static final String LOG_GET_COMPANY_POST = "GET_COMPANY_POST";
	
	//Constants for NewsfeedServiceImpl's Request and Response:
	public static final String REQUEST_TYPE_ALL = "ALL";
	public static final String REQUEST_TYPE_MARKET = "MARKET";
	public static final String REQUEST_TYPE_POST = "POST";
	public static final String[] TYPE_ALL = {"POST", "MARKET"};
	public static final String[] TYPE_MARKET = {"MARKET"};
	public static final String[] TYPE_POST = {"POST"};
	public static final int PAGE_SIZE = 10;
	public static final int CONTENT_LENGTH_DEFAULT = 200; 
	public static final String POST_STT_ACT = "ACTIVE";
	public static final String POST_STT_PEND = "PENDING";
	public static final String POSTER_ROLE_ADMIN = "ADMIN";
	public static final String POSTER_ROLE_SUPER_ADMIN = "SUPERADMIN";
	public static final String POSTER_ROLE_USER = "USER";
	
	public static final String[] POST_STATUS_A = {"ACTIVE"};
	public static final String[] POST_STATUS_A_P = {"ACTIVE", "PENDING"};
}
