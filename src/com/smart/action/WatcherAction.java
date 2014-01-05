//监视器  by zhouyu
package com.smart.action;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.smart.core.BaseAction;
import com.smart.core.MyUtils;
import com.smart.core.Page;
import com.smart.po.Watcher;
import com.smart.service.IWatcherService;

@SuppressWarnings("serial")
public class WatcherAction extends BaseAction {
	static Logger logger = Logger.getLogger(WatcherAction.class);

	private Integer watcherId;
	private String watcherIp; // 地址
	private String watcherMac; // 物理地址
	public String getWatcherMac() {
		return watcherMac;
	}

	public void setWatcherMac(String watcherMac) {
		this.watcherMac = watcherMac;
	}

	private String watcherCampus; // 校区
	private String watcherRoom; // 房间
	private IWatcherService watcherService;
	private boolean success;
	private Watcher watcher;
	private Page page;

	
	public String getWatcherIp() {
		return watcherIp;
	}

	public void setWatcherIp(String watcherIp) {
		this.watcherIp = watcherIp;
	}

	public String getWatcherCampus() {
		return watcherCampus;
	}

	public void setWatcherCampus(String watcherCampus) {
		this.watcherCampus = watcherCampus;
	}

	public String getWatcherRoom() {
		return watcherRoom;
	}

	public void setWatcherRoom(String watcherRoom) {
		this.watcherRoom = watcherRoom;
	}

	public Integer getWatcherId() {
		return watcherId;
	}

	public void setWatcherId(Integer watcherId) {
		this.watcherId = watcherId;
	}

	public IWatcherService getWatcherService() {
		return watcherService;
	}

	public void setWatcherService(IWatcherService watcherService) {
		this.watcherService = watcherService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Watcher getWatcher() {
		return watcher;
	}

	public void setWatcher(Watcher watcher) {
		this.watcher = watcher;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	//监视器----add
	public String saveWatcher() {

		watcherId = (Integer) watcherService.saveWatcher(watcher);
		if (watcherId != null) { success = true;}
		return SUCCESS;
	}

//监视器----find
	public String findAllWatcher() {

		page = new Page();
		
		String sort = getRequest().getParameter("sort") == null ? "watcherId" : getRequest().getParameter("sort");
		String dir = getRequest().getParameter("dir") == null ? "ASC" : getRequest().getParameter("dir");
		logger.debug("Sort gate by: " + sort + " " +dir);
		page.setObjCondition(sort + " " + dir);
		
		String strCondition = getRequest().getParameter("conditions") == null? "" :getRequest().getParameter("conditions");
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		
		page.setConditions(conditions);
		int start = getRequest().getParameter("start") == null? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null? 0 :Integer.valueOf(getRequest().getParameter("limit"));
		page.setStart(start);
		page.setLimit(limit = limit == 0 ? 20 : limit);
		page = watcherService.findByPage(page);
		return SUCCESS;
	}

//监视器----update
	public String updateWatcher() throws Exception {

		Watcher watcher = new Watcher();
		watcher.setWatcherId(watcherId);
		watcher.setWatcherIp(watcherIp);
		watcher.setWatcherCampus(watcherCampus);
		watcher.setWatcherRoom(watcherRoom);

		success = watcherService.updateWatcher(watcher);
		return SUCCESS;
	}

//监视器----delete
	 public String deleteWatcher() {

		String strId = getRequest().getParameter("watcherId");
		if (strId != null && !"".equals(strId)) {
			success = watcherService.deleteWatcher(Integer.valueOf(strId));
		}
		return SUCCESS;
	}

}
