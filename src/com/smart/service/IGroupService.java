//用户组  by zhouyu
package com.smart.service;

import com.smart.core.Page;
import com.smart.po.Group;

public interface IGroupService {
//用户组----add an object
	Object saveGroup(Group group);

//用户组----find a page of objects
	Page findByPage(Page page);
	
	int findUserByGroup(Integer groupId);
	
	int findPrivByGroup(Integer groupId);

//用户组----update an object
	boolean updateGroup(Group group) throws Exception;

//用户组----delete an object by id
	boolean deleteGroup(Integer groupId);

}
