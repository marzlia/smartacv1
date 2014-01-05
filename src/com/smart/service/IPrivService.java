//权限  by zhouyu
package com.smart.service;

import com.smart.core.Page;
import com.smart.po.Priv;

public interface IPrivService {
//权限----add an object
	Object savePriv(Priv priv);

//权限----find a page of objects
	Page findByPage(Page page);

//权限----update an object
	boolean updatePriv(Priv priv) throws Exception;

//权限----delete an object by id
	boolean deletePriv(Integer privId);

}
