//闸机  by zhouyu
package com.smart.service;

import com.smart.core.Page;
import com.smart.po.Gate;

public interface IGateService {
//闸机----add an object
	Object saveGate(Gate gate);

//闸机----find a page of objects
	Page findByPage(Page page);

//闸机----update an object
	boolean updateGate(Gate gate) throws Exception;

//闸机----delete an object by id
	boolean deleteGate(Integer gateId);

}
