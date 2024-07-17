package com.epay.ewallet.service.post.service.impl;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.ibatis.javassist.expr.NewArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.epay.ewallet.service.post.constant.EcodeConstant;
import com.epay.ewallet.service.post.mapperDataOne.IUser;
import com.epay.ewallet.service.post.model.Colleague;
import com.epay.ewallet.service.post.model.Division;
import com.epay.ewallet.service.post.model.User;
import com.epay.ewallet.service.post.payloads.request.TagColleagueRequest;
import com.epay.ewallet.service.post.payloads.response.CommonResponse;
import com.epay.ewallet.service.post.repository.DivisionRepository;
import com.epay.ewallet.service.post.repository.TagColleagueRepository;
import com.epay.ewallet.service.post.service.CodeService;
import com.epay.ewallet.service.post.service.TagColleagueService;
import com.epay.ewallet.service.post.utils.Utils;

@Service
public class TagColleagueServiceImpl implements TagColleagueService {

  @Autowired
  private TagColleagueRepository tagColleagueRepository;

  @Autowired
  private DivisionRepository divisionRepository;

  @Autowired
  private CodeService code;

  @Autowired
	private IUser userDao;

  private static final Logger log = LogManager.getLogger(TagColleagueServiceImpl.class);

  @Override
  public CommonResponse<Object> tagColleague(TagColleagueRequest request, User user, String requestId) {
    // TODO Auto-generated method stub

    CommonResponse<Object> response = new CommonResponse<>();
    log.info("tag service start...");
    
    
    response.setEcode(EcodeConstant.SUCCESS);
    response.setMessage(EcodeConstant.SUCCESS_MSG);
    response.setP_ecode(EcodeConstant.SUCCESS);
    response.setP_message(EcodeConstant.SUCCESS_MSG);
    CommonResponse<Object> validate_result = validate(request);
		if(!validate_result.getEcode().equals(EcodeConstant.SUCCESS)) {
      response.setEcode(validate_result.getEcode());
      response.setMessage(code.getEcode(validate_result.getEcode(), user.getLang()).toString());
      response.setP_ecode(EcodeConstant.ERR);
      response.setP_message(EcodeConstant.ERR_MSG);
      return response;
    }

      List<Colleague> _colleague = new ArrayList<Colleague>();


      Page<Colleague> pageColleagues;


      List<User> listUser;

      Pageable paging = PageRequest.of(request.getPage(), 20, Sort.by("name"));

    
//      log.info("chuoi 1 {}", nameAccented);
//      log.info("chuoi 2 {}", request.getName());

      
      ArrayList<LinkedHashMap<String, String>> newArr = new ArrayList<>();

      if(request.getName() == null || request.getName().equals("")){
        //client truyền lên chuỗi rỗng hoặc null
        //trả về tất cả nhưng mà phân trang 10 bản/lần
//        pageColleagues = tagColleagueRepository.findByNameContainingIgnoreCase("", paging);
    	  pageColleagues = tagColleagueRepository.findByNameContainingIgnoreCaseAndStatusGreaterThanEqualAndUserTypeIdAndUserIdIsNot("",3,1,user.getId() + "", paging);
    	  
        _colleague = pageColleagues.getContent();
        log.info("input name ko co du lieu, so luong user tim thay: " + _colleague.size());
        for (Colleague item : _colleague ) {
//          if(item.getStatus() >= 3 && item.getUserTypeId() == 1 && (!item.getUserId().equals(user.getId() + ""))){ //loai bo chinh minh ra khoi list
            LinkedHashMap<String, String> data = new LinkedHashMap<>();
            
//            List<Division> divition = divisionRepository.findByName(item.getName());
            List<Division> divition = divisionRepository.findByPhonenumber(item.getPhone());
//            log.info("divition: " + divition);
            if(divition.isEmpty()){
              data.put("avatar", item.getAvatar() != null ? item.getAvatar()  : "");
              data.put("name", item.getName());
              data.put("userId", item.getUserId());
              data.put("nickName", item.getNickname());
              data.put("division", null);
              data.put("posittion", null);
              newArr.add(data);
            }else{
              for(Division itemDivition : divition){
                data.put("avatar", item.getAvatar() != null ? item.getAvatar()  : "");
                data.put("name", item.getName());
                data.put("userId", item.getUserId());
                data.put("nickName", item.getNickname());
                data.put("position", itemDivition.getPosittion());
                data.put("division", itemDivition.getDivision());
                newArr.add(data);
              }
            }
//          }
        }
      }else{
    	  String nameAccented = Utils.removeAccent(request.getName());
        if(!nameAccented.equals(request.getName())){
          //client truyền lên chuỗi có dấu ===> tìm kiếm đúng 
//          pageColleagues = tagColleagueRepository.findByNameContainingIgnoreCase(request.getName(), paging);
        	 pageColleagues = tagColleagueRepository.findByNameContainingIgnoreCaseAndStatusGreaterThanEqualAndUserTypeIdAndUserIdIsNot("",3,1,user.getId() + "", paging);
          _colleague = pageColleagues.getContent();
//          log.info("vao dayyyy {}", _colleague.size());
          log.info("input name co dau, so luong user tim thay: " + _colleague.size());
          for (Colleague item : _colleague ) {
//            if(item.getStatus() >= 3 && item.getUserTypeId() == 1 && (!item.getUserId().equals(user.getId() + ""))){ //loai bo chinh minh ra khoi list
              LinkedHashMap<String, String> data = new LinkedHashMap<>();
//              List<Division> divition = divisionRepository.findByName(item.getName());
              List<Division> divition = divisionRepository.findByPhonenumber(item.getPhone());
//              log.info("divition: " + divition);
            if(divition.isEmpty()){
              data.put("avatar", item.getAvatar() != null ? item.getAvatar()  : "");
              data.put("name", item.getName());
              data.put("userId", item.getUserId());
              data.put("nickName", item.getNickname());
              data.put("division", null);
              data.put("posittion", null);
              newArr.add(data);
            }else{
              for(Division itemDivition : divition){
                data.put("avatar", item.getAvatar() != null ? item.getAvatar()  : "");
                data.put("name", item.getName());
                data.put("userId", item.getUserId());
                data.put("nickName", item.getNickname());
                data.put("position", itemDivition.getPosittion());
                data.put("division", itemDivition.getDivision());
                newArr.add(data);
              }
            }
//            }
          }
        }else{
          //client truyền lên chuỗi không dấu ===> tìm kiếm cả có dấu lẫn ko dấu
       
          log.info("dau {}",(request.getPage() + 1)*20);
          log.info("cuoi {}", request.getPage()*20 - 1);
          listUser = userDao.getUserByName(nameAccented.toUpperCase(),(request.getPage() + 1)*20, request.getPage()*20 - 1);
          log.info("input name ko co dau, so luong user tim thay: " + listUser.size());  
          for (User item : listUser ) {
        	  if (item.getId() != user.getId()) { //loai bo chinh minh ra khoi list
        		  LinkedHashMap<String, String> data = new LinkedHashMap<>();
//                List<Division> divition = divisionRepository.findByName(item.getName());
        		  List<Division> divition = divisionRepository.findByPhonenumber(item.getPhoneNumber());
//                log.info("divition: " + divition);
	              if(divition.isEmpty()){
	                data.put("avatar", item.getAvatar() != null ? item.getAvatar()  : "");
	                data.put("name", item.getName());
	                data.put("userId", item.getId() + "");
	                data.put("nickName", item.getNickname());
	                data.put("division", null);
	                data.put("posittion", null);
	                newArr.add(data);
	              }else{
	                for(Division itemDivition : divition){
	                  data.put("avatar", item.getAvatar() != null ? item.getAvatar()  : "");
	                  data.put("name", item.getName());
	                  data.put("userId", item.getId() + "");
	                  data.put("nickName", item.getNickname());
	                  data.put("position", itemDivition.getPosittion());
	                  data.put("division", itemDivition.getDivision());
	                  newArr.add(data);
	                }
	              }
			}
              
            } //end for
        }
      }

      if(_colleague.isEmpty()){
        response.setData(newArr);
      }else{
        // TODO Auto-generated method stub
        log.info("comment service End.");
        log.info("danh sach {}", _colleague.toString());
        response.setData(newArr);
      }
      return response;
  }


  
  @Override
  public CommonResponse<Object> validate(TagColleagueRequest request) {
    // TODO Auto-generated method stub
    CommonResponse<Object> response = new CommonResponse<>();
    response.setEcode(EcodeConstant.SUCCESS);
    log.info("Validate start.......");

    response.setMessage(EcodeConstant.SUCCESS_MSG);
    response.setP_ecode(EcodeConstant.SUCCESS);
    response.setP_message(EcodeConstant.SUCCESS_MSG);

		// if(request.getName() == null || request.getName() == "") {
    //       response.setEcode(EcodeConstant.REF_ID_NULL_EMPTY);
    // }

    if(request.getPage() < 0) {
      response.setEcode(EcodeConstant.REF_ID_NULL_EMPTY);
    }
    
        log.info("Validate end.");

        return response;
  }



 
}