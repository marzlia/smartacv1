//黑名单  by zhouyu
package com.smart.dao;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.Block;

public interface IBlockDao {
	public Object saveBlock(Block block) throws Exception;

	public List findByPage(Page page);

	public int findByCount(Page page);

	public Integer update(Block block) throws Exception;

	public Integer deleteById(Integer blockId);

}
