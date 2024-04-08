package com.vision.elk.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vision.elk.dto.User;

@Service
public class ElkService {
	Logger logger=LoggerFactory.getLogger(ElkService.class);
	
	public User createUser(int id) {
		List<User> users=getUsers();
		User user=users
				.stream()
				.filter(u->u.getId()==id).findAny().orElse(null);
		if(user!=null){
			logger.info("user found : {}",user);
			return user;
		}else{
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("User Not Found with ID : {}",Integer.valueOf(id));
			}
			return new User();
		}
	}
	
    private List<User> getUsers() {
        return Stream.of(new User(1, "John"),
				new User(2, "Shyam"),
				new User(3, "Rony"),
				new User(4, "mak"))
				.collect(Collectors.toList());
    }

}
