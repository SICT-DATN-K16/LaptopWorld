package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.sql.Date;
import java.time.LocalDateTime;

@StaticMetamodel(User.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class User_ {

	
	/**
	 * @see com.nah.laptopworld.model.User#address
	 **/
	public static volatile SingularAttribute<User, String> address;
	
	/**
	 * @see com.nah.laptopworld.model.User#role
	 **/
	public static volatile SingularAttribute<User, Role> role;
	
	/**
	 * @see com.nah.laptopworld.model.User#gender
	 **/
	public static volatile SingularAttribute<User, String> gender;
	
	/**
	 * @see com.nah.laptopworld.model.User#blogs
	 **/
	public static volatile ListAttribute<User, Blog> blogs;
	
	/**
	 * @see com.nah.laptopworld.model.User#fullName
	 **/
	public static volatile SingularAttribute<User, String> fullName;
	
	/**
	 * @see com.nah.laptopworld.model.User#avatar
	 **/
	public static volatile SingularAttribute<User, String> avatar;
	
	/**
	 * @see com.nah.laptopworld.model.User#cart
	 **/
	public static volatile SingularAttribute<User, Cart> cart;
	
	/**
	 * @see com.nah.laptopworld.model.User#createdAt
	 **/
	public static volatile SingularAttribute<User, LocalDateTime> createdAt;
	
	/**
	 * @see com.nah.laptopworld.model.User#password
	 **/
	public static volatile SingularAttribute<User, String> password;
	
	/**
	 * @see com.nah.laptopworld.model.User#forgotPassword
	 **/
	public static volatile SingularAttribute<User, ForgotPassword> forgotPassword;
	
	/**
	 * @see com.nah.laptopworld.model.User#phoneNumber
	 **/
	public static volatile SingularAttribute<User, String> phoneNumber;
	
	/**
	 * @see com.nah.laptopworld.model.User#dob
	 **/
	public static volatile SingularAttribute<User, Date> dob;
	
	/**
	 * @see com.nah.laptopworld.model.User#orders
	 **/
	public static volatile ListAttribute<User, Order> orders;
	
	/**
	 * @see com.nah.laptopworld.model.User#id
	 **/
	public static volatile SingularAttribute<User, Long> id;
	
	/**
	 * @see com.nah.laptopworld.model.User
	 **/
	public static volatile EntityType<User> class_;
	
	/**
	 * @see com.nah.laptopworld.model.User#email
	 **/
	public static volatile SingularAttribute<User, String> email;

	public static final String ADDRESS = "address";
	public static final String ROLE = "role";
	public static final String GENDER = "gender";
	public static final String BLOGS = "blogs";
	public static final String FULL_NAME = "fullName";
	public static final String AVATAR = "avatar";
	public static final String CART = "cart";
	public static final String CREATED_AT = "createdAt";
	public static final String PASSWORD = "password";
	public static final String FORGOT_PASSWORD = "forgotPassword";
	public static final String PHONE_NUMBER = "phoneNumber";
	public static final String DOB = "dob";
	public static final String ORDERS = "orders";
	public static final String ID = "id";
	public static final String EMAIL = "email";

}

