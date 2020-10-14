/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.comm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

import com.woshidaniu.common.factory.ServiceFactory;

/**
 * @author xiaokang
 * 为了获取jdbc数据库连接
 */
public class PlainDatasourceResource {

	protected SqlSessionFactoryBean ssfb = null;
	protected SqlSessionFactory sqlSessionFactory = null;
	protected SqlSession sqlSession = null;
	protected Connection conn = null;
	protected PreparedStatement statement;
	protected ResultSet resultSet;
	
	/**
	 * 初始化导入数据库连接
	 */
	protected void initConnection() {
		ssfb = (SqlSessionFactoryBean) ServiceFactory.getService(SqlSessionFactoryBean.class);
		try {
			sqlSessionFactory = ssfb.getObject();
		} catch (Exception e) {
			throw new RuntimeException("获取数据库连接失败！" + e.getMessage(), e);
		}
		sqlSession = sqlSessionFactory.openSession();
		conn = sqlSession.getConnection();
	}
	
	/**
	 * 关闭导入数据库对象
	 */
	protected void close(Connection conn , Statement ps, ResultSet rs , SqlSession sqlSession) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}				
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}				
		}
		if (conn!= null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}				
		}
		if (sqlSession != null) {
			sqlSession.close();				
		}
	}

	/**
	 * 获取数据库连接
	 * @return
	 */
	protected Connection getConn() {
		return conn;
	}
}
