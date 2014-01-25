<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="common/taglibs.jsp"%>
    <title>主页</title>
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
                <li class="active"><a href="${ctx}">用户列表</a></li>
                <li><a href="${ctx}/product">商品清单</a></li>
                <li><a href="${ctx}/statistic">统计</a></li>
            </ul>
            <form class="navbar-form navbar-right" role="form">
                <button class="btn btn-info" id="uploadExcel">
                    <span class="glyphicon glyphicon-upload"></span>上传Excel
                </button>
            </form>
        </div>
    </div>
</div>

<div class="container">
    <div class="display">
        <table class="table table-striped hide" id="user-list">
            <thead>
                <tr>
                    <th>编号</th>
                    <th>姓名</th>
                    <th>家庭住址</th>
                    <th>生日</th>
                    <th>性别</th>
                </tr>
            </thead>

            <tbody>
            </tbody>
        </table>

        <div class="text-center no-data">
            <h1>没有数据，请先上传数据文件</h1>
        </div>

        <div class="loading hide"></div>
    </div>
</div>

<%--用户购买清单--%>
<div class="modal fade" id="user-purchase-list" tabindex="-1" role="dialog"
     aria-labelledby="purchaselist-label" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="purchaselist-label">XX购买清单</h4>
            </div>
            <div class="modal-body">
                <table class="table table-striped" id="user-purchase-table">
                    <thead>
                        <tr>
                            <th>购买日期</th>
                            <th>商品名称</th>
                            <th>单价</th>
                            <th>数量</th>
                        </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%--上传文件--%>
<div class="modal fade" id="upload-excle-container" tabindex="-1" role="dialog"
     aria-labelledby="purchaselist-label" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="upload-label">选择Excel上传</h4>
            </div>
            <div class="modal-body">
                <form role="form" action="${ctx}/excel/upload" id="uploadForm" method="post"
                      target="uploadFrame" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="excel">选择文件</label>
                        <input type="file" name="file" id="excel">
                        <p class="help-block">选择Excel文件格式</p>
                    </div>
                    <button type="submit" class="btn btn-default" id="upload-excel">上传</button>
                    <iframe id="uploadFrame" name="uploadFrame"></iframe>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div class="alert alert-info upload-tip" id="upload-load">
    正在上传，请稍后。。。
</div>

<div class="alert alert-success upload-tip" id="upload-success">
    上传成功
</div>

<script src="${js}/jquery-1.10.2.min.js"></script>
<script src="${js}/bootstrap.min.js"></script>
<script src="${js}/common.js"></script>
<script src="${js}/user.js"></script>
</body>
</html>
