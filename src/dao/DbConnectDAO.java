package dao;
import java.sql.*;

public class DbConnectDAO {
	public static Connection getConnection() {
		try {
		//J��������Ϊ com.mysql.jdbc.Driver��Driver�ࡣ
			Class.forName("com.mysql.jdbc.Driver");
		//��DriverManagerȡ������
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/news", "root", "111111");
			if (con != null) {
				return con;
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;

	}

	public static void freeConnection(Connection con, Statement s, ResultSet rs) {

		try {

			if (null != rs) {
				con.close();
			}
			if (null != s) {
				con.close();
			}
			if (null != con) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

}
