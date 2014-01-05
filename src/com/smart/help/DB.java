package com.smart.help;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

public class DB {
	private String class_forname;
	private String con_str;
	
	Statement		stmt	=	null;
	Connection	conn	=	null;
	
	public DB(){
		class_forname	=	"org.gjt.mm.mysql.Driver";
		con_str		=	"jdbc:mysql://localhost/information_schema?user=root&characterEncoding=UTF8";
		
		/*
		 * @ l�ӵ���ݿ�
		 */
		try
		{
			Class.forName(class_forname).newInstance();
			conn = DriverManager.getConnection(con_str);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		}
		catch (Exception e)
		{
			System.out.println("ERROR in Create a DB connection!");
			e.printStackTrace();
		}
	}
	
	public ResultSet ExcuteQuery(String sql){
		try
		{
			ResultSet	rs	=	stmt.executeQuery(sql);
			return rs;
		}
		catch (Exception e)
		{
			System.out.println("error in  initialDataHeader"+e.getMessage());
			return null;
		}
	}
	
	public void Close(){
		try
		{
			stmt.close();
			conn.close();
		}
		catch	(Exception e)
		{
			System.out.println("ERROR:"+e.getMessage());
		}
	}
}
