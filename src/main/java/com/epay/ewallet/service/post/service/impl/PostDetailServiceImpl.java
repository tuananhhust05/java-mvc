package com.epay.ewallet.service.post.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epay.ewallet.service.post.constant.Constant;
import com.epay.ewallet.service.post.constant.EcodeConstant;
import com.epay.ewallet.service.post.constant.ServiceConstants;
import com.epay.ewallet.service.post.mapperDataOne.ICompany;
import com.epay.ewallet.service.post.mapperDataOne.IUser;
import com.epay.ewallet.service.post.model.Company;
import com.epay.ewallet.service.post.model.User;
import com.epay.ewallet.service.post.payloads.request.PostDetailRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;
import com.epay.ewallet.service.post.payloads.response.PostDetailResponse;
import com.epay.ewallet.service.post.payloads.response.Social;
import com.epay.ewallet.service.post.repository.CommentRepository;
import com.epay.ewallet.service.post.repository.PostDetailRepository;
import com.epay.ewallet.service.post.repository.RoleRepository;
import com.epay.ewallet.service.post.repository.UserSettingRepository;
import com.epay.ewallet.service.post.service.PostDetailService;
import com.epay.ewallet.service.post.utils.CommonUtils;
import com.epay.ewallet.service.post.utils.Validator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mongodb.BasicDBObject;

@Service
public class PostDetailServiceImpl implements PostDetailService {

	private static final Logger log = LogManager.getLogger(PostDetailServiceImpl.class);

	private Gson gson = new GsonBuilder().disableHtmlEscaping().create();

	@Autowired
	private PostDetailRepository postDetailRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private IUser userDao;

	@Autowired
	ICompany companyService;

	@Autowired
	UserSettingRepository userSettingRepository;

	/**
	 * getPostDetail
	 */
	@Override
	public CommonResponse<Object> getPostDetail(String requestId, String logCategory, String phone, String language,
			PostDetailRequest request) throws Exception {
		CommonResponse<Object> response = new CommonResponse<>();
		/******************************************
		 * Validate request
		 ******************************************/

		if (request == null || CommonUtils.isNullOrBlank(request.getPostId())) {
			log.error("{} | {} | Invalid postId", requestId, logCategory);
			response.setEcode(EcodeConstant.ERROR_INVALID_POST_ID);
			return response;
		}
		log.info("{} | {} | Validation done | request={}", requestId, logCategory, gson.toJson(request));

		if (CommonUtils.isNullOrBlank(phone)) {
			log.error("{} | {} | Invalid phone", requestId, logCategory);
			response.setEcode(EcodeConstant.ERROR_INVALID_PHONE);
			return response;
		}

		/******************************************
		 * Get user info
		 ******************************************/

		User onlineUser = userDao.getUserByPhone(phone);
		if (onlineUser == null) {
			log.error("{} | {} | User is not found | user={}", requestId, logCategory, gson.toJson(onlineUser));
			throw new Exception("User is not found");
		}
		log.info("{} | {} | Get user info done | phone={} | useIdr={}", requestId, logCategory, phone,
				gson.toJson(onlineUser));

		/******************************************
		 * Get post detail
		 ******************************************/
		try {
			BasicDBObject postDetail = postDetailRepository.postDetailById(request.getPostId());
			if (postDetail == null) {
				log.info("{} | {} | Get post detail from DB return null | postId={} | postDetail={}", requestId,
						logCategory, request.getPostId(), postDetail);
				response.setEcode(EcodeConstant.ERROR_NOT_EXIST_POST_ID);
				return response;
			}
			log.info("{} | {} | Get PostDetail is done | postId={} | postDetail={}", requestId, logCategory,
					request.getPostId(), postDetail.toJson());

			PostDetailResponse postResponse = buildResponse(postDetail, onlineUser);
			if (postResponse == null) {
				log.info("{} | {} | Convert postDetail to response data is failed | postId={} | postDetailResponse={}",
						requestId, logCategory, request.getPostId(), postResponse);
				response.setEcode(EcodeConstant.ERROR_NOT_EXIST_POST_ID);
				return response;
			} else {
				log.info("{} | {} | Convert postDetail to response data: | postId={} | postDetailResponse={}",
						requestId, logCategory, request.getPostId(), gson.toJson(postResponse));
				response.setData(postResponse);
				response.setEcode(EcodeConstant.SUCCESS);
				return response;
			}

		} catch (Exception e) {
			log.fatal("{} | {} | PostId: {} | Exception: {} | Get post detail is error = ", requestId, logCategory,
					request.getPostId(), e.getMessage(), e);
			response.setEcode(EcodeConstant.EXCEPTION);
			return response;
		}
	}

