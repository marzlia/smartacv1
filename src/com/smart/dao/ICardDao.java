//门禁卡  by zhouyu
package com.smart.dao;

import java.util.List;

import com.smart.core.Page;
import com.smart.po.Card;

public interface ICardDao {
	public Object saveCard(Card card);

	public List findByPage(Page page);

	public int findByCount(Page page);

	public Integer update(Card card) throws Exception;

	public Integer deleteById(Integer cardId);

}
