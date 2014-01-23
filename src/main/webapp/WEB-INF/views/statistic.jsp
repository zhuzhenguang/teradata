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
    <link href="${css}/jqueryui/css/start/jquery-ui-1.10.4.custom.min.css" rel="stylesheet" media="screen">
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
                    <li><a href="${ctx}">用户列表</a></li>
                    <li><a href="${ctx}/product">商品</a></li>
                    <li class="active"><a href="${ctx}/statistic">统计</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="container search_condition">
        <form role="form" class="form-inline" action="#" id="searchForm">
            <div class="form-group">
                <h4>查询条件：</h4>
            </div>
            <div class="form-group">
                <input placeholder="输入省的名称" class="form-control" name="address" id="address">
            </div>
            <input type="button" class="btn btn-success" id="search_button" value="查 询">
        </form>

        <div class="display">
            <canvas id="chart" height="550" width="800"></canvas>
            <div class="pull-right">
                <div class="current_month">最近一个月</div>
                <div class="previous_month">前一个月</div>
            </div>
        </div>
    </div>

    <script src="${js}/jquery-1.10.2.min.js"></script>
    <script src="${js}/jqueryui/js/jquery-ui-1.10.4.custom.min.js"></script>
    <script src="${js}/Chart.min.js"></script>
    <script src="${js}/bootstrap.min.js"></script>
    <script src="${js}/statistic.js"></script>
</body>
</html>