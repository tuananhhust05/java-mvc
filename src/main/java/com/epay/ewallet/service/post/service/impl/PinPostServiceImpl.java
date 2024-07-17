package com.epay.ewallet.service.post.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epay.ewallet.service.post.constant.EcodeConstant;
import com.epay.ewallet.service.post.model.PinPost;
import com.epay.ewallet.service.post.model.Post;
import com.epay.ewallet.service.post.model.Role;
import com.epay.ewallet.service.post.model.User;
import com.epay.ewallet.service.post.model.UserGroup;
import com.epay.ewallet.service.post.payloads.request.PinPostRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;
import com.epay.ewallet.service.post.repository.PinPostRepository;
import com.epay.ewallet.service.post.repository.PostDetailRepository;
import com.epay.ewallet.service.post.repository.RoleRepository;
import com.epay.ewallet.service.post.repository.UserGroupRepository;
import com.epay.ewallet.service.post.service.CodeService;
import com.epay.ewallet.service.post.service.PinPostService;
import com.epay.ewallet.service.post.utils.CommonUtils;

@Service
public class PinPostServiceImpl implements PinPostService {

	private static final Logger log = LogManager.getLogger(PostDetailServiceImpl.class);

	@Autowired
	private PostDetailRepository postDetailRepository;

	@Autowired
	private UserGroupRepository userGroupRepository;

	@Autowired
	private PinPostRepository pinPostRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private CodeService code;


	@Override
	public CommonResponse<Object> pinPost(PinPostRequest request, User user, String requestId) {
		// TODO Auto-generated method stub
		CommonResponse<Object> response = new CommonResponse<>();
		log.info("comment service start...");

		response.setEcode(EcodeConstant.SUCCESS);
		response.setMessage(EcodeConstant.SUCCESS_MSG);
		response.setP_ecode(EcodeConstant.SUCCESS);
		response.setP_message(EcodeConstant.SUCCESS_MSG);

		CommonResponse<Object> validate_result = validate(request);
		if (!validate_result.getEcode().equals(EcodeConstant.SUCCESS)) {
			response.setEcode(validate_result.getEcode());
			response.setMessage(code.getMessageByCode(user.getLang(), validate_result.getEcode()));
			response.setP_ecode(EcodeConstant.ERR);
			response.setP_message(EcodeConstant.ERR_MSG);
			return response;
		}

		Post _post = postDetailRepository.findByPostId(request.getPostId());
		PinPost _checkPinPostexist = pinPostRepository.findByReferenceId(request.getPostId());
		List<UserGroup> _userGroup = userGroupRepository.findRoleById(String.valueOf(user.getId()));
		ArrayList<String> userGroupArray = new ArrayList<>();
		if (!CommonUtils.isNullOrEmpty(_userGroup)) {
			for (UserGroup item : _userGroup) {
				userGroupArray.add(getRoleName(item.getRoleId()));
			}
		}

		if (_post == null) {
			// log.info("ERROR");
			// return new CommonResponse<>();
			// không tồn tại bài post
			response.setEcode(EcodeConstant.ERROR_NOT_EXIST_POST_ID);
			response.setMessage(code.getEcode(response.getEcode(), user.getLang()).getMessage());
			response.setP_message(code.getEcode(response.getEcode(), user.getLang()).getP_message());
			response.setP_ecode(code.getEcode(response.getEcode(), user.getLang()).getP_ecode());

			return response;

		} else {
				if(_checkPinPostexist != null && _checkPinPostexist.getUserId().equals(String.valueOf(user.getId()))){
				PinPost _pinPost = new PinPost();
					_pinPost = _checkPinPostexist;
					_pinPost.setFromDate(request.getFromDate());
					_pinPost.setToDate(request.getToDate());
					try {
						PinPost save_response = pinPostRepository.save(_pinPost);
						log.info("Save response {}", save_response.toString());
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}else{
					final String uuid = UUID.randomUUID().toString().replace("-", "");
					System.out.println("uuid" + uuid);
					PinPost _pinPost = new PinPost();
						_pinPost.setReferenceId(_post.getId());
						_pinPost.setId("pin_" + uuid);
						_pinPost.setUserId(String.valueOf(user.getId()));
						_pinPost.setCheckPage("INGROUP");
						_pinPost.setGroupId(user.getCompanyId());
						_pinPost.setStatus("1");
							if (userGroupArray.contains("ADMIN") || userGroupArray.contains("SUPERADMIN")) {
								_pinPost.setAdmin(true);
							} else {
								_pinPost.setAdmin(false);
							}
							_pinPost.setFromDate(request.getFromDate());
							_pinPost.setToDate(request.getToDate());
							try {
								PinPost save_response = pinPostRepository.save(_pinPost);
								log.info("Save response {}", save_response.toString());
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}
					}
				}
			// TODO Auto-generated method stub
			log.info("comment service End.");
			return response;
	}

	@Override
	public CommonResponse<Object> validate(PinPostRequest request) {
		// TODO Auto-generated method stub
		CommonResponse<Object> response = new CommonResponse<>();
		response.setEcode(EcodeConstant.SUCCESS);
		log.info("Validate start.......");

		response.setMessage(EcodeConstant.SUCCESS_MSG);
		response.setP_ecode(EcodeConstant.SUCCESS);
		response.setP_message(EcodeConstant.SUCCESS_MSG);

		if (request.getPostId() == null || request.getPostId().trim().length() < 1) {
			response.setEcode(EcodeConstant.REF_ID_NULL_EMPTY);
		}
		if (request.getFromDate() == null || request.getToDate().before(request.getFromDate())) {
			response.setEcode(EcodeConstant.REF_ID_NULL_EMPTY);
		}

		if (request.getToDate().before(new Date()) || request.getToDate() == null) {
			response.setEcode(EcodeConstant.REF_ID_NULL_EMPTY);
		}

		log.info("Validate end.");

		return response;
	}

	private String getRoleName(int roleId) {
		String roleName = "";
		Role role = roleRepository.findRoleNameById(roleId);
		if (role != null) {
			roleName = role.getName();
		}
		return roleName;
	}

}
