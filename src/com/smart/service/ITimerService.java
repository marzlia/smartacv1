//定时信息  by zhouyu
package com.smart.service;

import com.smart.core.Page;
import com.smart.po.Timer;

public interface ITimerService {
//定时信息----add an object
	Object saveTimer(Timer timer);

//定时信息----find a page of objects
	Page findByPage(Page page);

//定时信息----update an object
	boolean updateTimer(Timer timer) throws Exception;

//定时信息----delete an object by id
	boolean deleteTimer(Integer timerId);

}
