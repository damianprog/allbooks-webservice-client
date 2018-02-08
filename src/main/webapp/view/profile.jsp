<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/profile.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
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
						<c:when
							test="${reader.username == principalName}">
							<tr>
								<td>
									<form method="POST" action="/profile/profileUpload"
										enctype="multipart/form-data">
										<input type="file" name="file" /> <br /> <input
											type="submit" value="Submit" />
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
					<c:choose>
						<c:when test="${reader.username == principalName}">
							<a class="blackRefNon" href="/profile/showEdit">(edit
								profile)</a>
						</c:when>
						<c:when test="${invite == true}">
							<c:url var="friendUrl" value="/profile/inviteFriend">
								<c:param name="reader1login" value="${reader.username}" />
								<c:param name="reader2login"
									value="${principalName}" />
							</c:url>
							<c:choose>
								<c:when test="${pending == false}">
									<c:choose>
										<c:when
											test="${(booFriends == false) && (reader.username != principalName)}">
											<a class="blackRef" href="${friendUrl}">Send friend
												request</a>
										</c:when>
										<c:when test="${(booFriends == true) }">
									You are friends!
								</c:when>
									</c:choose>
								</c:when>
								<c:otherwise>
								Friends request has been sent
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
						<td class="tableTd">${details.about}</td>
					</tr>
				</table>
			</div>

			<div style="clear: both">
				<div class="underFirstTable">
					<c:url var="myBooks" value="/reader/showMyBooks">
						<c:param name="myBooks" value="false" />
						<c:param name="readerId" value="${readerId}" />
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
				<h4 id="topDesc">${reader.username} is currently reading</h4>
				<hr>
				<table class="currentlyReadingBooks">
					<c:forEach var="tempBook" items="${currentlyReadingList}">
						<c:url var="bookSite" value="/reader/showBook">
							<c:param name="bookName" value="${tempBook.minBookName}" />
						</c:url>
						<tr>
							<td><a href="${bookSite}"> <img
									src="/css/images/m${tempBook.minBookName}.jpg" />
							</a></td>
							<td>${reader.username} is currently reading<br>
								<h4 id="topDesc">
									<a class="blackRef" href="${bookSite}">${tempBook.fullBookName}</a>
								</h4> <br> by ${tempBook.author}<br> bookshelves:
								${tempBook.shelves}
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div id="rightSide">
			<c:choose>
				<c:when
					test="${reader.username == principalName}">
					<div id="friendsInvites">
						<h4 id="topDesc">Friends Invites</h4>
						<hr>
						<c:choose>
							<c:when test="${empty friendsInvites}">
			You have no friends requests
			</c:when>
							<c:otherwise>
								<c:forEach var="tempFriends" items="${friendsInvites}">
									<c:url var="senderProfile" value="/profile/showProfile">
										<c:param name="readerId" value="${tempFriends.reader2}" />
										<c:param name="guest" value="true" />
									</c:url>
									<a href="${senderProfile}">${tempFriends.reader2Login}</a> Has sent you a friends request!
				<form method="GET" action="/profile/acceptOrAbort">
										<select name="acceptOrAbort">
											<option value="accept">Accept</option>
											<option value="abort">Abort</option>
										</select> <input type="hidden" name="readerProfile"
											value="${reader.id}"> <input type="hidden"
											name="friendsId" value="${tempFriends.id}"> <input
											type="submit" value="Submit" />
									</form>
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
			Your friends list is empty
		</c:when>
					<c:otherwise>
						<table>
							<c:forEach var="tempFriends" items="${friends}">
								<c:url var="friendProfile" value="/profile/showProfile">
									<c:choose>
										<c:when test="${reader.id != tempFriends.reader1}">
											<c:param name="readerId" value="${tempFriends.reader1}" />
										</c:when>
										<c:otherwise>
											<c:param name="readerId" value="${tempFriends.reader2}" />
										</c:otherwise>
									</c:choose>
									<c:param name="guest" value="true" />
								</c:url>
								<tr>
									<td>Friend's name: <a class="blackRef"
										href="${friendProfile}"> <c:choose>
												<c:when test="${reader.id != tempFriends.reader1}">
						${tempFriends.reader1Login}
						</c:when>
												<c:otherwise>
						${tempFriends.reader2Login}
						</c:otherwise>
											</c:choose>
									</a> <c:choose>
											<c:when test="${reader.username == principalName }">
												<form:form action="/profile/deleteFriends" method="GET"
													id="deleteForm">
													<input type="hidden" name="friendsId"
														value="${tempFriends.id}">
													<input type="submit" value="Delete" />
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
</body>

</html>