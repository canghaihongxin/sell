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
                    <table class="table table-hover table-condensed ">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list productInfos.content as productInfo>
                        <tr>
                            <td>${productInfo.productId}</td>
                            <td>${productInfo.productName}</td>
                            <td> <img width="100px" height="100px" src="${productInfo.productIcon}"> </td>
                            <td>${productInfo.productPrice}</td>
                            <td>${productInfo.productStock}</td>
                            <td>${productInfo.productDescription}</td>
                            <td>${productInfo.categoryType}</td>
                            <td>${productInfo.createTime}</td>
                            <td>${productInfo.updateTime}</td>
                            <td>
                                <a  href="${systemName}/seller/product/index?orderId=${productInfo.productId}">详情</a>
                            </td>
                            <td>
                                <#if productInfo.getProductStatusEnums().msg == '在架'>
                                        <a href="${systemName}/seller/product/off_sale?orderId=${productInfo.productId}">下架</a>
                                    <#else >
                                        <a href="${systemName}/seller/product/on_sale?orderId=${productInfo.productId}">上架</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>
            <#--分页-->
                <div class="col-md-12 column pull-right">
                    <ul class="pagination">
                    <#if currentPage lte 1>  <#--小于等于1-->
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else >
                        <li><a href="${systemName}/seller/product/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                    </#if>


                    <#if productInfos.getTotalPages() gte 7 >  <#--总页数-->
                        <#if currentPage lte  6>
                            <#list 1..10 as index>
                                <#if currentPage== index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else >
                                    <li><a href="${systemName}/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                        <#elseif currentPage gte productInfos.getTotalPages()-3>
                            <#if currentPage lte productInfos.getTotalPages()-3>
                                <#list currentPage-6..productInfos.getTotalPages() as t>
                                    <#if currentPage== t>
                                        <li class="disabled"><a href="#">${t}</a></li>
                                    <#else >
                                        <li><a href="${systemName}/seller/product/list?page=${t}&size=${size}">${t}</a></li>
                                    </#if>
                                </#list>
                            <#else >
                                <#list productInfos.getTotalPages()-9..productInfos.getTotalPages() as t>
                                    <#if currentPage== t>
                                        <li class="disabled"><a href="#">${t}</a></li>
                                    <#else >
                                        <li><a href="${systemName}/seller/product/list?page=${t}&size=${size}">${t}</a></li>
                                    </#if>
                                </#list>
                            </#if>

                        <#else >
                            <#list currentPage-5..currentPage+4 as t>
                                <#if currentPage== t>
                                    <li class="disabled"><a href="#">${t}</a></li>
                                <#else >
                                    <li><a href="${systemName}/seller/product/list?page=${t}&size=${size}">${t}</a></li>
                                </#if>
                            </#list>
                        </#if>

                    <#else>
                        <#list 1..productInfos.getTotalPages() as index>
                            <#if currentPage== index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else >
                                <li><a href="${systemName}/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                    </#if>

                    <#if currentPage gte productInfos.getTotalPages()>  <#--小于等于1-->
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else >
                        <li><a href="${systemName}/seller/product/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

</div>

<#-- 消息订单 -->


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