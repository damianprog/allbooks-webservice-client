<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<link type="text/css" rel="stylesheet" href="/css/header.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">

<link href="https://fonts.googleapis.com/css?family=Lato:700"
	rel="stylesheet">
</head>

<body>

	<div id="headerContainer">

		<table id="titleMain">
			<tr>
				<td id="titleImg"><img src="/css/images/bookshelf.png"></td>



				<td id="allbooksName"><a id="allBooks" href="/reader/main">AllBooks</a></td>

				<td id="homeRef"><a class="blackRefNon" href="/reader/main">Home</a>

				</td>
				<td class="pageRef"><sec:authorize
						access="isFullyAuthenticated()">
						<c:url var="showMyBooks" value="/myBooks/showMyBooks">
							<c:param name="readerId" value="${sessionScope.readerId}"></c:param>
						</c:url>

						<a class="blackRefNon" href="${showMyBooks}">My Books</a>

					</sec:authorize></td>

				<td class="pageRef"><sec:authorize
						access="isFullyAuthenticated()">
						<a class="blackRefNon" href="/loggedReader/showNotifications">Notifications</a>
					</sec:authorize></td>


				<sec:authorize
						access="isFullyAuthenticated()">
				<td><form action="/reader/searchBooks">
						<input id="searchBarHeader" name="phrase" placeholder="Search books" required="required">
						<input id="searchHeader" type="submit" value="Search">

					</form></td>
				</sec:authorize>
				<sec:authorize access="isFullyAuthenticated()">
					<td id="loginPart"><c:url var="showProfile"
							value="/profile/showProfile">
							<c:param name="readerId" value="${sessionScope.readerId}"></c:param>
						</c:url> <div class="dropdown">
  					<div onclick="myFunction()" class="dropbtn"><sec:authentication 
  					property="principal.username" /></div>
						  <div id="myDropdown" class="dropdown-content">
						    <a href="${showProfile}">Profile</a>
						    <a href="/loggedReader/showFriends">Friends</a>
						    <sec:authorize access="hasAuthority('ADMIN')">
						    <a href="/admin/showAddBook">Admin</a>
						    </sec:authorize>
						    <a href="/loggedReader/showFavoriteGenres">Favorite genres</a>
						    <a href="/logout">Logout</a>
						  </div>
						</div></td>
				</sec:authorize>

				<sec:authorize access="!isFullyAuthenticated()">

					<form:form method="POST" action="/login">
						<td>
							<table id="mainLogin">
								<tr>
									<td><input type="text" placeholder="Login"
										class="inputBox" required="required" name="username" /></td>
									<td><input type="password" placeholder="Password"
										class="inputBox" required="required" name="password" /></td>
									<td><input class="signInHeader" type="submit"
										value="Sign In" /></td>
								</tr>
								<tr>
									<td id="cbMain"><input type="checkbox" name="remember-me" />
										Remember me</td>
									<td id="cbMainForgot"><a href="/readerAccount/forgot">Forgot
											it?</a></td>
								</tr>
							</table>
						</td>
					</form:form>
				</sec:authorize>
			</tr>
		</table>


	</div>

	<div style="clear: both;"></div>

</body>

<script type="text/javascript">

function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {

    var dropdowns = document.getElementsByClassName("dropdown-content");
    var i;
    for (i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

</script>

</html>