package cn.techaction.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionParamsDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionProductService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductListVo;

@Service
public class ActionProductServiceImpl implements ActionProductService {
	@Autowired
	private ActionProductDao actionProductDao;
	@Autowired
	private ActionParamsDao actionParamsDao;
	
	@Override
	public SverResponse<PageBean<ActionProduct>> findProduct(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize) {
		//1.先根据条件调用dao层获得查询商品的总条数
		//2.调用dao层获得分页查询的商品信息
		int totalCount = actionProductDao.getTotalCount(productId, partsId);
		PageBean<ActionProduct> pageBean = new PageBean<>(pageNum, pageSize, totalCount);
		pageBean.setData(actionProductDao.findProductsByInfo(productId, partsId, pageNum, pageSize));
		return SverResponse.createRespBySuccess(pageBean);
	}
	@Override
	public SverResponse<List<ActionProductListVo>> findProducts(ActionProduct product) {
		// TODO Auto-generated method stub
		if(product.getName() != null) {
			product.setName("%" + product.getName() + "%");
		}
		//调用DAO的类中的方法实现查询
		List<ActionProduct> products = actionProductDao.findProductsNoPage(product);
		//需要将ActionProduct对象转换为业务实体对象
		List<ActionProductListVo> voList = Lists.newArrayList();
		for (ActionProduct pro:products) {
			voList.add(createProductListVo(pro));
		}
		return SverResponse.createRespBySuccess(voList);
	}
	
	/**
	 **封装Vo对象
	 * @param product
	 * @return
	 */
	private ActionProductListVo createProductListVo(ActionProduct product) {
		ActionProductListVo vo = new ActionProductListVo();
		vo.setId(product.getId());
		vo.setName(product.getName());
		vo.setPrice(product.getPrice());
		vo.setStatus(product.getStatus());
		vo.setIconUrl(product.getIconUrl());
		vo.setHot(product.getHot());
		
		//处理特殊属性
		vo.setStatusDesc(ConstUtil.ProductStatus.getStatusDesc(product.getStatus()));
		vo.setHotDesc(ConstUtil.HotStatus.getHotDesc(product.getHot()));
		vo.setProductCategory(actionParamsDao.findParamById(product.getProductId()).getName());
		vo.setPartsCategory(actionParamsDao.findParamById(product.getPartsId()).getName());
		
		return vo;
	}
	
	@Override
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product) {
		// TODO Auto-generated method stub
		if(product == null) {
			return SverResponse.createByErrorMessage("商品的参数无效！");
		}
		
		if(!StringUtils.isEmpty(product.getSubImages())){
			String[] array = product.getSubImages().split(",");
			//拿住第一元素作为主图
			product.setIconUrl(array[0]);
			String temp = product.getSubImages();
			int index = temp.indexOf(",");
			if (index != -1) {
				if(temp.substring(index + 1).equals("null")) {
					product.setSubImages(null);
				}else {
					product.setSubImages(temp.substring(index + 1));
				}
			}else {
				product.setSubImages(null);
			}
		}
		int rs = 0;
		//判断是新增还是修改
		if(product.getId() != null) {
			product.setUpdated(new Date());
			//调用DAO层的修改方法
			rs = actionProductDao.updateProduct(product);
		}else {
			product.setStatus(ConstUtil.ProductStatus.STATUS_NEW);
			product.setHot(ConstUtil.HotStatus.NORMAL_STATUS);
			product.setCreated(new Date());
			product.setUpdated(new Date());
			rs = actionProductDao.insertProduct(product);
		}
		if(rs > 0) {
			return SverResponse.createRespBySuccessMessage("对商品操作成功");
		}
		return SverResponse.createByErrorMessage("对商品操作失败");
	}
	
	
	@Override
	public SverResponse<String> updateStatus(Integer id, Integer status, Integer hot) {
		// TODO Auto-generated method stub
		if(id == null || status == null || hot == null) {
			return SverResponse.createByErrorMessage("参数非法");
		}
		ActionProduct product = new ActionProduct();
		product.setId(id);
		product.setUpdated(new Date());
		//判断修改上下架还是热销
		if(status == -1) {
			product.setHot(hot);
		}else if(hot == -1) {
			product.setStatus(status);
		}
		//调用DAO层修改商品方法
		int rs = actionProductDao.updateProduct(product);
		if(rs > 0) {
			return SverResponse.createRespBySuccessMessage("修改商品状态成功");
		}
		return SverResponse.createByErrorMessage("修改商品状态失败");
	}

}
