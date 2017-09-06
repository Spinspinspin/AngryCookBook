package com.libertymutual.goforcode.angrycb.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "instructionId")
@Entity

public class Instructions {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long instructionId;

	@Column(nullable = false, length = 255)
	private String step;
	
	
	@ManyToMany(mappedBy="recipies")
	private List<Instructions> instructions;
	
	public Instructions() {}

	public Instructions(String step) {
		this.step = step;
	}
	
	// getters and setters

	public Long getId() {
		return instructionId;
	}

	public void setId(Long id) {
		this.instructionId = instructionId;
	}

	public List<Instructions> getInstructions() {
		return instructions;
	}

	public void setIngredients(List<Instructions> instructions) {
		this.instructions = instructions;

	}
}
