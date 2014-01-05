//黑名单  by zhouyu
package com.smart.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.smart.core.BaseAction;
import com.smart.core.MyUtils;
import com.smart.core.Page;
import com.smart.po.Block;
import com.smart.service.IBlockService;

@SuppressWarnings("serial")
public class BlockAction extends BaseAction {
	static Logger logger = Logger.getLogger(BlockAction.class);

	private Integer blockId;
	private String blockReason;
	private IBlockService blockService;
	private boolean success;
	private Block block;
	private Page page;

	public Integer getBlockId() {
		return blockId;
	}

	public void setBlockId(Integer blockId) {
		this.blockId = blockId;
	}

	public String getBlockReason() {
		return blockReason;
	}

	public void setBlockReason(String blockReason) {
		this.blockReason = blockReason;
	}

	public IBlockService getBlockService() {
		return blockService;
	}

	public void setBlockService(IBlockService blockService) {
		this.blockService = blockService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Block getBlock() {
		return block;
	}

	public void setBlock(Block block) {
		this.block = block;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	//黑名单----add
	public String saveBlock() {

		try{
			success = false;
			block.setBlockDatetime(new Date());
			blockId = (Integer) blockService.saveBlock(block);
			if (blockId != null) { 
				success = true;
			}
		} catch (Exception e){
			logger.error("Save a block failed, casued by " + e.getMessage());
			e.printStackTrace();
			
		}
		
		return SUCCESS;
	}

//黑名单----find
	public String findAllBlock() {

		page = new Page();
		
		String sort = getRequest().getParameter("sort") == null ? "blockId" : getRequest().getParameter("sort");
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
		page = blockService.findByPage(page);
		return SUCCESS;
	}

//黑名单----update
	public String updateBlock() throws Exception {

		try{
			success = false;
			
			Block block = new Block();
			block.setBlockId(blockId);
			//block.setBlockDatetime(new Date());
			block.setBlockReason(blockReason);
			
			success = blockService.updateBlock(block);
		} catch (Exception e){
			logger.error("Update a block{"+ blockId +"} failed, casued by " + e.getMessage());
			e.printStackTrace();
			
		}
		
		return SUCCESS;
	}

//黑名单----delete
	 public String deleteBlock() {

		String strId = getRequest().getParameter("blockId");
		if (strId != null && !"".equals(strId)) {
			success = blockService.deleteBlock(Integer.valueOf(strId));
		}
		return SUCCESS;
	}

}
