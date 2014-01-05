//监视器  by zhouyu
package com.smart.dao;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.Watcher;

public interface IWatcherDao {
	public Object saveWatcher(Watcher watcher);

	public List findByPage(Page page);

	public int findByCount(Page page);

	public Integer update(Watcher watcher) throws Exception;

	public Integer deleteById(Integer watcherId);

}
