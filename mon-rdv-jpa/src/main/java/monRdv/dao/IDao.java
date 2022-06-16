package monRdv.dao;

import java.util.List;

public interface IDao<T,PK> {
	List<T> findAll();
	T findById(PK id);
	T save(T obj);
	void delete(T obj);
}
