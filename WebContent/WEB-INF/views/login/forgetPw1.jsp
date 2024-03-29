<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<!-- header -->
<c:import url="/WEB-INF/views/login/header.jsp"></c:import>

<!--XMLHttpRequest 생성  -->
<script type="text/javascript" src="/resources/js/httpRequest.js"></script>

<script type="text/javascript">
$(document).ready(function(){
	//전역변수
	var searchId = null;
	
	$("#id").blur(function(){
		var param = "id=" + $("#id").val();
		sendRequest("POST", "/searchid", param, callback);		
	})
	function callback() {
		console.log("아이디찾기 콜백함수 호출");
		if( httpRequest.readyState == 4 ){
			if( httpRequest.status == 200){
				searchIdResult();
			} else console.log("AJAX 요청/응답 에러")
		}
	}
	function searchIdResult(){
		searchId = JSON.parse(httpRequest.responseText);
		console.log(searchId);
		
		if( searchId != null){
			$("#idv").text("Okay!");
			$("#idv").css("color", "blue");
			$("#searchid").attr("value", searchId );
		} else{
			$("#idv").text("아이디가 조회되지 않습니다.");
			$("#idv").css("color", "red");
		}
	}
	$("#btnNext").click(function(){
		
		if( searchId == null ){
			console.log("test")
			$("#idv").text("비밀번호를 찾을 아이디를 입력하세요.");
			$("#idv").css("color", "red");
			return false;
		}
	})
});
</script>

<div style="width: 500px; margin:0 auto; height: 700px;">
<h1>비밀번호 찾기</h1>
<hr>
<h5>비밀번호를 찾고자 하는 아이디를 입력해 주세요.</h5>
	<div class="form-group">
    	<input type="text" id="id" name="id" class="form-control" placeholder="아이디를 입력하세요">
    	<div id="idv"></div>
	</div>
	<form action="/forgetpw" method="POST" > 
	<div style="width: 120px; margin:0 auto;"> 
		<input type="text" id="searchid" name="id" style="display: none;" />
		<button id="btnNext" class="form-control" style="width: 100px; background-color:#ccc;">다음 ></button>
	</div>
	</form>
</div>

<c:import url="/WEB-INF/views/login/footer.jsp"></c:import>