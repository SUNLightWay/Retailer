package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.management.Query;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionOrderItemDao;
import cn.techaction.pojo.ActionOrder;
import cn.techaction.pojo.ActionOrderItem;

@Repository
public class ActionOrderItemDaoImpl implements ActionOrderItemDao {
	
	@Autowired
	private QueryRunner queryRunner;
	
	@Override
	public List<ActionOrderItem> getItemsByOrderNo(Long orderNo) {
		// TODO Auto-generated method stub
		String sql="select * from action_order_items where order_no=?";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionOrderItem>(ActionOrderItem.class),orderNo);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
