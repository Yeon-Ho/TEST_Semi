package web.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import web.dao.face.MailauthDao;
import web.dbutil.JDBCTemplate;
import web.dto.MailAuth;

public class MailauthDaoImpl implements MailauthDao {

	@Override
	public void insert(String code, String TO) {
		
		Connection conn = JDBCTemplate.getConnection();
		PreparedStatement ps = null;
		
		int result = -1;
		
		String sql = "INSERT INTO MAIL_AUTH VALUES(mailauth_seq.nextval, ?, ?)";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, TO);
			ps.setString(2, code);
			
			result = ps.executeUpdate();
			
			if( result > 0) {
				JDBCTemplate.commit(conn);
				System.out.println("인증코드와 이메일 삽입 성공");
			}else {
				JDBCTemplate.rollback(conn);
				System.out.println("인증코드와 이메일 삽입 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
		}
	}

	
	@Override
	public String selectRandom(String email) {

		Connection conn = JDBCTemplate.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String random = null;
		
		String sql = "select random from (select rownum rnum, B.* from (select * from mail_auth where email= ? order by random_no DESC) B ) where rnum = 1";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			
			rs = ps.executeQuery();
			
			while( rs.next() ) {
				random = rs.getString("RANDOM");
				System.out.println("저장된 랜덤값 : " + random);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(ps);
			JDBCTemplate.close(rs);
		}
		return random;
	}

}
