//权限  by zhouyu
package com.smart.dao;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.Priv;

public interface IPrivDao {
	public Object savePriv(Priv priv);

	public List findByPage(Page page);

	public int findByCount(Page page);

	public Integer update(Priv priv) throws Exception;

	public Integer deleteById(Integer privId);

}
