package web.controller.mail;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import web.service.face.MailauthService;
import web.service.impl.MailauthServiceImpl;
import web.util.MailAuth;

@WebServlet("/send")
public class MailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MailauthService mailauthService = new MailauthServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// FROM
		//	보내는 사람 정보
		final String FROM = "jiyoung8735@gmail.com"; // <<------------------------------수정하세요
		final String FROMNAME = "브랜드평점"; // <<------------------------------수정하세요
		
		// TO
		//	받는 사람 정보
		final String TO = req.getParameter("useremail"); // <<------------------------------수정하세요
		
		// --- UUID 생성 ---
		UUID uuid = UUID.randomUUID(); //랜덤 UID 생성
		String code = uuid.toString().split("-")[0]; //8자리 uid
		System.out.println("생성한 랜덤값 : " + code);
		
		
		// 생성한 랜덤값과 사용자 이메일 저장
		mailauthService.insert(code, TO);
		
		// 메일 제목
		final String SUBJECT = "회원가입 인증 테스트"; // <<------------------------------수정하세요

		// 메일 본문
		//	<p>태그 내부에 텍스트 내용에 입력
		final String BODY = String.join(
				"<h1>코드 : ",
				"<h1>이메일 인증 코드</h1><br>",
				code + "</h1>"); // <<------------------------------수정하세요

		// 인증 객체
		//	보내는 사람의 실제 구글 계정 입력
		Authenticator auth = new MailAuth("jiyoung8735@gmail.com", "rnlxhd8893"); // <<------------------------------수정하세요
		
		
		// 연결 설정
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		
		// 메일 세션 객체 생성
		Session session = Session.getDefaultInstance(props, auth);

		// 메시지 생성
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(FROM, FROMNAME));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
			msg.setSubject(SUBJECT);
			msg.setContent(BODY, "text/html;charset=utf-8");

			System.out.println("Sending...");

			//메시지 보내기
			Transport.send(msg);
			
			System.out.println("Email sent!");
			
			//이메일 발송 성공을 전달해주어야 한다
			resp.setContentType("application/json;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println( "{\"result\":true}" );
			
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			
			//이메일 발송 실패를 전달해주어야 한다
			resp.setContentType("application/json;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println( "{\"result\":false}" );
			
		} catch (MessagingException e) {
			e.printStackTrace();
			
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + e.getMessage());
			
			//이메일 발송 실패를 전달해주어야 한다
			resp.setContentType("application/json;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println( "{\"result\":false}" );
			
		} 			
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//테스트
		System.out.println("사용자가 입력한 코드 : " + req.getParameter("code") + ", 사용자 이메일 : " + req.getParameter("useremail") );
		
		//전달값 꺼내기
		String code = req.getParameter("code");
		String email = req.getParameter("useremail");
		
		//인증코드 비교
		boolean result = mailauthService.verifyCode(code, email);
		System.out.println(result);
	
		resp.setContentType("application/json;charset=utf-8");
		PrintWriter out = resp.getWriter();
		if( result ) {
			//인증 성공을 전달해주어야 한다
			out.println(  "{\"result\":true}" );
		}else {
			//인증 실패를 전달해주어야 한다
			out.println( "{\"result\": false}" );
		}
	
	}
	
	
	
	
	
}
