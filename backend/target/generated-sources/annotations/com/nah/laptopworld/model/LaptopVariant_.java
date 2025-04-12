package com.nah.laptopworld.model;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(LaptopVariant.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class LaptopVariant_ {

	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#sold
	 **/
	public static volatile SingularAttribute<LaptopVariant, Long> sold;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#quantity
	 **/
	public static volatile SingularAttribute<LaptopVariant, Long> quantity;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#laptopModel
	 **/
	public static volatile SingularAttribute<LaptopVariant, LaptopModel> laptopModel;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#display
	 **/
	public static volatile SingularAttribute<LaptopVariant, String> display;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#graphicCard
	 **/
	public static volatile SingularAttribute<LaptopVariant, String> graphicCard;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#weight
	 **/
	public static volatile SingularAttribute<LaptopVariant, Double> weight;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#storage
	 **/
	public static volatile SingularAttribute<LaptopVariant, Integer> storage;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#processor
	 **/
	public static volatile SingularAttribute<LaptopVariant, String> processor;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#graphicCardBrand
	 **/
	public static volatile SingularAttribute<LaptopVariant, String> graphicCardBrand;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#price
	 **/
	public static volatile SingularAttribute<LaptopVariant, Double> price;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#id
	 **/
	public static volatile SingularAttribute<LaptopVariant, Long> id;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#variantName
	 **/
	public static volatile SingularAttribute<LaptopVariant, String> variantName;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant
	 **/
	public static volatile EntityType<LaptopVariant> class_;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#processorBrand
	 **/
	public static volatile SingularAttribute<LaptopVariant, String> processorBrand;
	
	/**
	 * @see com.nah.laptopworld.model.LaptopVariant#ram
	 **/
	public static volatile SingularAttribute<LaptopVariant, Integer> ram;

	public static final String SOLD = "sold";
	public static final String QUANTITY = "quantity";
	public static final String LAPTOP_MODEL = "laptopModel";
	public static final String DISPLAY = "display";
	public static final String GRAPHIC_CARD = "graphicCard";
	public static final String WEIGHT = "weight";
	public static final String STORAGE = "storage";
	public static final String PROCESSOR = "processor";
	public static final String GRAPHIC_CARD_BRAND = "graphicCardBrand";
	public static final String PRICE = "price";
	public static final String ID = "id";
	public static final String VARIANT_NAME = "variantName";
	public static final String PROCESSOR_BRAND = "processorBrand";
	public static final String RAM = "ram";

}

