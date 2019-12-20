package cn.techaction.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAddressDao;
import cn.techaction.dao.ActionCartDao;
import cn.techaction.dao.ActionOrderDao;
import cn.techaction.dao.ActionOrderItemDao;
import cn.techaction.dao.ActionProductDao;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.pojo.ActionCart;
import cn.techaction.pojo.ActionOrder;
import cn.techaction.pojo.ActionOrderItem;
import cn.techaction.pojo.ActionProduct;
import cn.techaction.service.ActionOrderService;
import cn.techaction.utils.CalcUtil;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.DateUtils;
import cn.techaction.utils.PageBean;
import cn.techaction.vo.ActionAddressVo;
import cn.techaction.vo.ActionOrderItemVo;
import cn.techaction.vo.ActionOrderVo;
@Service
public class ActionOrderServiceImpl implements ActionOrderService {
	@Autowired
	private ActionOrderDao actionOrderDao;
	@Autowired
	private ActionAddressDao actionAddressDao;
	@Autowired
	private ActionOrderItemDao actionOrderItemDao;
	@Autowired
	private ActionOrderDao aOrderDao;
	@Autowired
	private ActionAddressDao aAddressDao;
	@Autowired
	private ActionOrderItemDao aOrderItemDao;
	@Autowired
	private ActionCartDao aCartDao;
	@Autowired
	private ActionProductDao aProductDao;
	/**
	 * ��ѯ������Ϣ
	 */
	@Override
	public SverResponse<List<ActionOrderVo>> findOrderForNoPages(Long orderNo) {
		// TODO Auto-generated method stub
		//1.����dao��ķ���
		List<ActionOrder> orders=actionOrderDao.searchOrders(orderNo);
		//2.ת����vo����
		List<ActionOrderVo> vos=Lists.newArrayList();
		for(ActionOrder temp:orders) {
			//ת����vo
			vos.add(this.createOrderVo(temp, true));
		}
		return SverResponse.createRespBySuccess(vos);
	}
	/**
	 * ��orderת����vo����
	 * @param order
	 * @param hasAddress
	 * @return
	 */
	private ActionOrderVo createOrderVo(ActionOrder order, boolean hasAddress) {
		ActionOrderVo orderVo=new ActionOrderVo();
		//������ͨ����
		this.setNormalProperty(order, orderVo);
		//���õ�ַ
		this.setAddressProperty(order, orderVo, hasAddress);
		//���ö�������
		//���ݶ����ŵõ��������鼯��
		List<ActionOrderItem> orderItems=actionOrderItemDao.getItemsByOrderNo(order.getOrderNo());
		List<ActionOrderItemVo> vos=Lists.newArrayList();
		for(ActionOrderItem item:orderItems) {
			vos.add(this.createOrderItemVo(item));
		}
		orderVo.setOrderItems(vos);
		return orderVo;
	}


	/**
	 * ����ַת���ɵ�ַvo
	 * @param addr
	 * @return
	 */
	private ActionAddressVo createAddressVo(ActionAddress addr) {
		ActionAddressVo vo=new ActionAddressVo();
		vo.setAddr(addr.getAddr());
		vo.setCity(addr.getCity());
		vo.setDistrict(addr.getDistrict());
		vo.setMobile(addr.getMobile());
		vo.setName(addr.getName());
		vo.setPhone(addr.getPhone());
		vo.setProvince(addr.getProvince());
		vo.setZip(addr.getZip());
		return vo;
	}

