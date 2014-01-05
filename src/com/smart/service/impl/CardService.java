//门禁卡  by zhouyu
package com.smart.service.impl;

import com.smart.core.Page;
import com.smart.po.Card;
import com.smart.service.ICardService;
import com.smart.dao.ICardDao;

public class CardService implements ICardService {
	private ICardDao cardDao;

	public void setCardDao(ICardDao cardDao) {
		this.cardDao = cardDao;
	}

//门禁卡----add
	public Object saveCard(Card card) {

		return cardDao.saveCard(card);
	}

//门禁卡----find
	public Page findByPage(Page page) {

		page.setRoot(cardDao.findByPage(page));
		page.setTotalProperty(cardDao.findByCount(page));
		return page;
	}

//门禁卡----update
	public boolean updateCard(Card card) throws Exception {

		Integer flag = cardDao.update(card);
		if (flag != null) { return true; }
		return false;
	}

//门禁卡----delete
	public boolean deleteCard(Integer cardId) {

		Integer flag = cardDao.deleteById(cardId);
		if (flag != null) { return true; }
		return false;
	}

}
