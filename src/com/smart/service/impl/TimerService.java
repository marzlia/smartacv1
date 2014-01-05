//定时信息  by zhouyu
package com.smart.service.impl;

import com.smart.core.Page;
import com.smart.po.Timer;
import com.smart.service.ITimerService;
import com.smart.dao.ITimerDao;

public class TimerService implements ITimerService {
	private ITimerDao timerDao;

	public void setTimerDao(ITimerDao timerDao) {
		this.timerDao = timerDao;
	}

//定时信息----add
	public Object saveTimer(Timer timer) {

		return timerDao.saveTimer(timer);
	}

//定时信息----find
	public Page findByPage(Page page) {

		page.setRoot(timerDao.findByPage(page));
		page.setTotalProperty(timerDao.findByCount(page));
		return page;
	}

//定时信息----update
	public boolean updateTimer(Timer timer) throws Exception {

		Integer flag = timerDao.update(timer);
		if (flag != null) { return true; }
		return false;
	}

//定时信息----delete
	public boolean deleteTimer(Integer timerId) {

		Integer flag = timerDao.deleteById(timerId);
		if (flag != null) { return true; }
		return false;
	}

}
