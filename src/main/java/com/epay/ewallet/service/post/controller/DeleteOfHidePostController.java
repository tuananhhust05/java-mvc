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
import com.epay.ewallet.service.post.payloads.request.DeleteOfHidePostRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;
import com.epay.ewallet.service.post.service.CodeService;
import com.epay.ewallet.service.post.service.DeleteOrHidePostService;
import com.epay.ewallet.service.post.utils.DecodeDataUtil;
import com.epay.ewallet.service.post.utils.Utils;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;

@RestController
@EnableAutoConfiguration
public class DeleteOfHidePostController {
	private static final Logger log = LogManager.getLogger(DeleteOfHidePostController.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CodeService codeService;

	@Autowired
	DeleteOrHidePostService deleteOrHidePostService;

	@Autowired
	DecodeDataUtil encryptService;

	@ResponseBody
	@RequestMapping(value = "/deleteOrHidePost", method = RequestMethod.POST)
	public CommonResponse<Object> deleteOrHidePost(@RequestBody JsonNode requestBody,
			@RequestHeader Map<String, String> header,
			@RequestParam(required = false, defaultValue = "true") boolean encrypted) {

		String logCategory = FunctionConstant.DELETE_OR_HIDE_POST;
		Gson gson = new Gson();

		String requestId = header.get("requestid");
		String language = header.get("language");
		String deviceId = Utils.getDeviceIdFromHeader(header);

		DeleteOfHidePostRequest request = encryptService.getRequest(requestId, logCategory, requestBody,
				DeleteOfHidePostRequest.class, encrypted, deviceId);

		log.info("{} | {} | Start | header={} | request={} | encrypted={}", requestId, logCategory, gson.toJson(header),
				gson.toJson(request), encrypted);

		CommonResponse<Object> response = new CommonResponse<Object>();
		try {
			String bearerToken = header.get("authorization");
			String token = jwtTokenUtil.getTokenFromBearerToken(bearerToken);
			String phone = jwtTokenUtil.getUsernameFromToken(token);
			response = deleteOrHidePostService.deleteOrHidePost(requestId, logCategory, phone, language, request);

			// Jump to finally code block before return
			return response;

		} catch (Exception e) {
			log.fatal("{} | {} | Exception | error={}", requestId, logCategory, e);
			e.printStackTrace();

			response.setEcode(EcodeConstant.EXCEPTION);

			// Jump to finally code block before return
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
				log.info("{} | {} | Create raw response done | rawResponse={}", requestId, logCategory,
						gson.toJson(response));

				String encryptedData = encryptService.encrypt(requestId, logCategory, deviceId, response.getData());
				response.setData(encryptedData);
			}

			log.info("{} | {} | End | response={}", requestId, logCategory, gson.toJson(response));
		}
	}
}