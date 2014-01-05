//通关  by zhouyu
package com.smart.service;

import com.smart.core.Page;
import com.smart.core.Data;
import com.smart.core.AccessPage;
import com.smart.core.AccessData;
import com.smart.po.Access;
import com.smart.po.Category;
import java.util.List;

public interface IAccessService {
//通关----add an object
	Object saveAccess(Access access);
	
	AccessPage findByAccessPage(AccessPage page);
	
	AccessData findAccessCount(AccessData data) throws Exception;
	
	List findCategory(Category category);
	
	Data findAggregation(Data data);

//通关----update an object
	boolean updateAccess(Access access) throws Exception;

//通关----delete an object by id
	boolean deleteAccess(Integer accessId);

}