	@SuppressWarnings("unchecked")
	private PostDetailResponse buildResponse(BasicDBObject postDetail, User onlineUser) {
		try {
			String posterId = postDetail.getString("userId");
			String postStatus = Validator.isStrNull(postDetail.getString("status")) ? "" : postDetail.getString("status");
			//Check if post status is pending -> only show post detail if the online user is the owner of this post
			if (postStatus.equals(Constant.PENDING_STATUS)) {
				if (!posterId.equals(String.valueOf(onlineUser.getId()))) {
					log.info("{} | {} | Post Status = PENDING && OnlineUser is not the Poster -> don't show post detail to this user");
					return null;
				}
			} else if(!postStatus.equals(Constant.ACTIVE_STATUS)) {
				log.info("{} | {} | Post Status = is not ACTIVE -> don't show post detail");
				return null;
			}
			
			ArrayList<String> listImages = postDetail.get("images") == null ? new ArrayList<>() : (ArrayList<String>) postDetail.get("images");
			ArrayList<Long> tempTagList = postDetail.get("tagList") == null ? new ArrayList<>() : (ArrayList<Long>) postDetail.get("tagList");
			ArrayList<Document> tagList = new ArrayList<>();
			if(!tempTagList.isEmpty()) {
				Map<String, Object> map = new HashMap<>();
				map.put("userIds", tempTagList);
				List<User> taggedUsers = userDao.getListUserById(map);
				for (User u: taggedUsers) {
					if(u != null) {
						Document document = new Document();
						document.put("userId", u.getId());
						document.put("avatar", Validator.isStrNull(u.getAvatar()) ? "" : u.getAvatar());
		//				String tagName = (u.getNickname() == null || u.getNickname().isEmpty()) ? u.getName() : u.getName() + "(" + u.getNickname() + ")";
						document.put("name", Validator.isStrNull(u.getName()) ? "" : u.getName());
						document.put("position", Validator.isStrNull(u.getPosition()) ? "" : u.getPosition());
						tagList.add(document);
					}
				}
			}
			
			String role = (postDetail.getString("postByRole") == null || postDetail.getString("postByRole").isEmpty()) ? "" : postDetail.getString("postByRole");
			User poster = onlineUser;
			if (!posterId.equals(String.valueOf(onlineUser.getId()))) {
				poster = userDao.getUserById(String.valueOf(posterId));
			}
			String nickname = (poster.getNickname() == null || poster.getNickname().isEmpty()) ? "" : poster.getNickname();

			Document posterDoc = new Document();
			if (role.equals(ServiceConstants.POSTER_ROLE_ADMIN)
					|| role.equals(ServiceConstants.POSTER_ROLE_SUPER_ADMIN)) {
				Company company = companyService.getCompanyById(Long.valueOf(poster.getCompanyId()));
				String logoSocial = (company.getLogoSocial() == null || company.getLogoSocial().isEmpty()) ? "" : company.getLogoSocial();
				posterDoc.put("groupId", String.valueOf(company.getId()));
				posterDoc.put("userId", "");
				posterDoc.put("avatar", logoSocial);
				posterDoc.put("name", company.getShortName());
			} else if (role.equals(ServiceConstants.POSTER_ROLE_USER)) {
				// String displayPosterName = (poster.getNickname() == null ||
				// poster.getNickname().isEmpty()) ? poster.getName() : poster.getName() + "(" +
				// poster.getNickname() + ")";
				String displayPosterName = poster.getName();
				String avatar = (poster.getAvatar() == null || poster.getAvatar().isEmpty()) ? "" : poster.getAvatar();
				posterDoc.put("groupId", "");
				posterDoc.put("userId", posterId);
				posterDoc.put("avatar", avatar);
				posterDoc.put("name", displayPosterName);
			} else {
				return null;
			}

			ArrayList<Document> reactList = postDetail.get("reactList") == null ? new ArrayList<>()
					: (ArrayList<Document>) postDetail.get("reactList");
			boolean hasReacted = false;
			if (reactList.size() > 0) {
				Document check = new Document();
				check.put("userId", String.valueOf(onlineUser.getId()));
				check.put("referenceId", postDetail.getString("postId"));
				check.put("status", "1");
				if (reactList.contains(check)) {
					hasReacted = true;
				}
			}

			String content = (postDetail.getString("content") == null || postDetail.getString("content").isEmpty()) ? "" : postDetail.getString("content");

			String socialList = (postDetail.getString("contactList") == null || postDetail.getString("contactList").isEmpty()) ? "" : postDetail.getString("contactList");
			String contactPhone = (poster.getContactPhone() == null || poster.getContactPhone().isEmpty()) ? "" : poster.getContactPhone();
			ArrayList<Document> contactList = getContactList(socialList, contactPhone);

			PostDetailResponse postResponse = new PostDetailResponse();
			postResponse.setPostId(postDetail.getString("postId"));
			postResponse.setNickname(nickname);
			postResponse.setContent(content);
			postResponse.setImages(listImages.toArray(new String[listImages.size()]));
			postResponse.setPoster(posterDoc);
			postResponse.setPostedDate(postDetail.getString("postedDate"));
			postResponse.setTagCount(tagList.size());
			postResponse.setTagList(tagList.toArray(new Document[tagList.size()]));
			postResponse.setType(postDetail.getString("type"));
			postResponse.setLikeCount(reactList.size());
			postResponse.setHasReacted(hasReacted);
			postResponse.setContactList(contactList.toArray(new Document[contactList.size()]));
			postResponse.setPostByRole(role);
			if (postResponse.getType().equals(ServiceConstants.REQUEST_TYPE_MARKET)) {
				String category = (postDetail.getString("category") == null
						|| postDetail.getString("category").isEmpty()) ? "" : postDetail.getString("category");
				String categoryId = (postDetail.getString("categoryId") == null
						|| postDetail.getString("categoryId").isEmpty()) ? "" : postDetail.getString("categoryId");
				postResponse.setCategory(category);
				postResponse.setCategoryId(categoryId);
				long price = Long.parseLong(postDetail.getString("price"));
				postResponse.setPrice(price);
			}

			// Get total comment of the post
			List<BasicDBObject> listCmtIdObj = commentRepository.getListCommentIdByPostId(postResponse.getPostId());
			int totalComment = 0;
			if (listCmtIdObj != null && listCmtIdObj.size() > 0) {
				totalComment = listCmtIdObj.size();
				List<String> listCmtIdString = new ArrayList<>();
				listCmtIdObj.forEach(id -> listCmtIdString.add(id.getString("cmtId")));

				try {
					int totalReply = commentRepository.countReply(listCmtIdString);
					totalComment += totalReply;
				} catch (Exception e) {
					log.fatal("PostId: {} | Exception: {} | Count total comment reply is failed. Error = ",
							postResponse.getPostId(), e.getMessage(), e);
//					return null;
				}
			}
			postResponse.setCommentCount(totalComment);

			return postResponse;
		} catch (Exception e) {
			log.fatal("PostDetailService:buildResponse| Exception: {} |  error = ", e.getMessage(), e);
			return null;
		}
	}

	private ArrayList<Document> getContactList(String social, String contactPhone) {
		ArrayList<Document> contactArray = new ArrayList<>();
		try {
			Document contactPhoneItem = new Document();
			contactPhoneItem.put("contactType", "CONTACT_PHONE");
			contactPhoneItem.put("contactName", "contactPhoneNumber");
			contactPhoneItem.put("content", contactPhone);
			contactPhoneItem.put("socialLogo", "");
			contactArray.add(contactPhoneItem);

			Gson gson = new Gson();
			ArrayList<Social> socialList = gson.fromJson(social, new TypeToken<ArrayList<Social>>() {
			}.getType());
			if (!CommonUtils.isNullOrEmpty(socialList)) {
				for (Social item : socialList) {
					Document socialItem = new Document();
					socialItem.put("contactType", item.getContactType());
					socialItem.put("contactName", item.getContactName());
					socialItem.put("content", item.getContent());
					socialItem.put("socialLogo", item.getSocialLogo());
					contactArray.add(socialItem);
				}
			}
		} catch (Exception e) {
			log.fatal("PostDetailService:getContactList | Exception: {} |  error = ", e.getMessage(), e);
			return contactArray;
		}
		return contactArray;
	}

}
