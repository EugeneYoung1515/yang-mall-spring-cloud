<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>地标商城-买特色美食上地标商城</title>
    <%
        String path = request.getContextPath();

        String host = request.getHeader("x-forwarded-host");

        String basePath;
        if(host!=null&&host.length()>0){
            String prefix=request.getHeader("x-forwarded-prefix");
            if(prefix==null){
                prefix="";
            }
            basePath= request.getHeader("x-forwarded-proto")+"://"+host+prefix+path+"/";
        }else {
            basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        }
    %>

    <base href="<%=basePath%>">


    <meta name="keywords" content="" />
<meta name="description" content="" />


<style type="text/css">
body {
_behavior: url(https://www.dbmall.com/shop/templates/default/css/csshover.htc);
}
.nc-appbar-tabs a.compare { display: none !important;}
</style>
<link rel="stylesheet" type="text/css" href="http://www.yangmall.com/shop/templates/default/css/dbmall.css" />
<link href="http://www.yangmall.com/shop/templates/default/css/base.css" rel="stylesheet" type="text/css">
<link href="http://www.yangmall.com/shop/templates/default/css/home_cart.css" rel="stylesheet" type="text/css">
<link href="http://www.yangmall.com/shop/resource/font/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
<!--[if IE 7]>
  <link rel="stylesheet" href="http://www.yangmall.com/shop/resource/font/font-awesome/css/font-awesome-ie7.min.css">
<![endif]-->
<script>
var COOKIE_PRE = 'E245_';var _CHARSET = 'utf-8';var SITEURL = 'https://www.dbmall.com/shop';var RESOURCE_SITE_URL = 'https://www.dbmall.com/data/resource';var RESOURCE_SITE_URL = 'https://www.dbmall.com/data/resource';var SHOP_TEMPLATES_URL = 'https://www.dbmall.com/shop/templates/default';var PRICE_FORMAT = '&yen;%s';
</script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://www.dbmall.com/data/resource/js/html5shiv.js"></script>
      <script src="https://www.dbmall.com/data/resource/js/respond.min.js"></script>
<![endif]-->
<!--[if IE 6]>
<script src="https://www.dbmall.com/data/resource/js/IE6_PNG.js"></script>
<script>
DD_belatedPNG.fix('.pngFix');
</script>
<script>
// <![CDATA[
if((window.navigator.appName.toUpperCase().indexOf("MICROSOFT")>=0)&&(document.execCommand))
try{
document.execCommand("BackgroundImageCache", false, true);
   }
catch(e){}
// ]]>
</script>
<![endif]-->
</head>
<body>
<link rel="stylesheet" type="text/css" href="http://www.yangmall.com/shop/templates/default/css/dbmall.css" />
<link rel="stylesheet" type="text/css" href="http://www.yangmall.com/shop/templates/default/css/compatible_new_head_foot.css?t=2017110241425" />
<script type="text/javascript" src="http://www.yangmall.com/data/resource/js/jquery.js"></script>
<script>

var _hmt = _hmt || [];

(function() {

  var hm = document.createElement("script");

  hm.src = "//hm.baidu.com/hm.js?5a74a1aa2333382de7ec6295b684ea35";

  var s = document.getElementsByTagName("script")[0];

  s.parentNode.insertBefore(hm, s);

})();

</script>
<div id="append_parent"></div>
<div id="ajaxwaitid"></div>



		<div class="top">
            <!-- S 顶部 用户登录信息 -->
            <div class="top-1" id="top-1">
                您好，欢迎来到					<a href="http://www.yangmall.com"  title="首页" alt="首页">
                地标商城</a>
                <a href="http://user.yangmall.com/login_register">[登录]</a>
                <!--<a href="<?/*=urlShop('login','register')*/?>">[<?/*=$lang['nc_register']*/?>]</a>-->
                <a href="http://user.yangmall.com/login_register">[注册]</a>
            </div>

            <div class="top-1" id="top-2">
                您好					<a href="https://www.dbmall.com/shop/index.php?act=member&op=home" id="phone">
                183****2639					</a>
                ，欢迎来到                    <a href="https://www.yangmall.com"  title="首页" alt="首页">
                地标商城</a>
                <a onclick="logout_action()" style="cursor: pointer">[退出]</a>
                <script>
                    function logout_action()
                    {
                        if(confirm('确定退出登录？'))
                        {
                            window.location = 'http://user.yangmall.com/logout';
                        }
                    }
                </script>
            </div>

            <!-- E 顶部 用户登录信息 -->
			
			<!-- S 顶部 右侧导航 -->
			<div class="top-2">
				<ul>
					<li class="top-2-phone"><a href="https://www.dbmall.com/wap"><img src="../shop/templates/default/images/new/dbmall_03.jpg"/>手机版</a></li>
					<li>/</li>
					<li><a href="https://www.dbmall.com/shop/index.php?act=member_order">我的订单</a></li>
					<li>/</li>
					<li><a href="https://www.dbmall.com/shop/index.php?act=member_goodsbrowse&op=list">我的足迹</a></li>
					<li>/</li>
					<li class="top-2-kffw">
						客户服务<img class="top-2-kffw-jt" src="http://www.yangmall.com/shop/templates/default/images/new/dbmall_06.jpg"/>
						<ul>
							<li><a href="https://www.dbmall.com/shop/index.php?act=article&op=article&ac_id=1">帮助中心</a></li>
							<li><a href="https://www.dbmall.com/shop/index.php?act=article&op=article&ac_id=4">售后服务</a></li>
						</ul>
					</li>
					<li>/</li>
					<li class="top-2-gywm">
						关注我们<img class="top-2-gywm-jt" src="http://www.yangmall.com/shop/templates/default/images/new/dbmall_06.jpg"/>
						<ul>
							<li>
								扫描二维码<br/>关注微信公众号<br/>
								<img src="https://www.dbmall.com/shop/templates/default/images/mall/qr100.png"/><br/>
							</li>
						</ul>
					</li>
				</ul>
			</div>
			<!-- E 顶部 右侧导航 -->
			<div class="clear"></div>
		</div>

<script>

//顶部菜单
$('.top-2-kffw,.top-2-gywm').mouseover(function(){
	$(this).find('ul').show();
	$("."+$(this).attr('class')+'-jt').attr('src','http://www.yangmall.com/shop/templates/default/images/new/dbmall_07.jpg');
});

$('.top-2-kffw,.top-2-gywm').mouseleave(function(){
	$(this).find('ul').hide();
	$("."+$(this).attr('class')+'-jt').attr('src','http://www.yangmall.com/shop/templates/default/images/new/dbmall_06.jpg');
});



    //document.oncontextmenu=function(){return false};
    $(function(){
        function over(){
            $(".button2").stop();
            $(this).next(".button2").animate({
                width:"120px"
            },500);
        }

        function out(){
            $(".button2").stop();
            $(".button2").animate({
                width:"40px"
            },500);
        }

        $(".button1").bind("mouseover",over);

        $(".button1").bind("mouseout",out);


        $(".map_open_goods").mouseover(function(){
            $(this).stop().animate({
                marginTop:"-10px"
            },600);
        });

        $(".map_open_goods").mouseout(function(){
            $(this).stop().animate({
                marginTop:"0px"
            },600);
        });
    });
</script><script src="http://www.yangmall.com/data/resource/js/jquery.validation.min.js"></script>
<div class="logo">
		<div>
			<div class="logo-1"><a href="https://www.dbmall.com"><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall__03.png"></a><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall__06.png"></div>
			<div class="logo-4">
				<ul>
					<li class="red">我的购物车</li>
					<li class="">填写核对购物信息</li>
					<li class="">支付提交</li>
					<li class="">订单完成</li>
				</ul>
			</div>
		</div>
		<div class="clear"></div>
	</div>
<!--  旧版流程图片样式
<header class="ncc-head-layout">
    <div class="site-logo"><a href="https://www.dbmall.com/shop"><img src="https://www.dbmall.com/shop/templates/default/images/mall/logo.png" class="pngFix"></a></div>
        <ul class="ncc-flow">
      <li class="current"><i class="step1"></i>
        <p>我的购物车</p>
        <sub></sub>
        <div class="hr"></div>
      </li>
      <li class=""><i class="step2"></i>
        <p>填写核对购物信息</p>
        <sub></sub>
        <div class="hr"></div>
      </li>
      <li class=""><i class="step3"></i>
        <p>支付提交</p>
        <sub></sub>
        <div class="hr"></div>
      </li>
      <li class=""><i class="step4"></i>
        <p>订单完成</p>
        <sub></sub>
        <div class="hr"></div>
      </li>
    </ul>
      </header>
-->
<div class="ncc-wrapper">

  <style>
.ncc-table-style tbody tr.item_disabled td {
	background: none repeat scroll 0 0 #F9F9F9;
	height: 30px;
	padding: 10px 0;
	text-align: center;
}
</style>
<div class="ncc-main">
  <div class="ncc-title">
    <h3>我的购物车</h3>
    <h5>查看购物车商品清单，增加减少商品数量，并勾选想要的商品进入下一步操作。</h5>
  </div>
  <form action="http://cart.yangmall.com/buy_step1" method="POST" id="form_buy" name="form_buy">
    <input type="hidden" value="1" name="ifcart">
      <input type="hidden" value="${reqToken}" name="reqToken">
      ${selectInfo}
    <table class="ncc-table-style" nc_type="table_cart">
      <thead>
        <tr>
          <th class="w50"><label>
              <input type="checkbox" checked value="1" id="selectAll">
              全选</label></th>
          <th></th>
          <th>商品</th>
          <th class="w120">单价(元)</th>
          <th class="w120">数量</th>
          <th class="w120">小计(元)</th>
          <th class="w80">操作</th>
        </tr>
      </thead>
            <tbody>
            <c:forEach items="${specAndNum}" var="entry" varStatus="status">
                <c:if test="${status.index != 0}">
        <tbody>
                </c:if>
                <tr id="cart_item_${entry.key.specId}" nc_group="${entry.key.specId}" class="shop-list ">
                    <td><input type="checkbox" checked nc_type="eachGoodsCheckBox" value="${entry.key.specId}|${entry.value}" id="cart_id${entry.key.specId}" name="cart_id[]"></td>
                    <td class="w60"><a href="http://item.yangmall.com/${entry.key.item.itemId}.html" target="_blank" class="ncc-goods-thumb"><img src="http://image.yangmall.com/60/${entry.key.item.imageUrl}" alt="${entry.key.specTitle}" /></a></td>
                    <td class="tl" ><dl class="ncc-goods-info">
                        <dt><a href="http://item.yangmall.com/${entry.key.item.itemId}.html" target="_blank">${entry.key.specTitle}</a></dt>
                        <dt><c:set var="key">${entry.key.specId}</c:set> ${quantityInfo[key]}</dt>

                        <!-- S gift list -->
                        <!-- E gift list -->
                    </dl></td>

                    <td class="w120">
                        <em id="item143602_price">

                                ${entry.key.representPrice}
                        </em></td>
                    <td class="w120 ws0">
                        <a href="JavaScript:void(0);" onclick="decrease_quantity(${entry.key.specId});" title="减少商品件数" class="add-substract-key tip">-</a>
                        <input id="input_item_${entry.key.specId}" value="${entry.value}" orig="${entry.value}" changed="${entry.value}" onkeyup="change_quantity(${entry.key.specId}, this);" type="text" class="text w20"/>
                        <a href="JavaScript:void(0);" onclick="add_quantity(${entry.key.specId});" title="增加商品件数" class="add-substract-key tip" >+</a>
                    </td>
                    <td class="w120">            <em id="item${entry.key.specId}_subtotal" nc_type="eachGoodsTotal">${specIdAndAmount[entry.key.specId]} </em>
                    </td>
                    <td class="w80">              <!--<a href="javascript:void(0)" onclick="collect_goods('3307');">收藏</a><br/>-->
                        <a href="javascript:void(0)" onclick="drop_cart_item(${entry.key.specId});">删除</a></td>
                </tr>
            </c:forEach>
              </tbody>
      <tfoot>
        <tr>
          <td colspan="20"><div class="ncc-all-account">商品总价（不含运费）<em id="cartTotal"></em>元</div></td>
        </tr>
      </tfoot>
    </table>
  </form>
  <div class="ncc-bottom"><a id="next_submit" href="javascript:void(0)" class="ncc-btn ncc-btn-acidblue fr"><i class="icon-pencil"></i>下一步，填写核对购物信息</a></div>

  <!-- 猜你喜欢 -->
  <!--
  <div id="guesslike_div"></div>
  <script type="text/javascript">
  $(function(){
      //猜你喜欢
      $('#guesslike_div').load('https://www.dbmall.com/shop/index.php?act=search&op=get_guesslike', function(){
          $(this).show();
      });
  });
  </script>
  -->
</div>


</div><!-- S Footer -->
<!-- <div class="sidebar_dbmall">
    <div class="sidebar_dbmall_img"></div>
    <div class="sidebar_dbmall_main">
        <div class="sidebar_dbmall_main_body">
        	<div class="sidebar_dbmall_main_online" dir="">
		    	<a href="http://tb.53kf.com/webCompany.php?arg=9000225&style=2" target="_blank"></a>
		    </div>
		    <img src="https://www.dbmall.com/shop/templates/default/images/sidebar_dbmall4.png" />
		    <div class="sidebar_dbmall_main_phone" dir="sidebar_dbmall2.png">
		    	<a href="javascript:;"></a>
		    </div>
		    <img src="https://www.dbmall.com/shop/templates/default/images/sidebar_dbmall4.png" />
		    <div class="sidebar_dbmall_main_weixin" dir="sidebar_dbmall3.png">
		    	<a href="javascript:;"></a>
		    </div>
		    <img src="https://www.dbmall.com/shop/templates/default/images/sidebar_dbmall4.png" />
		    <div class="sidebar_dbmall_main_top">
		    	<a href="#"></a>
		    </div>
        </div>
	</div>
</div> -->
<div style="clear:both;"></div>

		<div class="foot">
			<div class="foot-1">
				<ul>
					<li><img src="../shop/templates/default/images/new/dbmall_88.png"/><br>地标认证</li>
					<li><img src="../shop/templates/default/images/new/dbmall_90.png"/><br>正宗原产</li>
					<li><img src="../shop/templates/default/images/new/dbmall_92.png"/><br>品质保障</li>
					<li><img src="../shop/templates/default/images/new/dbmall_95.png"/><br>价格优势</li>
					<li><img src="../shop/templates/default/images/new/dbmall_98.png"/><br>售后无忧</li>
				</ul>
			</div>
			<div class="foot-2">
				<div class="foot-2-a">
				
				
				<!-- S 底部 文章列表 -->
            
                                    <div>
                        <ul>
                                                            <li>
                                    新手帮助                                </li>
                                
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=1" title="注册新用户"> 注册新用户                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=2" title="购物流程"> 购物流程                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=3" title="会员制度"> 会员制度                                            </a>
                                        </li>
                                                                    
                                                    </ul>
                    </div>
                                    <div>
                        <ul>
                                                            <li>
                                    配送方式                                </li>
                                
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=4" title="配送说明"> 配送说明                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=5" title="运费说明"> 运费说明                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=6" title="验货签收"> 验货签收                                            </a>
                                        </li>
                                                                    
                                                    </ul>
                    </div>
                                    <div>
                        <ul>
                                                            <li>
                                    支付方式                                </li>
                                
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=7" title="支付常见问题"> 支付常见问题                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=8" title="支付宝支付"> 支付宝支付                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=9" title="微信支付"> 微信支付                                            </a>
                                        </li>
                                                                    
                                                    </ul>
                    </div>
                                    <div>
                        <ul>
                                                            <li>
                                    售后服务                                </li>
                                
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=10" title="正品承诺"> 正品承诺                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=11" title="售后咨询"> 售后咨询                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=12" title="退换货办理"> 退换货办理                                            </a>
                                        </li>
                                                                    
                                                    </ul>
                    </div>
                

                        <!-- E 底部 文章列表 -->

				</div>
				<div class="foot-2-b">
					<ul>
						<li><a><img src="../shop/templates/default/images/new/dbmall_106.png"/><span>400-855-7128</span></a></li>
						<li><a href="http://tb.53kf.com/webCompany.php?arg=9000225&style=2"><img src="../shop/templates/default/images/new/dbmall_111.png"/><span>在线客服</span></a></li>
						<li><a href="http://weibo.com/360dbmall"><img src="../shop/templates/default/images/new/dbmall_114.png"/><span>新浪微博</span></a></li>
					</ul>
				</div>
				<div class="foot-2-c">
					<ul>
						<li><img src="https://www.dbmall.com/shop/templates/default/images/mall/qr.jpg"/></li>
						<li><span>地标商城官方微信</span></li>
					</ul>
				</div>
			</div>
			<div class="clear"></div>
			
						
			<div class="foot-3">
				友情链接：
				                                            <a target="_blank" href="http://www.360bot.com/">指南针商品交易市场</a>
                     /                                         <a target="_blank" href="http://b2b.360bot.com/">指南针现货交易</a>
                     /                                         <a target="_blank" href="http://news.360bot.com/">指南针资讯</a>
                     /                                         <a target="_blank" href="http://kjs.aqsiq.gov.cn/dlbzcpbhwz">地理标志产品保护政府网</a>
                     /                                         <a target="_blank" href="http://www.51qiyiguo.com">猕猴桃价格</a>
                     /                                         <a target="_blank" href="http://www.eczunyi.org.cn">遵义电子商务行业协会</a>
                     /                                         <a target="_blank" href="http://yoyopt.cn">优优辣品</a>
                                    			</div>
            			
			
						<div class="foot-4">
				<a href="https://www.dbmall.com">首页</a>
				                        / <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=13">招聘英才</a>
                                        / <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=15">联系我们</a>
                                        / <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=16">关于我们</a>
                			</div>
            
            	<div class="foot-5">
	    <!-- <a href="http://61.144.227.239:9003/webrecord/form_query.jsp" target = "_blank" rel="nofollow"><img style="height:35px;width:90px;" src="https://www.dbmall.com/shop/templates/default/images/mall/public_infomation.png" /></a>
                <a href="http://www.sznet110.gov.cn/netalarm/index.jsp" target = "_blank" rel="nofollow"><img style="height:35px;width:90px;" src="https://www.dbmall.com/shop/templates/default/images/mall/online_110.png" /></a> -->
        <a href="javascript:;" target = "_blank" rel="nofollow"><img style="height:35px;width:90px;" src="https://www.dbmall.com/shop/templates/default/images/mall/public_infomation.png" /></a>
        <a href="javascript:;" target = "_blank" rel="nofollow"><img style="height:35px;width:90px;" src="https://www.dbmall.com/shop/templates/default/images/mall/online_110.png" /></a>    
        <a href="javascript:void(0);" rel="nofollow"><img style="height:35px;width:90px;" src="https://www.dbmall.com/shop/templates/default/images/mall/alipay_logo.png" /></a>
        <a href="javascript:void(0);" rel="nofollow"><img style="height:35px;width:90px;" src="https://www.dbmall.com/shop/templates/default/images/mall/wxpay_logo.png" /></a>
        <a href="javascript:void(0);" rel="nofollow"><img style="height:35px;width:90px;" src="https://www.dbmall.com/shop/templates/default/images/mall/dbmall_118.jpg" /></a>
        <!-- <a href="http://szcert.ebs.org.cn/4446b0ca-c09b-401e-840c-298eda7bb086" target="_blank" rel="nofollow"><img style="height:35px;width:90px;" src="https://www.dbmall.com/shop/templates/default/images/mall/gswj.png" /></a> -->
        <a href="http://szcert.ebs.org.cn/65c59de9-ea6b-4898-b749-cb381fb52f06" target="_blank" rel="nofollow"><img style="height:35px;width:90px;" src="https://www.dbmall.com/shop/templates/default/images/mall/gswj.png" /></a>
	</div>
	<div class="foot-6">
	      Copyright © 2019 dbmall.com，All Rights Reserved <a href="http://www.miibeian.gov.cn" target="_blank">粤ICP备15109472号</a> 深公网安备4403300900603<br> 食品流通许可证SP4403052015027332
                        使用本网站即表示接受<a href="http://www.dbmall.com/shop/index.php?act=document&op=index&code=agreement" target="_blank">地标商城用户协议</a>。版权所有 深圳华夏地标电子商务有限公司
	</div>
<script type="text/javascript" src="http://www.yangmall.com/data/resource/js/jquery-2.1.1.min.js"></script>

            <script>
                $(function(){

                    $.ajax({
                        url:"http://user.yangmall.com/logininfo",
                        type:"get",
                        dataType: "json",
                        xhrFields: {withCredentials: true},
                        success:function(result){
                            if(result[1].status==1){
                                $('#top-1').remove();
                                $('#phone').text(result[1].phoneNumber)
                            }else{
                                $('#top-2').remove();
                            }
                        }
                    });
                });

            </script>

            <script>
$(function(){
    $.ajax({
        url:"/index.php?act=record&op=visit_record",
        type:"get",
        data:{
            goods_id : 0,
            channel_code : '0'
        },
        success:function(result){
            //不做处理
            return;
        }
    });
});
</script>
<div style="display:none;">
    <script type="text/javascript">var cnzz_protocol = (("https:" == document.location.protocol) ? " https://" : " http://");document.write(unescape("%3Cspan id='cnzz_stat_icon_1256676780'%3E%3C/span%3E%3Cscript src='" + cnzz_protocol + "s4.cnzz.com/z_stat.php%3Fid%3D1256676780' type='text/javascript'%3E%3C/script%3E"));</script>
</div>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "//hm.baidu.com/hm.js?5a74a1aa2333382de7ec6295b684ea35";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
            
        <!--     <script>

				$(".dbmall_main1_map1_pic_a img").mouseover(function(){
					$(this).stop().animate({
						marginTop:"-10px"
					},600);
				});
				
				$(".dbmall_main1_map1_pic_a img").mouseout(function(){
					$(this).stop().animate({
						marginTop:"0px"
					},600);
				});
				
				$(".sidebar_dbmall_main_phone,.sidebar_dbmall_main_weixin").mouseover(function () {
					$(".sidebar_dbmall_img").html("<img src='https://www.dbmall.com/shop/templates/default/images/" + $(this).attr('dir') + "'/>");
					$(".sidebar_dbmall_img").css("opacity","0");
					$(".sidebar_dbmall_img").css("marginRight","-100px");
					if($(this).attr("dir")=="sidebar_dbmall3.png"){
						$(".sidebar_dbmall_img").css("marginTop","0px");
					}else{
						$(".sidebar_dbmall_img").css("marginTop","40px");
					}
					$(".sidebar_dbmall_img").stop().animate({
						opacity:1,
						marginRight:"0px"
					},800);
				});
				
				$(".sidebar_dbmall_main_phone,.sidebar_dbmall_main_weixin").mouseleave(function () {
					$(".sidebar_dbmall_img").stop().animate({
						opacity:0,
						marginRight:"-100px"
					},800);
				});
				
				$(function(){
					$(window).scroll(function(){
						var top = $(".sidebar_dbmall").position().top;
						if(top<800){
							$(".sidebar_dbmall_main_top").stop().animate({
								opacity:0
							},100,function(){
								$(".sidebar_dbmall_main_top").hide();
							});
						}else{
							$(".sidebar_dbmall_main_top").show();
							$(".sidebar_dbmall_main_top").stop().animate({
								opacity:1
							},100);
						}
					});
				});
			</script> -->
		</div>


<script src="http://www.yangmall.com/data/resource/js/common.js"></script>
<script src="http://www.yangmall.com/data/resource/js/jquery-ui/jquery.ui.js"></script>
<script src="http://www.yangmall.com/data/resource/js/jquery.poshytip.min.js"></script>
<script type="text/javascript" src="http://www.yangmall.com/data/resource/js/dialog/dialog.js" id="dialog_js" charset="utf-8"></script>
    <script src="http://www.yangmall.com/data/resource/js/goods_cart.js"></script>

<script>
//提示信息
$('.tip').poshytip({
	className: 'tip-yellowsimple',
	showOn: 'hover',
	alignTo: 'target',
	alignX: 'center',
	alignY: 'top',
	offsetX: 0,
	offsetY: 5,
	allowTipHover: false
});
</script>
</body>
</html>
