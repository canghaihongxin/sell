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
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>
                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTOPage.content as orderDTO>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.buyerName}</td>
                            <td>${orderDTO.buyerPhone}</td>
                            <td>${orderDTO.buyerAddress}</td>
                            <td>${orderDTO.orderAmount}</td>
                            <td>${orderDTO.getOrderStatusEnum().msg}</td>
                            <td>${orderDTO.getPayStatusEnum().msg}</td>
                            <td>${orderDTO.createTime}</td>
                            <td>
                                <a  href="${systemName}/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>
                            </td>
                            <td>
                                <#if orderDTO.getOrderStatusEnum().msg == '新定单'>
                                    <a href="${systemName}/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
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
                        <li><a href="${systemName}/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                    </#if>


                    <#if orderDTOPage.getTotalPages() gte 7 >  <#--总页数-->
                        <#if currentPage lte  6>
                            <#list 1..10 as index>
                                <#if currentPage== index>
                                    <li class="disabled"><a href="#">${index}</a></li>
                                <#else >
                                    <li><a href="${systemName}/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                                </#if>
                            </#list>
                        <#elseif currentPage gte orderDTOPage.getTotalPages()-3>
                            <#if currentPage lte orderDTOPage.getTotalPages()-3>
                                <#list currentPage-6..orderDTOPage.getTotalPages() as t>
                                    <#if currentPage== t>
                                        <li class="disabled"><a href="#">${t}</a></li>
                                    <#else >
                                        <li><a href="${systemName}/seller/order/list?page=${t}&size=${size}">${t}</a></li>
                                    </#if>
                                </#list>
                            <#else >
                                <#list orderDTOPage.getTotalPages()-9..orderDTOPage.getTotalPages() as t>
                                    <#if currentPage== t>
                                        <li class="disabled"><a href="#">${t}</a></li>
                                    <#else >
                                        <li><a href="${systemName}/seller/order/list?page=${t}&size=${size}">${t}</a></li>
                                    </#if>
                                </#list>
                            </#if>

                        <#else >
                            <#list currentPage-5..currentPage+4 as t>
                                <#if currentPage== t>
                                    <li class="disabled"><a href="#">${t}</a></li>
                                <#else >
                                    <li><a href="${systemName}/seller/order/list?page=${t}&size=${size}">${t}</a></li>
                                </#if>
                            </#list>
                        </#if>

                    <#else>
                        <#list 1..orderDTOPage.getTotalPages() as index>
                            <#if currentPage== index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else >
                                <li><a href="${systemName}/seller/order/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>
                    </#if>

                    <#if currentPage gte orderDTOPage.getTotalPages()>  <#--小于等于1-->
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else >
                        <li><a href="${systemName}/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                    </#if>
                    </ul>
                </div>
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


    <audio id="notice" hidden loop="loop">
        <source src="${systemName}/mp3/wukong.mp3" type="audio/mpeg"/>
    </audio>
<#-- 弹出窗口 -->
<script>
    function wsCreateOrder (msg) {
        bootbox.confirm({
            title: "新消息",
            message: "您有新的订单请处理,订单号"+msg,
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> 关闭'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i>查看新订单'
                }
            },
            callback: function (result) {
                 console.log('This was logged in the callback: ' + result);
                var player = $("#notice")[0];
                player.pause();
            }
        });
    }

</script>

<script>
    var websocket=null;
    if('WebSocket' in window){
        websocket = new WebSocket("ws://budongfeng.tunnel.qydev.com/sell/webSocket");  //ws 或者wss 是websocket的统一资源标志符。  类似于http或者https
    }else {
        alert("该浏览器不支付websocket!");
    }

    websocket.onopen = function (event) {
        console.log("建立连接");
    }

    websocket.onclose=function (event) {
        console.log("连接关闭");
    }

    websocket.onmessage=function (event) {
        console.log("消息内容"+event.data);  //获取消息的内容
        //弹窗提醒，播放音乐
        wsCreateOrder(event.data);
        var player = $("#notice")[0];
        player.play();
    }
    websocket.onerror=function (event) {  //发生错误的时候
        alert("websocket通信发生错误");
    }
    websocket.onbeforeunload=function () {  //窗口关闭的时候 把websocket关闭掉
        websocket.close();
    }
</script>
</body>
</html>