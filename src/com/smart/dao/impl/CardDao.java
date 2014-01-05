//门禁卡  by zhouyu
package com.smart.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.smart.core.Page;
import com.smart.po.Card;
import com.smart.dao.ICardDao;

public class CardDao extends SqlMapClientDaoSupport implements ICardDao {

//门禁卡----删除对象
	public Integer deleteById(Integer cardId) {

		return getSqlMapClientTemplate().delete("Card.deleteById", cardId);
	}

//门禁卡----获得对象总数
	public int findByCount(Page page) {

		return (Integer) getSqlMapClientTemplate().queryForObject("Card.findByCount", page);
	}

//门禁卡----获取一页对象
	public List findByPage(Page page) {

		return getSqlMapClientTemplate().queryForList("Card.findByPage", page);
	}

//门禁卡----添加一个对象
	public Object saveCard(Card card) {

		return getSqlMapClientTemplate().insert("Card.save", card);
	}

//门禁卡----更新一个对象
	public Integer update(Card card) throws Exception {

		return getSqlMapClientTemplate().update("Card.update", card);
	}

}
