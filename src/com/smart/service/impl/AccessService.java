//通关  by zhouyu
package com.smart.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.smart.action.AccessAction;
import com.smart.core.AccessData;
import com.smart.core.AccessPage;
import com.smart.core.Data;
import com.smart.core.Page;
import com.smart.core.MetaData;
import com.smart.po.Access;
import com.smart.service.IAccessService;
import com.smart.dao.IAccessDao;
import com.smart.po.Category;

public class AccessService implements IAccessService {
	static Logger logger = Logger.getLogger(AccessService.class);
	
	private IAccessDao accessDao;

	public void setAccessDao(IAccessDao accessDao) {
		this.accessDao = accessDao;
	}

//通关----add
	public Object saveAccess(Access access) {

		return accessDao.saveAccess(access);
	}

//通关----update
	public boolean updateAccess(Access access) throws Exception {

		Integer flag = accessDao.update(access);
		if (flag != null) { return true; }
		return false;
	}

//通关----delete
	public boolean deleteAccess(Integer accessId) {

		Integer flag = accessDao.deleteById(accessId);
		if (flag != null) { return true; }
		return false;
	}

	@Override
	public Data findAggregation(Data data) {
		// TODO Auto-generated method stub
		data.setTotalProperty(accessDao.findAggCount(data));
		data.setRoot(accessDao.findAggList(data));
		
		return data;
	}

	@Override
	public List findCategory(Category category) {
		// TODO Auto-generated method stub
		return accessDao.findCategory(category);
	}

	@Override
	public AccessPage findByAccessPage(AccessPage page) {
		// 
		page.setRoot(accessDao.findByPage(page));
		page.setTotalProperty(accessDao.findByCount(page));
		
		return page;
	}

	@Override
	public AccessData findAccessCount(AccessData data) throws Exception {
		// Generate MetaData
		MetaData metaData = new MetaData();
		metaData.setRoot("results");
		metaData.setSuccessProperty("success");
		
		List fields = new ArrayList();
		
		Map field1 = new HashMap();
		field1.put("name","name");
		field1.put("type","string");
		fields.add(field1);
		
		for(int i=0; i<data.getValueList().size(); i++){
			Map field2 = new HashMap();
			field2.put("name",data.getKey() + "_" + data.getValueList().get(i));
			field2.put("type","int");
			fields.add(field2);
		}
		
		metaData.setFields(fields);
		data.setMetaData(metaData);
		
		// Query result from database
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		Date startDt = new Date();
		startDt = dateFormat.parse(data.getStartDate());
		
		Date endDt = new Date();
		endDt = dateFormat.parse(data.getEndDate());
	
		
		// Calendar of start date
		Calendar objCalendarDate1 = Calendar.getInstance();
		// Calendar of end date
		Calendar objCalendarDate2 = Calendar.getInstance();
		
		if(startDt.getTime() < endDt.getTime()){
			
			   objCalendarDate1.setTime(startDt);
			   objCalendarDate2.setTime(endDt);
		} else {
			objCalendarDate1.setTime(endDt);
			objCalendarDate2.setTime(startDt);
		}
		   //objCalendarDate1.setTime(startDt);
		   //objCalendarDate2.setTime(endDt);
		   
		List results = new ArrayList();
		
		
		if(data.getTimeGrain().equals("month")){
			for (int Y=objCalendarDate1.get(Calendar.YEAR); Y <= objCalendarDate2.get(Calendar.YEAR); Y++){
				int startM = 1;
				int endM = 12;
				if(Y == objCalendarDate1.get(Calendar.YEAR)){
					startM = objCalendarDate1.get(Calendar.MONTH)+1;
				}
				
				if(Y == objCalendarDate2.get(Calendar.YEAR)){
					endM = objCalendarDate2.get(Calendar.MONTH)+1;
				}
				
				for(int M = startM; M <= endM; M++ ){
					data.setName(String.format("%d-%02d",Y,M));
					logger.debug("Query access count in " + data.getName());
					//System.out.println(String.format("%d-%02d",Y,M));
					
					Map result = (HashMap)accessDao.findAccessCount(data);
					//logger.debug("The KV size of result is :" + result.size());
					//result.put("name", data.getName());
					results.add(result);
				}
			}
		} else {
			while (!objCalendarDate1.equals(objCalendarDate2)){
				data.setName(String.format("%d-%02d-%02d",objCalendarDate1.get(Calendar.YEAR),objCalendarDate1.get(Calendar.MONTH)+1,objCalendarDate1.get(Calendar.DAY_OF_MONTH)));
				logger.debug("Query access count in " + data.getName());
				
				Map result = (HashMap)accessDao.findAccessCount(data);
				results.add(result);
				
				objCalendarDate1.add(Calendar.DAY_OF_YEAR, 1);

			}
		}
		
		data.setResults(results);
		data.setSuccess(true);
		
		return data;
	}

}
