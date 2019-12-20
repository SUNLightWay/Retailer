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
import org.springframework.util.StringUtils;

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

	@Override
	public ActionAddress findAddrsById(Integer addrId) {
		String sql = "select id,user_id as uid,name,phone,mobile,"
				+ "province,city,district,addr,zip,default_addr,"
				+ "del_state,created,updated from action_address where id = ? and del_state=0";
		
		try {
			return queryRunner.query(sql, new BeanHandler<ActionAddress>(ActionAddress.class), addrId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	@Override
	public int findDefaultAddrByUserId(Integer userId) {
		String sql = "select count(id) as num from action_address where user_id= ? and default_addr = 1";
		try {
			return queryRunner.query(sql, new ColumnListHandler<Long>("num"), userId).get(0).intValue();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	@Override
	public int insertAddress(ActionAddress addr) {
		String sql ="insert into action_address(user_id,name,phone,"
				+ "mobile,province,city,district,addr,zip,default_addr,created,updated) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = {
				addr.getUid(),addr.getName(),addr.getPhone(),addr.getMobile(),addr.getProvince(),addr.getCity(),addr.getDistrict(),
				addr.getAddr(),addr.getZip(),addr.getDefault_addr(),addr.getCreated(),addr.getUpdated()
		};
		try {
			return queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	@Override
	public int updateAddress(ActionAddress addr) {
		String sql ="update action_address set updated = ?";
		List<Object> params = new ArrayList<>();
		params.add(addr.getUpdated());
		if(!StringUtils.isEmpty(addr.getName())) {
			sql+=",name = ?";
			params.add(addr.getName());
		}
		if(!StringUtils.isEmpty(addr.getPhone())) {
			sql+=",phone = ?";
			params.add(addr.getPhone());
		}
		if(!StringUtils.isEmpty(addr.getMobile())) {
			sql+=",mobile = ?";
			params.add(addr.getMobile());
		}
		if(!StringUtils.isEmpty(addr.getProvince())) {
			sql+=",province = ?";
			params.add(addr.getProvince());
		}
		if(!StringUtils.isEmpty(addr.getCity())) {
			sql+=",city = ?";
			params.add(addr.getCity());
		}
		if(!StringUtils.isEmpty(addr.getDistrict())) {
			sql+=",district = ?";
			params.add(addr.getDistrict());
		}
		if(!StringUtils.isEmpty(addr.getAddr())) {
			sql+=",addr = ?";
			params.add(addr.getAddr());
		}
		if(!StringUtils.isEmpty(addr.getZip())) {
			sql+=",zip = ?";
			params.add(addr.getZip());
		}
		if(addr.getDefault_addr()!=null) {
			sql+=",default_addr = ?";
			params.add(addr.getDefault_addr());
		}
		if(addr.getDel_state()!=null) {
			sql+=",del_state = ?";
			params.add(addr.getDel_state());
		}
		sql+=" where id = ?";
		params.add(addr.getId());	
		try {
			return queryRunner.update(sql,params.toArray());
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	@Override
	public List<ActionAddress> findAddrsByUserId(Integer userId) {
		String sql = "select id,user_id as uid,name,phone,mobile,"
				+ "province,city,district,addr,zip,default_addr,"
				+ "del_state,created,updated from action_address"
				+ " where user_id = ? and del_state=0 order by default_addr desc,updated desc ";	
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionAddress>(ActionAddress.class), userId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public ActionAddress findDefaultAddr(Integer userId) {
		String sql = "select id,user_id as uid,name,phone,"
				+ "mobile,province,city,district,addr,zip,default_addr,"
				+ "del_state,created,updated from action_address where user_id = ? and default_addr = 1 and del_state = 0";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionAddress>(ActionAddress.class), userId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	
	}
}
