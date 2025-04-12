package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CartDetail.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class CartDetail_ {

	
	/**
	 * @see com.nah.laptopworld.model.CartDetail#quantity
	 **/
	public static volatile SingularAttribute<CartDetail, Long> quantity;
	
	/**
	 * @see com.nah.laptopworld.model.CartDetail#price
	 **/
	public static volatile SingularAttribute<CartDetail, Double> price;
	
	/**
	 * @see com.nah.laptopworld.model.CartDetail#laptopVariant
	 **/
	public static volatile SingularAttribute<CartDetail, LaptopVariant> laptopVariant;
	
	/**
	 * @see com.nah.laptopworld.model.CartDetail#id
	 **/
	public static volatile SingularAttribute<CartDetail, Long> id;
	
	/**
	 * @see com.nah.laptopworld.model.CartDetail
	 **/
	public static volatile EntityType<CartDetail> class_;
	
	/**
	 * @see com.nah.laptopworld.model.CartDetail#cart
	 **/
	public static volatile SingularAttribute<CartDetail, Cart> cart;

	public static final String QUANTITY = "quantity";
	public static final String PRICE = "price";
	public static final String LAPTOP_VARIANT = "laptopVariant";
	public static final String ID = "id";
	public static final String CART = "cart";

}

