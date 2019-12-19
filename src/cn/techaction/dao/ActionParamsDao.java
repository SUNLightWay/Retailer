package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionParam;

public interface ActionParamsDao {
	/**
	 * 根据id获得商品类型信息
	 * @param id
	 * @return
	 */
	public ActionParam findParamById(Integer id);
	/**
	 * 根据父类型id和类型名称查找类型信息
	 * @param parentId
	 * @param name
	 * @return
	 */
	public ActionParam findParamByParentIdAndName(Integer parentId,String name);
	/**
	 * 新增类型
	 * @param param
	 * @return
	 */
	public int insertParam(ActionParam param);
	/**
	 * 修改类型
	 * @param param
	 * @return
	 */
	public int updateParam(ActionParam param);
	/**
	 * 根据父类型查询该父类型下的所有子类型
	 * @param parentId
	 * @return
	 */
	public List<ActionParam> findParamsByParentId(Integer parentId);
	/**
	 * 根据id删除类型对象
	 * @param id
	 * @return
	 */
	public int deleteParam(Integer id);
}
