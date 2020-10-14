package com.woshidaniu.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.junit.Test;

public class OracleDriverTest extends TestCase {

	@Test
	public void testName() throws Exception {

		try {
			// 加载Oracle的驱动类
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类 ，加载驱动失败！");
			e.printStackTrace();
		}

		// 连接MySql数据库，用户名和密码都是root
		String url = "jdbc:oracle:thin:@10.71.33.202:1521:orcl";
		String username = "root";
		String password = "root";
		try {
			Connection con = DriverManager.getConnection(url, username, password);
			PreparedStatement pstmt = con.prepareStatement("select *  from dual");
			ResultSet rs = pstmt.executeQuery();
			
			
		} catch (SQLException se) {
			System.out.println("数据库连接失败！");
			se.printStackTrace();
		}

	}

}
