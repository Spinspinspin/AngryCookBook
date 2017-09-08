package com.libertymutual.goforcode.angrycb.repositories;

import java.util.List;

import org.aspectj.apache.bcel.generic.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.angrycb.models.Ingredients;
import com.libertymutual.goforcode.angrycb.models.Instructions;

@Repository

public interface InstructionRepository extends JpaRepository<Instructions, Long> {


	List<Instructions> findByRecipeId(long id);
	}
