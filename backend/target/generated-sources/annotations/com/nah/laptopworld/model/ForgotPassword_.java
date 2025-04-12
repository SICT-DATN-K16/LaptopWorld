package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Date;

@StaticMetamodel(ForgotPassword.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ForgotPassword_ {

	
	/**
	 * @see com.nah.laptopworld.model.ForgotPassword#fpId
	 **/
	public static volatile SingularAttribute<ForgotPassword, Integer> fpId;
	
	/**
	 * @see com.nah.laptopworld.model.ForgotPassword#expirationTime
	 **/
	public static volatile SingularAttribute<ForgotPassword, Date> expirationTime;
	
	/**
	 * @see com.nah.laptopworld.model.ForgotPassword#otp
	 **/
	public static volatile SingularAttribute<ForgotPassword, Integer> otp;
	
	/**
	 * @see com.nah.laptopworld.model.ForgotPassword
	 **/
	public static volatile EntityType<ForgotPassword> class_;
	
	/**
	 * @see com.nah.laptopworld.model.ForgotPassword#user
	 **/
	public static volatile SingularAttribute<ForgotPassword, User> user;

	public static final String FP_ID = "fpId";
	public static final String EXPIRATION_TIME = "expirationTime";
	public static final String OTP = "otp";
	public static final String USER = "user";

}

