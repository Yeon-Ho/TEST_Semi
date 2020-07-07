package web.controller.eval;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.RequestWrapper;

import web.service.face.ReviewService;
import web.service.face.StarService;
import web.service.impl.ReviewServiceImpl;
import web.service.impl.StarServiceImpl;

@WebServlet("/eval/detail/insert")
public class DetailInsertController extends HttpServlet {
   
   private static final long serialVersionUID = 1L;
   private StarService starService = new StarServiceImpl();
   private ReviewService reviewService = new ReviewServiceImpl();
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

      request.setAttribute("menuno", request.getParameter("menuno"));
      HttpSession session = request.getSession();
      session.getAttribute("userno");
      System.out.println("여기는 유저아이디 : "+session.getAttribute("userno"));
     
      
      System.out.println("여기는 검증하는 코드쪽입니다 : "+request.getParameter("revcnt"));
      
      
      request.setAttribute("revcnt", request.getParameter("revcnt"));
      
   request.getRequestDispatcher("/WEB-INF/views/eval/insert.jsp").forward(request, response);
   
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  System.out.println("여기는 디테일 컨트롤러임다 : "+ request.getAttribute("revcnt"));
	  
    	  starService.StarInsert(request);
    	  reviewService.ReviewInsert(request); 

}
}