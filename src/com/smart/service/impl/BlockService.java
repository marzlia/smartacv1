//黑名单  by zhouyu
package com.smart.service.impl;

import com.smart.core.Page;
import com.smart.po.Block;
import com.smart.service.IBlockService;
import com.smart.dao.IBlockDao;

public class BlockService implements IBlockService {
	private IBlockDao blockDao;

	public void setBlockDao(IBlockDao blockDao) {
		this.blockDao = blockDao;
	}

//黑名单----add
	public Object saveBlock(Block block) throws Exception {

		return blockDao.saveBlock(block);
	}

//黑名单----find
	public Page findByPage(Page page) {

		page.setRoot(blockDao.findByPage(page));
		page.setTotalProperty(blockDao.findByCount(page));
		return page;
	}

//黑名单----update
	public boolean updateBlock(Block block) throws Exception {

		Integer flag = blockDao.update(block);
		if (flag != null) { return true; }
		return false;
	}

//黑名单----delete
	public boolean deleteBlock(Integer blockId) {

		Integer flag = blockDao.deleteById(blockId);
		if (flag != null) { return true; }
		return false;
	}

}
