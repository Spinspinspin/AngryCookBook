package com.libertymutual.goforcode.angrycb.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No such Item")  // 404
	public class ItemNotFoundException extends Exception {

		 
		
		
		private static final long serialVersionUID = 1L;

	}
	

