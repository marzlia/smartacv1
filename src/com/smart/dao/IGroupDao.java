//用户组  by zhouyu
package com.smart.dao;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.Group;

public interface IGroupDao {
	public Object saveGroup(Group group);

	public List findByPage(Page page);

	public int findByCount(Page page);

	public Integer update(Group group) throws Exception;

	public Integer deleteById(Integer groupId);

}
