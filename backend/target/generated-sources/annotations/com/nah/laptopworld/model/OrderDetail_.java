package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(OrderDetail.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class OrderDetail_ {

	
	/**
	 * @see com.nah.laptopworld.model.OrderDetail#quantity
	 **/
	public static volatile SingularAttribute<OrderDetail, Long> quantity;
	
	/**
	 * @see com.nah.laptopworld.model.OrderDetail#price
	 **/
	public static volatile SingularAttribute<OrderDetail, Double> price;
	
	/**
	 * @see com.nah.laptopworld.model.OrderDetail#laptopVariant
	 **/
	public static volatile SingularAttribute<OrderDetail, LaptopVariant> laptopVariant;
	
	/**
	 * @see com.nah.laptopworld.model.OrderDetail#id
	 **/
	public static volatile SingularAttribute<OrderDetail, Long> id;
	
	/**
	 * @see com.nah.laptopworld.model.OrderDetail
	 **/
	public static volatile EntityType<OrderDetail> class_;
	
	/**
	 * @see com.nah.laptopworld.model.OrderDetail#order
	 **/
	public static volatile SingularAttribute<OrderDetail, Order> order;

	public static final String QUANTITY = "quantity";
	public static final String PRICE = "price";
	public static final String LAPTOP_VARIANT = "laptopVariant";
	public static final String ID = "id";
	public static final String ORDER = "order";

}

