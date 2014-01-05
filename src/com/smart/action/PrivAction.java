//权限  by zhouyu
package com.smart.action;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.smart.core.BaseAction;
import com.smart.core.MyUtils;
import com.smart.core.Page;
import com.smart.po.Priv;
import com.smart.service.IPrivService;

@SuppressWarnings("serial")
public class PrivAction extends BaseAction {
	static Logger logger = Logger.getLogger(PrivAction.class);

	private Integer privId;
	private Integer groupId;
	private IPrivService privService;
	private boolean success;
	private Priv priv;
	private Page page;
	
	

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getPrivId() {
		return privId;
	}

	public void setPrivId(Integer privId) {
		this.privId = privId;
	}

	public IPrivService getPrivService() {
		return privService;
	}

	public void setPrivService(IPrivService privService) {
		this.privService = privService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Priv getPriv() {
		return priv;
	}

	public void setPriv(Priv priv) {
		this.priv = priv;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	//权限----add
	public String savePriv() {

		privId = (Integer) privService.savePriv(priv);
		if (privId != null) { success = true;}
		return SUCCESS;
	}

//权限----find
	public String findAllPriv() {
		
		logger.debug("Find all privlidges are not asiggned to group:" + groupId);
		
		page = new Page();
		page.setObjCondition(groupId);
		
		int start = getRequest().getParameter("start") == null? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null? 0 :Integer.valueOf(getRequest().getParameter("limit"));
		page.setStart(start);
		page.setLimit(limit = limit == 0 ? 20 : limit);
		
		page = privService.findByPage(page);
		
		return SUCCESS;
	}

//权限----update
	public String updatePriv() throws Exception {

		Priv priv = new Priv();
		priv.setPrivId(privId);
		// 

		success = privService.updatePriv(priv);
		return SUCCESS;
	}

//权限----delete
	 public String deletePriv() {

		String strId = getRequest().getParameter("privId");
		if (strId != null && !"".equals(strId)) {
			success = privService.deletePriv(Integer.valueOf(strId));
		}
		return SUCCESS;
	}

}
