package com.vision.elk.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vision.elk.dto.User;
import com.vision.elk.service.ElkService;

@RestController
@RequestMapping("/api/elk")
public class ElkController {

	Logger logger=LoggerFactory.getLogger(ElkController.class);
	
	@Autowired
	private ElkService elkService;

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
    	logger.info("User  ID : {}",Integer.valueOf(id));
		return elkService.createUser(id);
    }
    
    @GetMapping("/exception")
    public String generateException() {
    	String strReturn ="";
    	try {
    		throw new Exception ("Excepiton has been published");
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		logger.error("message", e);
    		PrintWriter pr = new PrintWriter(new StringWriter()); 
    		strReturn =pr.toString();
    		logger.error(strReturn);
    	}
    	return strReturn;
    }


}
