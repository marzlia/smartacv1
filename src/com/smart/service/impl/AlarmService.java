//警告  by zhouyu
package com.smart.service.impl;

import java.util.HashMap;
import java.util.List;
import com.smart.core.Page;
import com.smart.po.Alarm;
import com.smart.service.IAlarmService;
import com.smart.dao.IAlarmDao;

public class AlarmService implements IAlarmService {
	private IAlarmDao alarmDao;

	public void setAlarmDao(IAlarmDao alarmDao) {
		this.alarmDao = alarmDao;
	}

//警告----add
	public Object saveAlarm(Alarm alarm) {

		return alarmDao.saveAlarm(alarm);
	}

//警告----find
	public Page findByPage(Page page) {

		List rs = alarmDao.findByPage(page);
		
		HashMap obj = (HashMap)page.getObjCondition();
		
		if(obj.get("type") != null){
			if((Integer)obj.get("type") == 128 ){
				for(int i=0; i<rs.size(); i++){
					byte b0 = (byte) (0xFF & ((Alarm)rs.get(i)).getAlarmType().intValue());
					((Alarm)rs.get(i)).setAlarmCode(String.format("0x%02x",b0));
				}
			} else if ((Integer)obj.get("type") == 32768){
				for(int i=0; i<rs.size(); i++){
					byte b1 = (byte) (0xFF & ((Alarm)rs.get(i)).getAlarmType().intValue() >> 8 );
					((Alarm)rs.get(i)).setAlarmCode(String.format("0x%02x",b1));
				}
			}
		}
		
		page.setRoot(rs);
		page.setTotalProperty(alarmDao.findByCount(page));
		return page;
	}

//警告----update
	public boolean updateAlarm(Alarm alarm) throws Exception {

		Integer flag = alarmDao.update(alarm);
		if (flag != null) { return true; }
		return false;
	}

//警告----delete
	public boolean deleteAlarm(Integer alarmId) {

		Integer flag = alarmDao.deleteById(alarmId);
		if (flag != null) { return true; }
		return false;
	}

}
