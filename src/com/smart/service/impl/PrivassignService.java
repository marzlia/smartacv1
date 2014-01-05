//权限分配  by zhouyu
package com.smart.service.impl;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.Privassign;
import com.smart.service.IPrivassignService;
import com.smart.dao.IPrivassignDao;

public class PrivassignService implements IPrivassignService {
	private IPrivassignDao privassignDao;

	public void setPrivassignDao(IPrivassignDao privassignDao) {
		this.privassignDao = privassignDao;
	}

//权限分配----add
	public Object savePrivassign(Privassign privassign) {

		return privassignDao.savePrivassign(privassign);
	}

//权限分配----find
	public Page findByPage(Page page) {

		page.setRoot(privassignDao.findByPage(page));
		page.setTotalProperty(privassignDao.findByCount(page));
		return page;
	}

//权限分配----update
	public boolean updatePrivassign(Privassign privassign) throws Exception {

		Integer flag = privassignDao.update(privassign);
		if (flag != null) { return true; }
		return false;
	}

//权限分配----delete
	public boolean deletePrivassign(Integer privassignId) {

		Integer flag = privassignDao.deleteById(privassignId);
		if (flag != null) { return true; }
		return false;
	}

	@Override
	public Object savePrivassignBatch(List<Privassign> privassigns) {
		//
		return privassignDao.savePrivassignBatch(privassigns);
	}

	@Override
	public Integer deleteBatch(List<Privassign> privassigns) {
		// TODO Auto-generated method stub
		return privassignDao.deleteBatch(privassigns);
	}

}
