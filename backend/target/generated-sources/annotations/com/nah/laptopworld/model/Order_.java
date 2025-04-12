package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(Order.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Order_ {

	
	/**
	 * @see com.nah.laptopworld.model.Order#receiverAddress
	 **/
	public static volatile SingularAttribute<Order, String> receiverAddress;
	
	/**
	 * @see com.nah.laptopworld.model.Order#createdAt
	 **/
	public static volatile SingularAttribute<Order, LocalDateTime> createdAt;
	
	/**
	 * @see com.nah.laptopworld.model.Order#orderDetails
	 **/
	public static volatile ListAttribute<Order, OrderDetail> orderDetails;
	
	/**
	 * @see com.nah.laptopworld.model.Order#receiverPhone
	 **/
	public static volatile SingularAttribute<Order, String> receiverPhone;
	
	/**
	 * @see com.nah.laptopworld.model.Order#totalPrice
	 **/
	public static volatile SingularAttribute<Order, Double> totalPrice;
	
	/**
	 * @see com.nah.laptopworld.model.Order#receiverName
	 **/
	public static volatile SingularAttribute<Order, String> receiverName;
	
	/**
	 * @see com.nah.laptopworld.model.Order#id
	 **/
	public static volatile SingularAttribute<Order, Long> id;
	
	/**
	 * @see com.nah.laptopworld.model.Order
	 **/
	public static volatile EntityType<Order> class_;
	
	/**
	 * @see com.nah.laptopworld.model.Order#user
	 **/
	public static volatile SingularAttribute<Order, User> user;
	
	/**
	 * @see com.nah.laptopworld.model.Order#status
	 **/
	public static volatile SingularAttribute<Order, String> status;

	public static final String RECEIVER_ADDRESS = "receiverAddress";
	public static final String CREATED_AT = "createdAt";
	public static final String ORDER_DETAILS = "orderDetails";
	public static final String RECEIVER_PHONE = "receiverPhone";
	public static final String TOTAL_PRICE = "totalPrice";
	public static final String RECEIVER_NAME = "receiverName";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String STATUS = "status";

}

