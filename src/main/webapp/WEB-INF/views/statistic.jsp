<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common/taglibs.jsp"%>
    <title>统计</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${css}/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${css}/bootstrap-theme.min.css" rel="stylesheet" media="screen">
    <link href="${css}/custom.css" rel="stylesheet" media="screen">
    <!--[if IE]>
        <script src="../js/excanvas.js"></script>
    <![endif]-->
</head>
<body>
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">利润排行榜</a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="#">Home</a></li>
                    <li><a href="product.html">商品</a></li>
                    <li class="active"><a href="statistic.jsp">统计</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="display">
            <canvas id="chart" height="550" width="800"></canvas>
        </div>
    </div>

    <script src="${js}/jquery-1.10.2.min.js"></script>
    <script src="${js}/Chart.min.js"></script>
    <script src="${js}/bootstrap.min.js"></script>
    <script src="${js}/custom.js"></script>
</body>
</html>