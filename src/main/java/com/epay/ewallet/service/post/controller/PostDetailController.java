package com.epay.ewallet.service.post.controller;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.epay.ewallet.service.post.authen.JwtTokenUtil;
import com.epay.ewallet.service.post.constant.EcodeConstant;
import com.epay.ewallet.service.post.constant.FunctionConstant;
import com.epay.ewallet.service.post.model.Ecode;
import com.epay.ewallet.service.post.payloads.request.PostDetailRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;
import com.epay.ewallet.service.post.service.CodeService;
import com.epay.ewallet.service.post.service.PostDetailService;
import com.epay.ewallet.service.post.utils.DecodeDataUtil;
import com.epay.ewallet.service.post.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

@RestController
@EnableAutoConfiguration
public class PostDetailController {
	private static final Logger log = LogManager.getLogger(PostDetailController.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CodeService codeService;

	@Autowired
	PostDetailService postDetailService;

	@Autowired
	DecodeDataUtil encryptService;

	@ResponseBody
	@RequestMapping(value = "/getPostDetail", method = RequestMethod.POST)
	public CommonResponse<Object> getPostDetail(@RequestBody JsonNode requestBody,
			@RequestHeader Map<String, String> header,
			@RequestParam(required = false, defaultValue = "true") boolean encrypted) {
		Gson gson = new Gson();
		log.info("-------> Request {}", requestBody); // Log coming request info
		log.info("-------> Headers {}", gson.toJson(header));
		log.info("-------> Encrypted {}", encrypted);

		String requestId = header.get("requestid");
		String language = header.get("language");
		String deviceId = Utils.getDeviceIdFromHeader(header);

		PostDetailRequest request = encryptService.getRequest(requestId, FunctionConstant.GET_POST_DETAIL, requestBody,
				PostDetailRequest.class, encrypted, deviceId);

		CommonResponse<Object> response = new CommonResponse<Object>();
		try {
			String bearerToken = header.get("authorization");
			log.info("-------> bearerToken {}", bearerToken);
			String token = jwtTokenUtil.getTokenFromBearerToken(bearerToken);
			log.info("-------> token {}", token);
			String phone = jwtTokenUtil.getUsernameFromToken(token);
			log.info("-------> phone {}", phone);

			response = postDetailService.getPostDetail(requestId, FunctionConstant.GET_POST_DETAIL, phone, language,
					request);
			return response;
		} catch (Exception e) {
			log.fatal("{} | {} | Exception {} | error = ", requestId, FunctionConstant.GET_POST_DETAIL, e.getMessage(),
					e);
			response.setEcode(EcodeConstant.EXCEPTION);
			return response;
		} finally {
			/**
			 * Actions before return
			 */
			if (response.getMessage() == null || response.getMessage().isEmpty() == true) {
				// Set ecode message, p_ecode, p_message
				Ecode ecode = codeService.getEcode(response.getEcode(), language);
				response.setMessage(ecode.getMessage());
				response.setP_ecode(ecode.getP_ecode());
				response.setP_message(ecode.getP_message());
			}

			/**
			 * Encrypt data
			 */
			if (encrypted == true) {
				String encryptedData = encryptService.encrypt(requestId, FunctionConstant.GET_POST_DETAIL, deviceId,
						response.getData());
				response.setData(encryptedData);
			}

			log.info("{} | {} | End | response={}", requestId, FunctionConstant.GET_POST_DETAIL, gson.toJson(response));
		}
	}

}