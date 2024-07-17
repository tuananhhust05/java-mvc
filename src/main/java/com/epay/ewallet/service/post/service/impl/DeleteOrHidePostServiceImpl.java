package com.epay.ewallet.service.post.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epay.ewallet.service.post.constant.Constant;
import com.epay.ewallet.service.post.constant.EcodeConstant;
import com.epay.ewallet.service.post.constant.FunctionConstant;
import com.epay.ewallet.service.post.mapperDataOne.IUser;
import com.epay.ewallet.service.post.model.Comment;
import com.epay.ewallet.service.post.model.Post;
import com.epay.ewallet.service.post.model.ReportTemp;
import com.epay.ewallet.service.post.model.Role;
import com.epay.ewallet.service.post.model.User;
import com.epay.ewallet.service.post.model.UserGroup;
import com.epay.ewallet.service.post.payloads.request.DeleteOfHidePostRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;
import com.epay.ewallet.service.post.repository.CategoryRepository;
import com.epay.ewallet.service.post.repository.CommentRepository;
import com.epay.ewallet.service.post.repository.HashTagRepository;
import com.epay.ewallet.service.post.repository.MediaRepository;
import com.epay.ewallet.service.post.repository.PostDetailRepository;
import com.epay.ewallet.service.post.repository.ReactRepository;
import com.epay.ewallet.service.post.repository.ReportTempRepository;
import com.epay.ewallet.service.post.repository.RoleRepository;
import com.epay.ewallet.service.post.repository.TagRepository;
import com.epay.ewallet.service.post.repository.UserGroupRepository;
import com.epay.ewallet.service.post.service.DeleteOrHidePostService;
import com.epay.ewallet.service.post.service.UserService;
import com.epay.ewallet.service.post.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class DeleteOrHidePostServiceImpl implements DeleteOrHidePostService {

	private static final Logger log = LogManager.getLogger(DeleteOrHidePostServiceImpl.class);

	private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

	@Autowired
	private UserService userService;

	@Autowired
	private PostDetailRepository postDetailRepository;

	@Autowired
	private TagRepository tagRepository;

	@Autowired
	private HashTagRepository hashTagRepository;

	@Autowired
	private MediaRepository mediaRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReactRepository reactRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private IUser userDao;

	private static final DateFormat df = new SimpleDateFormat(Constant.YYYY_MM_DD_HH_MM_SS);

	@Autowired
	private ReportTempRepository reportTempRepository;

	@Autowired
	UserGroupRepository userGroupRepository;

	@Autowired
	RoleRepository roleRepository;

	/**
	 * deleteOrHidePost
	 */
	@Override
	public CommonResponse<Object> deleteOrHidePost(String requestId, String logCategory, String phone, String language,
			DeleteOfHidePostRequest request) throws Exception {
		CommonResponse<Object> response = new CommonResponse<>();
		LinkedHashMap<String, Object> data = new LinkedHashMap<>();

		/******************************************
		 * Validate request
		 ******************************************/

		if (request == null || CommonUtils.isNullOrBlank(request.getObjectId())) {
			log.error("{} | {} | Invalid ObjectId", requestId, logCategory);
			response.setEcode(EcodeConstant.ERROR_INVALID_OBJECT_ID);
			return response;
		}
		String objectId = request.getObjectId();

		if (request == null || CommonUtils.isNullOrBlank(request.getObjectType())) {
			log.error("{} | {} | Invalid ObjectType", requestId, logCategory);
			response.setEcode(EcodeConstant.ERROR_INVALID_OBJECT_TYPE);
			return response;
		}
		String objectType = request.getObjectType();

		if (request == null || CommonUtils.isNullOrBlank(request.getFunction())) {
			log.error("{} | {} | Invalid getFunction", requestId, logCategory);
			response.setEcode(EcodeConstant.ERROR_INVALID_FUNCTION);
			return response;
		}
		String function = request.getFunction();

		log.info("{} | {} | Validation done | request={}", requestId, logCategory, gson.toJson(request));

		if (CommonUtils.isNullOrBlank(phone)) {
			log.error("{} | {} | Invalid phone", requestId, logCategory);
			response.setEcode(EcodeConstant.ERROR_INVALID_PHONE);
			return response;
		}

		/******************************************
		 * Get user info
		 ******************************************/

		User user = userService.getUser(phone);
		long userId = 0;
		String companyId = "";
		{
			log.info("{} | {} | Get user info done | phone={} | useIdr={}", requestId, logCategory, phone,
					gson.toJson(user));
			if (user == null) {
				log.error("{} | {} | User is not found | user={}", requestId, logCategory, gson.toJson(user));
				throw new Exception("User is not found");
			}

			userId = user.getId();
			if (userId == 0) {
				log.error("{} | {} | Invalid userId | userId={}", requestId, logCategory, userId);
				throw new Exception("Invalid userId");
			}

			companyId = user.getCompanyId();
			if (CommonUtils.isNullOrBlank(companyId)) {
				log.error("{} | {} | Invalid companyId | companyId={}", requestId, logCategory, companyId);

				response.setEcode(EcodeConstant.ERROR_NO_COMPANY_INFO);
				return response;
			}
		}

		/******************************************
		 * Delete of hide or remove post
		 ******************************************/
		Boolean result = false;
		if (objectType.equalsIgnoreCase(FunctionConstant.POST)) {
			/******************************************
			 * Get post detail
			 ******************************************/
			Post post = postDetailRepository.findByPostId(objectId);
			if (post != null) {
				// Remove
				if (function.equalsIgnoreCase(FunctionConstant.REMOVE)) {
					if (request == null || CommonUtils.isNullOrBlank(request.getReportTypeId())) {
						log.error("{} | {} | Invalid ReportTypeId", requestId, logCategory);
						response.setEcode(EcodeConstant.ERROR_INVALID_REPORT_TYPE_ID);
						return response;
					}
					String reportTypeId = request.getReportTypeId().trim();
					String content = request.getContent();
					if (!reportTypeId.equalsIgnoreCase(Constant.OTHER)) {
						ReportTemp reportTemp = reportTempRepository.findReportTempByReportTypeId(reportTypeId,
								Constant.ACTIVE);
						content = reportTemp.getContentEN();
						if (language.equalsIgnoreCase(Constant.LANGUAGE_VN)) {
							content = reportTemp.getContentVN();
						} else if (language.equalsIgnoreCase(Constant.LANGUAGE_KR)) {
							content = reportTemp.getContentKR();
						}
					}
					UserGroup userGroup = userGroupRepository.findRoleByUserId(String.valueOf(userId), companyId);
					if (userGroup != null) {
						log.info("{} | {} | Get userGroup done | UserId={}", requestId, logCategory, data.get("id"),
								gson.toJson(userGroup));
						if (getRoleName(userGroup.getRoleId()).equalsIgnoreCase("ADMIN")
								|| getRoleName(userGroup.getRoleId()).equalsIgnoreCase("SUPERADMIN")) {
							Post haveBeenRemove = postDetailRepository.findByStatus(objectId, Constant.REMOVE_STATUS);
							if (haveBeenRemove == null) {
								post.setStatus("REMOVE");
								post.setReason(content);
								post.setReportTypeId(reportTypeId);
								postDetailRepository.save(post);
								result = true;
							} else {
								result = false;
								data.put("result", result);
								response.setEcode(EcodeConstant.ERROR_POSTS_HAVE_BEEN_REMOVE);
								response.setData(data);
								return response;
							}
						} else {
							result = false;
							data.put("result", result);
							response.setEcode(EcodeConstant.USER_IS_NOT_ADMIN);
							response.setData(data);
							return response;
						}
					} else {
						result = false;
						data.put("result", result);
						response.setEcode(EcodeConstant.USER_IS_NOT_ADMIN);
						response.setData(data);
						return response;
					}
				} else if (function.equalsIgnoreCase(FunctionConstant.HIDE)) {
					if (post.getUserId().equalsIgnoreCase(String.valueOf(userId))) {
						Post haveBeenHide = postDetailRepository.findByUserIdAndStatus(objectId, Constant.HIDE_STATUS, userId);
						if (haveBeenHide == null) {
							post.setStatus("HIDE");
							postDetailRepository.save(post);
							result = true;
						} else {
							result = false;
							data.put("result", result);
							response.setEcode(EcodeConstant.ERROR_POSTS_HAVE_BEEN_HIDE);
							response.setData(data);
							return response;
						}
					} else {
						result = false;
						data.put("result", result);
						response.setEcode(EcodeConstant.USER_IS_NOT_OWNER);
						response.setData(data);
						return response;
					}
				} else if (function.equalsIgnoreCase(FunctionConstant.DELETE)) {
					if (post.getUserId().equalsIgnoreCase(String.valueOf(userId))) {
						Post haveBeenDelete = postDetailRepository.findByUserIdAndStatus(objectId,
								Constant.DELETE_STATUS, userId);
						if (haveBeenDelete == null) {
							post.setStatus("DELETE");
							postDetailRepository.save(post);
							result = true;
						} else {
							result = false;
							data.put("result", result);
							response.setEcode(EcodeConstant.ERROR_POSTS_HAVE_BEEN_DELETE);
							response.setData(data);
							return response;
						}
					} else {
						result = false;
						data.put("result", result);
						response.setEcode(EcodeConstant.USER_IS_NOT_OWNER);
						response.setData(data);
						return response;
					}
				} else if (function.equalsIgnoreCase(FunctionConstant.UNHIDE)) {
					if (post.getUserId().equalsIgnoreCase(String.valueOf(userId))) {
						Post haveBeenUnhide = postDetailRepository.findByUserIdAndStatus(objectId,
								Constant.UNHIDE_STATUS, userId);
						if (haveBeenUnhide == null) {
							post.setStatus("ACTIVE");
							postDetailRepository.save(post);
							result = true;
						} else {
							result = false;
							data.put("result", result);
							response.setEcode(EcodeConstant.ERROR_POSTS_HAVE_BEEN_UNHIDE);
							response.setData(data);
							return response;
						}
					} else {
						result = false;
						data.put("result", result);
						response.setEcode(EcodeConstant.USER_IS_NOT_OWNER);
						response.setData(data);
						return response;
					}
				} else {
					result = false;
					data.put("result", result);
					response.setEcode(EcodeConstant.ERROR_INVALID_FUNCTION);
					response.setData(data);
					return response;
				}
			} else {
				result = false;
				response.setEcode(EcodeConstant.ERROR_INVALID_POST);
				response.setData(data);
				return response;
			}

		}

		/******************************************
		 * Delete of hide or remove comment
		 ******************************************/
		else if (objectType.equalsIgnoreCase(FunctionConstant.COMMENT)) {
			/******************************************
			 * Get comment detail
			 ******************************************/
			Comment comment = commentRepository.findByCommentId(objectId);
			if (comment != null) {
				if (function.equalsIgnoreCase(FunctionConstant.REMOVE)) {
					if (request == null || CommonUtils.isNullOrBlank(request.getReportTypeId())) {
						log.error("{} | {} | Invalid ReportTypeId", requestId, logCategory);
						response.setEcode(EcodeConstant.ERROR_INVALID_REPORT_TYPE_ID);
						return response;
					}
					String reportTypeId = request.getReportTypeId().trim();
					String content = request.getContent();
					if (!reportTypeId.equalsIgnoreCase(Constant.OTHER)) {
						ReportTemp reportTemp = reportTempRepository.findReportTempByReportTypeId(reportTypeId,
								Constant.ACTIVE);
						content = reportTemp.getContentEN();
						if (language.equalsIgnoreCase(Constant.LANGUAGE_VN)) {
							content = reportTemp.getContentVN();
						} else if (language.equalsIgnoreCase(Constant.LANGUAGE_KR)) {
							content = reportTemp.getContentKR();
						}
					}

					UserGroup userGroup = userGroupRepository.findRoleByUserId(String.valueOf(userId), companyId);

					if (userGroup != null) {
						log.info("{} | {} | Get userGroup done | UserId={}", requestId, logCategory, data.get("id"),
								gson.toJson(userGroup));
						if (getRoleName(userGroup.getRoleId()).equalsIgnoreCase("ADMIN")
								|| getRoleName(userGroup.getRoleId()).equalsIgnoreCase("SUPERADMIN")) {
							Comment haveBeenRemove = commentRepository.findCommentsByIdAndStatus(objectId,
									Constant.REMOVE_STATUS);
							if (haveBeenRemove == null) {
								comment.setStatus("REMOVE");
								comment.setReason(content);
								comment.setReportTypeId(reportTypeId);
								commentRepository.save(comment);
								result = true;
							} else {
								result = false;
								data.put("result", result);
								response.setEcode(EcodeConstant.ERROR_COMMENTS_HAVE_BEEN_REMOVE);
								response.setData(data);
								return response;
							}
						} else {
							result = false;
							data.put("result", result);
							response.setEcode(EcodeConstant.USER_IS_NOT_ADMIN);
							response.setData(data);
							return response;
						}
					} else {
						result = false;
						data.put("result", result);
						response.setEcode(EcodeConstant.USER_IS_NOT_ADMIN);
						response.setData(data);
						return response;
					}
				} else if (function.equalsIgnoreCase(FunctionConstant.DELETE)) {
					if (userId == comment.getUserId()) {
						Comment haveBeenDelete = commentRepository.findCommentsByIdAndStatusAndUserId(objectId, Constant.DELETE_STATUS, userId);
						if (haveBeenDelete == null) {
							comment.setStatus("DELETE");
							commentRepository.save(comment);
							result = true;
						} else {
							result = false;
							data.put("result", result);
							response.setEcode(EcodeConstant.ERROR_COMMENTS_HAVE_BEEN_DELETE);
							response.setData(data);
							return response;
						}
					} else {
						result = false;
						data.put("result", result);
						response.setEcode(EcodeConstant.USER_IS_NOT_OWNER);
						response.setData(data);
						return response;
					}
				} else {
					result = false;
					data.put("result", result);
					response.setEcode(EcodeConstant.ERROR_INVALID_FUNCTION);
					response.setData(data);
					return response;
				}
			} else {
				result = false;
				data.put("result", result);
				response.setEcode(EcodeConstant.ERROR_INVALID_COMMENT);
				response.setData(data);
				return response;
			}

		} else {
			result = false;
			data.put("result", result);
			response.setEcode(EcodeConstant.ERROR_INVALID_FUNCTION);
			response.setData(data);
			return response;
		}
		data.put("result", result);
		response.setEcode(EcodeConstant.SUCCESS);
		response.setData(data);
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
