package cn.techaction.dao;

import java.util.List;

import cn.techaction.pojo.ActionProduct;

public interface ActionProductDao {
	/**
	 *     根据条件获得商品数目
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
	 * 根据多个条件查询商品信息
	 * 
	 * @param condition
	 * @return
	 */
	public List<ActionProduct> findProductsNoPage(ActionProduct condition);
	
	/**
	 * 新增商品
	 * @param product
	 * @return
	 */
	public int insertProduct(ActionProduct product);
	
	/**
	 * 修改商品信息
	 * @param product
	 * @return
	 */
	public int updateProduct(ActionProduct product);
	
	/**
	 * 根据配件类型Id查找商品信息
	 * @param partsId
	 * @return
	 */
	public List<ActionProduct> findProductsByPartsId(Integer partsId);
	
	/**
	 * 查找热门商品
	 * @param num
	 * @return
	 */
	public List<ActionProduct> findHotProducts(Integer num);
	
	/**
	 * 根据商品编号查询商品信息
	 * @param productId
	 * @return
	 */
	public ActionProduct findProductById(Integer productId);

	/**
	 * 根据产品类型查询商品信息
	 * @param typeHntjx
	 * @return
	 */
	public List<ActionProduct> findProductsByProductCategory(Integer categoryId);

	/**
	 * 根据条件查询总记录数
	 * @param product
	 * @return
	 */
	public Integer getTotalCount(ActionProduct product);
	/**
	 * 根据条件分页查询
	 * @param product
	 * @param startIndex
	 * @param pageSize
	 * @return
	 */
	public List<ActionProduct> findProducts(ActionProduct product, int startIndex, int pageSize);

	/**
	 * 删除某个用户购物车中所有商品
	 * @param uid
	 */
	public int deleteCartProduct(Integer uid);

}
