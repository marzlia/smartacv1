//门禁卡  by zhouyu
package com.smart.service;

import com.smart.core.Page;
import com.smart.po.Card;

public interface ICardService {
//门禁卡----add an object
	Object saveCard(Card card);

//门禁卡----find a page of objects
	Page findByPage(Page page);

//门禁卡----update an object
	boolean updateCard(Card card) throws Exception;

//门禁卡----delete an object by id
	boolean deleteCard(Integer cardId);

}
