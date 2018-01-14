<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>商城后台管理系统</title>
    <link rel="stylesheet" type="text/css" href="${systemName}/css/bootstrap.css"/>
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-success">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>成功!</h4>
                <strong>${msg!""}</strong><a href="${url}" class="alert-link">3秒之后跳转</a>
            </div>
        </div>
    </div>
</div>
<script>
    setTimeout('location.href="${url}"',3000);
</script>
</body>
</html>