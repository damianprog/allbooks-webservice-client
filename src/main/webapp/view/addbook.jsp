<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html lang="eng">

<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>Add Book</title>

<link type="text/css" rel="stylesheet" href="/css/addbook.css" />
<link href="https://fonts.googleapis.com/css?family=Roboto|Spectral+SC"
	rel="stylesheet">
</head>

<body>

	<div id="container">
		<jsp:include page='/view/header.jsp' />

		<h2>Add Book</h2>
		<hr />
		<form:form action="addBook" modelAttribute="book" method="POST"
			enctype="multipart/form-data">
			<table>
				<tr>
					<td class="addingName">Book Photo (JPG extension, please)</td>
				</tr>
				<tr>
					<td class="bottomTd"><input type="file" name="bookPhotoTemp"
						class="inputBox" required="required" /></td>
				</tr>
				<tr>
					<td class="addingName">Author Photo (JPG extension, please)</td>
				</tr>
				<tr>
					<td class="bottomTd"><input type="file" name="authorPhotoTemp"
						class="inputBox" required="required" /></td>
				</tr>
				<tr>
					<td class="addingName">Full Title</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:input path="fullTitle"
							class="inputBox" required="required" /></td>
				</tr>
				<tr>
					<td class="addingName">Mini Title</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:input path="miniTitle"
							class="inputBox" required="required" /></td>
				</tr>
				<tr>
					<td class="addingName">Review Author</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:input path="reviewAuthor"
							class="inputBox" required="required" /></td>
				</tr>
				<tr>
					<td class="addingName">Book Author</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:input path="author"
							class="inputBox" required="required" /></td>
				</tr>
				<tr>
					<td class="addingName">About Author</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:textarea path="aboutAuthor"
							class="aboutArea" required="required" /></td>
				</tr>

				<tr>
					<td class="addingName">Book Quotes (Two Quotes in quotation
						marks separated by right slash)</td>
				</tr>
				<tr>

					<td class="bottomTd"><form:textarea path="bookQuotes"
							class="smallerArea" required="required" /></td>
				</tr>
				<tr>
					<td class="addingName">Cover Type</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:input path="coverType"
							class="inputBox" required="required" /></td>
				</tr>
				<tr>
					<td class="addingName">Pages</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:input path="pages" class="inputBox"
							required="required" /></td>
				</tr>

				<tr>
					<td class="addingName">Publish Date</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:input type="date"
							path="publishDate" class="inputBox" required="required" /></td>
				</tr>
				<tr>
					<td class="addingName">Publish Company</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:input path="publishCompany"
							class="inputBox" required="required" /></td>
				</tr>
				<tr>
					<td class="addingName">Link to Shop</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:input path="buyBook"
							class="inputBox" required="required" /></td>
				</tr>
				<tr>
					<td class="addingName">Category</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:select path="category"
							class="inputBox">
							<option value="art">Art</option>
							<option value="biography">Biography</option>
							<option value="business">Business</option>
							<option value="children">Children's</option>
							<option value="christian">Christian</option>
							<option value="classics">Classics</option>
							<option value="comics">Comics</option>
							<option value="cookbooks">Cookbooks</option>
							<option value="ebooks">Ebooks</option>
							<option value="fantasy">Fantasy</option>
							<option value="fiction">Fiction</option>
							<option value="graphicNovels">Graphic Novels</option>
							<option value="historicalFiction">Historical Fiction</option>
							<option value="history">History</option>
							<option value="horror">Horror</option>
							<option value="memoir">Memoir</option>
						</form:select></td>
				</tr>
				<tr>
					<td class="addingName">Description</td>
				</tr>
				<tr>
					<td class="bottomTd"><form:textarea path="description"
							class="descriptionArea" required="required" /></td>
				</tr>
			</table>
			<input id="submit" type="submit" value="Create" />
		</form:form>
	</div>
</body>

</html>