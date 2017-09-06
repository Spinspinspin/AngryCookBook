package com.libertymutual.goforcode.angrycb.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "ingredientId")
@Entity

public class Ingredients {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ingredientId;

	@Column(nullable = false, length = 75)
	private String title;

	@Column(nullable = true, length = 255)
	private String description;

	@Column(nullable = false, length = 255)
	private String foodItem;

	@Column(nullable = false)
	private double measureUnit;

	@Column(nullable = true, length = 255)
	private int quantity;

	private List<Ingredients> ingredients;

	public Ingredients() {}

	public Ingredients(String title, String description, String foodItem, double measureUnit, int quantity) {
		this.title = title;
		this.description = description;
		this.foodItem = foodItem;
		this.measureUnit = measureUnit;
		this.quantity = quantity;
	}
	
	// getters and setters

	public Long getId() {
		return ingredientId;
	}

	public void setId(Long id) {
		this.ingredientId = ingredientId;
	}

	public List<Ingredients> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredients> ingredients) {
		this.ingredients = ingredients;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFoodItem() {
		return foodItem;
	}

	public void setFoodItem(String foodItem) {
		this.foodItem = foodItem;
	}

	public double getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(double measureUnit) {
		this.measureUnit = measureUnit;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
