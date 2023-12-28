<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Account,java.util.List,java.util.Date"%>
<%-- ページのタイトルを設定する --%>
<% 
   pageContext.setAttribute("title", "アカウント更新", PageContext.PAGE_SCOPE);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
				<%-- アカウント更新する際のデータベース操作におけるエラーメッセージ --%>
				<c:if test="${dbError != null}">
					<div class="alert alert-danger" role="alert">${dbError}</div>
				</c:if>
				<form action="./AccountUpdateServlet" method="post">
					<div class="md-3">
						<div class="border col-md-2">
							<label for="exampleInput" class="space form-label">アカウント名</label>
						</div>
						<%-- アカウント名が未入力の場合のエラーメッセージを表示 --%>
						<%-- アカウント更新する際のアカウント名を入力 --%>
						<input type="text"
							class="form-control<c:if test="${errors.name != null}"> is-invalid</c:if>"
							value="<c:out value="${accountUser.name}" />" name="name"
							id="name" required>
						<%-- アカウント更新する際のアカウント名におけるエラーメッセージを表示 --%>
						<c:if test="${errors.name != null}">
							<span class="text-danger">${errors.name}</span>
						</c:if>
					</div>
					<div class="mt-4">
						<div class="md-3">
							<div class="border col-md-2">
								<label for="exampleInputEmail1" class="space form-label">E-mailアドレス</label>
							</div>
							<%-- E-mailが未入力か不完全な形式の場合のエラーメッセージを表示 --%>
							<%-- アカウント更新する際のE-mailを入力 --%>
							<input type="email"
								class="form-control<c:if test="${errors.email != null}"> is-invalid</c:if>"
								value="<c:out value="${accountUser.email}" />" name="email"
								id="exampleInputEmail1" aria-describedby="emailHelp" required>

							<%-- アカウント更新する際のE-mailにおけるエラーメッセージを表示 --%>
							<c:if test="${errors.email != null}">
								<span class="text-danger">${errors.email}</span>
							</c:if>
						</div>
					</div>
					<div class="mt-4">
						<div class="md-3">
							<div class="border col-md-2">
								<label for="exampleInputPassword1" class="space form-label">パスワード</label>
							</div>
							<%-- パスワードが未入力の場合のエラーメッセージを表示 --%>
							<%-- アカウント更新する際のパスワードを入力 --%>
							<input type="password"
								class="form-control<c:if test="${errors.password != null}"> is-invalid</c:if>"
								value="<c:out value="${accountUser.password}" />"
								name="password" id="exampleInputPassword" required>
							<%-- アカウント更新する際のパスワードにおけるエラーメッセージを表示 --%>
							<c:if test="${errors.password != null}">
								<span class="text-danger">${errors.password}</span>
							</c:if>
						</div>
					</div>
					<div class="mt-4">
						<%-- アカウント情報を更新し、ログインページに移る --%>
						<button type="submit" class="btn btn-outline-primary">更新</button>
						<%-- 検索ページに移る --%>
						<a href="./MainServlet" class="position-absolute end-0 pe-3">検索ページに戻る</a>
					</div>
					<div class="mt-4">
						<div class="custom-control custom-checkbox col-md-3">
							<%-- 削除ボックスにチェックが入っている場合に、そのアカウント情報を使用できなくする --%>
							<input type="checkbox" class="custom-control-input"
								id="isDeleted" name="isDeleted" value="1">
							<c:if test="${accountUser.isDeleted == 1}"> checked</c:if>
							<label class="custom-control-label"
								for="isDeleted<c:out value="${accountUser.id}" />">削除</label>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>