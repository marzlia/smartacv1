package com.smart.dao;

import com.smart.po.User;
import java.util.List;

public interface ISysDao {
	public User login(User user);
	public List findUserPriv(Integer userId);
}
