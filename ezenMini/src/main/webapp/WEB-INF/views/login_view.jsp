<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>   
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">    
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" 
integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" 
crossorigin="anonymous">
<!--
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
<title>logIn 창</title>
<style>
html,body {
 height: 100%;
 margin: 0;
 padding: 0;
}

#main {
 height: auto;
}

h3 {
 color: white;
 text-shadow: 1px 1px 2px black, 0 0 25px blue, 0 0 5px darkblue;
}
</style>
</head>
<body>

<%@ include file="nav.jsp" %>
<%@ include file="carousel.jsp" %>

<div id="main" class="container mt-5">
	<h3 class="text-center text-info">로그인</h3>
	<!-- login결과를 ajax에서 출력 -->
	<div id="div1" class="text-success"></div>
	<!-- spring security에서는 form을 이용하여 로그인해야하며 action의 login은 
		security에서 사용하는 기본값으로 controller의 login으로 요청하지 않고 spring security로 요청 -->
	<form id="frm1" name="frm1" method="post" action="login">
		<!-- csrf를 방지하기 위해 헤더 부분 추가 -->
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<div class="form-group">
			<label for="uId">아이디</label>
			<input type="email" class="form-control" name="pid" 
				placeholder="E-Mail주소 입력" id="uId" required />
		</div>
		<div class="form-group">
			<label for="uPw">비밀번호</label>
			<input type="password" class="form-control" name="ppw" id="uPw"
				placeholder="비밀번호 입력" required />
		</div>
		<div class="form-group form-check">
			<input type="checkbox" class="form-check-input" id="rememberMe" 
				name="remember-me" checked>
			<label class="form-check-label" for="rememberMe" 
				aria-describedby="rememberMeHelp">remember-me</label>	
				<!-- aria-describedby는 스크린리더에서 사용하며 좀 더 상세한 정보 표시 -->
		</div>
		<button type="submit" class="btn btn-success">로그인</button>&nbsp;&nbsp;
		<!-- social로그인 google,naver,kakao예쩡 -->
		<a href="join_view" class="btn btn-danger">회원가입</a>
	</form>
</div>

<%@ include file="footer.jsp" %>

<script>
$(document).ready(function(){
	//로그인 결과에 따른 안내문을 #div1에 출력하는 처리
	//if~else if~els의 jstl에서 처리
	<c:choose>
		<c:when test="${not empty log}"> 
			$("#div1").text("Welcome!");
		</c:when>
		<c:when test="${not empty logout}">
			$("#div1").text("Logout 성공");
		</c:when>
		<c:when test="${not empty error}">
			$("#div1").text("Login 실패");
		</c:when>
		<c:otherwise>
			$("#div1").text("welcome");
		</c:otherwise>
	</c:choose>
});
</script>
</body>
</html>