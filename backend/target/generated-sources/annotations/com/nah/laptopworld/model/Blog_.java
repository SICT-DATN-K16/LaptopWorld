package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Blog.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Blog_ {

	
	/**
	 * @see com.nah.laptopworld.model.Blog#createdAt
	 **/
	public static volatile SingularAttribute<Blog, LocalDateTime> createdAt;
	
	/**
	 * @see com.nah.laptopworld.model.Blog#id
	 **/
	public static volatile SingularAttribute<Blog, Long> id;
	
	/**
	 * @see com.nah.laptopworld.model.Blog#title
	 **/
	public static volatile SingularAttribute<Blog, String> title;
	
	/**
	 * @see com.nah.laptopworld.model.Blog
	 **/
	public static volatile EntityType<Blog> class_;
	
	/**
	 * @see com.nah.laptopworld.model.Blog#user
	 **/
	public static volatile SingularAttribute<Blog, User> user;
	
	/**
	 * @see com.nah.laptopworld.model.Blog#slug
	 **/
	public static volatile SingularAttribute<Blog, String> slug;
	
	/**
	 * @see com.nah.laptopworld.model.Blog#content
	 **/
	public static volatile SingularAttribute<Blog, String> content;
	
	/**
	 * @see com.nah.laptopworld.model.Blog#thumbnailUrl
	 **/
	public static volatile SingularAttribute<Blog, String> thumbnailUrl;

	public static final String CREATED_AT = "createdAt";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String USER = "user";
	public static final String SLUG = "slug";
	public static final String CONTENT = "content";
	public static final String THUMBNAIL_URL = "thumbnailUrl";

}

