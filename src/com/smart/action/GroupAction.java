//用户组  by zhouyu
package com.smart.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.smart.core.BaseAction;
import com.smart.core.MyUtils;
import com.smart.core.Page;
import com.smart.po.Group;
import com.smart.service.IGroupService;

@SuppressWarnings("serial")
public class GroupAction extends BaseAction {
	
	static Logger logger = Logger.getLogger(GroupAction.class);
	
	private Integer groupId;
	private String groupName;
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	private IGroupService groupService;
	private boolean success;
	private String msg;
	private Group group;
	private Page page;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public IGroupService getGroupService() {
		return groupService;
	}

	public void setGroupService(IGroupService groupService) {
		this.groupService = groupService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	//用户组----add
	public String saveGroup() {
		logger.debug("Save a new group is: " + group.getGroupName());
		success = false;
		groupId = (Integer) groupService.saveGroup(group);
		if (groupId != null) { success = true;}
		return SUCCESS;
	}

//用户组----find
	public String findAllGroup() {

		page = new Page();
		
		// Get sort and dir
		String sort = getRequest().getParameter("sort") == null ? "GroupId" : getRequest().getParameter("sort");
		String dir = getRequest().getParameter("dir") == null ? "ASC" : getRequest().getParameter("dir");
		logger.debug("Sort group by: " + sort + " " +dir);
		
		page.setObjCondition(sort + " " + dir);
		
		
		
		// Set Filter conditions
		String strCondition = getRequest().getParameter("conditions") == null ? "" : getRequest().getParameter("conditions");
		logger.debug("String condition used to filter group is: " + strCondition);
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		page.setConditions(conditions);
		
		// Set start and limit
		
		int start = getRequest().getParameter("start") == null ? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null ? 0 : Integer.valueOf(getRequest().getParameter("limit"));
		logger.debug("Start is: " + start + ", limit is :"+ limit);
		
		page.setStart(start);
		page.setLimit(limit = limit == 0 ? 20 : limit);
		
		//return SUCCESS;
		page = groupService.findByPage(page);
		
		
		return SUCCESS;//
	}

//用户组----update
	public String updateGroup() {

		Group group = new Group();
		group.setGroupId(groupId);
		group.setGroupName(groupName);
		
		logger.debug("Update group (groupId" + groupId+ "), groupName: " + groupName);
		
		success = false;
		try {
			success = groupService.updateGroup(group);
		}catch (Exception e){
			logger.error("更新用户组时发生错误: " + e.getMessage() );
			
			e.printStackTrace();
		}
		return SUCCESS;
	}

//用户组----delete
	 public String deleteGroup() {
		 
		 if(groupId == null) return ERROR;
		 
		// 查询该groupId下是否还有用户存在
		logger.debug("Find user count of group : " + groupId);
		
		if(groupService.findUserByGroup(groupId) > 0){
			success = false;
			msg = "该用户组包含用户,不能删除!";
			return SUCCESS;
		}
		
		logger.debug("Find priv count of group : " + groupId);
		
		if(groupService.findPrivByGroup(groupId) > 0){
			success = false;
			msg = "该用户组分配了权限,不能删除!";
			return SUCCESS;
		}
		
		 
		// 查询该groupId下是否还有权限存在
		 
		 logger.debug("Delete group : " + groupId);
		 
		 success = groupService.deleteGroup(groupId);
		 
	/*	String strId = getRequest().getParameter("groupId");
		if (strId != null && !"".equals(strId)) {
			success = groupService.deleteGroup(Integer.valueOf(strId));
		} */
		return SUCCESS;
	}

}
