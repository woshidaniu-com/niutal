/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

/**
 * 装饰SqlSession,限制commit和rollback操作,防止业务开发人员在插件里面搞破坏!!!
 * @author 		：康康（1571）
 */
class DelegateSqlSession implements SqlSession{
	
	private SqlSession orginalSqlSesion;
	
	public DelegateSqlSession(SqlSession orginalSqlSesion) {
		super();
		this.orginalSqlSesion = orginalSqlSesion;
	}

	@Override
	public <T> T selectOne(String statement) {
		return this.orginalSqlSesion.selectOne(statement);
	}

	@Override
	public <T> T selectOne(String statement, Object parameter) {
		return this.orginalSqlSesion.selectOne(statement, parameter);
	}

	@Override
	public <E> List<E> selectList(String statement) {
		return this.orginalSqlSesion.selectList(statement);
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter) {
		return this.orginalSqlSesion.selectList(statement, parameter);
	}

	@Override
	public <E> List<E> selectList(String statement, Object parameter, RowBounds rowBounds) {
		return this.orginalSqlSesion.selectList(statement, parameter, rowBounds);
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, String mapKey) {
		return this.orginalSqlSesion.selectMap(statement, mapKey);
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey) {
		return this.orginalSqlSesion.selectMap(statement, parameter, mapKey);
	}

	@Override
	public <K, V> Map<K, V> selectMap(String statement, Object parameter, String mapKey, RowBounds rowBounds) {
		return this.orginalSqlSesion.selectMap(statement, parameter, mapKey, rowBounds);
	}

	@Override
	public <T> Cursor<T> selectCursor(String statement) {
		return this.orginalSqlSesion.selectCursor(statement);
	}

	@Override
	public <T> Cursor<T> selectCursor(String statement, Object parameter) {
		return this.orginalSqlSesion.selectCursor(statement, parameter);
	}

	@Override
	public <T> Cursor<T> selectCursor(String statement, Object parameter, RowBounds rowBounds) {
		return this.orginalSqlSesion.selectCursor(statement, parameter, rowBounds);
	}

	@Override
	public void select(String statement, Object parameter, ResultHandler handler) {
		this.orginalSqlSesion.select(statement, parameter, handler);
	}

	@Override
	public void select(String statement, ResultHandler handler) {
		this.orginalSqlSesion.select(statement, handler);
	}

	@Override
	public void select(String statement, Object parameter, RowBounds rowBounds, ResultHandler handler) {
		this.orginalSqlSesion.select(statement,parameter,rowBounds, handler);
	}

	@Override
	public int insert(String statement) {
		return this.orginalSqlSesion.insert(statement);
	}

	@Override
	public int insert(String statement, Object parameter) {
		return this.orginalSqlSesion.insert(statement,parameter);
	}

	@Override
	public int update(String statement) {
		return this.orginalSqlSesion.update(statement);
	}

	@Override
	public int update(String statement, Object parameter) {
		return this.orginalSqlSesion.update(statement,parameter);
	}

	@Override
	public int delete(String statement) {
		return this.orginalSqlSesion.delete(statement);
	}

	@Override
	public int delete(String statement, Object parameter) {
		return this.orginalSqlSesion.delete(statement, parameter);
	}

	@Override
	public void commit() {
		throw new UnsupportedOperationException("不允许的操作");
	}

	@Override
	public void commit(boolean force) {
		throw new UnsupportedOperationException("不允许的操作");
	}

	@Override
	public void rollback() {
		throw new UnsupportedOperationException("不允许的操作");
	}

	@Override
	public void rollback(boolean force) {
		throw new UnsupportedOperationException("不允许的操作");
	}

	@Override
	public List<BatchResult> flushStatements() {
		return this.orginalSqlSesion.flushStatements();
	}

	@Override
	public void close() {
		throw new UnsupportedOperationException("不允许的操作");
	}

	@Override
	public void clearCache() {
		throw new UnsupportedOperationException("不允许的操作");
	}

	@Override
	public Configuration getConfiguration() {
		return this.orginalSqlSesion.getConfiguration();
	}

	@Override
	public <T> T getMapper(Class<T> type) {
		return this.orginalSqlSesion.getMapper(type);
	}

	@Override
	public Connection getConnection() {
		return this.orginalSqlSesion.getConnection();
	}
}
