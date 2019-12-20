package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionOrderItemDao;
import cn.techaction.pojo.ActionOrderItem;

@Repository
public class ActionOrderItemDaoImpl implements ActionOrderItemDao {
	
	@Autowired
	private QueryRunner queryRunner;
	private String alias ="id,uid,order_no as orderNo,goods_id as goodsId,"
			+ "goods_name as goodsName,icon_url as iconUrl,price,quantity,total_price as totalPrice,created,updated";

	
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
	
	
	@Override
	public int[] batchInsert(List<ActionOrderItem> orderItems) {
		String sql = "insert into action_order_items(uid,order_no,goods_id,"
				+ "goods_name,icon_url,price,quantity,total_price,created,updated) values(?,?,?,?,?,?,?,?,?,?)";
		Object[][] params = new Object[orderItems.size()][];
		for(int i=0;i<orderItems.size();i++) {
			ActionOrderItem item = orderItems.get(i);
			params[i]=new Object[] {
				item.getUid(),item.getOrderNo(),item.getGoodsId(),item.getGoodsName(),item.getIconUrl(),item.getPrice(),
				item.getQuantity(),item.getTotalPrice(),item.getCreated(),item.getUpdated()
			};
		}
		try {
			return queryRunner.batch(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	

}
