package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Policy.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Policy_ {

	
	/**
	 * @see com.nah.laptopworld.model.Policy#createdAt
	 **/
	public static volatile SingularAttribute<Policy, LocalDateTime> createdAt;
	
	/**
	 * @see com.nah.laptopworld.model.Policy#id
	 **/
	public static volatile SingularAttribute<Policy, Long> id;
	
	/**
	 * @see com.nah.laptopworld.model.Policy#title
	 **/
	public static volatile SingularAttribute<Policy, String> title;
	
	/**
	 * @see com.nah.laptopworld.model.Policy
	 **/
	public static volatile EntityType<Policy> class_;
	
	/**
	 * @see com.nah.laptopworld.model.Policy#slug
	 **/
	public static volatile SingularAttribute<Policy, String> slug;
	
	/**
	 * @see com.nah.laptopworld.model.Policy#content
	 **/
	public static volatile SingularAttribute<Policy, String> content;

	public static final String CREATED_AT = "createdAt";
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String SLUG = "slug";
	public static final String CONTENT = "content";

}

