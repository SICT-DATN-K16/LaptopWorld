package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Brand.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class Brand_ {

	
	/**
	 * @see com.nah.laptopworld.model.Brand#name
	 **/
	public static volatile SingularAttribute<Brand, String> name;
	
	/**
	 * @see com.nah.laptopworld.model.Brand#logo
	 **/
	public static volatile SingularAttribute<Brand, String> logo;
	
	/**
	 * @see com.nah.laptopworld.model.Brand#description
	 **/
	public static volatile SingularAttribute<Brand, String> description;
	
	/**
	 * @see com.nah.laptopworld.model.Brand#id
	 **/
	public static volatile SingularAttribute<Brand, Long> id;
	
	/**
	 * @see com.nah.laptopworld.model.Brand
	 **/
	public static volatile EntityType<Brand> class_;
	
	/**
	 * @see com.nah.laptopworld.model.Brand#laptopModels
	 **/
	public static volatile ListAttribute<Brand, LaptopModel> laptopModels;

	public static final String NAME = "name";
	public static final String LOGO = "logo";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String LAPTOP_MODELS = "laptopModels";

}

