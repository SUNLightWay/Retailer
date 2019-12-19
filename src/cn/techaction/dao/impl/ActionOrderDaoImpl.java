package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionOrderDao;
import cn.techaction.pojo.ActionOrder;

@Repository
public class ActionOrderDaoImpl implements ActionOrderDao {
	
	@Resource
	private QueryRunner queryRunner;
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

}
