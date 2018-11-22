<%@ page import="java.io.Console" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018/11/21
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% System.out.println(" timeout.jsp  !!!!!!!!!!!!!!!!!!!!"); %>
<% System.out.println(request.getDispatcherType()); %>

<html>
<body>
<h2>Timeout!</h2>
<p>主线程ID    ${mainThreadID}
<p>主线程进入时间    ${acceptedTime}</p>
<p>主线程取得请求参数 ${mainparam}</p>
<p>主线程完成时间    ${endTime}</p>
<br>
<p>异步线程ID ${asyncThreadID}</p>
<p>异步线程完成时间 ${completedTime}</p>
<p>异步线程取得请求参数 ${params}</p>
<p><a href="${pageContext.request.contextPath}/AsyncgetParams">返回AsyncgetParams</a></p>
</body>
</html>