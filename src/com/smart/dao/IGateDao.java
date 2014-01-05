//闸机  by zhouyu
package com.smart.dao;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.Gate;

public interface IGateDao {
	public Object saveGate(Gate gate);

	public List findByPage(Page page);

	public int findByCount(Page page);

	public Integer update(Gate gate) throws Exception;

	public Integer deleteById(Integer gateId);

}
