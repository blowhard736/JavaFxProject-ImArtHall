package control;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
	static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
	static final String driver = "oracle.jdbc.driver.OracleDriver";

	public static Connection getConnection() throws Exception {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, "scott", "tiger");
		System.out.println("연결성공");
		return con;
	}
}
