<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>新增用户页面</title>
</head>
<body>
<form id="mainForm" action="add" method="post">
<table class="table-bordered m-table">
    <tbody>
        <tr>
            <td class="f-txtr tdbg">
            	用户名:<input type="text" id="loginName" name="loginName" maxlength="32"/> 
            	密码:<input type="text" id="password" name="password" maxlength="32"/> 
            </td>
            <td class="f-txtr tdbg">
            	<button type="submit" >保存</button>
			</td>
        </tr>
    </tbody>
</table>
                    
</form>
</body>
</html>