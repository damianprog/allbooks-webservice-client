<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.allbooks.org/tags" prefix="ei"%>
	
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/book/searchBooks.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<br>
		
		<div id="searchContainer">
		<h2>Search Books</h2>
		
		<hr>
		
		<div id="greyContainer">
		
		<form action="/visitor/searchBooks">
			<input id="searchBar" name="phrase">
			<input id="search" type="submit" value="search">
		</form>
		</div>
		
		<table id="booksTable">
		<c:choose>
		<c:when test="${!empty books}">
			<c:forEach var="tempBook" items="${books}">
				<c:url var="bookSite" value="/visitor/showBook">
					<c:param name="bookId" value="${tempBook.id}" />
				</c:url>
				<tr>
					<td><a href="${bookSite}"> <img
							src="<ei:image image='${tempBook.bookPhoto}' width='115' height='180'/>"/>
					</a></td>
					<td class="bookDesc">
						<h4 class="bookTitle">
							<a class="blackRef" href="${bookSite}">${tempBook.fullTitle}</a>
						</h4>
						<h4>by ${tempBook.author}</h4>
						${fn:substring(tempBook.description,0,300)}...
					</td>
				</tr>
			</c:forEach>
			</c:when>
			<c:otherwise>
				<tr><td>Unfortunately we couldn't find your book</td></tr>
			</c:otherwise>
		</c:choose>
		</table>
		
		</div>
		
	</div>

</body>

</html>