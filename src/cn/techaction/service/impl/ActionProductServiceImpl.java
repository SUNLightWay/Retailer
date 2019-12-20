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
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

@Service
public class ActionProductServiceImpl implements ActionProductService {
	@Autowired
	private ActionProductDao actionProductDao;
	@Autowired
	private ActionParamsDao actionParamsDao;
	@Autowired
	private ActionProductDao aProductDao;
	@Autowired
	private ActionParamsDao aParamsDao;
	
	@Override
	public SverResponse<PageBean<ActionProduct>> findProduct(Integer productId, Integer partsId, Integer pageNum,
			Integer pageSize) {
		//1.�ȸ�����������dao���ò�ѯ��Ʒ��������
		//2.����dao���÷�ҳ��ѯ����Ʒ��Ϣ
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
		//����DAO�����еķ���ʵ�ֲ�ѯ
		List<ActionProduct> products = actionProductDao.findProductsNoPage(product);
		//��Ҫ��ActionProduct����ת��Ϊҵ��ʵ�����
		List<ActionProductListVo> voList = Lists.newArrayList();
		for (ActionProduct pro:products) {
			voList.add(createProductListVo(pro));
		}
		return SverResponse.createRespBySuccess(voList);
	}
	
	/**
	 **��װVo����
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
		
		//������������
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
			return SverResponse.createByErrorMessage("��Ʒ�Ĳ�����Ч��");
		}
		
		if(!StringUtils.isEmpty(product.getSubImages())){
			String[] array = product.getSubImages().split(",");
			//��ס��һԪ����Ϊ��ͼ
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
		//�ж������������޸�
		if(product.getId() != null) {
			product.setUpdated(new Date());
			//����DAO����޸ķ���
			rs = actionProductDao.updateProduct(product);
		}else {
			product.setStatus(ConstUtil.ProductStatus.STATUS_NEW);
			product.setHot(ConstUtil.HotStatus.NORMAL_STATUS);
			product.setCreated(new Date());
			product.setUpdated(new Date());
			rs = actionProductDao.insertProduct(product);
		}
		if(rs > 0) {
			return SverResponse.createRespBySuccessMessage("����Ʒ�����ɹ�");
		}
		return SverResponse.createByErrorMessage("����Ʒ����ʧ��");
	}
	
	
	@Override
	public SverResponse<String> updateStatus(Integer id, Integer status, Integer hot) {
		// TODO Auto-generated method stub
		if(id == null || status == null || hot == null) {
			return SverResponse.createByErrorMessage("�����Ƿ�");
		}
		ActionProduct product = new ActionProduct();
		product.setId(id);
		product.setUpdated(new Date());
		//�ж��޸����¼ܻ�������
		if(status == -1) {
			product.setHot(hot);
		}else if(hot == -1) {
			product.setStatus(status);
		}
		//����DAO���޸���Ʒ����
		int rs = actionProductDao.updateProduct(product);
		if(rs > 0) {
			return SverResponse.createRespBySuccessMessage("�޸���Ʒ״̬�ɹ�");
		}
		return SverResponse.createByErrorMessage("�޸���Ʒ״̬ʧ��");
	}
	@Override
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num) {
		// TODO Auto-generated method stub
		//ֱ�Ӳ�ѯ�������ݼ���
		List<ActionProduct> products = actionProductDao.findHotProducts(num);
		return SverResponse.createRespBySuccess(products);
	}
	@Override
	public SverResponse<ActionProductFloorVo> findFloorProducts() {
		// TODO Auto-generated method stub
		//����vo����
		ActionProductFloorVo vo = new ActionProductFloorVo();
		//1¥����
		List<ActionProduct> products1 = actionProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_HNTJX); 
		vo.setOneFloor(products1);
		//2¥����
		List<ActionProduct> products2 = actionProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_JZQZJJX); 
		vo.setTwoFloor(products2);
		//3¥����
		List<ActionProduct> products3 = actionProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_GCQZJJX); 
		vo.setTwoFloor(products3);
		//4¥����
		List<ActionProduct> products4 = actionProductDao.findProductsByProductCategory(ConstUtil.ProductType.TYPE_LMJX); 
		vo.setTwoFloor(products4);
		
		return SverResponse.createRespBySuccess(vo);
	}
	@Override
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId) {
		//�жϲ�Ʒ����Ƿ�Ϊ��
		if(productId ==null) {
			return SverResponse.createByErrorMessage("��Ʒ��Ų���Ϊ��");
		}
		//��ѯ��Ʒ����
		ActionProduct product = aProductDao.findProductById(productId);
		//�жϲ�Ʒ�Ƿ��¼�
		if(product==null) {
			return SverResponse.createByErrorMessage("��Ʒ�Ѿ��¼ܣ�");
		}
		if(product.getStatus()==ConstUtil.ProductStatus.STATUS_OFF_SALE) {
			return SverResponse.createByErrorMessage("��Ʒ�Ѿ��¼ܣ�");
		}
		return SverResponse.createRespBySuccess(product);
	}

	@Override
	public SverResponse<PageBean<ActionProductListVo>> findProductsForProtal(Integer productTypeId, Integer partsId,
			String name, int pageNum, int pageSize) {
		//��������
		ActionProduct product = new ActionProduct();
		int totalRecord = 0;
		//�ж�name�Ƿ�Ϊ��
		if(name !=null && !name.equals("")) {
			product.setName(name);
		}
		if(productTypeId!=0) {
			product.setProductId(productTypeId);
		}
		if(partsId!=0) {
			product.setPartsId(partsId);;
		}
		//ǰ����ʾ��Ʒ��Ϊ����
		product.setStatus(2);
		//���ҷ����������ܼ�¼��
		totalRecord = aProductDao.getTotalCount(product);
		//������ҳ����
		PageBean<ActionProductListVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//��ȡ���� 
		List<ActionProduct> products = aProductDao.findProducts(product,pageBean.getStartIndex(),pageSize);
		//��װvo
		List<ActionProductListVo> voList = Lists.newArrayList();
		for(ActionProduct p:products) {
			voList.add(createProductListVo(p));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}
	
}
