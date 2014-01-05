//权限分配  by zhouyu
package com.smart.dao;

import java.sql.SQLException;
import java.util.List;

import com.smart.core.Page;
import com.smart.po.Privassign;

public interface IPrivassignDao {
	public Object savePrivassign(Privassign privassign);
	
	public Object savePrivassignBatch(List<Privassign> privassigns);

	public List findByPage(Page page);

	public int findByCount(Page page);
	
	public int findByGroup(Integer groupId);

	public Integer update(Privassign privassign) throws Exception;

	public Integer deleteById(Integer privassignId);
	
	public Integer deleteBatch(List<Privassign> privassigns);

}
