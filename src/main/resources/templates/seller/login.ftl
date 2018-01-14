<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>商城后台管理系统</title>
<#include "../common/common.ftl">
</head>
<body style="background-color: #f5f5f5;">

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <form role="form" method="post" action="${systemName}/seller/doLogin">
                <div class="form-group">
                    <label for="exampleInputEmail1">账号</label><input name="username" type="text" class="form-control" id="exampleInputEmail1" />
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">密码</label><input name="password" type="password" class="form-control" id="exampleInputPassword1" />
                </div>
                <button type="submit" class="btn btn-default">登录</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>