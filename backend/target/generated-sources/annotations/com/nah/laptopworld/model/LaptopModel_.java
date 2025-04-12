package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(LaptopModel.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class LaptopModel_ {

	
	/**
	 * @see com.nah.laptopworld.model.LaptopModel#detailDesc
	 **/
	public static volatile SingularAttribute<LaptopModel, String> detailDesc;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopModel#comments
	 **/
	public static volatile ListAttribute<LaptopModel, Comment> comments;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopModel#ProductImages
	 **/
	public static volatile ListAttribute<LaptopModel, ProductImage> ProductImages;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopModel#name
	 **/
	public static volatile SingularAttribute<LaptopModel, String> name;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopModel#shortDesc
	 **/
	public static volatile SingularAttribute<LaptopModel, String> shortDesc;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopModel#laptopVariant
	 **/
	public static volatile ListAttribute<LaptopModel, LaptopVariant> laptopVariant;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopModel#id
	 **/
	public static volatile SingularAttribute<LaptopModel, Long> id;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopModel
	 **/
	public static volatile EntityType<LaptopModel> class_;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopModel#brand
	 **/
	public static volatile SingularAttribute<LaptopModel, Brand> brand;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopModel#target
	 **/
	public static volatile SingularAttribute<LaptopModel, String> target;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopModel#status
	 **/
	public static volatile SingularAttribute<LaptopModel, Boolean> status;

	public static final String DETAIL_DESC = "detailDesc";
	public static final String COMMENTS = "comments";
	public static final String PRODUCT_IMAGES = "ProductImages";
	public static final String NAME = "name";
	public static final String SHORT_DESC = "shortDesc";
	public static final String LAPTOP_VARIANT = "laptopVariant";
	public static final String ID = "id";
	public static final String BRAND = "brand";
	public static final String TARGET = "target";
	public static final String STATUS = "status";

}

