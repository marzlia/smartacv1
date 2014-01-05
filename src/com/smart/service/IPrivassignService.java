//权限分配  by zhouyu
package com.smart.service;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.Privassign;

public interface IPrivassignService {
//权限分配----add an object
	Object savePrivassign(Privassign privassign);
	
	Object savePrivassignBatch(List<Privassign> privassigns);

//权限分配----find a page of objects
	Page findByPage(Page page);

//权限分配----update an object
	boolean updatePrivassign(Privassign privassign) throws Exception;

//权限分配----delete an object by id
	boolean deletePrivassign(Integer privassignId);
	
	Integer deleteBatch(List<Privassign> privassigns);

}
