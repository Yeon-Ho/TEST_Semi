package web.controller.eval;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import web.dto.Report;
import web.service.face.ImageService;
import web.service.face.MenuService;
import web.service.face.ReportService;
import web.service.face.ReviewService;
import web.service.face.StarService;
import web.service.impl.ImageServiceImpl;
import web.service.impl.MenuServiceImpl;
import web.service.impl.ReportServiceImpl;
import web.service.impl.ReviewServiceImpl;
import web.service.impl.StarServiceImpl;

@WebServlet("/eval/detail/report")
public class DetailReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MenuService menuService = new MenuServiceImpl();

	private ImageService imageService = new ImageServiceImpl();
	
	private StarService starService = new StarServiceImpl();
	
	private ReviewService reviewService = new ReviewServiceImpl();
	
	private ReportService reportService = new ReportServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	      HttpSession session = req.getSession();
	      
	     session.getAttribute("userno");
	      
	      String menuno = req.getParameter("menuno");
	      String reviewno = req.getParameter("reviewno");
	      String targetno = req.getParameter("targetno");
	      String content = req.getParameter("content");
	      
	      System.out.println("- - - - - - - - -- -- -- -- -- -- -- -- -- -- -- -");
	      System.out.println("로그인한 유저넘버 : "+session.getAttribute("userno"));
	      System.out.println("신고등록할 메뉴넘버 : " + req.getParameter("menuno"));
	      System.out.println("리뷰번호 : " + req.getParameter("reviewno"));
	      System.out.println("대상자번호 : " + req.getParameter("targetno"));
	      System.out.println("한줄평내용 : " + req.getParameter("content"));
	      System.out.println("- - - - - - - - -- -- -- -- -- -- -- -- -- -- -- -");
	      
	      
	      req.setAttribute("loginUserNo", session.getAttribute("userno"));
	      req.setAttribute("targetno", targetno);
	      req.setAttribute("content", content);
	      
	      req.setAttribute("menuno", menuno);
	      req.setAttribute("reviewno", reviewno);
	      
	      
	      
	      
	      req.getRequestDispatcher("/WEB-INF/views/eval/report.jsp").forward(req, resp);
	
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("ajax 수정 받았음");
	
		Report report = new Report();
		
		int reason = Integer.parseInt(req.getParameter("report_reason"));
		req.getParameter("report_content");
		int loginUserNo = Integer.parseInt(req.getParameter("loginUserNo"));
		int targetNo  = Integer.parseInt(req.getParameter("targetNo"));
		int reviewNo =Integer.parseInt( req.getParameter("reviewNo"));
	
		report.setRptRsn(reason);
		report.setRptDetail(req.getParameter("report_content"));
		report.setRptUser(loginUserNo);
		report.setRptTarget(targetNo);
		report.setReviewNo(reviewNo);
		
		System.out.println(report );
		
		reportService.reviewReport(report);
		
		
	}
	
}
