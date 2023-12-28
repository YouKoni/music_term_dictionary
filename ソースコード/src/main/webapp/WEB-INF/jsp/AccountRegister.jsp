<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Account"%>
<%-- ページのタイトルを設定する --%>
<% 
   pageContext.setAttribute("title", "アカウント登録", PageContext.PAGE_SCOPE);
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
			<div class="card-header bg-primary text-white text-center">${title}</div>
			<div class="card-body">
				<%-- アカウント登録する際のメールアドレスの重複におけるエラーメッセージ --%>
				<c:if test="${dbError != null}">
					<div class="alert alert-danger" role="alert">${dbError}</div>
				</c:if>
			
				<%-- アカウント登録する際のアカウント名入力におけるエラーメッセージ --%>
				<c:if test="${errors.name != null}">
					<div class="alert alert-danger" role="alert">${errors.name}</div>
				</c:if>
				<%-- アカウント登録する際のE-mail入力におけるエラーメッセージ --%>
				<c:if test="${errors.email != null}">
					<div class="alert alert-danger" role="alert">${errors.email}</div>
				</c:if>
				<%-- アカウント登録する際のパスワード入力におけるエラーメッセージ --%>
				<c:if test="${errors.password != null}">
					<div class="alert alert-danger" role="alert">${errors.password}</div>
				</c:if>
				<%-- アカウント登録する際のデータベース操作におけるエラーメッセージ --%>
				<c:if test="${db_error != null}">
					<div class="alert alert-danger" role="alert">${db_error}</div>
				</c:if>
				<form action="./AccountRegisterServlet" method="post">
					<div class="md-3">
						<div class="border col-md-2">
							<label for="exampleInput" class="space form-label">アカウント名</label>
						</div>
						<%-- アカウント名が未入力の場合のエラーメッセージを表示 --%>
						<%-- アカウント登録する際のアカウント名を入力 --%>
						<input type="text"
							class="form-control<c:if test="${errors.name != null}"> is-invalid</c:if>"
							value="<c:out value="${accountUser.name}" />" name="name" id="" required>
					</div>
					<div class="mt-4">
						<div class="md-3">
							<div class="border col-md-2">
								<label for="exampleInputEmail1" class="space form-label">E-mailアドレス</label>
							</div>
							<%-- E-mailが未入力か不完全な形式の場合のエラーメッセージを表示 --%>
							<%-- アカウント登録する際のE-mailを入力 --%>
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
							<%-- パスワードが未入力の場合のエラーメッセージを表示 --%>
							<%-- アカウント登録する際のパスワードを入力 --%>
							<input type="password"
								class="form-control<c:if test="${errors.password != null}"> is-invalid</c:if>"
								value="<c:out value="${accountUser.password}" />"
								name="password" id="exampleInputPassword" required>
						</div>
					</div>
					<div class="mt-4">
						<%-- アカウント情報を登録し、ログインページに移る --%>
						<button type="submit" class="btn btn-outline-primary">登録</button>
						<%-- ログインページに移る --%>
						<a href="./LoginServlet" class="col-md-2 offset-md-2">ログインページはこちら</a>
						<%-- アカウント登録せずに検索ページに移る --%>
						<a href="./MainServlet" class="col-md-3 offset-md-3">アカウント登録をせずに使用する</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>