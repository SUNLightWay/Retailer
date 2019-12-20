package cn.techaction.vo;

import java.math.BigDecimal;
import java.util.List;

public class ActionCartVo {
	private List<ActionCartsListVo> lists;
	private BigDecimal totalPrice; //×Ü¼Û¸ñ
	public List<ActionCartsListVo> getLists() {
		return lists;
	}
	public void setLists(List<ActionCartsListVo> lists) {
		this.lists = lists;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
