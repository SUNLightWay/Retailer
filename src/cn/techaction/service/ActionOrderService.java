package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionOrderVo;

public interface ActionOrderService {
	/**
	 * ��ѯ������Ϣ
	 * @param orderNo
	 * @return
	 */
	public SverResponse<List<ActionOrderVo>> findOrderForNoPages(Long orderNo);
	/**
	 * ���ݶ����Ż�ö�������
	 * @param orderNo
	 * @return
	 */
	public SverResponse<ActionOrderVo> mgrDetail(Long orderNo);
	
	/**
	 * ������ҳ�б�
	 * @param id
	 * @param status
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionOrderVo>> findOrders(Integer uid, Integer status, int pageNum, int pageSize);
	/**
	 * ȡ������
	 * @param id
	 * @param orderNo
	 * @return
	 */
	public SverResponse<String> cancelOrder(Integer uid, Long orderNo);
	/**
	 * ���ݱ�Ż�ȡ��������
	 * @param id
	 * @param orderNo
	 * @return
	 */
	public SverResponse<ActionOrderVo> findOrderDetail(Integer uid, Long orderNo);
	/**
	 * ���ɶ���
	 * @param id
	 * @param addrId
	 * @return
	 */
	public SverResponse<ActionOrderVo> generateOrder(Integer uid, Integer addrId);

}
