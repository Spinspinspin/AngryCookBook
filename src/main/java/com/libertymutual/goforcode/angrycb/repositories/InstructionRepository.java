package com.libertymutual.goforcode.angrycb.repositories;

import org.aspectj.apache.bcel.generic.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.angrycb.models.Instructions;

@Repository

public interface InstructionRepository extends JpaRepository<Instructions, Long> {



}
