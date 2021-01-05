package dao;
import java.sql.*;

public class DbConnectDAO {
	public static Connection getConnection() {
		try {
		//J加载名字为 com.mysql.jdbc.Driver的Driver类。
			Class.forName("com.mysql.jdbc.Driver");
		//从DriverManager取得连接
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
