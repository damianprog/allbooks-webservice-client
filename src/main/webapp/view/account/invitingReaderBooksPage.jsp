<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet"
	href="/css/account/invitingReaderBooksPage.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />
		<br>

		<h1>See What ${invitingReader.username} is Reading!</h1>

		<div id="leftSide">
			<div id="topDescription">Here are some of
				${invitingReader.username}'s&nbsp;favorite books.Rate each book if
				you've read it, or you can add the review and shelf the book by
				using the 'add to my books button'.</div>

			<div id="latestReaderBooks">

				<c:forEach var="readerBook" items="${readerBooksList}">

					<div class="readerBookDiv">
						<div class="readerBookPhoto">
							<img
								src="data:image/jpeg;base64,${readerBook.book.encodedBookPhoto}">
						</div>
						<div class="bookInformations">
							<div class="readerBookTitle">${readerBook.book.fullTitle}</div>
							<div class="readerBookAuthor">by ${readerBook.book.author}
								(Allbooks author)</div>
							<div class="readerBookRating">
								Average rating: ${readerBook.overallRating}
								<c:if test="${readerBook.readerRating != null}">
								 ${invitingReader.username} rated it: ${readerBook.readerRating.rate}
								 </c:if>
							</div>
						</div>


					</div>
					<br>
					<div style="clear: both"></div>
					<hr>
				</c:forEach>

			</div>

		</div>

		<div id="rightSide">
			<a class="blackRef" href="/reader/successfulRegistrationInformation">Continue
				to the next page</a>
			<hr>
			
			<c:url var="invitingReaderProfile" value="/profile/showProfile">
					<c:param name="readerId" value="${invitingReader.id}"/>
				</c:url>
			
			<div id="invitingReaderPhoto">
				<a class="blackRef" href="${invitingReaderProfile}"><img
					src="data:image/jpeg;base64,${invitingReader.encodedProfilePhoto}"></a>
			</div>
			<div id="invitingReaderInformation">
				
				<a class="blackRef" href="${invitingReaderProfile}">${invitingReader.username}</a><br>
				${invitingReaderReaderBooksQuantity} Books
			</div>
		</div>

	</div>

</body>

</html>