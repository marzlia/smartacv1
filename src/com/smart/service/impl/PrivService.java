//权限  by zhouyu
package com.smart.service.impl;

import com.smart.core.Page;
import com.smart.po.Priv;
import com.smart.service.IPrivService;
import com.smart.dao.IPrivDao;

public class PrivService implements IPrivService {
	private IPrivDao privDao;

	public void setPrivDao(IPrivDao privDao) {
		this.privDao = privDao;
	}

//权限----add
	public Object savePriv(Priv priv) {

		return privDao.savePriv(priv);
	}

//权限----find
	public Page findByPage(Page page) {

		page.setRoot(privDao.findByPage(page));
		page.setTotalProperty(privDao.findByCount(page));
		return page;
	}

//权限----update
	public boolean updatePriv(Priv priv) throws Exception {

		Integer flag = privDao.update(priv);
		if (flag != null) { return true; }
		return false;
	}

//权限----delete
	public boolean deletePriv(Integer privId) {

		Integer flag = privDao.deleteById(privId);
		if (flag != null) { return true; }
		return false;
	}

}
