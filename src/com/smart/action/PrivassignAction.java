//权限分配  by zhouyu
package com.smart.action;

import org.apache.log4j.Logger;
import com.smart.core.BaseAction;
import com.smart.core.Page;
import com.smart.po.Privassign;
import com.smart.service.IPrivassignService;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class PrivassignAction extends BaseAction {
	static Logger logger = Logger.getLogger(PrivassignAction.class);

	private Long privassignId;
	private Integer groupId;
	private String privIdArray;
	
	public String getPrivIdArray() {
		return privIdArray;
	}

	public void setPrivIdArray(String privIdArray) {
		this.privIdArray = privIdArray;
	}

	private IPrivassignService privassignService;
	private boolean success;
	private Privassign privassign;
	private Page page;

	public Long getPrivassignId() {
		return privassignId;
	}

	public void setPrivassignId(Long privassignId) {
		this.privassignId = privassignId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public IPrivassignService getPrivassignService() {
		return privassignService;
	}

	public void setPrivassignService(IPrivassignService privassignService) {
		this.privassignService = privassignService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Privassign getPrivassign() {
		return privassign;
	}

	public void setPrivassign(Privassign privassign) {
		this.privassign = privassign;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	//权限分配----add
	public String savePrivassign() {

		logger.debug("Aissgn privlidges ("+ privIdArray+") to group:" + groupId);
		
		//success = true;
		 String[] privIds = privIdArray.split(",");
		 List privassigns = new ArrayList();
		 
		 for(int i=0; i < privIds.length; i++){
			 privassign = new Privassign();
			 privassign.setGroupId(groupId);
			 privassign.setPrivId(Integer.valueOf(privIds[i].trim()));
			 privassigns.add(privassign);
		 }
		 
		
		
		Integer count = (Integer)privassignService.savePrivassignBatch(privassigns);
		if (count == privIds.length) { success = true;}
		
		//privassignId = (Long)privassignService.savePrivassign(privassign);
		//if (privassignId != null) { success = true;}
		
		
		return SUCCESS;
	}

//权限分配----find
	public String findAllPrivassign() {

		logger.debug("Find all privlidges have been asiggned to group:" + groupId);
		page = new Page();
		page.setObjCondition(groupId);
		
		int start = getRequest().getParameter("start") == null? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null? 0 :Integer.valueOf(getRequest().getParameter("limit"));
		page.setStart(start);
		page.setLimit(limit = limit == 0 ? 20 : limit);
		
		
		page = privassignService.findByPage(page);
		return SUCCESS;
	}

//权限分配----update
	public String updatePrivassign() throws Exception {

		Privassign privassign = new Privassign();
		privassign.setPrivassignId(privassignId);
		// 

		success = privassignService.updatePrivassign(privassign);
		return SUCCESS;
	}

//权限分配----delete
	 public String deletePrivassign() {

		 logger.debug("Remove privlidges ("+ privIdArray+") from group:" + groupId);
			
			//success = true;
			 String[] privIds = privIdArray.split(",");
			 List privassigns = new ArrayList();
			 
			 for(int i=0; i < privIds.length; i++){
				 privassign = new Privassign();
				 privassign.setGroupId(groupId);
				 privassign.setPrivId(Integer.valueOf(privIds[i].trim()));
				 privassigns.add(privassign);
			 }
			
			Integer count = (Integer)privassignService.deleteBatch(privassigns);
			if (count == privIds.length) { success = true;}
			
		return SUCCESS;
	}

}
