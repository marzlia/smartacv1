//黑名单  by zhouyu
package com.smart.service;

import com.smart.core.Page;
import com.smart.po.Block;

public interface IBlockService {
//黑名单----add an object
	Object saveBlock(Block block) throws Exception;

//黑名单----find a page of objects
	Page findByPage(Page page);

//黑名单----update an object
	boolean updateBlock(Block block) throws Exception;

//黑名单----delete an object by id
	boolean deleteBlock(Integer blockId);

}
