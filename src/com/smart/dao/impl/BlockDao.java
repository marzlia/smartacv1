//黑名单  by zhouyu
package com.smart.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.core.Page;
import com.smart.po.Block;
import com.smart.dao.IBlockDao;

public class BlockDao extends SqlMapClientDaoSupport implements IBlockDao {

//黑名单----删除对象
	public Integer deleteById(Integer blockId) {

		return getSqlMapClientTemplate().delete("Block.deleteById", blockId);
	}

//黑名单----获得对象总数
	public int findByCount(Page page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("Block.findByCount", page);
	}

//黑名单----获取一页对象
	public List findByPage(Page page) {

		return getSqlMapClientTemplate().queryForList("Block.findByPage", page);
	}

//黑名单----添加一个对象
	public Object saveBlock(Block block) throws Exception {

		return getSqlMapClientTemplate().insert("Block.save", block);
	}

//黑名单----更新一个对象
	public Integer update(Block block) throws Exception {

		return getSqlMapClientTemplate().update("Block.update", block);
	}

}
