package cn.techaction.service;

import java.util.List;

import cn.techaction.common.SverResponse;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionProductFloorVo;
import cn.techaction.vo.ActionProductListVo;

public interface ActionProductService {
	/**
	 * ��ѯָ����Ʒ�б�
	 * @param productId
	 * @param partsId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public SverResponse<PageBean<ActionProduct>> findProduct(Integer productId,Integer partsId,
			Integer pageNum,Integer pageSize);
	

	/**
	 * ��������ѯ��Ʒ��Ϣ
	 * @param product
	 * @return
	 */
	public SverResponse<List<ActionProductListVo>> findProducts(ActionProduct product);

	/**
	 * ������Ʒ��Ϣ���������޸ģ�
	 * @param product
	 * @return
	 */
	public SverResponse<String> saveOrUpdateProduct(ActionProduct product); 
	
	/**
	 * ������Ʒ״̬�����¼ܡ�����
	 * @param productId
	 * @param status
	 * @param hot
	 * @return
	 */
	public SverResponse<String> updateStatus(Integer id, Integer status, Integer hot);


	/**
	 * ����������Ʒ
	 * @param num ��������
	 * @return
	 */
	public SverResponse<List<ActionProduct>> findHotProducts(Integer num);


	/**
	 * �Ż��������ҳ����¥������
	 * @return
	 */
	public SverResponse<ActionProductFloorVo> findFloorProducts();

	/**
	 * �Ż���������Ʒ��Ż�ȡ��Ʒ����
	 * @param productId
	 * @return
	 */
	public SverResponse<ActionProduct> findProductDetailForPortal(Integer productId); 
}
