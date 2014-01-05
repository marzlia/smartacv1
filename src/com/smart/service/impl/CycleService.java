//循环方式  by zhouyu
package com.smart.service.impl;

import com.smart.core.Page;
import com.smart.po.Cycle;
import com.smart.service.ICycleService;
import com.smart.dao.ICycleDao;

public class CycleService implements ICycleService {
	private ICycleDao cycleDao;

	public void setCycleDao(ICycleDao cycleDao) {
		this.cycleDao = cycleDao;
	}

//循环方式----add
	public Object saveCycle(Cycle cycle) {

		return cycleDao.saveCycle(cycle);
	}

//循环方式----find
	public Page findByPage(Page page) {

		page.setRoot(cycleDao.findByPage(page));
		page.setTotalProperty(cycleDao.findByCount(page));
		return page;
	}

//循环方式----update
	public boolean updateCycle(Cycle cycle) throws Exception {

		Integer flag = cycleDao.update(cycle);
		if (flag != null) { return true; }
		return false;
	}

//循环方式----delete
	public boolean deleteCycle(Integer cycleId) {

		Integer flag = cycleDao.deleteById(cycleId);
		if (flag != null) { return true; }
		return false;
	}

}
