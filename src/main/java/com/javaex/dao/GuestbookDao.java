package com.javaex.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javaex.vo.GuestbookVo;

@Repository
public class GuestbookDao {

	//필드
	@Autowired
	private SqlSession sqlSession;
   
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
	
	//Guestbook 찾기
	public GuestbookVo oneGuest(int no) {
		
		System.out.println("Dao>oneGuest");
		GuestbookVo guestbookVo = sqlSession.selectOne("guestbook.oneGuest", no);
		System.out.println(guestbookVo);
		
		return guestbookVo;
		
	}
	//정보 삭제 메소드
	public int guestDelete(int no, String password) {
		
		System.out.println("Dao>guestDelete");
		int count = sqlSession.delete("guestbook.delete", no);
		System.out.println(count);
		
		return count;
		
	}
		
}
