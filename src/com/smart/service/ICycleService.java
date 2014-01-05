//循环方式  by zhouyu
package com.smart.service;

import com.smart.core.Page;
import com.smart.po.Cycle;

public interface ICycleService {
//循环方式----add an object
	Object saveCycle(Cycle cycle);

//循环方式----find a page of objects
	Page findByPage(Page page);

//循环方式----update an object
	boolean updateCycle(Cycle cycle) throws Exception;

//循环方式----delete an object by id
	boolean deleteCycle(Integer cycleId);

}
