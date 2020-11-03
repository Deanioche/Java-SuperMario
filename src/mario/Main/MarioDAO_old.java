package mario.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MarioDAO_old {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "bit";

	private static MarioDTO_old instance;

	/* ******************************************************************* */
	// 생성자
	public MarioDAO_old() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}	// MarioDAO()
	
	
	
	/* ******************************************************************* */

	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 *  DB에 접속
	 */
	
	
	/* ******************************************************************* */

	public int insertArticle(MarioDAO_old dto) {

	}
	
	
	
	/* ******************************************************************* */

	public int updateArticle(MarioDAO_old dto) {

	}
	
	
	
	/* ******************************************************************* */

	public void deleteArticle(int seq) {

		
		
	}
	
	/* ******************************************************************* */
}
