//循环方式  by zhouyu
package com.smart.dao;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.Cycle;

public interface ICycleDao {
	public Object saveCycle(Cycle cycle);

	public List findByPage(Page page);

	public int findByCount(Page page);

	public Integer update(Cycle cycle) throws Exception;

	public Integer deleteById(Integer cycleId);

}
