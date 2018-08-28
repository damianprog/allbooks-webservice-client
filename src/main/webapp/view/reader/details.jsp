<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>
<title>Join</title>
<link type="text/css" rel="stylesheet" href="/css/reader/details.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">

</head>

<body>



	<div id="container">

		<jsp:include page='/view/header.jsp' />

		<h2>Account Settings</h2>

		<form:form action="saveDetails" modelAttribute="details" method="POST">
			
			<form:hidden path="id" />
			<table>

				<tr>
					<td class="detailsName">First Name</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:input path="firstName"  class="inputBox" /></td>
				</tr >
				
				<tr>
					<td class="detailsName">Last Name</td>
				</tr>
				<tr >
					<td class="bottomTd"><form:input path="lastName"  class="inputBox" /></td>
				</tr>
				
				<tr>
					<td class="detailsName">Gender</td>
				</tr>
				<tr >
					<td class="bottomTd"><form:select path="gender"  class="inputBox">
						<form:option value="Male">Male</form:option>
						<form:option value="Female">Female</form:option>
					</form:select>
					</td>
				</tr>
				
				<tr>
					<td class="detailsName">Country</td>
				</tr>
				<tr >
					<td class="bottomTd"><form:input path="country"  class="inputBox" /></td>
				</tr>
				
				<tr>
					<td class="detailsName">City</td>
				</tr>
				<tr >
					<td class="bottomTd"><form:input path="city"  class="inputBox" /></td>
				</tr>
				
				<tr>
					<td class="detailsName">Date of Birth</td>
				</tr>
				<tr >
					<td class="bottomTd"><form:input path="dateBirth" type="date" class="inputBox" /></td>
				</tr>
				
				<tr>
					<td class="detailsName">My Website</td>
				</tr>
				<tr >
					<td class="bottomTd"><form:input path="website" class="inputBoxWider" /></td>
				</tr>
				
				<tr>
					<td class="detailsName">My Interests - favorite subjects, or really anything you know a lot about
						<br>
						<div id="detailsSmall">(in comma separated phrases, please)</div>
					</td>
				</tr>
				<tr >
					<td class="bottomTd"><form:input path="interests" class="inputBoxWider" /></td>
				</tr>
				
				<tr>
					<td class="detailsName">Favorite Books
						<br>
						<div id="detailsSmall">(in comma separated titles, please)</div>
					</td>
				</tr>
				<tr >
					<td class="bottomTd"><form:input path="favouriteBooks" class="inputBoxWider" /></td>
				</tr>
				
				<tr>
					<td class="detailsName">About
					</td>
				</tr>
				<tr >
					<td class="bottomTd"><form:textarea path="about" class="aboutArea" /></td>
				</tr>
				
				<tr>
					<td>
					<input id="submit" type="submit" value="Save profile settings" />
					</td>
				</tr>
			</table>
		</form:form>
	</div>

</body>

</html>