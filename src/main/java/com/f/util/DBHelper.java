package com.f.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBHelper {
	private static String driver;
	private static String url;
	private static String name;
	private static String password;
	private static Connection conn;
	static {
		try {
			Properties p = new Properties();
			InputStream in = DBHelper.class.getResourceAsStream("db.properties");
			p.load(in);
			driver = p.getProperty("db.driver");
			url = p.getProperty("db.url");
			name = p.getProperty("db.username");
			password = p.getProperty("db.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		try {
			Class.forName(driver);
			try {
				conn = DriverManager.getConnection(url, name, password);
			} catch (Exception e) {
				// TODO: handle exception
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return conn;
	}
	
	public static void closeConn(Connection conn) {
		if(conn!=null) {
			try {
				conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
