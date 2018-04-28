<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>用户展示页面</title>
</head>
<body>
	<table>
	<thead>
		<tr>
		    <td>id</td>
			<td>名称</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="user" items="${userList}">
			<tr>
			    <td>${user.id}</td>
				<td>${user.loginName}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</body>
</html>