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
	/**
	 * ��ȡ�û���������������״̬�µģ�
	 * @param uid
	 * @param status
	 * @return
	 */
    public int getTotalRecord(Integer uid, Integer status);
    /**
     * ��ȡ�û�������ҳ�б�
     * @param uid
     * @param status
     * @param startIndex
     * @param pageSize
     */
	public List<ActionOrder> findOrders(Integer uid, Integer status, int startIndex, int pageSize);
	/**
	 * �����û��Ͷ�����Ų�ѯ������Ϣ
	 * @param uid
	 * @param orderNo
	 * @return
	 */
	public ActionOrder findOrderByUserAndOrderNo(Integer uid, Long orderNo);
	/**
	 * ���¶�����Ϣ
	 * @param updateOrder
	 * @return
	 */
	public int updateOrder(ActionOrder updateOrder);
	/**
	 * ���涩����Ϣ
	 * @param order
	 * @return
	 */
	public int insertOrder(ActionOrder order);
}
