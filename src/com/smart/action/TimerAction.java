//定时信息  by Yu Zhou
package com.smart.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.smart.core.BaseAction;
import com.smart.core.MyUtils;
import com.smart.core.Page;
import com.smart.po.Timer;
import com.smart.service.ITimerService;

@SuppressWarnings("serial")
public class TimerAction extends BaseAction {
	static Logger logger = Logger.getLogger(TimerAction.class);

	private Long timerId;
	private Date timerBegin; // 开始时间
	private Date timerEnd; // 结束时间
	private Integer timerEnable; // 激活
	private Integer timerCreatenew; // 是否创建新进程
	private String timerTask; // 定时任务
	private Integer cycleId; // 循环方式
	
	public Date getTimerBegin() {
		return timerBegin;
	}

	public void setTimerBegin(Date timerBegin) {
		this.timerBegin = timerBegin;
	}

	public Date getTimerEnd() {
		return timerEnd;
	}

	public void setTimerEnd(Date timerEnd) {
		this.timerEnd = timerEnd;
	}

	public Integer getTimerEnable() {
		return timerEnable;
	}

	public void setTimerEnable(Integer timerEnable) {
		this.timerEnable = timerEnable;
	}

	public Integer getTimerCreatenew() {
		return timerCreatenew;
	}

	public void setTimerCreatenew(Integer timerCreatenew) {
		this.timerCreatenew = timerCreatenew;
	}

	public String getTimerTask() {
		return timerTask;
	}

	public void setTimerTask(String timerTask) {
		this.timerTask = timerTask;
	}

	public Integer getCycleId() {
		return cycleId;
	}

	public void setCycleId(Integer cycleId) {
		this.cycleId = cycleId;
	}

	private ITimerService timerService;
	private boolean success;
	private Timer timer;
	private Page page;

//定时信息----add
	public String saveTimer() {

		logger.debug("Save a time: " + timer);
		
		timerId = (Long) timerService.saveTimer(timer);
		if (timerId != null) { success = true;}
		return SUCCESS;
	}

	public Long getTimerId() {
		return timerId;
	}

	public void setTimerId(Long timerId) {
		this.timerId = timerId;
	}

	public ITimerService getTimerService() {
		return timerService;
	}

	public void setTimerService(ITimerService timerService) {
		this.timerService = timerService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	//定时信息----find
	public String findAllTimer() {

		page = new Page();
		
		String sort = getRequest().getParameter("sort") == null ? "timerId" : getRequest().getParameter("sort");
		String dir = getRequest().getParameter("dir") == null ? "ASC" : getRequest().getParameter("dir");
		logger.debug("Sort access by: " + sort + " " +dir);
		page.setObjCondition(sort + " " + dir);
		
		String strCondition = getRequest().getParameter("conditions") == null? "" :getRequest().getParameter("conditions");
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		page.setConditions(conditions);
		
		
		int start = getRequest().getParameter("start") == null? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null? 0 :Integer.valueOf(getRequest().getParameter("limit"));
		page.setStart(start);
		page.setLimit(limit = limit == 0 ? 20 : limit);
		page = timerService.findByPage(page);
		return SUCCESS;
	}

//定时信息----update
	public String updateTimer() throws Exception {

		Timer timer = new Timer();
		timer.setTimerId(timerId);
		timer.setCycleId(cycleId);
		timer.setTimerBegin(timerBegin);
		timer.setTimerCreatenew(timerCreatenew);
		timer.setTimerEnable(timerEnable);
		timer.setTimerEnd(timerEnd);
		timer.setTimerTask(timerTask);

		success = timerService.updateTimer(timer);
		
		return SUCCESS;
	}

//定时信息----delete
	 public String deleteTimer() {

		String strId = getRequest().getParameter("timerId");
		if (strId != null && !"".equals(strId)) {
			success = timerService.deleteTimer(Integer.valueOf(strId));
		}else{
			logger.warn("timerId is not set for deleteTimer");
		}
		return SUCCESS;
	}

}
