package com.smart.action;

import com.smart.core.BaseAction;
import com.smart.core.MD5;
import org.apache.log4j.Logger;

import com.smart.po.User;
import com.smart.service.ISysService;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@SuppressWarnings("serial")
public class SysAction extends BaseAction {
	
	static Logger logger = Logger.getLogger(SysAction.class);
	
	private ISysService sysService;
	private User user;
	
	public ISysService getSysService() {
		return sysService;
	}

	public void setSysService(ISysService sysService) {
		this.sysService = sysService;
	}

	private String userName;
	private String userPassword;
	private boolean success;
	private String msg;
	private List modList;
	
	public List getModList() {
		return modList;
	}

	public void setModList(List modList) {
		this.modList = modList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public SysAction(){
		if(logger.isDebugEnabled())
			logger.debug("Enter into constructor of SysAction");
	}
			
	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	
	public String login() {
			
		logger.debug("Enter into login function of SysAction");
		logger.debug("The UserName inputted is " + userName);
		
		user = new User();
		
		user.setUserName(userName);
		user.setUserPassword(MD5.md5String(userPassword));
		
		user = sysService.login(user);
		
		if(null == user){
			success = false;
			msg = "用户名或密码错误";
			getSession().removeAttribute("user");
		} else {
			logger.debug("The UserName queried is " + user.getUserName());
			success = true;
			msg = SUCCESS;
			getSession().setAttribute("user", user);
		}
		
		return SUCCESS;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String logout() {
		
		logger.debug("Enter into logout function of SysAction");
		//MD5 md5 = new MD5();
		getSession().removeAttribute("user");
		success = true;
		return SUCCESS;
	}
	
	// 查找登录用户的权限
	public String findModule(){
		
		logger.debug("Find modules which current login user could access :");
		
		User _user = (User)this.getSession().getAttribute("user");
		
		if(null == _user){
			success = false;
			msg = "用户未登录";
			logger.debug("No login user");
		} else {
			logger.debug("Current user is " + _user.getUserName());
			success = true;
			
			modList = new ArrayList();
			
			// Query Priv List of login user
			List privList = sysService.findUserPriv(_user.getUserId());
			
			for (int i=0; i<privList.size(); i++){
				String privName = (String)privList.get(i);
				if(privName.equals("PRIV_QUERY")){
					// Add Query module to module list : BEGIN
					Map queryMod = new HashMap();
					queryMod.put("text", "数据查询");
					queryMod.put("expanded", true);
					
					
					List queryChildren = new ArrayList();
					
					Map accessdetailMod = new HashMap();
					accessdetailMod.put("id", "accessdetail");
					accessdetailMod.put("text", "数据明细");
					accessdetailMod.put("leaf", true);
					queryChildren.add(accessdetailMod);
					
					Map accessviewMod = new HashMap();
					accessviewMod.put("id", "accessview");
					accessviewMod.put("text", "信息视图");
					accessviewMod.put("leaf", true);
					queryChildren.add(accessviewMod);
					
					Map accessanalysisMod = new HashMap();
					accessanalysisMod.put("id", "accessanalysis");
					accessanalysisMod.put("text", "维度分析");
					accessanalysisMod.put("leaf", true);
					queryChildren.add(accessanalysisMod);
					
					Map alarmMod = new HashMap();
					alarmMod.put("id", "alarm");
					alarmMod.put("text", "警告追忆");
					alarmMod.put("leaf", true);
					queryChildren.add(alarmMod);
					
					Map failMod = new HashMap();
					failMod.put("id", "fail");
					failMod.put("text", "故障分析");
					failMod.put("leaf", true);
					queryChildren.add(failMod);
					
					Map gateMod = new HashMap();
					gateMod.put("id", "gate");
					gateMod.put("text", "闸机监视");
					gateMod.put("leaf", true);
					queryChildren.add(gateMod);
					
					Map cardMod = new HashMap();
					cardMod.put("id", "card");
					cardMod.put("text", "门卡明细");
					cardMod.put("leaf", true);
					queryChildren.add(cardMod);
					
					
					queryMod.put("children", queryChildren);
					modList.add(queryMod);
					// Add Query module to module list: END
				} else if (privName.equals("PRIV_USER")){
					// Add Query module to module list : BEGIN
					Map queryMod = new HashMap();
					queryMod.put("text", "用户管理");
					queryMod.put("expanded", true);
					
					List queryChildren = new ArrayList();
									
					Map userMod = new HashMap();
					userMod.put("id", "user");
					userMod.put("text", "用户管理");
					userMod.put("leaf", true);
					queryChildren.add(userMod);
					
					Map groupMod = new HashMap();
					groupMod.put("id", "group");
					groupMod.put("text", "用户组管理");
					groupMod.put("leaf", true);
					queryChildren.add(groupMod);
					
					queryMod.put("children", queryChildren);
					modList.add(queryMod);
					// Add Query module to module list: END
				} else if (privName.equals("PRIV_SETTING")){
					Map settingMod = new HashMap();
					settingMod.put("text", "系统配置");
					settingMod.put("expanded", true);
					
					List settingChildren = new ArrayList();
					
					Map gatemanageMod = new HashMap();
					gatemanageMod.put("id", "gatemanage");
					gatemanageMod.put("text", "闸机管理");
					gatemanageMod.put("leaf", true);
					settingChildren.add(gatemanageMod);
					
					Map watcherMod = new HashMap();
					watcherMod.put("id", "watcher");
					watcherMod.put("text", "监视管理");
					watcherMod.put("leaf", true);
					settingChildren.add(watcherMod);
					
					Map timerMod = new HashMap();
					timerMod.put("id", "timer");
					timerMod.put("text", "数据更新设置");
					timerMod.put("leaf", true);
					settingChildren.add(timerMod);
					
					Map blockMod = new HashMap();
					blockMod.put("id", "block");
					blockMod.put("text", "黑名单");
					blockMod.put("leaf", true);
					settingChildren.add(blockMod);
					
					settingMod.put("children", settingChildren);
					modList.add(settingMod);
				}
			}
			
			
			
		}
		
		return SUCCESS;
	}
	
	public String setGateParam(){
		
		return SUCCESS;
	}
	
	public String setGateScreen(){
		
		return SUCCESS;
	}

}
