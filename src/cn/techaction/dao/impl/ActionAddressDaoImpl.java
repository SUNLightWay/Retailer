package cn.techaction.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionAddressDao;
import cn.techaction.pojo.ActionAddress;

@Repository
public class ActionAddressDaoImpl implements ActionAddressDao {
	@Resource
	private QueryRunner queryRunner;
	
	@Override
	public ActionAddress findAddrById(Integer id) {
		// TODO Auto-generated method stub
		String sql="select * from action_address where id=?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionAddress>(ActionAddress.class),id);
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
