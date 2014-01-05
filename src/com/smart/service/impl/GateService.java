//闸机  by zhouyu
package com.smart.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.smart.core.Page;
import com.smart.po.Gate;
import com.smart.service.IGateService;
import com.smart.dao.IGateDao;

public class GateService implements IGateService {
	
	static Logger logger = Logger.getLogger(GateService.class);
	
	private IGateDao gateDao;

	public void setGateDao(IGateDao gateDao) {
		this.gateDao = gateDao;
	}

//闸机----add
	public Object saveGate(Gate gate) {

		return gateDao.saveGate(gate);
	}

//闸机----find
	public Page findByPage(Page page) {

		List rs = gateDao.findByPage(page);
		for(int i=0; i<rs.size(); i++){
			Gate gate = (Gate)rs.get(i);
			int status = Integer.valueOf(gate.getGateStatus());
			if((status & 0x00008080) == 0){
				gate.setGateStatus("状态正常");
			} else {
				byte b0 = (byte) (0xFF & status);
				byte b1 = (byte) (0xFF & (status >> 8));
				gate.setGateStatus(String.format("0x%02x%02x", b1,b0));
			}
		}
		
		page.setRoot(rs);
		page.setTotalProperty(gateDao.findByCount(page));
		return page;
	}

//闸机----update
	public boolean updateGate(Gate gate) throws Exception {

		Integer flag = gateDao.update(gate);
		if (flag != null) { return true; }
		return false;
	}

//闸机----delete
	public boolean deleteGate(Integer gateId) {

		Integer flag = gateDao.deleteById(gateId);
		logger.debug("The return flag of delete a gate by Id is : " + flag);
		if (flag != null && flag > 0) { 
			return true; 
		}
		return false;
	}

}
