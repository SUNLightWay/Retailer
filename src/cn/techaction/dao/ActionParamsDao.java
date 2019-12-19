package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionParam;

public interface ActionParamsDao {
	/**
	 * ����id�����Ʒ������Ϣ
	 * @param id
	 * @return
	 */
	public ActionParam findParamById(Integer id);
	/**
	 * ���ݸ�����id���������Ʋ���������Ϣ
	 * @param parentId
	 * @param name
	 * @return
	 */
	public ActionParam findParamByParentIdAndName(Integer parentId,String name);
	/**
	 * ��������
	 * @param param
	 * @return
	 */
	public int insertParam(ActionParam param);
	/**
	 * �޸�����
	 * @param param
	 * @return
	 */
	public int updateParam(ActionParam param);
	/**
	 * ���ݸ����Ͳ�ѯ�ø������µ�����������
	 * @param parentId
	 * @return
	 */
	public List<ActionParam> findParamsByParentId(Integer parentId);
	/**
	 * ����idɾ�����Ͷ���
	 * @param id
	 * @return
	 */
	public int deleteParam(Integer id);
}
