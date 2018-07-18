<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/loggedReaderMain.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<br>

		<div id="leftSideContainer">
		
			<div id="searchBooks">
				
				<h3>Search books</h3>
				
				<div id="searchBookImage">
				<img src="/css/images/books-stack-of-three.png">
				</div>
				
				<div id="searchBookDesc">Search any book</div>
				
				<div style="clear:both"></div>
				
				<form action="/reader/searchBooks">
					<input class="mainInputBox" name="phrase" placeholder="Search books" required="required">
					<input id="search" type="submit" value="Search">
					
				</form>
				
			</div>
		
			<hr class="leftSideHr">
		
			<div id="readingChallange">
				
				<h3>2018 Reading Challange</h3>
				<div id="readingChallangeDesc">
				Challenge yourself to read more this year!
				</div>
				
				<div id="readingChallangeImage">
					<img src="/css/images/books.png">
				</div>
				
				<div id="readingChallangeActions">
					
					<c:choose>
						<c:when test="${empty readingChallange}">
							<form action="/loggedReader/readingChallange">
							<span>I want to read</span><br>
							<input name="numberOfBooks" id="numberOfBooks" type="number" min="0"><br>
							<span>books in 2018</span><br>
							<input id="startChallange" type="submit" value="Start Challange">
							</form>
						</c:when>
						<c:otherwise>
						
						<c:url var="readingChallangeRef" value="/loggedReader/showReadingChallange">
							<c:param name="readerId" value="${loggedReaderId}"/>
						</c:url>
						
							<div style="font-size:28px;">${currentNumberOfBooks}</div>
							books completed<br>
							<div style="margin-top:10px;font-size:14px;color:#767676;">You're on track!</div>
							<div style="margin-top:5px;">${currentNumberOfBooks}/${readingChallange.numberOfBooks} (${readingProgressPercentage} &#37;)</div>
							<div><a class="blueLink" href="${readingChallangeRef}">view challange</a></div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		
			<div style="clear:both"></div>
		
			<hr class="leftSideHr">
		
			<div id="wantToRead">
			
				<h3>Want to read</h3>
			
				<div id="wantToReadImage">
					<img src="/css/images/living-room-books-group.png">
				</div>
			
				<div id="wantToReadDesc">
					What do you want to read next?
				</div>
			
			</div>
		
			<div style="clear:both"></div>
		
			<hr class="leftSideHr">
			
			<div id="bookShelves">
				<h3>Bookshelves</h3>
				Read<br>
				Currently Reading<br>
				Want to Read<br>
			</div>
		
		</div>
		
		<div id="middleContainer">
			
			<c:forEach var="post" items="${latestReaderbooks}" varStatus="status">
				
				<div class="whiteContainer">
					
					
					
				</div>
				
			</c:forEach>
			
		</div>
		
	</div>

</body>

</html>