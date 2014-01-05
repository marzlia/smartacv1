//通关  by zhouyu
package com.smart.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import org.apache.log4j.Logger;
import com.smart.core.BaseAction;
import com.smart.core.MyUtils;
import com.smart.core.Page;
import com.smart.core.AccessPage;
import com.smart.core.Data;
import com.smart.core.AccessData;
import com.smart.core.MetaData;
import com.smart.po.Access;
import com.smart.po.Category;
import com.smart.service.IAccessService;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

@SuppressWarnings("serial")
public class AccessAction extends BaseAction {
	static Logger logger = Logger.getLogger(AccessAction.class);

	private Long accessId;
	private IAccessService accessService;

	private boolean success;
	private Access access;
	//private Page page;
	private Data data;
	private List categoryList;
	private AccessPage accessPage;
	private AccessData accessData;

	public AccessPage getAccessPage() {
		return accessPage;
	}

	public void setAccessPage(AccessPage accessPage) {
		this.accessPage = accessPage;
	}

	public AccessData getAccessData() {
		return accessData;
	}

	public void setAccessData(AccessData accessData) {
		this.accessData = accessData;
	}

	public Long getAccessId() {
		return accessId;
	}

	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}

	public IAccessService getAccessService() {
		return accessService;
	}

	public void setAccessService(IAccessService accessService) {
		this.accessService = accessService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Access getAccess() {
		return access;
	}

	public void setAccess(Access access) {
		this.access = access;
	}
	
	/*
	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}*/
	
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	
	

	public List getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List categoryList) {
		this.categoryList = categoryList;
	}

	//通关----add
	public String saveAccess() {

		accessId = (Long) accessService.saveAccess(access);
		if (accessId != null) { success = true;}
		return SUCCESS;
	}

	public String findAllCategory(){
		
		String key = getRequest().getParameter("key") == null ? "gateCampus" : getRequest().getParameter("key");
		//String parentKey = getRequest().getParameter("parentKey") == null ? "" : getRequest().getParameter("parentKey");
		//String parentValue = getRequest().getParameter("parentValue") == null ? "" : getRequest().getParameter("parentValue");
		
		
		String parent = getRequest().getParameter("parent") == null? "" : getRequest().getParameter("parent");
		logger.debug("Find all sub-category in parent category: (" + parent +")");
		
		List parentList = new ArrayList();
		MyUtils.addToCollection(parentList, MyUtils.split(parent, " ,"));
		
		Category category = new Category();
		category.setKey(key);
		//category.setParentKey(parentKey);
		//category.setParentValue(parentValue);
		category.setParentList(parentList);
		
		
		
		
		categoryList = accessService.findCategory(category);
		return SUCCESS;
	}
