package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;

public interface ActionParamsService {
	/**
	 * ��������
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> addParam(ActionParam actionParam);
	/**
	 * �޸�����
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> updateParam(ActionParam actionParam);
	/**
	 * ɾ��ָ������
	 * @param id
	 * @return
	 */
	public SverResponse<String> delParam(Integer id);
	/**
	 * ���ݸ����Ͳ���������
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParam>> findParamChildren(Integer id);
}
