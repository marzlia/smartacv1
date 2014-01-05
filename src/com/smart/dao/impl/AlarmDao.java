//警告  by zhouyu
package com.smart.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.core.Page;
import com.smart.po.Alarm;
import com.smart.dao.IAlarmDao;

public class AlarmDao extends SqlMapClientDaoSupport implements IAlarmDao {

//警告----删除对象
	public Integer deleteById(Integer alarmId) {

		return getSqlMapClientTemplate().delete("Alarm.deleteById", alarmId);
	}

//警告----获得对象总数
	public int findByCount(Page page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("Alarm.findByCount", page);
	}

//警告----获取一页对象
	public List findByPage(Page page) {

		return getSqlMapClientTemplate().queryForList("Alarm.findByPage", page);
	}

//警告----添加一个对象
	public Object saveAlarm(Alarm alarm) {

		return getSqlMapClientTemplate().insert("Alarm.save", alarm);
	}

//警告----更新一个对象
	public Integer update(Alarm alarm) throws Exception {

		return getSqlMapClientTemplate().update("Alarm.update", alarm);
	}

}
