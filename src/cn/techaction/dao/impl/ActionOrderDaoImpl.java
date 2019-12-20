package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionOrderDao;
import cn.techaction.pojo.ActionOrder;

@Repository
public class ActionOrderDaoImpl implements ActionOrderDao {
	
	@Resource
	private QueryRunner queryRunner;
	private String alias="id,order_no as orderNo,uid,addr_id  as addrId,amount,type,"
			+ "freight,status,payment_time as paymentTime,delivery_time as deliveryTime,finish_time as "
			+ "finishTime,close_time as closeTime,updated,created";
	@Override
	public List<ActionOrder> findOrderByUid(Integer uid) {
		// TODO Auto-generated method stub
		String sql="select * from action_orders where uid=?";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionOrder>(ActionOrder.class),uid);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public List<ActionOrder> searchOrders(Long orderNo) {
		// TODO Auto-generated method stub
		String sql="select * from action_orders where 1=1 ";
		try {
			if(orderNo!=null) {
				sql+=" and order_no=? order by created";
				return queryRunner.query(sql, new BeanListHandler<ActionOrder>(ActionOrder.class),orderNo);
			}
			return queryRunner.query(sql, new BeanListHandler<ActionOrder>(ActionOrder.class));
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public ActionOrder findOrderDetailByNo(Long orderNo) {
		// TODO Auto-generated method stub
		String sql="select * from action_orders where order_no=?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionOrder>(ActionOrder.class),orderNo);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int getTotalRecord(Integer uid, Integer status) {
		String sql = "select count(id) as num from action_orders where uid = ?";
		List<Object> params = new ArrayList<>();
		params.add(uid);
		if(status !=0) {
			sql+=" and status = ?";
			params.add(status);
		}
		try {
			return queryRunner.query(sql, new ColumnListHandler<Long>("num"), params.toArray()).get(0).intValue();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public List<ActionOrder> findOrders(Integer uid, Integer status, int startIndex, int pageSize) {
		String sql = "select " + this.alias +" from action_orders where uid = ?";
		List<Object> params = new ArrayList<>();
		params.add(uid);
		if(status != 0 ) {
			sql+=" and status = ?";
			params.add(status);
		}
		sql+=" limit ?,?";
		params.add(startIndex);
		params.add(pageSize);
		
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionOrder>(ActionOrder.class), params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public ActionOrder findOrderByUserAndOrderNo(Integer uid, Long orderNo) {
		String sql = "select " +this.alias+" from action_orders where uid = ? and order_no = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionOrder>(ActionOrder.class), uid,orderNo);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public int updateOrder(ActionOrder updateOrder) {
		String sql = "update action_orders set updated = ?";
		List<Object> params = new ArrayList<>();
		params.add(updateOrder.getUpdated());
		if(updateOrder.getStatus() != null) {
			sql+=" ,status = ?";
			params.add(updateOrder.getStatus());
		}
		if(updateOrder.getPaymentTime()!=null) {
			sql+=" ,payment_time = ?";
			params.add(updateOrder.getPaymentTime());
		}
		if(updateOrder.getDeliveryTime()!=null) {
			sql+=" ,delivery_time = ?";
			params.add(updateOrder.getDeliveryTime());
		}
		if(updateOrder.getFinishTime()!=null) {
			sql+=" ,finish_time = ?";
			params.add(updateOrder.getFinishTime());
		}
		if(updateOrder.getCloseTime()!=null) {
			sql+=" ,close_time = ?";
			params.add(updateOrder.getCloseTime());
		}
		sql+=" where id = ?";
		params.add(updateOrder.getId());
		
		try {
			return queryRunner.update(sql, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int insertOrder(ActionOrder order) {
		String sql = "insert into action_orders(order_no,uid,addr_id,amount,type,freight,status"
				+ " ,payment_time,delivery_time,finish_time,close_time,updated,created)"
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {order.getOrderNo(),order.getUid(),order.getAddrId(),order.getAmount(),order.getType()
				,order.getFreight(),order.getStatus(),order.getPaymentTime(),order.getDeliveryTime(),order.getFinishTime(),
				order.getCloseTime(),order.getUpdated(),order.getCreated()
				};
		try {
			return queryRunner.update(sql,params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}
