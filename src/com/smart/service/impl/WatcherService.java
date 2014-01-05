//监视器  by zhouyu
package com.smart.service.impl;

import com.smart.core.Page;
import com.smart.po.Watcher;
import com.smart.service.IWatcherService;
import com.smart.dao.IWatcherDao;

public class WatcherService implements IWatcherService {
	private IWatcherDao watcherDao;

	public void setWatcherDao(IWatcherDao watcherDao) {
		this.watcherDao = watcherDao;
	}

//监视器----add
	public Object saveWatcher(Watcher watcher) {

		return watcherDao.saveWatcher(watcher);
	}

//监视器----find
	public Page findByPage(Page page) {

		page.setRoot(watcherDao.findByPage(page));
		page.setTotalProperty(watcherDao.findByCount(page));
		return page;
	}

//监视器----update
	public boolean updateWatcher(Watcher watcher) throws Exception {

		Integer flag = watcherDao.update(watcher);
		if (flag != null) { return true; }
		return false;
	}

//监视器----delete
	public boolean deleteWatcher(Integer watcherId) {

		Integer flag = watcherDao.deleteById(watcherId);
		if (flag != null) { return true; }
		return false;
	}

}
