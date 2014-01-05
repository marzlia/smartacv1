//用户管理  by zhouyu
package com.smart.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import com.smart.core.BaseAction;
import com.smart.core.MyUtils;
import com.smart.core.Page;
import com.smart.core.MD5;
import com.smart.po.User;
import com.smart.service.IUserService;

@SuppressWarnings("serial")
public class UserAction extends BaseAction {
	static Logger logger = Logger.getLogger(UserAction.class);
	
	private Integer userId;
	private String userName; // 姓名
	private String userPassword; // 密码
	private Integer groupId; // 用户组
	private String olduserName; // 姓名
	private String olduserPassword; // 密码
	private Integer oldgroupId; // 用户组
	
	private IUserService userService;
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	private boolean success;
	private String msg;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private User user;
	private Page page;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	//用户管理----add
	public String saveUser() {
		logger.debug("Save an User, userName: " + user.getUserName() + ",groupId: " + user.getGroupId());
		
		//encrypt for password
		user.setUserPassword(MD5.md5String(user.getUserPassword()));
		
		userId = (Integer) userService.saveUser(user);
		if (userId != null) { success = true;}
		return SUCCESS;
	}

//用户管理----find
	public String findAllUser() {

		page = new Page();
		
		String strCondition = getRequest().getParameter("conditions")==null? "" : getRequest().getParameter("conditions");
		logger.debug("String condition used to filter user is: " + strCondition);
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		page.setConditions(conditions);
		
		int start = getRequest().getParameter("start") == null ? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null ? 0 :Integer.valueOf(getRequest().getParameter("limit"));
		
		page.setStart(start);
		page.setLimit(limit = limit == 0 ? 20 : limit);
		page = userService.findByPage(page);
		return SUCCESS;
	}

//用户管理----update
	public String updateUser() throws Exception {

		User user = new User();
		user.setUserId(userId);
		
		logger.debug("Update userName: " + userName +
				", userPassword: " + userPassword + 
				", groupId: " + groupId);
		// 
		//success = false;
		user.setGroupId(groupId);
		user.setUserName(userName);
		if(userPassword != null && !userPassword.isEmpty()){
			user.setUserPassword(MD5.md5String(userPassword));
		}
		
		success = userService.updateUser(user);
		return SUCCESS;
	}

//用户管理----delete
	 public String deleteUser() {

		String strId = getRequest().getParameter("userId");
		if (strId != null && !"".equals(strId)) {
			success = userService.deleteUser(Integer.valueOf(strId));
		}
		return SUCCESS;
	}
	 
	 public String findExistUserName(){
		 
		 // 
		 logger.debug("Find UserName: " + userName + " if exist");
		 User user = new User();
		 user.setUserName(userName);
		 if(userId != null){
			 logger.debug(" except user : " + userId);
			 user.setUserId(userId);
		 }
		 
		 if(null == userService.findUserByName(user)){
			 success = false;
		 }
		 else {
			 success = true;
		 }
		 
		 
		 return SUCCESS;
	 }

}
