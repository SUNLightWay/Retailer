package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionParam;

public interface ActionParamsService {
	/**
	 * 新增类型
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> addParam(ActionParam actionParam);
	/**
	 * 修改类型
	 * @param actionParam
	 * @return
	 */
	public SverResponse<String> updateParam(ActionParam actionParam);
	/**
	 * 删除指定类型
	 * @param id
	 * @return
	 */
	public SverResponse<String> delParam(Integer id);
	/**
	 * 根据父类型查找子类型
	 * @param id
	 * @return
	 */
	public SverResponse<List<ActionParam>> findParamChildren(Integer id);
}
