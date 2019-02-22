package Controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtility {

	public static Connection getConnection() {
		Connection con = null;
		try {
			// 1.MySql database class 로드한다
			Class.forName("com.mysql.jdbc.Driver");
			// 2.주소,아이디,비밀번호를 통해서 접속요청한다.
			con = DriverManager.getConnection("jdbc:mysql://localhost/gamedb", "root", "123456");
		} catch (Exception e) {
			GameController.callAlert("데이터베이스 연결 실패:연결 실패하였습니다.");
			return null;
		}
		return con;
	}

}
