<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Account"%>

<%-- ページのタイトルを設定する --%>
<% 
   pageContext.setAttribute("title", "アカウントログイン", PageContext.PAGE_SCOPE);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>${title}</title>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<style>
.space {
	text-indent: 1.1em;
}
</style>

</head>
<body>

	<div class="container-md">

		<div class="card border-dark mb-3">

			<div class="card-header bg-success text-white text-center">${title}</div>

			<div class="card-body">
				<!-- ログインできない場合のエラーメッセージを表示 -->
				<c:if test="${LoginError != null}">
					<div class="alert alert-danger" role="alert">${LoginError}</div>
				</c:if>
				<!-- ログアウト成功時のメッセージを表示 -->
				<c:if test="${LogoutSuccess != null}">
					<div class="alert alert-success" role="alert">${LogoutSuccess}</div>
				</c:if>

				<form action="./LoginServlet" method="post">
					<div class="mt-4">
						<div class="md-3">

							<div class="border col-md-2">
								<label for="exampleInputEmail1" class="space form-label">E-mailアドレス</label>
							</div>

							<!-- E-mailが未入力か不完全な形式の場合のエラーメッセージを表示 -->
							<!-- ログインする際のE-mailを入力 -->
							<input type="email"
								class="form-control<c:if test="${errors.email != null}"> is-invalid</c:if>"
								value="<c:out value="${accountUser.email}" />" name="email"
								id="exampleInputEmail1" aria-describedby="emailHelp" required>
						</div>
					</div>

					<div class="mt-4">
						<div class="md-3">
							<div class="border col-md-2">
								<label for="exampleInputPassword1" class="space form-label">パスワード</label>
							</div>

							<!-- パスワードが未入力の場合のエラーメッセージを表示 -->
							<!-- ログインする際のパスワードを入力 -->
							<input type="password"
								class="form-control<c:if test="${errors.password != null}"> is-invalid</c:if>"
								value="<c:out value="${accountUser.password}" />"
								name="password" id="exampleInputPassword" required>
						</div>
					</div>

					<div class="mt-4">
						<!-- ログインし、検索ページに移る -->
						<button type="submit" class="btn btn-outline-primary">ログイン</button>

						<!-- アカウント登録画面へ移る -->
						<a href="./AccountRegisterServlet" class="col-md-2 offset-md-2">アカウント登録はこちら</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>