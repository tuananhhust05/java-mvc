package com.epay.ewallet.service.post.repository;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epay.ewallet.service.post.model.Post;
import com.mongodb.BasicDBObject;

@Repository
public interface PostDetailRepository extends MongoRepository<Post, String> {

	@Query("{ 'id' : ?0 }")
	Post findByPostId(String postId);

	@Query("{'id' : :#{#id}, 'status' : :#{#status}, 'userId' : :#{#userId}}")
	Post findByUserIdAndStatus(@Param("id") String id, @Param("status") String status, @Param("userId") long userId);

	@Query("{'id' : :#{#id}, 'status' : :#{#status}}")
	Post findByStatus(@Param("id") String id, @Param("status") String status);
	
	@Aggregation({
		"{$lookup: {from: \"media\", let: {searchId: \"$_id\"}, pipeline:[{$match: {$expr: {$and: [{$eq: [\"$referenceId\", \"$$searchId\"]}, {$eq: [\"$status\", \"1\"]}]}}}, {$project: {_id: 0, mediaUrl: 1}}, {$sort: {seq: 1}}], as: \"images\"}}",
		"{$lookup: {from: \"tags\", let: {searchId: \"$_id\"}, pipeline:[{$match: {$expr: {$and: [{$eq: [\"$postId\", \"$$searchId\"]},{$eq: [\"$status\", \"1\"]}]}}}, {$project: {_id: 0, userId: {$toLong: \"$userId\"}}}], as: \"tagList\"}}",
		"{$lookup: {from: \"category\", let: {searchId: \"$category\"}, pipeline: [{$match: {$expr: {$and: [{$eq: [\"$$searchId\", \"$_id\"]}, {$eq: [\"$status\", \"1\"]}]}}}], as: \"category\"}}",
		"{$lookup: {from: \"reacts\", let: {searchId: \"$_id\"}, pipeline: [{$match: {$expr: {$and: [{$eq: [\"$referenceId\", \"$$searchId\"]}, {$eq: [\"$status\", \"1\"]}]}}}, {$project: {_id: 0, userId: 1, status: 1, referenceId: 1}}], as: \"reactList\"}}",
		"{$lookup: {from: \"usersetting\", let: {searchId: \"$userId\"}, pipeline: [{$match: {$expr: {$eq: [\"$$searchId\", \"$userId\"]}}}, {$project: {_id: 0, userId: 1, list_social: 1}}], as: \"userSetting\"}}",
		"{$match: {_id: ?0}}",
		"{$project: {_id: 0, userId: 1, postId: \"$_id\", content: 1, images: \"$images.mediaUrl\", editDate: 1, postedDate: {$dateToString: {date: \"$createDate\", format: \"%Y/%m/%d %H:%M:%S\"}}, status: 1, tagList: \"$tagList.userId\", type: \"$marketType\", categoryId: {$arrayElemAt: [\"$category._id\", 0]}, category: {$arrayElemAt: [\"$category.name\", 0]}, price: 1, reactList: 1, postByRole: 1, contactList: {$arrayElemAt: [\"$userSetting.list_social\", 0]}}}"
	})
	BasicDBObject postDetailById(String postId);

}