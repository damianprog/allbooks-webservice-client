<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>

<html>

<head>
<title>Join</title>
<link type="text/css" rel="stylesheet" href="/css/categories.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">

</head>

<body>



	<div id="container">

		<jsp:include page='/view/header.jsp' />

		<span style="color: #00635D">Genres > ${fn:toUpperCase(category)}</span><br>

		<div id="categoryName">
			<h3 style="float: left">${fn:toUpperCase(category)}</h3>
		</div>
		<div id="pages">
			<h3>Page: ${currentPage} from 
			<c:choose>
			<c:when test="${booksPage.totalPages == 0}">1</c:when>
			<c:otherwise>${booksPage.totalPages}</c:otherwise>
			</c:choose>
			</h3>
		</div>

		<hr>

		<table class="books">
			<c:forEach var="tempBook" items="${books}">
				<c:url var="bookSite" value="/reader/showBook">
					<c:param name="bookName" value="${tempBook.miniTitle}" />
				</c:url>
				<tr>
					<td><a href="${bookSite}"> <img
							src="data:image/jpeg;base64,${tempBook.encodedBookPic}" />
					</a></td>
					<td id="bookDesc">
						<h4 id="bookTitle">
							<a class="blueLink" href="${bookSite}">${tempBook.fullTitle}</a>
						</h4>
						<h4>by ${tempBook.author}</h4>
						${fn:substring(tempBook.description,0,300)}...
					</td>
				</tr>
			</c:forEach>
		</table>

		<c:forEach begin="1" end="${booksPage.totalPages}" var="i">
			<c:url var="page" value="/reader/showCategory">
				<c:param name="page" value="${i}"></c:param>
				<c:param name="categoryName" value="${category}"></c:param>
			</c:url>
			<h3 id="pageNum"><a href="${page}">${i}</a></h3>
		</c:forEach>

	</div>

</body>

</html>