	@Override
	public SverResponse<ActionOrderVo> mgrDetail(Long orderNo) {
		// TODO Auto-generated method stub
		//1.�����ŵ��ж�
		if(orderNo==null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//2.����dao��ķ��������ݶ����Ż�ö�������ActionOrder
		ActionOrder order=actionOrderDao.findOrderDetailByNo(orderNo);
		if(order==null) {
			return SverResponse.createByErrorMessage("���û��Ķ��������ڣ�");
		}
		//3.��order����ת����orderVo����
		ActionOrderVo orderVo=this.createOrderVo(order, true);
		return SverResponse.createRespBySuccess(orderVo);
	}
	
	@Override
	public SverResponse<PageBean<ActionOrderVo>> findOrders(Integer uid, Integer status, int pageNum, int pageSize) {
		//�ж�uid�Ƿ�Ϊ��
		if(uid == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//���ҷ����������ܼ�¼��
		int totalRecord = aOrderDao.getTotalRecord(uid,status);
		//������ҳ��װ����
		PageBean<ActionOrderVo> pageBean = new PageBean<>(pageNum, pageSize, totalRecord);
		//��ȡ����
		List<ActionOrder> orders = aOrderDao.findOrders(uid,status,pageBean.getStartIndex(),pageSize);
		//��װvo
		List<ActionOrderVo> voList =Lists.newArrayList();
		for(ActionOrder order:orders) {
			voList.add(createOrderVo1(order,false));
		}
		pageBean.setData(voList);
		return SverResponse.createRespBySuccess(pageBean);
	}
	//��װ����Vo
		private ActionOrderVo createOrderVo(ActionOrder order, List<ActionOrderItem> orderItems) {
			ActionOrderVo orderVo = new ActionOrderVo();
			setNormalProperty(order,orderVo);
			setAddressProperty(order,orderVo,true);
			//���ö�����
			setOrderItemProperty(orderItems,orderVo);
			return orderVo;
		}
	//��װvo
	private ActionOrderVo createOrderVo1(ActionOrder order, boolean hasAddress) {
		ActionOrderVo orderVo = new ActionOrderVo();
		setNormalProperty(order,orderVo);
		setAddressProperty(order,orderVo,hasAddress);
		//���ö�����
		List<ActionOrderItem> orderItems = aOrderItemDao.getItemsByOrderNo(order.getOrderNo());
		setOrderItemProperty(orderItems,orderVo);
		return orderVo;
	}
	/**
	 * ��װ����������
	 * @param orderItems
	 * @param orderVo
	 */
	private void setOrderItemProperty(List<ActionOrderItem> orderItems, ActionOrderVo orderVo) {
		List<ActionOrderItemVo> items = Lists.newArrayList();
		for(ActionOrderItem orderItem:orderItems) {
			items.add(createOrderItemVo(orderItem));
		}
		orderVo.setOrderItems(items);
	}
	/**
	 * ��װ������vo
	 * @param orderItem
	 * @return
	 */
	private ActionOrderItemVo createOrderItemVo(ActionOrderItem orderItem) {
		ActionOrderItemVo itemVo = new ActionOrderItemVo();
		itemVo.setOrderNo(orderItem.getOrderNo());
		itemVo.setGoodsId(orderItem.getGoodsId());
		itemVo.setGoodsName(orderItem.getGoodsName());
		itemVo.setIconUrl(orderItem.getIconUrl());
		itemVo.setCurPrice(orderItem.getPrice());
		itemVo.setTotalPrice(orderItem.getTotalPrice());
		itemVo.setQuantity(orderItem.getQuantity());
		return itemVo;
	}
	/**
	 * ��װ��ַ����
	 * @param order
	 * @param orderVo
	 * @param hasAddress
	 */
	private void setAddressProperty(ActionOrder order, ActionOrderVo orderVo, boolean hasAddress) {
		ActionAddress address = aAddressDao.findAddrsById(order.getAddrId());
		if(address !=null) {
			orderVo.setDeliveryName(address.getName());
			if(hasAddress) {
				orderVo.setAddress(createAddressVo(address));
			}else {
				orderVo.setAddress(null);
			}
		}
		
	}

	/**
	 * ��װ������vo��ͨ����
	 * @param order
	 * @param orderVo
	 */
	private void setNormalProperty(ActionOrder order, ActionOrderVo orderVo) {
		orderVo.setOrderNo(order.getOrderNo());
		orderVo.setAmount(order.getAmount());
		orderVo.setType(order.getType());
		orderVo.setTypeDesc(ConstUtil.PaymentType.getTypeDesc(order.getType()));
		orderVo.setFreight(order.getFreight());
		orderVo.setStatus(order.getStatus());
		orderVo.setStatusDesc(ConstUtil.OrderStatus.getStatusDesc(order.getStatus()));
		orderVo.setAddrId(order.getAddrId());
		// ʱ��
		orderVo.setPaymentTime(DateUtils.date2Str(order.getPaymentTime()));
		orderVo.setDeliveryTime(DateUtils.date2Str(order.getDeliveryTime()));
		;
		orderVo.setFinishTime(DateUtils.date2Str(order.getFinishTime()));
		orderVo.setCloseTime(DateUtils.date2Str(order.getCloseTime()));
		orderVo.setCreated(DateUtils.date2Str(order.getCreated()));
	}

	@Override
	public SverResponse<String> cancelOrder(Integer uid, Long orderNo) {
		//��ѯ����
		ActionOrder order = aOrderDao.findOrderByUserAndOrderNo(uid,orderNo);
		//�ж϶����Ƿ����
		if(order == null) {
			return SverResponse.createByErrorMessage("���û����������ڣ�����ɾ����");
		}
		//�ж϶����Ƿ��Ѿ�����
		if(order.getStatus() == ConstUtil.OrderStatus.ORDER_PAID) {
			return SverResponse.createByErrorMessage("�ö����Ѿ�����޷�ȡ����");
		}
		//�ж�״̬�޸Ķ�����Ϣ
		ActionOrder updateOrder = new ActionOrder();
		updateOrder.setId(order.getId());
		updateOrder.setUpdated(new Date());
		if(order.getStatus() == 1) {
			updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_CANCELED);
			int row = aOrderDao.updateOrder(updateOrder);
			if(row > 0) {
				return SverResponse.createRespBySuccessMessage("�����Ѿ�ȡ����");
			}
		}
		if(order.getStatus() == 3) {
			updateOrder.setStatus(ConstUtil.OrderStatus.ORDER_SUCCESS);
			int row = aOrderDao.updateOrder(updateOrder);
			if(row > 0) {
				return SverResponse.createRespBySuccessMessage("�����Ѿ�ȷ���ջ���");
			}
		}
		return SverResponse.createByErrorMessage("ʧ�ܣ�");
	}

	@Override
	public SverResponse<ActionOrderVo> findOrderDetail(Integer uid, Long orderNo) {
		//�жϲ����Ƿ���ȷ
		if(uid == null || orderNo == null) {
			return SverResponse.createByErrorMessage("��������");
		}
		//���Ҷ�������װ
		ActionOrder order = aOrderDao.findOrderByUserAndOrderNo(uid, orderNo);
		if(order == null) {
			return SverResponse.createByErrorMessage("���û����������ڣ�����ɾ����");
		}
		ActionOrderVo orderVo = createOrderVo1(order, true);
		return SverResponse.createRespBySuccess(orderVo);
	}

	@Override
	public SverResponse<ActionOrderVo> generateOrder(Integer uid, Integer addrId) {
		//��ȡ���ﳵ����Ʒ��Ϣ
		List<ActionCart> carts = aCartDao.findCartByUser(uid);
		//���㹺�ﳵ��ÿ����Ʒ�ܼ۸����ɶ�����
		SverResponse resp = this.cart2OrderItem(uid,carts);
		if(!resp.isSuccess()) {
			return resp;
		}
		//ȡ���������еļ۸���㶩���ܼ۸�
		List<ActionOrderItem> orderItems = (List<ActionOrderItem>) resp.getData();
		BigDecimal totalPrice = this.calcOrderTotalPrice(orderItems);
		//���ɶ�������������
		ActionOrder order =saveOrder(uid,addrId,totalPrice);
		if(order == null) {
			return SverResponse.createByErrorMessage("����������������������ύ��");
		}
		if(CollectionUtils.isEmpty(orderItems)) {
			return SverResponse.createByErrorMessage("������Ϊ�գ���ѡ��Ҫ�������Ʒ��");
		}
		//�������붩����
		for(ActionOrderItem orderItem:orderItems) {
			//Ϊ���������ö�������
			orderItem.setOrderNo(order.getOrderNo());
		}
		aOrderItemDao.batchInsert(orderItems);
		//������Ʒ���п��
		for(ActionOrderItem orderItem:orderItems) {
			ActionProduct product = aProductDao.findProductById(orderItem.getGoodsId());
			//���ٿ��
			product.setStock(product.getStock() - orderItem.getQuantity());
			product.setUpdated(new Date());
			//���¿��
			aProductDao.updateProduct(product);
		}	
		//��չ��ﳵ
		aProductDao.deleteCartProduct(uid);
		//��װ����ǰ��
		ActionOrderVo orderVo =createOrderVo(order, orderItems);
		return SverResponse.createRespBySuccess(orderVo);
	}
	

	/**
	 * ���涩��
	 * @param uid
	 * @param addrId
	 * @param totalPrice
	 * @return
	 */
	private ActionOrder saveOrder(Integer uid, Integer addrId, BigDecimal totalPrice) {
		ActionOrder order = new ActionOrder();
		//���ɶ�����
		long currentTime = System.currentTimeMillis();
		Long orderNo = currentTime + new Random().nextInt(100);
		order.setOrderNo(orderNo);
		order.setStatus(ConstUtil.OrderStatus.ORDER_NO_PAY);//Ĭ��δ����
		order.setType(ConstUtil.PaymentType.PAY_ON_LINE); //����֧��
		order.setFreight(0);
		order.setAmount(totalPrice);
		order.setAddrId(addrId);
		order.setUid(uid);
		order.setUpdated(new Date());
		order.setCreated(new Date());
		//���붩��
		int rs = aOrderDao.insertOrder(order);
		if(rs > 0) {
			return order;
		}
		return null;
	}

	/**
	 * ���㶩���ܼ۸�
	 * @param orderItems
	 * @return
	 */
	private BigDecimal calcOrderTotalPrice(List<ActionOrderItem> orderItems) {
		BigDecimal totalPrice = new BigDecimal("0");
		for(ActionOrderItem item:orderItems) {
			totalPrice = CalcUtil.add(totalPrice.doubleValue(),item.getTotalPrice().doubleValue());
		}
		return totalPrice;
	}

	/**
	 * �����ﳵ����Ʒ��װΪ������
	 * @param uid
	 * @param carts
	 * @return
	 */
	private SverResponse cart2OrderItem(Integer uid, List<ActionCart> carts) {
		List<ActionOrderItem> items = Lists.newArrayList();
		//�жϹ��ﳵ�Ƿ�Ϊ��
		if(CollectionUtils.isEmpty(carts)) {
			return SverResponse.createByErrorMessage("���ﳵΪ�գ���ѡ��Ҫ�������Ʒ");
		}
		for(ActionCart cart:carts) {
			//�鿴���ﳵ����Ʒ״̬
			ActionProduct product = aProductDao.findProductById(cart.getProductId());
			//�鿴��Ʒ״̬
			if(ConstUtil.ProductStatus.STATUS_ON_SALE != product.getStatus()) {
				//�����Ʒ�����ϼ� ���� ���򷵻���ʾ��Ϣ
				return SverResponse.createByErrorMessage("��Ʒ"+product.getName() +"�Ѿ��¼ܣ��������߹���");
			}
			//�鿴���
			if(cart.getQuantity() >product.getStock()) {
				return SverResponse.createByErrorMessage("��Ʒ"+product.getName() +"��治�㣡");
			}
			//��װ������
			ActionOrderItem orderItem = new ActionOrderItem();
			orderItem.setUid(uid);
			orderItem.setGoodsName(product.getName());
			orderItem.setGoodsId(product.getId());
			orderItem.setIconUrl(product.getIconUrl());
			orderItem.setPrice(product.getPrice());
			orderItem.setQuantity(cart.getQuantity());
			orderItem.setTotalPrice(CalcUtil.mul(orderItem.getPrice().doubleValue(), orderItem.getQuantity().doubleValue()));
			orderItem.setCreated(new Date());
			orderItem.setUpdated(new Date());
			items.add(orderItem);
		}
		return SverResponse.createRespBySuccess(items);
	}
}
