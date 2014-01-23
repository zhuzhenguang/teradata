<%--
  Created by IntelliJ IDEA.
  User: zhu
  Date: 14-1-24
  Time: 上午2:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script>
        parent.document.getElementById('upload-load').style.display = 'none';
        parent.document.getElementById('upload-success').style.display = 'block';

        setTimeout(function() {
            parent.document.getElementById('upload-success').style.display = 'none';
        }, 3000);

        parent.$.UserListObject.loadRemote();
    </script>
</head>
<body>

</body>
</html>
