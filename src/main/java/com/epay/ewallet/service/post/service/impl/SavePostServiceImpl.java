package com.epay.ewallet.service.post.service.impl;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epay.ewallet.service.post.constant.EcodeConstant;
import com.epay.ewallet.service.post.model.Post;
import com.epay.ewallet.service.post.model.SavedPost;
import com.epay.ewallet.service.post.model.User;
import com.epay.ewallet.service.post.payloads.request.SavePostRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;
import com.epay.ewallet.service.post.repository.PostDetailRepository;
import com.epay.ewallet.service.post.repository.SavedPostRepository;
import com.epay.ewallet.service.post.service.CodeService;
import com.epay.ewallet.service.post.service.SavePostService;

@Service
public class SavePostServiceImpl implements SavePostService {

	private static final Logger log = LogManager.getLogger(PostDetailServiceImpl.class);

	@Autowired
	private PostDetailRepository postDetailRepository;
	
	@Autowired
	private SavedPostRepository savedPostRepository;

	@Autowired
  private CodeService code;



	@Override
	public CommonResponse<Object> toggleSave(SavePostRequest request, User user, String requestId) {
		// TODO Auto-generated method stub
		CommonResponse<Object> response = new CommonResponse<>();
		response.setEcode(EcodeConstant.SUCCESS);
		log.info("save & unsave service start.......");

		response.setEcode(EcodeConstant.SUCCESS);
    response.setMessage(EcodeConstant.SUCCESS_MSG);
    response.setP_ecode(EcodeConstant.SUCCESS);
    response.setP_message(EcodeConstant.SUCCESS_MSG);

		CommonResponse<Object> validate_result = validate(request);
    if (!validate_result.getEcode().equals(EcodeConstant.SUCCESS)) {
      response.setEcode(validate_result.getEcode());
      response.setMessage(code.getEcode(validate_result.getEcode(), user.getLang()).toString());
      response.setP_ecode(EcodeConstant.ERR);
      response.setP_message(EcodeConstant.ERR_MSG);
      return response;
    }

		Post _post = postDetailRepository.findByPostId(request.getPostId());
		Optional<SavedPost> _savePost_ref = savedPostRepository.findByPostIdAndUserId(request.getPostId(), user.getId() + "");
		
		SavedPost _savedPost = new SavedPost();
		log.info("user ID {}", user.getId());

		if(_post == null){
			//không tồn tại bài viết
			response.setEcode(EcodeConstant.ERROR_NOT_EXIST_POST_ID);
			response.setMessage(code.getEcode(response.getEcode(), user.getLang()).getMessage());
			response.setP_message(code.getEcode(response.getEcode(), user.getLang()).getP_message());
			response.setP_ecode(code.getEcode(response.getEcode(), user.getLang()).getP_ecode());
			 
			return response;
		}else{
			//SAVE bài viết
			if(request.getType().equals("SAVE")){
					final String uuid = UUID.randomUUID().toString().replace("-", "");
					_savedPost.setPostId(request.getPostId());
					_savedPost.setUserId(user.getId() + "");
					_savedPost.setId(uuid);
					_savedPost.setSaveDate(new Date());
					_savedPost.setStatus("1");

					savedPostRepository.save(_savedPost);
			}else{
				//UNSAVE bài viết
				if(request.getType().equals("UNSAVE")){
					if(_savePost_ref.isPresent()){
						savedPostRepository.deleteById(_savePost_ref.get().getId());
						log.info("XOA BAN GHI REACT CO ID: " + _savePost_ref.get().getId());
					}else{
						//không có bài viết đã save trong bảng
						log.info("BAI VIET CHUA DUOC SAVE!");
						response.setEcode(EcodeConstant.ERROR_NOT_EXIST_POST_ID);
						response.setMessage(code.getEcode(response.getEcode(), user.getLang()).getMessage());
						response.setP_message(code.getEcode(response.getEcode(), user.getLang()).getP_message());
						response.setP_ecode(code.getEcode(response.getEcode(), user.getLang()).getP_ecode());
						return response;
					}
				}else{
						//data type không hợp lệ
						log.info("DATA TYPE KHONG HOP LE!");
						response.setEcode(EcodeConstant.ERROR_NOT_EXIST_POST_ID);
						response.setMessage(code.getEcode(response.getEcode(), user.getLang()).getMessage());
						response.setP_message(code.getEcode(response.getEcode(), user.getLang()).getP_message());
						response.setP_ecode(code.getEcode(response.getEcode(), user.getLang()).getP_ecode()); 
						return response;
						}
			}
		}
		// TODO Auto-generated method stub
		log.info("comment service End.");
		return response;
	}

	@Override
	public CommonResponse<Object> validate(SavePostRequest request) {
		// TODO Auto-generated method stub
    CommonResponse<Object> response = new CommonResponse<>();
    response.setEcode(EcodeConstant.SUCCESS);
    log.info("Validate start.......");

    response.setMessage(EcodeConstant.SUCCESS_MSG);
    response.setP_ecode(EcodeConstant.SUCCESS);
    response.setP_message(EcodeConstant.SUCCESS_MSG);

    
    if (
      request.getPostId() == null || request.getPostId().trim().length() < 1
    ) {
      response.setEcode(EcodeConstant.COMMENT_TYPE_NULL_EMPTY);
    }

    log.info("Validate end.");

    return response;
	}


}
