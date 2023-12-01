<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.MusicTerm,java.util.List,java.util.Date"%>

<%-- ページのタイトルを設定する --%>
<% 
   pageContext.setAttribute("title", "音楽記号・用語解説", PageContext.PAGE_SCOPE);
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
.size img {
	width: 600px;
	height: 400px;
}
</style>

</head>
<body>
	<form action="./ExplainServlet" method="get" class="inline-form">
		<div class="container">

			<div class="row mt-3">
				<div class="col-6">
					<div class="size">
						<!-- 音楽用語・記号の画像を表示 -->
						<img
							src="<%= request.getContextPath() + "/img/" %>${ musicTerm.image }"
							class="border-dark img-thumbnail border border-3" alt="...">
					</div>
					<div class="mt-5">
						<!-- 検索ページに戻ると同時に検索キーワードの情報を渡す -->
						<a href="./MainServlet?key=${ key }"> <br>検索ページに戻る
						</a>
					</div>
				</div>
				<div class="col-6">
					<!-- 音楽用語・記号の名前を表示 -->
					<div class="rounded border-dark text-center bg-info"
						style="height: 150px; width: 600px;">
						<br>
						<h1>${ musicTerm.term }</h1>
					</div>
					<!-- 音楽用語・記号の解説文を表示 -->
					<div class="mt-3 border rounded border-primary text-left"
						style="height: 900px; width: 600px;">
						<br>
						<p class="fs-3">${ musicTerm.termExplain }</p>
					</div>
				</div>
			</div>

			<!-- 音楽用語・記号のID情報を追加 -->
			<input type="hidden" name="id"
				value="<c:out value="${ musicTerm.id }" />">

		</div>
	</form>
</body>
</html>