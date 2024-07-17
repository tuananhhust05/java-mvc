package com.epay.ewallet.service.post.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.React;
import com.epay.ewallet.service.post.model.ReportTemp;

@Repository
public interface ReportTempRepository extends MongoRepository<React, String> {

	@Query("{'status' : :#{#status}}")
	List<ReportTemp> findReportTempByStatus(@Param("status") String status);

	@Query("{'id' : :#{#id}}, 'status' : :#{#status}}")
	ReportTemp findReportTempByReportTypeId(@Param("id") String id, @Param("status") String status);
}