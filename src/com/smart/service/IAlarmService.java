//警告  by zhouyu
package com.smart.service;

import com.smart.core.Page;
import com.smart.po.Alarm;

public interface IAlarmService {
//警告----add an object
	Object saveAlarm(Alarm alarm);

//警告----find a page of objects
	Page findByPage(Page page);

//警告----update an object
	boolean updateAlarm(Alarm alarm) throws Exception;

//警告----delete an object by id
	boolean deleteAlarm(Integer alarmId);

}
