package com.kh.wsp.notice.model.service;

import static com.kh.wsp.common.DBCP.getConnection;

import java.sql.Connection;
import java.util.List;

import com.kh.wsp.notice.model.dao.NoticeExDAO;
import com.kh.wsp.notice.model.vo.Notice;

public class NoticeExService {
	
	private NoticeExDAO dao;
	
	public NoticeExService() throws Exception{
		dao = new NoticeExDAO();
	}
	
	
	
	/** 공지사항 목록 조회 
	 * @return list
	 * @throws Exception
	 */
	public List<Notice> selectList() throws Exception{
		Connection conn = getConnection();
		
		List<Notice> list =dao.selectList(conn);
		
		conn.close();
		
		return list;
		
	}
	
	/** 공지사항 등록 Service
	 * @param notice
	 * @return result
	 * @throws Exception
	 */
	public int insertNotice(Notice notice) throws Exception {
		
		Connection conn = getConnection();
		
		int noticeNo = dao.selectNextNo(conn);
		
		int result = 0;  // 삽입 결과 저장 변수
		
		if(noticeNo > 0) {
			
			// 크로스 사이트 스크립팅 방지
			notice.setNoticeContent(replaceParameter(notice.getNoticeContent()));
			
			// HTML 출력 시 개행문자 유지를 위한 처리
			notice.setNoticeContent(notice.getNoticeContent().replaceAll("\r\n", "<br>"));
		
			notice.setNoticeNo(noticeNo);
			result = dao.insertNotice(conn,notice);
			
			if(result > 0) {
				conn.commit();
				
				// 등록된 글번호를 result에 저장하여 전달
				result = noticeNo;
			}else {
				conn.rollback();
			}
		 
		}
		conn.close();
		
		return result;
	}
       // 크로스 사이트 스크립트 방지 메소드
	   private String replaceParameter(String param) {
		      String result = param;
		      if(param != null) {
		    	 result = result.replaceAll("&", "&amp;");
		         result = result.replaceAll("<", "&lt;");
		         result = result.replaceAll(">", "&gt;");
		         result = result.replaceAll("\"", "&quot;");
		      }
		      
		      return result;
		   }



	/** 게시글 상세 조회 Service
	 * @param noticeNo
	 * @return notice
	 * @throws Exception
	 */
	public Notice selectNotice(int noticeNo) throws Exception {
		Connection conn = getConnection();
		
		// 1. 공지사항 상세 조회
		Notice notice = dao.selectNotice(conn, noticeNo);
		
		
		// 2. 상세 조회 성공 시 조회수 1증가
		if(notice != null) {
			int result = dao.increaseCount(conn, noticeNo);
			
			if(result > 0) {
				conn.commit();
				
				// 반환되는 notice에 저장된 조회수를
				// DB와 맞게 1증가
				notice.setReadCount(notice.getReadCount() + 1);
				
			}
			 		
		}
		
		conn.close();
		return notice;
	}	
	
	
	
	
	
	
}
