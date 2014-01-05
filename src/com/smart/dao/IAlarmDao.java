//警告  by zhouyu
package com.smart.dao;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.Alarm;

public interface IAlarmDao {
	public Object saveAlarm(Alarm alarm);

	public List findByPage(Page page);

	public int findByCount(Page page);

	public Integer update(Alarm alarm) throws Exception;

	public Integer deleteById(Integer alarmId);

}
