package com.epay.ewallet.service.post.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.Report;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {

	@Query("{'referenceId' : :#{#referenceId}, 'status' : :#{#status}}")
	Page<Report> findUserReportById(@Param("referenceId") String referenceId, @Param("status") String status,
			Pageable pageable);

}