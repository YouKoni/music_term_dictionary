<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Account,model.BrowseHistory,model.MusicTerm,java.util.List,java.util.Date"%>
<%-- ページのタイトルを設定する --%>
<%
pageContext.setAttribute("title", "検索ページ", PageContext.PAGE_SCOPE);
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
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<style>
.container {
	background-color: whitesmoke;
}

.line {
	width: 200px;
	border-bottom: solid 4px gray;
}

.finished {
	text-decoration: double line-through;
}

a {
	text-decoration: none;
}

.space {
	padding: 10px 10px 10px 10px;
}
</style>
</head>
<body>
	<div class="container">
		<div class="d-flex justify-content-start">
			<div class="mt-1">
				<div class="border border-primary bg-primary">
					<h5 class="p-2 bd-highlight text-white">音楽記号・用語辞書</h5>
				</div>
			</div>
			<nav class="navbar navbar-expand-lg navbar-whitesmoke">
				<div class="container-fluid">
					<button class="navbar-toggler" type="button"
						data-bs-toggle="collapse"
						data-bs-target="#navbarNavWhiteSmokeDropdown"
						aria-controls="navbarNavWhiteSmokeDropdown" aria-expanded="false"
						aria-label="Toggle navigation">
						<span class="navbar-toggler-icon"></span>
					</button>
					<%-- ログインしている人にのみ表示される --%>
					<c:if test="${ accountUser != null }">
						<div class="collapse navbar-collapse mb-5 "
							id="navbarNavWhiteSmokeDropdown">
							<ul class="navbar-nav">
								<li class="nav-item dropdown">
									<%-- ログインした人のアカウント名が表示 --%> 
									<a class="nav-link dropdown-toggle"
										href="#" id="navbarWhiteSmokeDropdownMenuLink" role="button"
										data-bs-toggle="dropdown" aria-expanded="false"> 
										<c:out value="${ accountUser.name }" /></a> 
									<%-- アカウント更新とログアウトする際のメニューが表示 --%>
									<div class="dropdown-menu dropdown-menu-whitesmoke"
										aria-labelledby="navbarWhiteSmokeDropdownMenuLink">
										<%-- アカウント更新ページへ移る --%>
										<a class="dropdown-item" href="./AccountUpdateServlet">アカウント更新</a>
										<div class="dropdown-divider"></div>
										<%-- ログアウトし、ログインページへ移る --%>
										<a class="dropdown-item" href="./LogoutServlet">ログアウト</a>
									</div>
								</li>
							</ul>
						</div>
					</c:if>
				</div>
			</nav>
			<form class="form-inline mb-5" action="./MainServlet" method="get">
				<div class="mt-2">
					<div class="d-flex justify-content-end">
						<div class="input-group">
							<%-- 検索キーワードを"key"とし、それに該当する音楽用語・記号を検索する --%>
							<div>
								<input type="text" class="form-control me-5" name="key"
									value="<c:out value="${ key }"/>" placeholder="検索ワードを入力してください">
							</div>
							<div>
								<button class="btn btn-outline-success" type="submit"
									id="button-addon2">
									<i class="fas fa-search"></i>検索
								</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<div class="container bg-white">
		<div class="mt-1">
			<div class="row">
				<div class="col-4">
					<table class="table table-borderless">
						<thead>
							<tr>
								<th scope="col">
									<h5 class="p-1 bd-highlight">検索表示</h5>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="musicTerm" items="${ musicTermList }">
								<tr>
									<td>
										<%-- 検索された音楽用語・記号を表示--%>
										<%-- 解説画面ページに移り、音楽用語・記号の情報をID情報を基に表示 --%>
										<a href="./ExplainServlet?musicTermId=${ musicTerm.id }">
											<c:out value="${ musicTerm.term }" />
										</a>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="col-8">
					<table class="table table-borderless">
						<thead>
							<tr>
								<%-- ログインしている人にのみ表示される --%>
								<c:if test="${ accountUser != null }">
									<th scope="col">
										<h5 class="border border-dark p-1 bd-highlight">閲覧日</h5>
									</th>
									<th scope="col">
										<h5 class="border border-dark p-1 bd-highlight">検索履歴</h5>
									</th>
								</c:if>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="browseHistory" items="${ browseHistoryList }">
								<%-- ログインしている人にのみ表示される --%>
								<c:if test="${ accountUser != null }">
									<tr>
										<td>
											<%-- 解説画面ページから検索ページに移った際の音楽用語・記号の閲覧日を表示(ログインするアカウントによって異なる) --%>
											<fmt:formatDate var="date"
												value="${ browseHistory.checkedDate }" pattern="yyyy年MM月dd日" />
											<c:out value="${ date }" />
										</td>
										<td>
											<%-- 解説画面ページから検索ページに移った際の音楽用語・記号を表示(ログインするアカウントによって異なる) --%>
											<a href="./ExplainServlet?musicTermId=${ browseHistory.termId }">
												<c:out value="${ browseHistory.termName }" />
											</a>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>