package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

public interface ActionProductService {
	/**
	 * 查询指定商品列表
	 * @param productId
	 * @param partsId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProduct>> findProduct(Integer productId,Integer partsId,
			Integer pageNum,Integer pageSize);
	

	/**
	 * 多条件查询商品信息
	 * @param product
	 * @return
	 */
	public SverResponse<List<ActionProductListVo>> findProducts(ActionProduct product);

	/**
	 * 保存商品信息（新增，修改）
	 * @param product
	 * @return
	 */
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product); 
	
	/**
	 * 更新商品状态：上下架、热销
	 * @param productId
	 * @param status
	 * @param hot
	 * @return
	 */
	public SverResponse<String> updateStatus(Integer id, Integer status, Integer hot);


	/**
	 * 查找热门商品
	 * @param num 查找数量
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num);


	/**
	 * 门户：获得首页所有楼层数据
	 * @return
	 */
	public SverResponse<ActionProductFloorVo> findFloorProducts();

	/**
	 * 门户：根据商品编号获取商品详情
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId); 
}
