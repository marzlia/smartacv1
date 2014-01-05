//权限分配  by zhouyu
package com.smart.dao.impl;

import java.sql.SQLException;
import java.util.List;
//import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.*;
import com.ibatis.sqlmap.client.SqlMapClient;

import com.smart.action.PrivassignAction;
import com.smart.core.Page;
import com.smart.po.Privassign;
import com.smart.dao.IPrivassignDao;

public class PrivassignDao extends SqlMapClientDaoSupport implements IPrivassignDao {
	
	static Logger logger = Logger.getLogger(PrivassignDao.class);

//权限分配----删除对象
	public Integer deleteById(Integer privassignId) {

		return getSqlMapClientTemplate().delete("Privassign.deleteById", privassignId);
	}

//权限分配----获得对象总数
	public int findByCount(Page page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("Privassign.findByCount", page);
	}

//权限分配----获取一页对象
	public List findByPage(Page page) {

		return getSqlMapClientTemplate().queryForList("Privassign.findByPage", page);
	}

//权限分配----添加一个对象
	public Object savePrivassign(Privassign privassign) {
		
		return getSqlMapClientTemplate().insert("Privassign.save", privassign);
	}

//权限分配----更新一个对象
	public Integer update(Privassign privassign) throws Exception {

		return getSqlMapClientTemplate().update("Privassign.update", privassign);
	}

	@Override
	public Object savePrivassignBatch(List<Privassign> privassigns){
		
		int count = 0; 
		SqlMapClient client = this.getSqlMapClient();
		try {  
			
			client.startTransaction(); 
			client.startBatch();  
			for (Privassign privassign : privassigns) {  
				try {  
					logger.debug("Insert a Privassign.save to Batch");
					client.insert("Privassign.save", privassign);  
			    } catch (Exception e) {  
			    	logger.debug("Error when insert a Privassign.save to Batch");
			    	logger.debug(e.getMessage());
			    }  	
			}  
			logger.debug("Execute Privassign.save's Batch");
			count = client.executeBatch();  
			
		} catch (SQLException e){
			logger.debug("Error when execute Privassign.save's Batch");
	    	logger.debug(e.getMessage());
		}
		finally {  
			try{
				client.endTransaction();  
			}catch (SQLException e){
				logger.debug("Error when stop transaction");
		    	logger.debug(e.getMessage());
			}
		}  
		
		return count;
	}

	@Override
	public Integer deleteBatch(List<Privassign> privassigns) {
		int count = 0; 
		SqlMapClient client = this.getSqlMapClient();
		try {  
			
			client.startTransaction(); 
			client.startBatch();  
			for (Privassign privassign : privassigns) {  
				try {  
					logger.debug("Insert a Privassign.deleteByGroupIdAndPrivId to Batch");
					client.delete("Privassign.deleteByGroupIdAndPrivId", privassign);  
			    } catch (Exception e) {  
			    	logger.debug("Error when insert a Privassign.deleteByGroupIdAndPrivId to Batch");
			    	logger.debug(e.getMessage());
			    }  	
			}  
			logger.debug("Execute Privassign.deleteByGroupIdAndPrivId's Batch");
			count = client.executeBatch();  
			
		} catch (SQLException e){
			logger.debug("Error when execute Privassign.deleteByGroupIdAndPrivId's Batch");
	    	logger.debug(e.getMessage());
		}
		finally {  
			try{
				client.endTransaction();  
			}catch (SQLException e){
				logger.debug("Error when stop transaction");
		    	logger.debug(e.getMessage());
			}
		}  
		
		return count;
	}

	@Override
	public int findByGroup(Integer groupId) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject("Privassign.findByGroup", groupId);
	}

}
