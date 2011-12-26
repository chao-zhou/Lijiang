package com.utopia.lijiang.db;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

import android.database.sqlite.SQLiteOpenHelper;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper {

	private SQLiteOpenHelper sqliteOpenHelper = null;
	private ConnectionSource connectionSource = null;
	
	public DBHelper(SQLiteOpenHelper sqliteOpenHelper){
		this.sqliteOpenHelper = sqliteOpenHelper;
	}
	
	public void openConnectionSource(){
		connectionSource = new AndroidConnectionSource(sqliteOpenHelper);
	} 
	
	public void closeConnection() throws SQLException{
		if(connectionSource !=null){
			connectionSource.close();
		}
	}
	
	public <T,ID> Dao<T,ID> createDao(Class<T> clazz) throws SQLException{
		Dao<T,ID> dao =
			     DaoManager.createDao(connectionSource, clazz);
		return dao;
	}
	
	public <T,ID> void Save(List<T> list,Class<T> clazz) throws SQLException{	
		openConnectionSource();
		createTableIfNotExists(clazz);
		
		Callable<Void> call = createSaveListCallable(list,clazz);
		TransactionManager.callInTransaction(connectionSource,call);
		
		closeConnection();
	}
	
	private <T> void createTableIfNotExists(Class<T> clazz) throws SQLException{
		TableUtils.createTableIfNotExists(connectionSource, clazz);
	}
	
	private <T,ID> Callable<Void> createSaveListCallable(final List<T> list,final Class<T> clazz){
		return  new Callable<Void>() {
		    public Void call() throws Exception {
		    	saveList(list,clazz);
		        return null;
		    }
		};
	}
	
	private <T,ID> void saveList(List<T> list,Class<T> clazz) throws SQLException{
		Dao<T,ID> dao = createDao(clazz);
		T data = null;
		for(int location = 0; location < list.size();location++){
			data = list.get(location);
		 	dao.createOrUpdate(data);
		}
	
	}
}
