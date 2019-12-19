package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
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
}
