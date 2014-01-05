//循环方式  by zhouyu
package com.smart.action;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.smart.core.BaseAction;
import com.smart.core.MyUtils;
import com.smart.core.Page;
import com.smart.po.Cycle;
import com.smart.service.ICycleService;

@SuppressWarnings("serial")
public class CycleAction extends BaseAction {
	static Logger logger = Logger.getLogger(CycleAction.class);

	private Integer cycleId;
	private ICycleService cycleService;
	private boolean success;
	private Cycle cycle;
	private Page page;

	public Integer getCycleId() {
		return cycleId;
	}

	public void setCycleId(Integer cycleId) {
		this.cycleId = cycleId;
	}

	public ICycleService getCycleService() {
		return cycleService;
	}

	public void setCycleService(ICycleService cycleService) {
		this.cycleService = cycleService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Cycle getCycle() {
		return cycle;
	}

	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	//循环方式----add
	public String saveCycle() {

		cycleId = (Integer) cycleService.saveCycle(cycle);
		if (cycleId != null) { success = true;}
		return SUCCESS;
	}

//循环方式----find
	public String findAllCycle() {

		String strCondition = getRequest().getParameter("conditions") == null? "" :getRequest().getParameter("conditions");
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		page = new Page();
		page.setConditions(conditions);
		int start = getRequest().getParameter("start") == null? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null? 0 :Integer.valueOf(getRequest().getParameter("limit"));
		page.setStart(start);
		page.setLimit(limit = limit == 0 ? 20 : limit);
		page = cycleService.findByPage(page);
		return SUCCESS;
	}

//循环方式----update
	public String updateCycle() throws Exception {

		Cycle cycle = new Cycle();
		cycle.setCycleId(cycleId);
		// 

		success = cycleService.updateCycle(cycle);
		return SUCCESS;
	}

//循环方式----delete
	 public String deleteCycle() {

		String strId = getRequest().getParameter("cycleId");
		if (strId != null && !"".equals(strId)) {
			success = cycleService.deleteCycle(Integer.valueOf(strId));
		}
		return SUCCESS;
	}

}
