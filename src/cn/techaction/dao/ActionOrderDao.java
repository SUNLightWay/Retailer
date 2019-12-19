package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionOrder;

public interface ActionOrderDao {
	/**
	 * �����û�id��ö�����Ϣ
	 * @param uid
	 * @return
	 */
	public List<ActionOrder> findOrderByUid(Integer uid);
	/**
	 * ���ݶ����Ų�ѯ������Ϣ
	 * @param orderNo
	 * @return
	 */
	public List<ActionOrder> searchOrders(Long orderNo);
	/**
	 * ���ݶ����Ż�ö�������
	 * @param orderNo
	 * @return
	 */
	public ActionOrder findOrderDetailByNo(Long orderNo);
}
