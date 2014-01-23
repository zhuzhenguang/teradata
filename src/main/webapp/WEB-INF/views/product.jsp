<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common/taglibs.jsp"%>
    <title>商品</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${css}/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${css}/bootstrap-theme.min.css" rel="stylesheet" media="screen">
    <link href="${css}/custom.css" rel="stylesheet" media="screen">
</head>
<body>
    <div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Display主页</a>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav">
                    <li><a href="${ctx}">Home</a></li>
                    <li class="active"><a href="${ctx}/product">商品</a></li>
                    <li><a href="${ctx}/statistic">统计</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="container">
        <div class="display">
            <table class="table table-striped" id="product-list">
                <thead>
                    <tr>
                        <th>编号</th>
                        <th>产品</th>
                        <th>客户数</th>
                        <th>销售额</th>
                        <th>利润</th>
                    </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
    </div>

    <div class="modal fade" id="product-purchase-list" tabindex="-1" role="dialog"
         aria-labelledby="product-label" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="product-label">XX购买清单</h4>
                </div>
                <div class="modal-body">
                    <table class="table table-striped" id="product-purchase-table">
                        <thead>
                            <tr>
                                <th>销售日期</th>
                                <th>客户姓名</th>
                                <th>金额</th>
                                <th>购买量</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
                <form action="#" id="participate_form"></form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <script src="${js}/jquery-1.10.2.min.js"></script>
    <script src="${js}/bootstrap.min.js"></script>
    <script src="${js}/common.js"></script>
    <script src="${js}/product.js"></script>
</body>
</html>