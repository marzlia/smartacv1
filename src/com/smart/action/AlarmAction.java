//警告  by zhouyu
package com.smart.action;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import com.smart.core.BaseAction;
import com.smart.core.MyUtils;
import com.smart.core.Page;
import com.smart.po.Alarm;
import com.smart.po.Image;
import java.io.File;
import com.smart.service.IAlarmService;

@SuppressWarnings("serial")
public class AlarmAction extends BaseAction {
	static Logger logger = Logger.getLogger(AlarmAction.class);

	private Long alarmId;
	private IAlarmService alarmService;
	private boolean success;
	private Alarm alarm;
	private Page page;
	
	public Long getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(Long alarmId) {
		this.alarmId = alarmId;
	}

	public IAlarmService getAlarmService() {
		return alarmService;
	}

	public void setAlarmService(IAlarmService alarmService) {
		this.alarmService = alarmService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	//警告----add
	public String saveAlarm() {
		
		alarmId = (Long) alarmService.saveAlarm(alarm);
		if (alarmId != null) { success = true;}
		return SUCCESS;
	}

//警告----find
	public String findAllAlarm() {
		//过滤功能有问题

		page = new Page();
		
		Map objCondition = new HashMap();
		
		String sort = getRequest().getParameter("sort") == null ? "alarmDatetime" : getRequest().getParameter("sort");
		String dir = getRequest().getParameter("dir") == null ? "DESC" : getRequest().getParameter("dir");
		logger.debug("Sort access by: " + sort + " " +dir);
		
		objCondition.put("sort", sort + " " + dir);
		
		String type = getRequest().getParameter("type");
		if(type != null && !type.isEmpty()){
			objCondition.put("type", Integer.valueOf(type));
		}
		
		page.setObjCondition(objCondition);
		
		
		
		String strCondition = getRequest().getParameter("conditions") == null? "" :getRequest().getParameter("conditions");
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		
		page.setConditions(conditions);
		int start = getRequest().getParameter("start") == null? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null? 0 :Integer.valueOf(getRequest().getParameter("limit"));
		page.setStart(start);
		page.setLimit(limit = limit == 0 ? 20 : limit);
		page = alarmService.findByPage(page);
		return SUCCESS;
	}
	
	public String findAllImage(){
		
		page = new Page();
		
		String strAlarmId = getRequest().getParameter("alarmId");
		String strAlarmDatetime = getRequest().getParameter("alarmDatetime");
		if(null == strAlarmDatetime){
			logger.warn("In action findAllImage, the parameter alarmDateTime is null");
		} else {
			
			String alarmImagePath =  getSession().getServletContext().getRealPath("/") + "system\\alarm\\" + strAlarmDatetime;
			logger.debug("find All alarm Image occured at " + alarmImagePath);
			
			List<String> arrayList = MyUtils.getListFiles(alarmImagePath,"jpg",true);
			
			int count = arrayList.size();
			
			logger.debug("find  " + count + " alarm images");
			page.setTotalProperty(count);
			
			List<Image> root = new ArrayList();
			
			for(int i =0; i< count; i++){
				
				
				
				File  f = new File(arrayList.get(i));
				logger.debug(f.toURI());
				
				if(f.isFile()){
					
					Image image1 = new Image();
					image1.setAlarmId(Long.valueOf(strAlarmId));
					image1.setImageId(f.getName());
					image1.setImageUrl("alarm/" + strAlarmDatetime + "/" + f.getName());
					image1.setImageSize(f.length()/1024.0);
					image1.setImageMdate(new Date(f.lastModified()));/**/
					
					root.add(image1);
					
				}
			}
			
			page.setRoot(root);
		}
		
		
		
		
		return SUCCESS;
	}

//警告----update
	public String updateAlarm() throws Exception {

		Alarm alarm = new Alarm();
		alarm.setAlarmId(alarmId);
		// 

		success = alarmService.updateAlarm(alarm);
		return SUCCESS;
	}

//警告----delete
	 public String deleteAlarm() {

		String strId = getRequest().getParameter("alarmId");
		if (strId != null && !"".equals(strId)) {
			success = alarmService.deleteAlarm(Integer.valueOf(strId));
		}
		return SUCCESS;
	}

}
