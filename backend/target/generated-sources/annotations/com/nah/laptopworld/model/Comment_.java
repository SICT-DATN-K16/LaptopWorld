package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;

@StaticMetamodel(Comment.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Comment_ {

	
	/**
	 * @see com.nah.laptopworld.model.Comment#createdAt
	 **/
	public static volatile SingularAttribute<Comment, Instant> createdAt;
	
	/**
	 * @see com.nah.laptopworld.model.Comment#laptopModel
	 **/
	public static volatile SingularAttribute<Comment, LaptopModel> laptopModel;
	
	/**
	 * @see com.nah.laptopworld.model.Comment#replies
	 **/
	public static volatile ListAttribute<Comment, Comment> replies;
	
	/**
	 * @see com.nah.laptopworld.model.Comment#createdBy
	 **/
	public static volatile SingularAttribute<Comment, String> createdBy;
	
	/**
	 * @see com.nah.laptopworld.model.Comment#rate
	 **/
	public static volatile SingularAttribute<Comment, Integer> rate;
	
	/**
	 * @see com.nah.laptopworld.model.Comment#parentComment
	 **/
	public static volatile SingularAttribute<Comment, Comment> parentComment;
	
	/**
	 * @see com.nah.laptopworld.model.Comment#id
	 **/
	public static volatile SingularAttribute<Comment, Long> id;
	
	/**
	 * @see com.nah.laptopworld.model.Comment#message
	 **/
	public static volatile SingularAttribute<Comment, String> message;
	
	/**
	 * @see com.nah.laptopworld.model.Comment#userName
	 **/
	public static volatile SingularAttribute<Comment, String> userName;
	
	/**
	 * @see com.nah.laptopworld.model.Comment
	 **/
	public static volatile EntityType<Comment> class_;
	
	/**
	 * @see com.nah.laptopworld.model.Comment#email
	 **/
	public static volatile SingularAttribute<Comment, String> email;

	public static final String CREATED_AT = "createdAt";
	public static final String LAPTOP_MODEL = "laptopModel";
	public static final String REPLIES = "replies";
	public static final String CREATED_BY = "createdBy";
	public static final String RATE = "rate";
	public static final String PARENT_COMMENT = "parentComment";
	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String USER_NAME = "userName";
	public static final String EMAIL = "email";

}

