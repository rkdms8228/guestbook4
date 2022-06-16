package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	@Autowired
	private SqlSession sqlSession;
   
	//필드
	private String id = "webdb";
	private String pw = "webdb";
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
   
	//생성자
   
   
	//메소드-gs
   
   
	//메소드-일반
	//정보 전체 조회 메소드
	public List<GuestbookVo> getList() {
		
		System.out.println("Dao>getList");
		List<GuestbookVo> guestList = sqlSession.selectList("guestbook.selectList");
		System.out.println(guestList);
		
		return guestList;
		
	}
	
	//정보 등록 메소드
	public int guestInsert(GuestbookVo guestbookVo) {
		
		System.out.println("Dao>guestInsert");
		int count = sqlSession.insert("guestbook.guestInsert", guestbookVo);
		System.out.println(count);
		
		return count;
		
	}
	
	//정보 삭제 메소드
	public int guestDelete(int no, String password) {
		
		System.out.println("Dao>guestDelete");
		int count = sqlSession.delete("guestbook.delete", no);
		System.out.println(count);
		
		return count;
		
	}
	
	//Guestbook 찾기
	public GuestbookVo oneGuest(int delNo) {
		
		System.out.println("Dao>oneGuest");
		GuestbookVo guestbookVo = sqlSession.selectOne("guestbook.oneGuest", delNo);
		System.out.println(guestbookVo);
		
		return guestbookVo;
		
	}
		
	public void getConnection() {
		
		try {

			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);
			
			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}
	
	//>자원 정리 메소드
	public void close() {
		
		// 5. 자원정리
		try {
			
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
	}
	
	//정보 삭제 메소드
	public int guestDelete2(int no, String password) {
		
		int count = -1;
		
		getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			
			//SQL문 준비
			String query = "";
			query += " delete from guestbook ";
			query += " where no = ? ";
			query += " and password = ? ";
			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.setString(2, password);
			

			//실행
			count = pstmt.executeUpdate();

			
			//바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			pstmt.setString(2, password);
			
			//실행
			count = pstmt.executeUpdate();
			
			// 4.결과처리
			System.out.println("["+count + "건이 삭제되었습니다.]");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		
		return count;
		
	}

}
