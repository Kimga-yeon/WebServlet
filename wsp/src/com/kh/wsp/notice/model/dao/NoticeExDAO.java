package com.kh.wsp.notice.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.wsp.notice.model.vo.Notice;

public class NoticeExDAO {
	private Properties prop;
	
	public NoticeExDAO() throws FileNotFoundException, IOException {
		String fileName = 
				NoticeExDAO.class.getResource("/com/kh/wsp/sql/notice/notice-query.properties").getPath();
		
		prop = new Properties();
		prop.load(new FileReader(fileName));
		
	}
	
	

	/** 공지사항 목록 조회 DAO
	 * @param conn
	 * @return list
	 * @throws Exception
	 */
	public List<Notice> selectList(Connection conn) throws Exception {
		Statement stmt = null;
		ResultSet rset = null;
		
		List<Notice> list = null;
		
		String query = prop.getProperty("selectList");
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			list = new ArrayList<Notice>();
			
			while(rset.next()) {
				list.add(new Notice(rset.getInt("NOTICE_NO"),
									rset.getString("NOTICE_TITLE"),
									rset.getString("MEMBER_ID"),
									rset.getInt("READ_COUNT"),
									rset.getDate("NOTICE_MOD"
											+ ""
											+ "IFY_DT")));
			}
			
			
			
		}finally {
			rset.close();
			stmt.close();
		}
		
		return list;
	}
	
	public int selectNextNo(Connection conn) throws Exception {

		Statement stmt = null;
		ResultSet rset = null;
		int noticeNo = 0;
		
		String query = prop.getProperty("selectNextNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next()) noticeNo = rset.getInt(1);		
		
		}finally {
			rset.close();
			stmt.close();
		}

	return noticeNo;

	}



	public int insertNotice(Connection conn, Notice notice) throws Exception {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("insertNotice");
		
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, notice.getNoticeNo());
			pstmt.setString(2, notice.getNoticeTitle());
			pstmt.setString(3, notice.getNoticeContent());
			pstmt.setString(4, notice.getMemberId());
			
			result = pstmt.executeUpdate();
			
		}finally {
			pstmt.close();
		}

		return result;
	
	}



	public Notice selectNotice(Connection conn, int noticeNo) throws Exception {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Notice notice = null;
		
		String query = prop.getProperty("selectNotice");

		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				notice= new Notice(noticeNo, 
						rset.getString("NOTICE_TITLE"), 
						rset.getString("NOTICE_CONTENT"), 
						rset.getString("MEMBER_ID"), 
						rset.getInt("READ_COUNT"), 
						rset.getDate("NOTICE_MODIFY_DT"));
			}
			
			
		}finally {
			rset.close();
			pstmt.close();
		}
		
		return notice;
		
	}



	/** 조회수 증가 DAO
	 * @param conn
	 * @param noticeNo
	 * @return result
	 * @throws Exception
	 */
	public int increaseCount(Connection conn, int noticeNo) throws Exception {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			result = pstmt.executeUpdate();
			
			
		}finally {
			pstmt.close();
		}
		
		return result;
	}
	
	
	
	
	
	
	

}
