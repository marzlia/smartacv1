//通关  by zhouyu
package com.smart.dao;

import java.util.List;

import com.smart.core.Page;
import com.smart.core.AccessPage;
import com.smart.core.Data;
import com.smart.core.AccessData;
import com.smart.po.Access;
import com.smart.po.Category;

public interface IAccessDao {
	public Object saveAccess(Access access);

	public List findByPage(AccessPage page);

	public int findByCount(AccessPage page);
	
	public Object findAccessCount(AccessData data);

	public List findAggList(Data data);
	
	public List findCategory(Category category);

	public int findAggCount(Data data);
	
	public Integer update(Access access) throws Exception;

	public Integer deleteById(Integer accessId);

}
