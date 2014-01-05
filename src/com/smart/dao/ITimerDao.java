//定时信息  by zhouyu
package com.smart.dao;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.Timer;

public interface ITimerDao {
	public Object saveTimer(Timer timer);

	public List findByPage(Page page);

	public int findByCount(Page page);

	public Integer update(Timer timer) throws Exception;

	public Integer deleteById(Integer timerId);

}