// 分组查询
	public String findAggregation(){
		data = new Data();
		
		String startDate = getRequest().getParameter("startDate") == null ? "" : getRequest().getParameter("startDate");
		String endDate = getRequest().getParameter("endDate") == null ? "" : getRequest().getParameter("endDate");
		String groupColname = getRequest().getParameter("groupColname") == null ? "" : getRequest().getParameter("groupColname");
		
		//logger.debug("Get access data from " + startDate + " to " + endDate);
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date startdt  = new Date();
		if(startDate.isEmpty()){
			startdt.setTime(startdt.getTime() - 24*60*60*1000);
		} else {
			try{
				startdt = dateFormat.parse(startDate);
			}
			catch(ParseException e){
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		
		Date enddt  = new Date();
		if(endDate.isEmpty()){//
		} else {
			try{
				enddt = dateFormat.parse(endDate);
			}
			catch(ParseException e){
				logger.debug(e.getMessage());
				e.printStackTrace();
			}
		}
		
		logger.debug("Get access data from " + startdt + " to " + enddt);
		logger.debug("Group access data by " + groupColname);
		
		data.setStart(startdt);
		data.setEnd(enddt);
		
		if(groupColname.isEmpty()){
			data.setGroupColumn("DATE_FORMAT(accessDatetime,'%Y-%m-%d %H%时')");
		}else if(groupColname.equals("年")){
			data.setGroupColumn("DATE_FORMAT(accessDatetime,'%Y%年')");
		}else if(groupColname.equals("月")){
			data.setGroupColumn("DATE_FORMAT(accessDatetime,'%Y-%m月')");
		}else if(groupColname.equals("周")){
			data.setGroupColumn("DATE_FORMAT(accessDatetime,'%Y-%U%周')");
		}else if(groupColname.equals("日")){
			data.setGroupColumn("DATE_FORMAT(accessDatetime,'%Y-%m-%d日')");
		}else if(groupColname.equals("时")){
			data.setGroupColumn("DATE_FORMAT(accessDatetime,'%Y-%m-%d %H%时')");
		}else{
			//data.setGroupColumn("DATE_FORMAT(accessDatetime,'%Y-%m-%d %H%时')");
			data.setGroupColumn(groupColname);
		}
		
		
		data = accessService.findAggregation(data);
		
		return SUCCESS;
	}

	//通关----find
	public String findAllAccess() {

		accessPage = new AccessPage();
		
		// Get Filters
		String filter = getRequest().getParameter("filter");
		List filterList = new ArrayList();
		if(null != filter){
			//JSONObject jsonObj = JSONObject.fromObject(filter);
			JSONArray jsonArr = JSONArray.fromObject(filter);
			int filterSize = jsonArr.size();
			logger.debug("Get " + filterSize + " filters");
			
			for (int i=0; i<filterSize; i++){
				JSONObject jsonObj = JSONObject.fromObject(jsonArr.get(i));
				logger.debug((i+1) + ": " + jsonObj);
				
				try{
					String filterType = jsonObj.getString("type");
					String field = jsonObj.getString("field");
					String value = jsonObj.getString("value");
					
				
					if(filterType.equals("numeric")){ // 数值类型
						String compare = jsonObj.getString("comparison");
						if(compare.equals("eq")){
							filterList.add(field + "=" + value);
						} else if(compare.equals("lt")){
							filterList.add(field + "<" + value);
						} else if(compare.equals("gt")){
							filterList.add(field + ">" + value);
						}
					} else if(filterType.equals("string")){
						filterList.add(field + " like '%" + value + "%'");
					} else if(filterType.equals("list")){
						List valueItems = new ArrayList();
						MyUtils.addToCollection(valueItems, MyUtils.split(value, ","));
						if(valueItems.size()>1){
							String valueStr = "";
							int j=0;
							for(j=0; j<valueItems.size()-1; j++){
								valueStr += "'" + valueItems.get(j) + "',";
							}
							valueStr += "'" + valueItems.get(j) + "'";
							filterList.add(field + " in (" + valueStr + ")");
						} else {
							filterList.add(field + "='" + value + "'");
						}
					}else if(filterType.equals("boolean")){
						filterList.add(field + "=" + value);
					}else if(filterType.equals("date")){
						String compare = jsonObj.getString("comparison");
						if(compare.equals("eq")){
							filterList.add(field + "='" + value + "'");
						} else if(compare.equals("lt")){
							filterList.add(field + "<'" + value + "'");
						} else if(compare.equals("gt")){
							filterList.add(field + ">'" + value + "'");
						}
					}
				
				} catch(net.sf.json.JSONException e){
					logger.debug(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		
		accessPage.setFilter(filterList);
		
		
		
		//String startDate = getRequest().getParameter("startDate") == null ? "" : getRequest().getParameter("startDate");
		//String endDate = getRequest().getParameter("endDate") == null ? "" : getRequest().getParameter("endDate");
		
		String sort = getRequest().getParameter("sort") == null ? "accessId" : getRequest().getParameter("sort");
		String dir = getRequest().getParameter("dir") == null ? "ASC" : getRequest().getParameter("dir");
		logger.debug("Sort access by: " + sort + " " +dir);
		accessPage.setObjCondition(sort + " " + dir);
		
		String strCondition = getRequest().getParameter("conditions") == null? "" :getRequest().getParameter("conditions");
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		
		accessPage.setConditions(conditions);
		int start = getRequest().getParameter("start") == null? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null? 0 :Integer.valueOf(getRequest().getParameter("limit"));
		accessPage.setStart(start);
		accessPage.setLimit(limit = limit == 0 ? 20 : limit);
		accessPage = accessService.findByAccessPage(accessPage);
		
		return SUCCESS;
	}
	
	//指定时间与区域----通关信息查询
	public String findAccess() {
		
		logger.debug("Find access data by below conditions: ");
		
		//
		accessPage = new AccessPage();
		
		// Add validation of datetime string.
		
		String startDate = getRequest().getParameter("startDate");
		
		logger.debug("Start datetime: " + startDate);
		accessPage.setStartDate(startDate);
		
		String endDate = getRequest().getParameter("endDate");
		logger.debug("End datetime: " + endDate);
		accessPage.setEndDate(endDate);
		
		String parent = getRequest().getParameter("parent") == null? "" : getRequest().getParameter("parent");
		logger.debug("Parent Category: " + parent);
		
		List parentList = new ArrayList();
		MyUtils.addToCollection(parentList, MyUtils.split(parent, " ,"));
		accessPage.setParentList(parentList);
		
		String key = getRequest().getParameter("key");
		logger.debug("Category Key: " + key);
		accessPage.setKey(key);
		
		String value = getRequest().getParameter("value") == null? "" : getRequest().getParameter("value");
		logger.debug("Category Value List: " + value);
		
		List valueList = new ArrayList();
		MyUtils.addToCollection(valueList, MyUtils.split(value, " ,"));
		accessPage.setValueList(valueList);
		
		
		int start = getRequest().getParameter("start") == null? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null? 0 :Integer.valueOf(getRequest().getParameter("limit"));
		accessPage.setStart(start);
		accessPage.setLimit(limit = limit == 0 ? 20 : limit);
		
		String sort = getRequest().getParameter("sort") == null ? "accessId" : getRequest().getParameter("sort");
		String dir = getRequest().getParameter("dir") == null ? "ASC" : getRequest().getParameter("dir");
		logger.debug("Sort access by: " + sort + " " +dir);
		accessPage.setObjCondition(sort + " " + dir);
		
		accessPage = accessService.findByAccessPage(accessPage);
		
		logger.debug("Total Property : " + accessPage.getTotalProperty());
		
		return SUCCESS;
	}
	
	public String findAccessCount() {
		
		logger.debug("Find access count by below conditions: ");
		
		accessData = new AccessData();
		
		String timeGrain = getRequest().getParameter("timeGrain");
		accessData.setTimeGrain(timeGrain);
		
		if(timeGrain.equals("month")){
			accessData.setTimeFormat("%Y-%m");
		}else {
			accessData.setTimeFormat("%Y-%m-%d");
		}
		
		String startDate = getRequest().getParameter("startDate");
		
		logger.debug("Start datetime: " + startDate);
		accessData.setStartDate(startDate);
		
		String endDate = getRequest().getParameter("endDate");
		logger.debug("End datetime: " + endDate);
		accessData.setEndDate(endDate);
		
		String parent = getRequest().getParameter("parent") == null? "" : getRequest().getParameter("parent");
		logger.debug("Parent Category: " + parent);
		
		List parentList = new ArrayList();
		MyUtils.addToCollection(parentList, MyUtils.split(parent, " ,"));
		accessData.setParentList(parentList);
		
		String key = getRequest().getParameter("key");
		logger.debug("Category Key: " + key);
		accessData.setKey(key);
		
		String value = getRequest().getParameter("value") == null? "" : getRequest().getParameter("value");
		logger.debug("Category Value List: " + value);
		
		List valueList = new ArrayList();
		MyUtils.addToCollection(valueList, MyUtils.split(value, " ,"));
		accessData.setValueList(valueList);
		
		try{
			accessData = accessService.findAccessCount(accessData);
		} catch (Exception e) {
			logger.debug("Error in findAccessCount: " + e.getMessage());
			accessData.setSuccess(false);
		}
	
		/*
		MetaData metaData = new MetaData();
		metaData.setRoot("results");
		metaData.setSuccessProperty("success");
		
		List fields = new ArrayList();
		
		Map field1 = new HashMap();
		field1.put("name","name");
		field1.put("type","string");
		fields.add(field1);
		
		Map field2 = new HashMap();
		field2.put("name","visits");
		field2.put("type","int");
		fields.add(field2);
		
		Map field3 = new HashMap();
		field3.put("name","校区A");
		field3.put("type","int");
		fields.add(field3);
		
		metaData.setFields(fields);
		
		accessData.setMetaData(metaData);
		
		accessData.setSuccess(true);
		
		List results = new ArrayList();
		
		Map r1 = new HashMap();
		r1.put("name", "Jul 07");
		r1.put("visits", 245000);
		r1.put("校区A", 3000000);
		results.add(r1);
		
		Map r2 = new HashMap();
		r2.put("name", "Aug 07");
		r2.put("visits", 240000);
		r2.put("校区A", 3500000);
		results.add(r2);
		
		Map r3 = new HashMap();
		r3.put("name", "Sep 07");
		r3.put("visits", 355000);
		r3.put("校区A", 4000000);
		results.add(r3);
		
		Map r4 = new HashMap();
		r4.put("name", "Oct 07");
		r4.put("visits", 375000);
		r4.put("校区A", 4200000);
		results.add(r4);
		
		accessData.setResults(results);*/
		
		return SUCCESS;
	}

//通关----update
	public String updateAccess() throws Exception {

		Access access = new Access();
		access.setAccessId(accessId);
		// 

		success = accessService.updateAccess(access);
		return SUCCESS;
	}

//通关----delete
	 public String deleteAccess() {

		String strId = getRequest().getParameter("accessId");
		if (strId != null && !"".equals(strId)) {
			success = accessService.deleteAccess(Integer.valueOf(strId));
		}
		return SUCCESS;
	}

}
