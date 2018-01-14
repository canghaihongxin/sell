<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>商城后台管理系统</title>
    <#include "../common/common.ftl">
    </head>
<body>


<div id="wrapper" class="toggled">
    <#--防止在是否显示导航的时候颜色变黑-->
    <#--  <div class="overlay" style="display: block;"></div>-->

    <!-- Sidebar -->
    <#include "../common/nav.ftl">
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">
        <button type="button" class="hamburger animated fadeInLeft is-open" data-toggle="offcanvas">
            <span class="hamb-top"></span>
            <span class="hamb-middle"></span>
            <span class="hamb-bottom"></span>
        </button>
        <div class="container-fluid" style="background-color: #55b9b9;">
            <div class="row clearfix">
                    <div class="col-md-12 column">
                        <form role="form" method="post" action="${systemName}/seller/category/save">
                            <div class="form-group">
                                <label for="exampleInputEmail1">名称</label>
                                <input type="text" class="form-control" name="categoryName" value="${(productCategory.categoryName)!''}" />
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">type</label>
                                <input type="number" class="form-control" name="categoryType" value="${(productCategory.categoryType)!''}" />
                            </div>
                            <input hidden  type="text" name="categoryId" value="${(productCategory.categoryId)!''}">
                            <button type="submit" class="btn btn-default">提交</button>
                        </form>
                    </div>
            <#--分页-->
            </div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

</div>


<script>
    $(document).ready(function() {
        var trigger = $('.hamburger'),
                overlay = $('.overlay'),
                isClosed = false;

        trigger.click(function() {
            hamburger_cross();
        });

        function hamburger_cross() {
            if(isClosed == true) {
                overlay.hide();
                trigger.removeClass('is-open');
                trigger.addClass('is-closed');
                isClosed = false;
            } else {
                overlay.show();
                trigger.removeClass('is-closed');
                trigger.addClass('is-open');
                isClosed = true;
            }
        }

        $('[data-toggle="offcanvas"]').click(function() {
            $('#wrapper').toggleClass('toggled');
        });
    });
</script>
</body>
</html>