package dao;

import java.sql.*;

public class Conn {
	public static Connection con = null;

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/grade", "root", "mupkmupk");
			System.out.println("���ݿ����ӳɹ�");
		} catch (Exception e) {
			System.out.println("���ݿ�����ʧ��");
		}
		return con;
	}
}