package com.epay.ewallet.service.post.mapperDataOne;

import org.apache.ibatis.annotations.Mapper;

import com.epay.ewallet.service.post.model.Company;

@Mapper
public interface ICompany {
	Company getCompanyById(long id);
}
