package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrderItem;

public interface ActionOrderItemDao {
	/**
	 * ���ݶ����Ż�ö�������
	 * @param orderNo
	 * @return
	 */
	public List<ActionOrderItem> getItemsByOrderNo(Long orderNo);
}
