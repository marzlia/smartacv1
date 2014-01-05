//门禁卡  by zhouyu
package com.smart.action;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.smart.core.BaseAction;
import com.smart.core.MyUtils;
import com.smart.core.Page;
import com.smart.po.Card;
import com.smart.service.ICardService;

@SuppressWarnings("serial")
public class CardAction extends BaseAction {
	static Logger logger = Logger.getLogger(CardAction.class);

	private Long cardId;
	private ICardService cardService;
	private boolean success;
	private Card card;
	private Page page;
	
	

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public ICardService getCardService() {
		return cardService;
	}

	public void setCardService(ICardService cardService) {
		this.cardService = cardService;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	//门禁卡----add
	public String saveCard() {

		cardId = (Long) cardService.saveCard(card);
		if (cardId != null) { success = true;}
		return SUCCESS;
	}

//门禁卡----find
	public String findAllCard() {

		page = new Page();
		
		String sort = getRequest().getParameter("sort") == null ? "cardId" : getRequest().getParameter("sort");
		String dir = getRequest().getParameter("dir") == null ? "DESC" : getRequest().getParameter("dir");
		logger.debug("Sort access by: " + sort + " " +dir);
		page.setObjCondition(sort + " " + dir);
		
		String strCondition = getRequest().getParameter("conditions") == null? "" :getRequest().getParameter("conditions");
		List conditions = new ArrayList();
		MyUtils.addToCollection(conditions, MyUtils.split(strCondition, " ,"));
		page.setConditions(conditions);
		
		int start = getRequest().getParameter("start") == null? 0 : Integer.valueOf(getRequest().getParameter("start"));
		int limit = getRequest().getParameter("limit") == null? 0 :Integer.valueOf(getRequest().getParameter("limit"));
		page.setStart(start);
		page.setLimit(limit = limit == 0 ? 20 : limit);
		
		page = cardService.findByPage(page);
		return SUCCESS;
	}

//门禁卡----update
	public String updateCard() throws Exception {

		Card card = new Card();
		card.setCardId(cardId);
		// 

		success = cardService.updateCard(card);
		return SUCCESS;
	}

//门禁卡----delete
	 public String deleteCard() {

		String strId = getRequest().getParameter("cardId");
		if (strId != null && !"".equals(strId)) {
			success = cardService.deleteCard(Integer.valueOf(strId));
		}
		return SUCCESS;
	}

}
