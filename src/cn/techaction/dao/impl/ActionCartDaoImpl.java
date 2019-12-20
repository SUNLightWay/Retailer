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


import cn.techaction.dao.ActionCartDao;
import cn.techaction.pojo.ActionCart;
@Repository
public class ActionCartDaoImpl implements ActionCartDao {
	@Resource
	private QueryRunner queryRunner;
	
	private String alias = "id, user_id as userId,product_id as productId,quantity,created,updated,checked";

	@Override
	public List<ActionCart> findCartByUser(Integer uid) {
		String sql = "select " +this.alias+" from action_carts where user_id = ?";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionCart>(ActionCart.class), uid);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}

	@Override
	public ActionCart findCartByUserAndProductId(Integer userId, Integer productId) {
		String sql ="select " +this.alias+ " from action_carts where user_id = ? and product_id = ?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionCart>(ActionCart.class), userId,productId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public int insertCart(ActionCart cart) {
		String sql = "insert into action_carts(user_id,product_id,quantity,created,updated) values(?,?,?,?,?)";
		Object[] params = {cart.getUserId(),cart.getProductId(),cart.getQuantity(),cart.getCreated(),cart.getUpdated()};
		
		try {
			return queryRunner.update(sql,params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateCartById(ActionCart actionCart) {
		String sql = "update action_carts set quantity = ? where id = ?";
		Object[] params = {
				actionCart.getQuantity(),actionCart.getId()
		};
		
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public int deleteCartByUserId(Integer userId) {
		String sql = "delete from action_carts where user_id = ?";
		try {
			return queryRunner.update(sql, userId);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public int updateCartByUserIdAndProductId(ActionCart actionCart) {
		String sql = "update action_carts set quantity = ?";
		List<Object> params = new ArrayList<>();
		params.add(actionCart.getQuantity());
		if(actionCart.getChecked()!=null) {
			sql+=" ,checked = ?";
			params.add(actionCart.getChecked());
		}
		sql+=" where user_id = ? and product_id = ?";
		params.add(actionCart.getUserId());
		params.add(actionCart.getProductId());
		
		try {
			return queryRunner.update(sql, params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}

	@Override
	public int deleteCarts(Integer userId, Integer productId) {
		String sql ="delete from action_carts where product_id = ? and user_id = ?";
		try {
			return queryRunner.update(sql,productId,userId);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}	
	}

	@Override
	public int getCartCountByUserId(Integer userId) {
		String sql = "select count(id) as num from action_carts where user_id = ?";
		try {
			return queryRunner.query(sql, new ColumnListHandler<Long>("num"), userId).get(0).intValue();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}
