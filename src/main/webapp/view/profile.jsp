<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/profile.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
<script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
</head>

<body>



	<div id="container">

		<jsp:include page='/view/header.jsp' />
		<div style="clear: both"></div>
		<br>
		<div id="leftSide">
			<div id="image">
				<table id="picTable">
					<tr>
						<td><c:choose>
								<c:when test="${empty profilePic}">
									<img src="/css/images/regularProfileImage.jpg">
								</c:when>
								<c:otherwise>
									<img src="data:image/jpeg;base64,${profilePic}">
								</c:otherwise>
							</c:choose></td>
					</tr>
					<c:choose>
						<c:when test="${reader.username == principalName}">
							<tr>
								<td>
								<div id="uploadPhotoLabel">
									Upload profile photo
								</div>
								
								<div id="extensionError"></div>
									<form method="POST" action="/profile/profileUpload"
										enctype="multipart/form-data" id="uploadForm">
										<input type="file" name="file" required="required" accept=".jpg" id="photoInput"/> <br /> <input
											type="submit" value="Submit" id="photoSubmit"/>
									</form>
								</td>
							</tr>
						</c:when>
					</c:choose>
				</table>
			</div>
			<div id="firstTableDiv">
				<div id="loginEdit">
					<h2>${reader.username}</h2>

					<sec:authentication var="principal" property="principal" />

					<c:choose>
						<c:when test="${reader.username == principalName}">
							<a class="blackRefNon" href="/profile/showEdit">(edit
								profile)</a>
						</c:when>

						<c:when test="${not empty invite}">
							<c:url var="friendUrl" value="/profile/inviteFriend">
								<c:param name="recipentId" value="${reader.id}" />
								<c:param name="senderId" value="${sessionScope.readerId}" />
								<c:param name="recipentLogin" value="${reader.username}" />
								<c:param name="senderLogin" value="${principal.username}" />
							</c:url>
							<c:choose>
								<c:when test="${pending == false}">
									<c:choose>
										<c:when test="${(areTheyFriends == false)}">
											<a class="blackRef" href="${friendUrl}">Send friend's
												request</a>
										</c:when>
										<c:otherwise>
									You are friends!
								</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${isItSenderProfile == true}">
										Sent you Friends request!
									</c:when>
										<c:otherwise>
										Friends request has been sent
									</c:otherwise>
									</c:choose>

								</c:otherwise>
							</c:choose>
						</c:when>
					</c:choose>
				</div>
				<hr>
				<table class="detailsTable">
					<tr class="bordered">
					</tr>
					<tr>
						<td>Details</td>
						<td>${details.gender},${details.city},${details.country}</td>
					</tr>
					<tr>
						<td>Interests</td>
						<td>${details.interests}</td>
					</tr>
					<tr>
						<td>Favorite Books</td>
						<td>${details.favouriteBooks}</td>
					</tr>
					<tr>
						<td>About Me</td>
						<td class="tableTd">
							<div id="profileAbout">${details.about}</div> <br /> <c:choose>
								<c:when test="${details.about.length() > 258}">
									<a class="moreDesc" href="#" data-value="profileAbout">More...</a>
								</c:when>
							</c:choose>
						</td>
					</tr>
				</table>
			</div>

			<div style="clear: both">
				<div class="underFirstTable">
					<c:url var="myBooks" value="/myBooks/showMyBooks">
						<c:param name="readerId" value="${reader.id}" />
					</c:url>
					<h4 id="topDesc">
						<a class="blackRef" href="${myBooks}">${reader.username}'s
							Bookshelves</a>
					</h4>
					<hr>
					<table>
						<tr>
							<td>read(${read})</td>
							<td>currently-reading(${currentlyReading})</td>
							<td>to-read(${wantToRead})</td>
						</tr>
					</table>
				</div>

			</div>
			<div class="underFirstTable">
				<h4 id="topDesc">${reader.username}&nbsp;is currently reading</h4>
				<hr>
				
					<c:forEach var="tempBook" items="${currentlyReadingList}">
					
					<div class="currentBookPhoto">
						<a href="${bookSite}"> <img
									src="data:image/jpeg;base64,${tempBook.book.encodedBookPhoto}" />
							</a>
					</div>
					
					<div class="currentBookDetails">
					
						<c:url var="bookSite" value="/reader/showBook">
							<c:param name="bookId" value="${tempBook.book.id}" />
						</c:url>
							${reader.username}&nbsp;is currently reading
								<div class="topDesc">
									<a class="blackRef" href="${bookSite}">${tempBook.book.fullTitle}</a>
								</div> by ${tempBook.book.author}<br> bookshelves:
								${tempBook.shelvesStates.shelveState()}
							
							<div class="currentBookDescription">
								${fn:substring(tempBook.book.description,0,300)}...	
							</div>
					</div>
					<div style="clear:both"></div>			
					</c:forEach>
			</div>
			<div class="underFirstTable">
				<h4>${reader.username}'s recent reviews</h4>
				<hr>
				<c:choose>
					<c:when test="${!empty readerReviews}">
						<table>
							<c:forEach var="tempReview" items="${readerReviews}" begin="0"
								end="2">
								<c:url var="reviewLink" value="/bookActions/reviewPage">
									<c:param name="reviewId" value="${tempReview.id}" />
									<c:param name="readerLogin"
										value="${tempReview.postingReader.username}" />
									<c:param name="bookId" value="${tempReview.book.id}" />
									<c:param name="readerRating" value="${tempReview.rating.rate}" />
									<c:param name="fullBookName"
										value="${tempReview.book.fullTitle}" />
									<c:param name="authorName" value="${tempReview.book.author}" />
								</c:url>
								<c:url var="bookPage" value="/reader/showBook">
									<c:param name="bookId" value="${tempReview.book.id}" />
								</c:url>
								<tr>
									<td>
										<h4>
											<a class="blackRef" href="${bookPage}">
												${tempReview.book.miniTitle}</a>
										</h4> <a class="blackRef" href="${reviewLink}">
											${tempReview.title}</a>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:when>
					<c:otherwise>${reader.username} has no any recent reviews</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div id="rightSide">
			<c:choose>
				<c:when test="${reader.username == principalName}">
					<div id="friendsInvites">
						<h4 id="topDesc">Friends Invites</h4>
						<hr>
						<c:choose>
							<c:when test="${empty friendsInvites}">
			You have no friends requests
			</c:when>
							<c:otherwise>
								<c:forEach var="tempPending" items="${friendsInvites}">
								<div class="pendingDiv">
									<c:url var="senderProfile" value="/profile/showProfile">
										<c:param name="readerId" value="${tempPending.sender.id}" />
									</c:url>
									<a href="${senderProfile}">${tempPending.sender.username}</a> Has sent you a friends request!
				<form method="POST" action="/profile/acceptOrAbort">
										<select name="acceptOrAbort">
											<option value="accept">Accept</option>
											<option value="abort">Abort</option>
										</select> <input type="hidden" name="recipentId" value="${reader.id}">
										<input type="hidden" name="senderId"
											value="${tempPending.sender.id}"> <input
											type="hidden" name="pendingId" value="${tempPending.id}">
										<input type="submit" value="Submit" />
									</form>
								</div>
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</div>
				</c:when>
			</c:choose>
			<div id="friendsList">
				<h4 id="topDesc">${reader.username}'s friends(${friendsNum})</h4>
				<br>
				<hr>
				<c:choose>
					<c:when test="${empty friends}">
			 Friends list is empty
		</c:when>
					<c:otherwise>
						<table id="friendsTable">
							<c:forEach var="tempFriends" items="${friends}">
								<c:url var="friendProfile" value="/profile/showProfile">
									<c:param name="readerId" value="${tempFriends.id}" />
								</c:url>
								<tr>
									<td>Friend's name: <a class="blackRef"
										href="${friendProfile}"> ${tempFriends.username} </a>
										</td>
										<td> <c:choose>
											<c:when test="${reader.username == principalName }">
												<form:form action="/profile/deleteFriends" method="DELETE"
													class="deleteForm">
													<input type="hidden" name="friendId"
														value="${tempFriends.id}">
													<input type="hidden" name="readerId" value="${reader.id}">
													<input class="delete" type="submit" value="Delete" onclick="return confirm('Are you sure you want to delete this friend?');"/>
												</form:form>
											</c:when>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
	<script src="/js/profileShowMore.js"></script>
	<script src="/js/photoUpload.js"></script>
	
</body>

</html>