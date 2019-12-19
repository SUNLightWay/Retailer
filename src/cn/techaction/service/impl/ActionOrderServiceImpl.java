package cn.techaction.service.impl;

import java.util.List;

import org.aspectj.apache.bcel.generic.ReturnaddressType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.techaction.common.SverResponse;
import cn.techaction.dao.ActionAddressDao;
import cn.techaction.dao.ActionOrderDao;
import cn.techaction.dao.ActionOrderItemDao;
import cn.techaction.pojo.ActionAddress;
import cn.techaction.pojo.ActionOrder;
import cn.techaction.pojo.ActionOrderItem;
import cn.techaction.service.ActionOrderService;
import cn.techaction.utils.ConstUtil;
import cn.techaction.utils.DateUtils;
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
		List<ActionOrderItem> orderItems=actionOrderItemDao.getItemsByOrderNo(order.getOrder_no());
		List<ActionOrderItemVo> vos=Lists.newArrayList();
		for(ActionOrderItem item:orderItems) {
			vos.add(this.createOrderItemVo(item));
		}
		orderVo.setOrderItems(vos);
		return orderVo;
	}
	/**
	 * ת����ͨ����
	 * @param order
	 * @param orderVo
	 */
	private void setNormalProperty(ActionOrder order,ActionOrderVo orderVo) {
		orderVo.setOrderNo(order.getOrder_no());
		orderVo.setAmount(order.getAmount());
		orderVo.setType(order.getType());
		orderVo.setTypeDesc(ConstUtil.PaymentType.getTypeDesc(order.getType()));
		orderVo.setFreight(order.getFreight());
		orderVo.setStatus(order.getStatus());
		orderVo.setStatusDesc(ConstUtil.OrderStatus.getStatusDesc(order.getStatus()));
		orderVo.setAddrId(order.getAddr_id());
		//ʱ��
		orderVo.setPaymentTime(DateUtils.date2Str(order.getPayment_time()));
		orderVo.setDeliveryTime(DateUtils.date2Str(order.getDelivery_time()));
		orderVo.setFinishTime(DateUtils.date2Str(order.getFinish_time()));
		orderVo.setCloseTime(DateUtils.date2Str(order.getClose_time()));
		orderVo.setCreated(DateUtils.date2Str(order.getCreated()));
	}
	/**
	 * ��װ��ַ����
	 * @param order
	 * @param orderVo
	 * @param flag
	 */
	private void setAddressProperty(ActionOrder order,ActionOrderVo orderVo,boolean flag) {
		//���ݵ�ַid��õ�ַ����
		ActionAddress address=actionAddressDao.findAddrById(order.getAddr_id());
		if(address!=null) {
			orderVo.setDeliveryName(address.getName());
			if(flag) {
				orderVo.setAddress(this.createAddressVo(address));
			}else {
				orderVo.setAddress(null);
			}
		}
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
	/**
	 * ��orderItemת����orderItemVo
	 * @param orderItem
	 * @return
	 */
	private ActionOrderItemVo createOrderItemVo(ActionOrderItem orderItem) {
		ActionOrderItemVo vo=new ActionOrderItemVo();
		vo.setOrderNo(orderItem.getOrder_no());
		vo.setGoodsId(orderItem.getGoods_id());
		vo.setGoodsName(orderItem.getGoods_name());
		vo.setIconUrl(orderItem.getIcon_url());
		vo.setCurPrice(orderItem.getPrice());
		vo.setTotalPrice(orderItem.getTotal_price());
		vo.setQuantity(orderItem.getQuantity());
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
}
