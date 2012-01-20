package com.utopia.lijiang.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.utopia.lijiang.R;

public class DBHelper extends OrmLiteSqliteOpenHelper {

	private ConnectionSource connectionSource = null;
	
	public DBHelper(Context context,CursorFactory factory,int version){
		super(context, context.getString(R.string.app_name), factory, version);
	}
	
	public void openConnectionSource(){
		connectionSource = new AndroidConnectionSource(this);
	} 
	
	public void closeConnection(){
		if(connectionSource !=null){
			try {
				connectionSource.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public <T,ID> Dao<T,ID> createDao(Class<T> clazz) throws SQLException{
		Dao<T,ID> dao =
			     DaoManager.createDao(connectionSource, clazz);
		
		return dao;
	}
	
	public <T,ID> Iterator<T> read(Class<T> clazz) throws SQLException{
		createTableIfNotExists(clazz);
		Dao<T,ID> dao = createDao(clazz);
		CloseableWrappedIterable<T> iterator = dao.getWrappedIterable();
		return iterator.iterator();
	} 
	
	public <T,ID> void save(T object)throws SQLException{
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) object.getClass();
		List<T> list = new ArrayList<T>();
		list.add(object);
		this.save(list, clazz);
	}
	
	public <T,ID> void save(List<T> list,Class<T> clazz) throws SQLException{	
		openConnectionSource();
		createTableIfNotExists(clazz);
		
		Callable<Void> call = createSaveListCallable(list,clazz);
		TransactionManager.callInTransaction(connectionSource,call);
		
		closeConnection();
	}
	
	public <T,ID> int delete(T object) throws SQLException{
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) object.getClass();
		Dao<T,ID> dao = createDao(clazz);
		return dao.delete(object);
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

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}
}
