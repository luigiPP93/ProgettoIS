package gestioneProdotti;

import java.sql.SQLException;
import java.util.Collection;

public interface ShopModel<T>{
	
	public T doRetrieveByKey(String code) throws SQLException;
	
	public Collection<T> doRetrieveAll(String order) throws SQLException;
	
	public void doSave(T item) throws SQLException;
	
	public boolean doUpdate(T item) throws SQLException;

	public boolean doDelete(T item) throws SQLException;

}