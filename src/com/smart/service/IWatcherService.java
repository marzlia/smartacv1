//监视器  by zhouyu
package com.smart.service;

import com.smart.core.Page;
import com.smart.po.Watcher;

public interface IWatcherService {
//监视器----add an object
	Object saveWatcher(Watcher watcher);

//监视器----find a page of objects
	Page findByPage(Page page);

//监视器----update an object
	boolean updateWatcher(Watcher watcher) throws Exception;

//监视器----delete an object by id
	boolean deleteWatcher(Integer watcherId);

}
