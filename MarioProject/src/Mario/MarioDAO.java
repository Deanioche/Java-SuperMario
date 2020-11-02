package Mario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarioDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String username = "c##java";
	private String password = "bit";

	private static MarioDAO instance = null;

	/* ******************************************************************* */
	// 생성자
	public MarioDAO() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}	// MarioDAO()
	
	
	
	/* ******************************************************************* */

	/*
	 *  DB에 접속
	 */
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/* ******************************************************************* */
	
	//★싱글톤 
	public static MarioDAO getInstance() {
		if(instance == null) {
			synchronized(MarioDAO.class) {
				instance = new MarioDAO();
			}
		}
		return instance;
	}
	/* ******************************************************************* */
	
	//★DB에서 시퀀스 받아오기(본 java파일이 아닌 dto객체생성쪽 insertArticle()에서만 사용, 
	//                   update&delete는 시퀀스 수령방법 확인필요(136번 주석 참조))
	public int getSeq() {
		int seq = 0;
		String sql = "select seq_mario.nextval from dual";
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			seq = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return seq;
	}
	
	
	
	/* ******************************************************************* */
    //★회원가입시 DB에 입력(8항목)
	public int insertArticle(MarioDTO dto) { 
		int su = 0;
		String sql = "insert into mario values(?,?,?,?,?,?,?,?)"; 
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getSeq());
			pstmt.setString(2, dto.getClientAccount()); //메일 도메인은 dto객체 생성시 받을 예정
			pstmt.setString(3, dto.getPassword());
			pstmt.setString(4, dto.getRealName());
			pstmt.setInt(5, dto.getAge());
			pstmt.setString(6, dto.getNickname());
			pstmt.setInt(7, dto.getGender());
			pstmt.setInt(8, dto.getInfoAgree());
			
			su = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
		return su;
	}
	
	/* ******************************************************************* */

	//★회원정보 수정, 게임 종료 후 DB에 입력 (11항목)
	public int updateArticle(MarioDTO dto) {
		int su = 0;
		String sql = "update mario set clientaccount=?" //★★★★★★회원정보 수정창에서 입력창비활성화
				   + ",password=?" //2
				   + ",realname=?" //3
				   + ",age=?" //4
				   + ",nickname=?" //5
				   + ",gender=?"//6
				   + ",infoagree=?"//7
				   + ",score=?"//8
				   + "goaltime=?"//9
				   + "playerrank=?"//10
				   + "where seq=?"; //11
		        
		getConnection();	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getClientAccount());
			pstmt.setString(2, dto.getPassword());
			pstmt.setString(3, dto.getRealName());
			pstmt.setInt(4, dto.getAge());
			pstmt.setString(5, dto.getNickname());
			pstmt.setInt(6, dto.getGender());
			pstmt.setInt(7, dto.getInfoAgree());
			pstmt.setInt(8,dto.getScore());
			pstmt.setInt(9, dto.getPlayerRank());
			pstmt.setString(10, dto.getGoalTime());
			pstmt.setInt(11, dto.getSeq()); //★★★★중요!!객체에서 선택한 seq(새로 생성한 시퀀스X)로 넘겨주고 있는지 확인
			                                //★★★★seq 현재 로그인하고 있는 유저로 구분?(게시판 작성 참조) 
			                                //->새로 정보 받음
			
			su = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			try {
			
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return su;
	}
	
	
	
	/* ******************************************************************* */

	public void deleteArticle(int seq) {
		String sql = "delete mario where seq = ?";
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, seq); //★★★★중요!!객체에서 선택한 seq(새로 생성한 시퀀스X)로 넘겨주고 있는지 확인
								 //★★★★seq 현재 로그인하고 있는 유저로 구분?(게시판 작성 참조)
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/* ******************************************************************* */
	//★DB테이블에 저장되어있는 정보 가져오는 메소드 
	//테이블 정보 어디에 출력할지 레이아웃 확인
	public List<MarioDTO> getMarioList(){
		List<MarioDTO> dtolist = new ArrayList<MarioDTO>();
		String sql = "select * from mario order by seq";
		getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				MarioDTO dto = new MarioDTO();
				dto.setSeq(rs.getInt("seq"));
				dto.setClientAccount(rs.getString("clientaccount"));
				dto.setPassword(rs.getString("password"));
				dto.setRealName(rs.getString("realname"));
				dto.setAge(rs.getInt("age"));
				dto.setNickname(rs.getString("nickname"));
				dto.setGender(rs.getInt("gender"));
				dto.setInfoAgree(rs.getInt("infoagree"));
				
				dto.setScore(rs.getInt("score"));
				dto.setGoalTime(rs.getString("goaltime"));
				dto.setPlayerRank(rs.getInt("playerrank"));
				
				dtolist.add(dto);
				
			}//while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//finally
		
		return dtolist;
	
	}//getMarioList()
	/* ******************************************************************* */
} //class

/*
<DB테이블 생성>
 1.
create table mario(seq number primary key,
clientaccount varchar2(30) not null,
password varchar2(15)not null,
realname varchar2(15) not null,
age number not null,
nickname varchar2(30) not null,
gender number not null,
infoagree number not null,

score number,
goaltime varchar2(24),
playerrank number);

2.
create sequence seq_mario nocycle nocache;

 */



/*
 <상의 내용> 2020.11.02
 1. DTO에 seq항목 추가 및 setter&getter / playerRank setter&getter 추가 
     ->OK
 2. 닉네임은 마인드마이스터 상에서 회원가입시로 되어있는데 어느 타이밍?(테이블 notnull, insertArticle 수정필요)
     ->회원가입시 데이터베이스에 전송
 	마인드마이스터에 회원가입 정보 다시한번 정리(생년월일, 성별, 이메일, 나머지 텍스트 필드는 구체적으로?)
 	 ->정리한것 갤탭패드에 있음
 2. nickname,playerIdNum 유사한 기능? playerIdNum의 역할
     ->playerIdNum삭제
 2. DAO의 메소드 파라미터 MarioDAO dto로 되어있던데 오타? 아니면 다른 형식으로 구성?
     ->현재 작성내용으로 진행
 3. DB테이블은 아래와 같은 항목, 상세 정보는 갤탭, playerColorString 추가 필요여부 확인
     ->DB에 입력할 항목 정리
 4. insertArticel()은 회원가입시, updateArticle()은 
       채팅방 입장(캐릭터 생성), 회원정보수정, 게임종료 후로 생각하는데 맞는지?
     ->회원정보수정(이름,점수,골타임,랭크 비활성화), 게임종료 후(점수, 골타임, 랭크만 update)
 5. updateArticle(), deleteArticel()은 seq번호 받아오는 방법 주의, 
      어디서 어떻게 받아올지? 로그인하고 있는 계정과 삭제버튼을 누르는 계정이 같은지 조회?(게시판 작성 참조)
     ->회원정보 수정할때 한번 더 비밀번호 입력
 6. 테이블에서 정보가져오는 메소드 미완성
     ->완성, 나중에 다 같이 합쳐서 한번 확인하기ㄴ
      레이아웃 상, 어디서 어떤 정보를 출력할 것인지 확인하기
*/
 

