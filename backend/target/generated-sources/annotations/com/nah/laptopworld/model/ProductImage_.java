package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ProductImage.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ProductImage_ {

	
	/**
	 * @see com.nah.laptopworld.model.ProductImage#image
	 **/
	public static volatile SingularAttribute<ProductImage, String> image;
	
	/**
	 * @see com.nah.laptopworld.model.ProductImage#laptopModel
	 **/
	public static volatile SingularAttribute<ProductImage, LaptopModel> laptopModel;
	
	/**
	 * @see com.nah.laptopworld.model.ProductImage#id
	 **/
	public static volatile SingularAttribute<ProductImage, Long> id;
	
	/**
	 * @see com.nah.laptopworld.model.ProductImage
	 **/
	public static volatile EntityType<ProductImage> class_;

	public static final String IMAGE = "image";
	public static final String LAPTOP_MODEL = "laptopModel";
	public static final String ID = "id";

}

