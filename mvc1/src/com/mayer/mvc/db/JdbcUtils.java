package com.mayer.mvc.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBC操作的工具类
 * @author MaY1E
 *
 */
public class JdbcUtils {
	
	/**
	 * 释放Connection连接
	 * @param conn
	 */
	public static void releaseConnection(Connection connection){
		try {
			if(connection !=null ){
				connection.close();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static DataSource datesource = null;
	static {
		datesource = new ComboPooledDataSource("mvc1");
	}
	/**
	 * 返回数据源的一个connection对象
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws SQLException{
		return datesource.getConnection();
	}
}
