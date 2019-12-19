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
	 * 查询订单信息
	 */
	@Override
	public SverResponse<List<ActionOrderVo>> findOrderForNoPages(Long orderNo) {
		// TODO Auto-generated method stub
		//1.调用dao层的方法
		List<ActionOrder> orders=actionOrderDao.searchOrders(orderNo);
		//2.转换成vo对象
		List<ActionOrderVo> vos=Lists.newArrayList();
		for(ActionOrder temp:orders) {
			//转换成vo
			vos.add(this.createOrderVo(temp, true));
		}
		return SverResponse.createRespBySuccess(vos);
	}
	/**
	 * 将order转换成vo对象
	 * @param order
	 * @param hasAddress
	 * @return
	 */
	private ActionOrderVo createOrderVo(ActionOrder order, boolean hasAddress) {
		ActionOrderVo orderVo=new ActionOrderVo();
		//设置普通属性
		this.setNormalProperty(order, orderVo);
		//设置地址
		this.setAddressProperty(order, orderVo, hasAddress);
		//设置订单详情
		//根据订单号得到订单详情集合
		List<ActionOrderItem> orderItems=actionOrderItemDao.getItemsByOrderNo(order.getOrder_no());
		List<ActionOrderItemVo> vos=Lists.newArrayList();
		for(ActionOrderItem item:orderItems) {
			vos.add(this.createOrderItemVo(item));
		}
		orderVo.setOrderItems(vos);
		return orderVo;
	}
	/**
	 * 转换普通属性
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
		//时间
		orderVo.setPaymentTime(DateUtils.date2Str(order.getPayment_time()));
		orderVo.setDeliveryTime(DateUtils.date2Str(order.getDelivery_time()));
		orderVo.setFinishTime(DateUtils.date2Str(order.getFinish_time()));
		orderVo.setCloseTime(DateUtils.date2Str(order.getClose_time()));
		orderVo.setCreated(DateUtils.date2Str(order.getCreated()));
	}
	/**
	 * 封装地址属性
	 * @param order
	 * @param orderVo
	 * @param flag
	 */
	private void setAddressProperty(ActionOrder order,ActionOrderVo orderVo,boolean flag) {
		//根据地址id获得地址对象
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
	 * 将地址转换成地址vo
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
	 * 将orderItem转换成orderItemVo
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
		//1.订单号的判断
		if(orderNo==null) {
			return SverResponse.createByErrorMessage("参数错误！");
		}
		//2.调用dao层的方法：根据订单号获得订单对象ActionOrder
		ActionOrder order=actionOrderDao.findOrderDetailByNo(orderNo);
		if(order==null) {
			return SverResponse.createByErrorMessage("该用户的订单不存在！");
		}
		//3.将order对象转换成orderVo对象
		ActionOrderVo orderVo=this.createOrderVo(order, true);
		return SverResponse.createRespBySuccess(orderVo);
	}
}
