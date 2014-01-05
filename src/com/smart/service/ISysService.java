package com.smart.service;

import com.smart.po.User;
import java.util.List;
import java.util.Map;

public interface ISysService {
	
	User login(User user);
	List findUserPriv(Integer userId);
	//Map findPrivModule(String privName);
}
