package com.epay.ewallet.service.post.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.Comment;
import com.mongodb.BasicDBObject;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {

	@Query("{'referenceId' : :#{#referenceId}, 'status' : :#{#status}}")
	List<Comment> findCommentsById(@Param("referenceId") String referenceId, @Param("status") String status);

	@Query("{ 'id' : ?0 }")
	Comment findByCommentId(String id);

	@Aggregation({ "{$match: {status: \"ACTIVE\", referenceId: ?0}}",
			"{$project: {commentId: {$toString: \"$_id\"}, _id: 0}}" })
	List<Comment> getListCommentsByReferenceId(String postId);

	@Query("{'id' : :#{#id}, 'status' : :#{#status}}")
	Comment findCommentsByIdAndStatus(@Param("id") String id, @Param("status") String status);

	@Query("{'id' : :#{#id}, 'status' : :#{#status}, 'userId' : :#{#userId}}")
	Comment findCommentsByIdAndStatusAndUserId(@Param("id") String id, @Param("status") String status,
			@Param("userId") long userId);
	/*----------------------------------------------------------------------------------------------------*/
	
	@Aggregation({
		"{$match: {referenceId: ?0, status: \"ACTIVE\"}}",
		"{$project: {cmtId: \"$_id\", _id: 0}}"
	})
	List<BasicDBObject> getListCommentIdByPostId(String postId);
	
	@CountQuery("{referenceId: {$in: ?0}, status: \"ACTIVE\"}")
	int countReply(List<String> level1Id);
}