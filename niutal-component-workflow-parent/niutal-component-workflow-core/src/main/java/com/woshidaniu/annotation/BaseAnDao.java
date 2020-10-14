package com.woshidaniu.annotation;

import java.util.List;

public interface BaseAnDao<T,Q> {
	public void insert(T entity);
	public void update(T entity);
	public void delete(T entity);
	public List<T> findList(Q query);
	public T findById(Q query);
}
