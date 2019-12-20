package cn.techaction.service;

import cn.techaction.common.SverResponse;
import cn.techaction.vo.ActionCartVo;

public interface ActionCartService {
	/**
	 * ������Ʒ��Ϣ�����ﳵ
	 * @param id
	 * @param productId
	 * @param count
	 * @return
	 */
	public SverResponse<String> saveOrUpdate(Integer userId, Integer productId, Integer count);
	/**
	 * ��ѯ�û����ﳵ����Ʒ��Ϣ
	 * @param id
	 * @return
	 */
	public SverResponse<ActionCartVo> findAllCarts(Integer userId);
	/**
	 * ��չ��ﳵ
	 * @param id
	 * @return
	 */
	public SverResponse<String> clearCart(Integer userId);
	/**
	 * ���¹��ﳵ����Ʒ������
	 * @param id
	 * @param productId
	 * @param productId2
	 * @param productId3
	 * @return
	 */
	public SverResponse<ActionCartVo> updateCart(Integer userId, Integer productId, Integer count, Integer checked);
	/**
	 * ɾ�����ﳵ�е���Ʒ��Ϣ
	 * @param userId
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionCartVo> deleteCart(Integer userId, Integer productId);
	/**
	 * ��ȡ��¼�û����ﳵ����Ʒ�ĸ���
	 * @param id
	 * @return
	 */
	public SverResponse<Integer> getCartCount(Integer userId);

}
