package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public interface ActionProductDao {
	/**
	 *     �������������Ʒ��Ŀ
	 * @param productId
	 * @param partsId
	 * @return
	 */
	public Integer getTotalCount(Integer productId,Integer partsId);
	/**
	 * 
	 * @param productId
	 * @param partsId
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProductsByInfo(Integer productId,Integer partsId,Integer startIndex,Integer pageSize);
	
	/**
	 * ���ݶ��������ѯ��Ʒ��Ϣ
	 * @param condition
	 * @return
	 */
	public List<ActionProduct> findProductsNoPage(ActionProduct condition);
	
	/**
	 * ������Ʒ
	 * @param product
	 * @return
	 */
	public int insertProduct(ActionProduct product);
	
	/**
	 * �޸���Ʒ��Ϣ
	 * @param product
	 * @return
	 */
	public int updateProduct(ActionProduct product);
	
	/**
	 * �����������Id������Ʒ��Ϣ
	 * @param partsId
	 * @return
	 */
	public List<ActionProduct> findProductsByPartsId(Integer partsId);
}
