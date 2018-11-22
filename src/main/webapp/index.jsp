<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2018/6/13
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String contextPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
%>

<html>
<head>
    <title>AgentExternalTest</title>
</head>
<body>

<p>通过jvm.properties设置听云探针堆内存阈值，增加内存消耗，直接触发听云探针资源保护<a href="<%=contextPath%>/memoryAdd"><%=contextPath%>/memoryAdd</a>
</p>
<p>清内存<a href="<%=contextPath%>/memoryClean"><%=contextPath%>/memoryClean</a></p>
<p>通过修改size值，增加指定大小的内存消耗（单位：M）：<a href="<%=contextPath%>/memoryAdd?size=10"><%=contextPath%>/memoryAdd?size=10</a></p>
<p>测试非异步servlet：<a href="<%=contextPath%>/getParams?paramName=hahaha"><%=contextPath%>/getParams?paramName=hahaha</a></p>
<p>测试异步servlet dispatch：<a href="<%=contextPath%>/asyncDis?paramName=hahaha&time=100"><%=contextPath%>
    /asyncDis?paramName=hahaha&time=100</a></p>
<p>测测试异步servlet complete：</p>
<a href="<%=contextPath%>/asyncCom?paramName=hahaha&&time=100"><%=contextPath%>
    /asyncCom?paramName=hahaha&time=100
</a></p>
<p>数据库操作请求参数为： db=1</p>
<p>外部调用参数为： urlparam=<%=contextPath%></p>

</body>
</html>

