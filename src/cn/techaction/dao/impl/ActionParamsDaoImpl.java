package cn.techaction.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.stereotype.Repository;

import cn.techaction.dao.ActionParamsDao;
import cn.techaction.pojo.ActionParam;

@Repository
public class ActionParamsDaoImpl implements ActionParamsDao {
	@Resource
	private QueryRunner queryRunner;
	
	@Override
	public ActionParam findParamById(Integer id) {
		// TODO Auto-generated method stub
		String sql="select * from action_params where id=?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionParam>(ActionParam.class),id);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ActionParam findParamByParentIdAndName(Integer parentId, String name) {
		// TODO Auto-generated method stub
		String sql="select * from action_params where parent_id=? and name=?";
		try {
			return queryRunner.query(sql, new BeanHandler<ActionParam>(ActionParam.class),parentId,name);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int insertParam(ActionParam param) {
		// TODO Auto-generated method stub
		String sql="INSERT into action_params(parent_id,name,sort_order,status,level,created,updated) values(?,?,?,?,?,?,?)";
		Object[] params= {param.getParent_id(),param.getName(),param.getSort_order(),param.getStatus(),param.getLevel(),param.getCreated(),param.getUpdated()};
		try {
			return queryRunner.update(sql,params);
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateParam(ActionParam param) {
		// TODO Auto-generated method stub
		String sql="update action_params set parent_id=?,name=?,sort_order=?,status=?,level=?,created=?,updated=? where id=?";
		Object[] params= {param.getParent_id(),param.getName(),
				param.getSort_order(),param.getStatus(),param.getLevel(),
				param.getCreated(),param.getUpdated(),param.getId()};
		try {
			return queryRunner.update(sql,params);
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<ActionParam> findParamsByParentId(Integer parentId) {
		// TODO Auto-generated method stub
		String sql="select * from action_params where parent_id=? order by sort_order";
		try {
			return queryRunner.query(sql, new BeanListHandler<ActionParam>(ActionParam.class),parentId);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteParam(Integer id) {
		// TODO Auto-generated method stub
		String sql="delete from action_params where id=?";
		try {
			return queryRunner.update(sql,id);
		}catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
