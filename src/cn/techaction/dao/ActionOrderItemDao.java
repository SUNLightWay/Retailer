package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrderItem;

public interface ActionOrderItemDao {
	/**
	 * 根据订单号获得订单详情
	 * @param orderNo
	 * @return
	 */
	public List<ActionOrderItem> getItemsByOrderNo(Long orderNo);
}
