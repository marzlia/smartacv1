//闸机  by zhouyu
package com.smart.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.smart.core.BaseAction;
import com.smart.core.MyUtils;
import com.smart.core.Page;
import com.smart.po.Gate;
import com.smart.po.Image;
import com.smart.service.IGateService;

import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class GateAction extends BaseAction {
	static Logger logger = Logger.getLogger(GateAction.class);
	static final int SET_TIMEOUT = 30; // second
	static final int DATA_SIZE = 7;
	

	private Long gateId;
	private IGateService gateService;
	private boolean success;
	public String getGateIp() {
		return gateIp;
	}

	public void setGateIp(String gateIp) {
		this.gateIp = gateIp;
	}

	public String getGateMac() {
		return gateMac;
	}

	public void setGateMac(String gateMac) {
		this.gateMac = gateMac;
	}

	public String getGateCampus() {
		return gateCampus;
	}

	public void setGateCampus(String gateCampus) {
		this.gateCampus = gateCampus;
	}

	public String getGateRoom() {
		return gateRoom;
	}

	public void setGateRoom(String gateRoom) {
		this.gateRoom = gateRoom;
	}

	private Gate gate;
	private Page page;
	
	private String gateIp; // 地址
	private String gateMac; // 物理地址
	private String gateCampus; // 校区
	private String gateRoom; // 房间
	
	
	public Long getGateId() {
		return gateId;
	}

	public void setGateId(Long gateId) {
		this.gateId = gateId;
	}

	public IGateService getGateService() {
		return gateService;
	}

	public void setGateService(IGateService gateService) {
		this.gateService = gateService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Gate getGate() {
		return gate;
	}

	public void setGate(Gate gate) {
		this.gate = gate;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	//闸机----add
	public String saveGate() {

		gateId = (Long) gateService.saveGate(gate);
		if (gateId != null) { success = true;}
		return SUCCESS;
	}

//闸机----find
	public String findAllGate() {

		page = new Page();
		
		String sort = getRequest().getParameter("sort") == null ? "gateId" : getRequest().getParameter("sort");
		String dir = getRequest().getParameter("dir") == null ? "ASC" : getRequest().getParameter("dir");
		logger.debug("Sort gate by: " + sort + " " +dir);
		page.setObjCondition(sort + " " + dir);
		
		String strCondition = getRequest().getParameter("conditions") == null? "" :getRequest().getParameter("conditions");
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		page.setConditions(conditions);
		
		
		int start = getRequest().getParameter("start") == null? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null? 0 :Integer.valueOf(getRequest().getParameter("limit"));
		page.setStart(start);
		page.setLimit(limit = limit == 0 ? 20 : limit);
		page = gateService.findByPage(page);
		return SUCCESS;
	}

//闸机----update
	public String updateGate() throws Exception {

		Gate gate = new Gate();
		
		gate.setGateId(gateId);
		gate.setGateCampus(gateCampus);
		gate.setGateIp(gateIp);
		gate.setGateMac(gateMac);
		gate.setGateRoom(gateRoom);
		
		success = gateService.updateGate(gate);
		return SUCCESS;
	}

//闸机----delete
	 public String deleteGate() {
		String strId = getRequest().getParameter("gateId");
		logger.debug("Delete a gate where gateId = " + strId);
		if (strId != null && !"".equals(strId)) {
			success = gateService.deleteGate(Integer.valueOf(strId));
		}
		return SUCCESS;
	}
	 
	 private File upload;
	 

	 public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	private String uploadContentType;// 上传文件类型
	 private String uploadFileName; // 上传文件名
	 private String allowedTypes;// 允许上传的文件类型列表
	 private String savePath;// 文件保存路径,通过ioc注入
	 private float maxHeightSize;// 缩略图最大高度
	 private float maxWidthSize;//缩略图最大宽度
		
	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getAllowedTypes() {
		return allowedTypes;
	}

	public void setAllowedTypes(String allowedTypes) {
		this.allowedTypes = allowedTypes;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public float getMaxHeightSize() {
		return maxHeightSize;
	}

	public void setMaxHeightSize(float maxHeightSize) {
		this.maxHeightSize = maxHeightSize;
	}

	public float getMaxWidthSize() {
		return maxWidthSize;
	}

	public void setMaxWidthSize(float maxWidthSize) {
		this.maxWidthSize = maxWidthSize;
	}

	private String upload(){
		
		logger.debug("In upload");
		
			String _imageUrl = null;
			logger.debug("getUploadContentType: " + getUploadContentType());
			logger.debug("getAllowedTypes: " + getAllowedTypes());
			
			if (MyUtils.isValid(getUploadContentType(), getAllowedTypes().split(","))) {
				
				
				
				String rootPath = getSession().getServletContext().getRealPath("/");
				String savePath = rootPath + getSavePath();
				
				logger.debug("savePath: " + savePath);
				
				MyUtils.mkDirectory(savePath);
				String newFileName = MyUtils.upload(getUploadFileName(), savePath, getUpload());
				
				
				_imageUrl = newFileName;
				/*
				try {
					MyUtils.createMiniPic(new File(savePath + newFileName), maxWidthSize,maxHeightSize);
				} catch (IOException e) {
					e.printStackTrace();
				}*/
				
			}
			return _imageUrl;
		}
	 
	 public String setScreen() {
		 
		 logger.debug("In setScreen");
		 logger.debug("ContentType of response: " + this.getResponse().getContentType());
		 
		 String fileName = this.upload();
		 logger.debug(fileName + " is saved");
		 
		 if(null == fileName){
			 success = false;
			 this.jsonString = "{success:false}";
			 this.getRequest().setAttribute("jsonString", jsonString);
		 }else{
			 success = true;
			 this.jsonString = "{success:true,imageUrl:'" + fileName + "'}";
			 this.getRequest().setAttribute("jsonString", jsonString);
		 }
		 
		 //this.getResponse().setContentType("text/html; charset=UTF-8");
		 logger.debug("ContentType of response: " + this.getResponse().getContentType());
		 
		 //this.outString("{success:true}");
		 
		 return SUCCESS;
	 }

	 private String errMsg;
	 
	 public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String setGateSreen() throws UnknownHostException, IOException{
		 
		 String strGateIp = getRequest().getParameter("gateIp");
		 String strGateScreen = getRequest().getParameter("gateScreen");
		 
		 logger.debug("In setGateSreen, set " + strGateScreen + " to gate(IP: " + strGateIp + ")");
		 
		 if(null != strGateIp && null != strGateScreen){
			 //
			 Socket socket = null;
			 //PrintWriter out = null;
			 try{
				 socket = new Socket(strGateIp,10018);
				 socket.setSendBufferSize(255);
				 
				 socket.getOutputStream().write(strGateScreen.getBytes());
				 socket.getOutputStream().flush();
				 //out = new PrintWriter(socket.getOutputStream(),true);
				 //out.print(strGateScreen + "0");
				 //out.flush();
			 } catch (UnknownHostException e){
				 errMsg = "发生UnknownHostException异常";
				 logger.error(e.getMessage());
				 e.printStackTrace();
			 }catch(IOException e){
				 errMsg = "发生IOException异常";
				 logger.error(e.getMessage());
				 e.printStackTrace();
			 } 
			 finally {
				 
				/* logger.debug("Close output stream of socket");
				 if(null != out){
					 out.close();
					 out = null;
				 }*/
				 
				 logger.debug("Close socket");
				 if(null != socket){
					 socket.close();
					 socket = null;
				 }
				 
				
			 }
		 } else {
			 errMsg = "请求参数错误";
		 }
		 
		 if(errMsg != null){
			 success = false;
			 logger.error(errMsg);
		 } else {
			 logger.debug("set screen successfully");
			 success = true;
		 }
		 
		 return SUCCESS;
	 }
	
	public String clearGateSreen() throws UnknownHostException, IOException{
		 
		 String strGateIp = getRequest().getParameter("gateIp");
		 
		 logger.debug("In clearGateSreen, clear screen of gate(IP: " + strGateIp + ")");
		 
		 if(null != strGateIp){
			 //
			 Socket socket = null;
			 
			 try{
				 socket = new Socket(strGateIp,10018);
				 socket.setSendBufferSize(1);
				 
				 socket.getOutputStream().write((byte)0x00);
				 socket.getOutputStream().flush();
				 
			 } catch (UnknownHostException e){
				 errMsg = "发生UnknownHostException异常";
				 logger.error(e.getMessage());
				 e.printStackTrace();
			 }catch(IOException e){
				 errMsg = "发生IOException异常";
				 logger.error(e.getMessage());
				 e.printStackTrace();
			 } 
			 finally {
				 
				 logger.debug("Close socket");
				 if(null != socket){
					 socket.close();
					 socket = null;
				 }
			 }
		 } else {
			 errMsg = "请求参数错误";
		 }
		 
		 if(errMsg != null){
			 success = false;
			 logger.error(errMsg);
		 } else {
			 logger.debug("Clear screen successfully");
			 success = true;
		 }
		 
		 return SUCCESS;
	 }
	
	public String findAllScreen(){
		
		page = new Page();
		
		String alarmImagePath =  getSession().getServletContext().getRealPath("/") + "system\\screen\\";
		logger.debug("find All screen image occured at " + alarmImagePath);
		
		List<String> arrayList = MyUtils.getListFiles(alarmImagePath,null,false);
		
		int count = arrayList.size();
		
		logger.debug("find  " + count + " screen images");
		page.setTotalProperty(count);
		
		List root = new ArrayList();
		
		for(int i =0; i< count; i++){
			
			File  f = new File(arrayList.get(i));
			logger.debug(f.toURI());
			
			if(f.isFile()){
				
				Map screen = new HashMap();
				screen.put("name", f.getName());
				
				root.add(screen);
				
			}
		}
		
		page.setRoot(root);
		
		
		
		
		return SUCCESS;
	}
	
	public String setGate() throws UnknownHostException, IOException{
		 
		
		 String strParam = this.requesetjson();
		 //String strgateParam = getRequest().getParameter("gateParam");
		 
		 logger.debug("In setGate, request parameter: " + strParam );
		 JSONObject jsonObject = JSONObject.fromObject(strParam);
		 String strGateIp = jsonObject.getString("gateIp");
		 logger.debug("gateIp: " + strGateIp);
		 JSONObject gateParam = (JSONObject)jsonObject.get("gateParam");
		 String gateSetDelay1 = gateParam.getString("gateSetDelay1");
		 logger.debug("gateSetDelay1: " + gateSetDelay1);
		 String gateSetDelay2 = gateParam.getString("gateSetDelay2");
		 logger.debug("gateSetDelay2: " + gateSetDelay2);
		 String gateSetMaxcount = gateParam.getString("gateSetMaxcount");
		 logger.debug("gateSetMaxcount: " + gateSetMaxcount);
		 String gateSetState = gateParam.getString("gateSetState");
		 logger.debug("gateSetState: " + gateSetState);
		 
		 if(null != strGateIp){
			 //
			 Socket socket = null;
			 //PrintWriter out = null;
			 //BufferedReader in  = null;
			 try{
				 // Create socket connection with Gate server side
				 socket = new Socket(strGateIp,10016);
				 
				 // Set the send buffer size
				 socket.setSendBufferSize(DATA_SIZE);
				 logger.debug("The send buffer size of Socket is " + socket.getSendBufferSize());
				 
				 // 
				 socket.setReceiveBufferSize(DATA_SIZE);
				 logger.debug("The receive buffer size of Socket is " + socket.getReceiveBufferSize());
				 
				 //
				 socket.setSoTimeout(SET_TIMEOUT*1000);
				 logger.debug("The timeout of Socket read call is " + socket.getSoTimeout() + "ms");
				 
				 //out = new PrintWriter(socket.getOutputStream(),true);
				 //in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				 
				 try{
						logger.debug("Sleep one second ");
						Thread.sleep(1000);
				 } catch (InterruptedException e) {
					logger.warn(e.getMessage());
				 }
				 
				 // Set Delay1 of gate 设置开门不通过延时关门时间
				 if(!gateSetDelay1.isEmpty()){
					 logger.debug("Set gateSetDelay1");
					 byte toSend[] = {0x01,0x03,0x00,0x00,0x00,0x00,0x00};
					 toSend[2] = (byte)Integer.valueOf(gateSetDelay1).intValue();
					 toSend[DATA_SIZE-1] = MyUtils.CRC8_cal(toSend, DATA_SIZE-1);
					 logger.debug("Send Bytes Data: " + MyUtils.ByteArrayToHexString(toSend, DATA_SIZE));
					 socket.getOutputStream().write(toSend,0,DATA_SIZE);
					 socket.getOutputStream().flush();
					 
					 try{
						logger.debug("Sleep one second ");
						Thread.sleep(1000);
					 } catch (InterruptedException e) {
						logger.warn(e.getMessage());
					 }
					 logger.debug("Begin to wait for response from server side");
					 // Wait for 30s to
					 int i = 0;
					 byte recv[] = new byte[DATA_SIZE];
				 
					 int recvCount = socket.getInputStream().read(recv,0,DATA_SIZE);
					 logger.debug("Read Bytes Count: " + recvCount);
					 logger.debug("Read Bytes Data: " + MyUtils.ByteArrayToHexString(recv, recvCount));
	
						// Integrity validate
					 if(recvCount < DATA_SIZE){
						 throw new GateException("设置开门不通过延时关门时间,反馈数据不完整(" + recvCount + "字节)");
					 }
						// CRC8 Validate
					 if(recv[DATA_SIZE-1] != MyUtils.CRC8_cal(recv, DATA_SIZE-1)){
						 throw new GateException("设置开门不通过延时关门时间,反馈数据CRC8校验失败");
					 }
						
					 	// Success validate
					 if(MyUtils.ByteArrayEqual(recv, toSend)){
						 logger.debug("Receive data is right, successfully set gate parameter");
					 } else if(recv[1] == (byte)0x43){
						 throw new GateException("设置开门不通过延时关门时间,发生闸门通信错误");
					 } else {
						 throw new GateException("设置开门不通过延时关门时间,发生未定义错误");
					 } 
				/*
					 logger.debug("Set gateSetDelay1");
					 char toSend[] = {0x01, 0x03,0x00,0x00,0x00,0x00,0x00};
					 toSend[2] = (char)Integer.valueOf(gateSetDelay1).intValue();
					 toSend[DATA_SIZE-1] = MyUtils.CRC8_cal(toSend, DATA_SIZE-1);
					 logger.debug("Send Bytes Data: " + MyUtils.ByteArrayToHexString(toSend, DATA_SIZE));
					 
					 //out.printf("%c%c%c%c", toSend[0], toSend[1], toSend[2], toSend[3]);
					 out.write(toSend,0,DATA_SIZE);
					 out.flush();
					 
					 logger.debug("Begin to wait for response from server side");
					 // Wait for 30s to
					 int i = 0;
					 while(i < SET_TIMEOUT){
						try{
							logger.debug("Sleep one second: " + (i+1));
							
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							logger.warn(e.getMessage());
						}
						if(in.ready()){
							logger.debug("Read response data");
							char res[] = new char[DATA_SIZE];
							//int resCount = in.read(res,0,DATA_SIZE);
							int resCount = in.read(res);
							
							
							logger.debug("Read Bytes Count: " + resCount);
							logger.debug("Read Bytes Data: " + MyUtils.ByteArrayToHexString(res, resCount));
							
							// Integrity validate
							if(resCount < DATA_SIZE){
								throw new GateException("设置开门不通过延时关门时间,反馈数据不完整(" + resCount + "字节)");
							}
							// CRC8 Validate
							if(res[3] != MyUtils.CRC8_cal(res, 3)){
								throw new GateException("设置开门不通过延时关门时间,反馈数据CRC8校验失败");
							}
							
							// Success validate
							if(res[0] == toSend[0] && res[1] == toSend[1]
							&& res[2] == toSend[2] && res[3] == toSend[3]){
								break;
							} else if(res[1] == 0x43){
								throw new GateException("设置开门不通过延时关门时间,发生闸门通信错误");
							} else {
								throw new GateException("设置开门不通过延时关门时间,发生未定义错误");
							}
						}
						i++;
					 }
					 if(i == SET_TIMEOUT){
						 logger.debug("Throw a timeout excetpion");
						 throw new GateException("设置开门不通过延时关门时间,获取反馈数据超时(" + SET_TIMEOUT + "秒)");
					 }*/
				 }
				 
				 if(!gateSetDelay2.isEmpty()){
					 
					 logger.debug("Set gateSetDelay2");
					 byte toSend[] = {0x01,0x04,0x00,0x00,0x00,0x00,0x00};
					 toSend[2] = (byte)(Integer.valueOf(gateSetDelay2).intValue()/5); // The Multiples of 5 ms
					 toSend[DATA_SIZE-1] = MyUtils.CRC8_cal(toSend, DATA_SIZE-1);
					 logger.debug("Send Bytes Data: " + MyUtils.ByteArrayToHexString(toSend, DATA_SIZE));
					 socket.getOutputStream().write(toSend,0,DATA_SIZE);
					 socket.getOutputStream().flush();
					 
					 try{
						logger.debug("Sleep one second ");
						Thread.sleep(1000);
					 } catch (InterruptedException e) {
						logger.warn(e.getMessage());
					 }
					 logger.debug("Begin to wait for response from server side");
					 // Wait for 30s to
					 int i = 0;
					 byte recv[] = new byte[DATA_SIZE];
				 
					 int recvCount = socket.getInputStream().read(recv,0,DATA_SIZE);
					 logger.debug("Read Bytes Count: " + recvCount);
					 logger.debug("Read Bytes Data: " + MyUtils.ByteArrayToHexString(recv, recvCount));
	
						// Integrity validate
					 if(recvCount < DATA_SIZE){
						 throw new GateException("设置通过后关门时间,反馈数据不完整(" + recvCount + "字节)");
					 }
						// CRC8 Validate
					 if(recv[DATA_SIZE-1] != MyUtils.CRC8_cal(recv, DATA_SIZE-1)){
						 throw new GateException("设置通过后关门时间,反馈数据CRC8校验失败");
					 }
						
					 	// Success validate
					 if(MyUtils.ByteArrayEqual(recv, toSend)){
						 logger.debug("Receive data is right, successfully set gate parameter");
					 } else if(recv[1] == (byte)0x44){
						 throw new GateException("设置通过后关门时间,发生闸门通信错误");
					 } else {
						 throw new GateException("设置通过后关门时间,发生未定义错误");
					 } 
				 }
				 
				 if(!gateSetMaxcount.isEmpty()){
					 
					 logger.debug("Set gateSetMaxcount");
					 byte toSend[] = {0x01,0x05,0x00,0x00,0x00,0x00,0x00};
					 toSend[2] = (byte)Integer.valueOf(gateSetMaxcount).intValue();
					 toSend[DATA_SIZE-1] = MyUtils.CRC8_cal(toSend, DATA_SIZE-1);
					 logger.debug("Send Bytes Data: " + MyUtils.ByteArrayToHexString(toSend, DATA_SIZE));
					 socket.getOutputStream().write(toSend,0,DATA_SIZE);
					 socket.getOutputStream().flush();
					 
					 try{
						logger.debug("Sleep one second ");
						Thread.sleep(1000);
					 } catch (InterruptedException e) {
						logger.warn(e.getMessage());
					 }
					 logger.debug("Begin to wait for response from server side");
					 // Wait for 30s to
					 int i = 0;
					 byte recv[] = new byte[DATA_SIZE];
				 
					 int recvCount = socket.getInputStream().read(recv,0,DATA_SIZE);
					 logger.debug("Read Bytes Count: " + recvCount);
					 logger.debug("Read Bytes Data: " + MyUtils.ByteArrayToHexString(recv, recvCount));
	
						// Integrity validate
					 if(recvCount < DATA_SIZE){
						 throw new GateException("设置最大未通过人数,反馈数据不完整(" + recvCount + "字节)");
					 }
						// CRC8 Validate
					 if(recv[DATA_SIZE-1] != MyUtils.CRC8_cal(recv, DATA_SIZE-1)){
						 throw new GateException("设置最大未通过人数,反馈数据CRC8校验失败");
					 }
						
					 	// Success validate
					 if(MyUtils.ByteArrayEqual(recv, toSend)){
						 logger.debug("Receive data is right, successfully set gate parameter");
					 } else if(recv[1] == (byte)0x45){
						 throw new GateException("设置最大未通过人数,发生闸门通信错误");
					 } else if(recv[1] == (byte)0x85){
						 throw new GateException("设置最大未通过人数,人数大于" + (int)(recv[1] & 0xff));
					 } else {
						 throw new GateException("设置最大未通过人数,发生未定义错误");
					 } 
				 }
				 
				 if(!gateSetState.isEmpty()){
					 logger.debug("Set gateSetState");
					 byte toSend[] = {0x01,0x02,0x00,0x00,0x00,0x00,0x00};
					 toSend[2] = (byte)Integer.valueOf(gateSetState).intValue();
					 toSend[DATA_SIZE-1] = MyUtils.CRC8_cal(toSend, DATA_SIZE-1);
					 logger.debug("Send Bytes Data: " + MyUtils.ByteArrayToHexString(toSend, DATA_SIZE));
					 socket.getOutputStream().write(toSend,0,DATA_SIZE);
					 socket.getOutputStream().flush();
					 
					 try{
						logger.debug("Sleep one second ");
						Thread.sleep(1000);
					 } catch (InterruptedException e) {
						logger.warn(e.getMessage());
					 }
					 logger.debug("Begin to wait for response from server side");
					 // Wait for 30s to
					 int i = 0;
					 byte recv[] = new byte[DATA_SIZE];
				 
					 int recvCount = socket.getInputStream().read(recv,0,DATA_SIZE);
					 logger.debug("Read Bytes Count: " + recvCount);
					 logger.debug("Read Bytes Data: " + MyUtils.ByteArrayToHexString(recv, recvCount));
	
						// Integrity validate
					 if(recvCount < DATA_SIZE){
						 throw new GateException("写工作状态,反馈数据不完整(" + recvCount + "字节)");
					 }
						// CRC8 Validate
					 if(recv[DATA_SIZE-1] != MyUtils.CRC8_cal(recv, DATA_SIZE-1)){
						 throw new GateException("写工作状态,反馈数据CRC8校验失败");
					 }
						
					 	// Success validate
					 if(MyUtils.ByteArrayEqual(recv, toSend)){
						 logger.debug("Receive data is right, successfully set gate parameter");
					 } else if(recv[1] == (byte)0x42){
						 throw new GateException("写工作状态,发生闸门通信错误");
					 } else if(recv[1] == (byte)0x82){
						 throw new GateException("写工作状态,闸门卡住没有到位");
					 } else {
						 throw new GateException("写工作状态,发生未定义错误");
					 }
				 }
				 
			 } catch (UnknownHostException e){
				 errMsg = "发生UnknownHostException";
				 logger.error(e.getMessage());
				 e.printStackTrace();
			 } catch(SocketTimeoutException e){
				 errMsg = "发生SocketTimeoutException";
				 logger.error(e.getMessage());
				 e.printStackTrace();
			 } catch(IOException e){
				 errMsg = "发生IOException";
				 logger.error(e.getMessage());
				 e.printStackTrace();
			 }  catch (GateException e){
				 errMsg = e.getMessage();
				 logger.error(e.getMessage());
			 }catch (Exception e){
				 errMsg = e.getMessage();
				 logger.error(e.getMessage());
				 e.printStackTrace();
			 }
			 finally {
				 
				 logger.debug("Close socket");
				 if(null != socket){
					 socket.close();
					 socket = null;
				 }
			 }
		 } else {
			 errMsg = "请求参数错误";
		 }
		 
		 if(errMsg != null){
			 success = false;
			 logger.error(errMsg);
		 } else {
			 logger.debug("set Gate Parameter successfully");
			 success = true;
		 }
		 
		 return SUCCESS;
	 }
	
	public class GateException extends Exception {
		public GateException(){
			
		}
		
		public GateException(String msg){
			super(msg);
		}
	}
}


