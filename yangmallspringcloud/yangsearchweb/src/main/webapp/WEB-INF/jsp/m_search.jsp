<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE HTML>
<head>
    <title>商城搜索_${keyword}</title>
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


	<meta name="keywords" content="search"/>
    <meta name="description" content="search"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" type="text/css"
          href="http://www.yangmall.com/shop/templates/default/css/dbmall_2016_02_23.css"/>
    <script type="text/javascript" src="http://www.yangmall.com/shop/resource/js/swipe.js"></script>
</head>
<body>
<!-- S Header -->

<link rel="stylesheet" type="text/css" href="http://www.yangmall.com/shop/templates/default/css/dbmall_2016_02_23.css?t=201901101140" />
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
	<div class="top_container">
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
					<li class="top-2-phone"><a href="https://www.dbmall.com/wap"><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_03.jpg"/>手机版</a></li>
					<li>/</li>
					<li><a href="https://www.dbmall.com/shop/index.php?act=member_order">我的订单</a></li>
					<li>/</li>
					<li><a href="https://www.dbmall.com/shop/index.php?act=member_goodsbrowse&op=list">我的足迹</a></li>
					<li>/</li>
					<li class="top-2-kffw">
						客户服务<img class="top-2-kffw-jt" src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_06.jpg"/>
						<ul>
							<li><a href="https://www.dbmall.com/shop/index.php?act=article&op=article&ac_id=1">帮助中心</a></li>
							<li><a href="https://www.dbmall.com/shop/index.php?act=article&op=article&ac_id=4">售后服务</a></li>
						</ul>
					</li>
					<li>/</li>
					<li class="top-2-gywm">
						关注我们<img class="top-2-gywm-jt" src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_06.jpg"/>
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
	$("."+$(this).attr('class')+'-jt').attr('src','../shop/templates/default/images/new/dbmall_07.jpg');
});

$('.top-2-kffw,.top-2-gywm').mouseleave(function(){
	$(this).find('ul').hide();
	$("."+$(this).attr('class')+'-jt').attr('src','../shop/templates/default/images/new/dbmall_06.jpg');
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
</script>		<div class="logo">
			<div>
				<div class="logo-1">
                    <h1>
                        <a href="https://www.dbmall.com"><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall__03.png"/></a>
                    </h1>
                    <img id="logo_right_title" src="https://www.dbmall.com/shop/templates/default/images/new/dbmall__06.png?t=201610111533">
                   <!--  <div class="logo-ad">
                        <a href="https://www.dbmall.com/shop/index.php?act=active&op=doubletenth"><img src="https://www.dbmall.com/shop/templates/default/images/new/sy_tpic.jpg"></a><br/>
                        <img id="logo_right_title" src="https://www.dbmall.com/shop/templates/default/images/new/dbmall__06.png">
                    </div> -->
				</div>
                                <div class="logo-2">
                    <form id="search_form" action="http://search.yangmall.com" method="get" >
                        <input class="search_info" type="text" name="keyword" placeholder="搜索商品" value="${keyword}" style="color:black;">
                        <a onclick="document.getElementById('search_form').submit();" class="search_btn">搜索</a>
                    </form>
                    <div class="keyword">
                        <ul>

						</ul>
                  </div>
                </div>
				<div class="logo-3"><a href="https://www.dbmall.com/shop/index.php?act=member&op=home">我的商城</a><a id="my_cart" href="https://www.dbmall.com/shop/index.php?act=cart">我的购物车</a></div>
			</div>
			<div class="clear"></div>
		</div>

<!-- E Header -->

<script>
    $(function(){
        dbmall.show_cart_goods_num();
    });

    var dbmall = {
        show_cart_goods_num : function(){
            $.ajax({
                url : 'https://www.dbmall.com/index.php?act=cart&op=ajax_load',
                type : 'GET',
                dataType: "json",
                success : function(data){
                    var $cart_num = $("#my_cart");
                    if(data.cart_goods_num>0)
                    {
                        $cart_num.show();
                        $cart_num.html("我的购物车<span><span>"+data.cart_goods_num+"</span></span>");
                    }else{
                    	$cart_num.html("我的购物车");
                    }
                }
            });
        }
    };
</script>
<!-- S Menu -->
	<div class="menu-out">
		<div class="menu">
			<a id="area_all" href="https://www.dbmall.com/all">全部地标展馆</a>
			
			<a id="area_index" href="https://www.dbmall.com">首页</a>
			
			<a id="type_20" href="https://www.dbmall.com/dibiaozhenpin">地标珍品</a>
			
			<a id="type_21" href="https://www.dbmall.com/liangyoufushi">粮油副食</a>
			
			<a id="type_19" href="https://www.dbmall.com/jiushuichayin">酒水茶饮</a>
			
			<a id="type_18" href="https://www.dbmall.com/shanzhenhaiwei">山珍海味</a>
			
			<a id="type_17" href="https://www.dbmall.com/shengxianguoshu">生鲜果蔬</a>
			
			<a id="type_16" href="https://www.dbmall.com/xiuxianlingshi">休闲零食</a>
			
<!-- 			<a id="type_" href="https://www.dbmall.com/dibiaochanpinshujuzhongxin" target="_blank">地标产品数据中心 </a>
			 -->
<!-- 

<div class="menu-focus">
				<div>
					<span>华北</span>
					<a id="area_beijing" href="https://www.dbmall.com/zg/beijing">北京市</a>
					<a id="area_tianjin" href="https://www.dbmall.com/zg/tianjin">天津市</a>
					<a id="area_shanxi" href="https://www.dbmall.com/zg/shanxi">山西省</a>
					<a id="area_shandong" href="https://www.dbmall.com/zg/shandong">山东省</a>
					<a id="area_hebei" href="https://www.dbmall.com/zg/hebei">河北省</a>
					<a id="area_neimenggu" href="https://www.dbmall.com/zg/neimenggu">内蒙古</a>
				</div>
				<div>
					<span>东北</span>
					<a id="area_liaoning" href="https://www.dbmall.com/zg/liaoning">辽宁</a>
					<a id="area_jiling" href="https://www.dbmall.com/zg/jiling">吉林</a>
					<a id="area_heilongjiang" href="https://www.dbmall.com/zg/heilongjiang">黑龙江</a>
				</div>
				<div>
					<span>华东</span>
					<a id="area_shanghai" href="https://www.dbmall.com/zg/shanghai">上海</a>
					<a id="area_jiangsu" href="https://www.dbmall.com/zg/jiangsu">江苏</a>
					<a id="area_zhejiang" href="https://www.dbmall.com/zg/zhejiang">浙江</a>
					<a id="area_anhui" href="https://www.dbmall.com/zg/anhui">安徽</a>
					<a id="area_jiangxi" href="https://www.dbmall.com/zg/jiangxi">江西</a>
				</div>
				<div>
					<span>华中</span>
					<a id="area_hunan" href="https://www.dbmall.com/zg/hunan">湖南</a>
					<a id="area_hebei" href="https://www.dbmall.com/zg/hubei">湖北</a>
					<a id="area_henan" href="https://www.dbmall.com/zg/henan">河南</a>
				</div>
				<div>
					<span>华南</span>
					<a id="area_guangdong" href="https://www.dbmall.com/zg/guangdong">广东</a>
					<a id="area_guangxi" href="https://www.dbmall.com/zg/guangxi">广西</a>
					<a id="area_fujian" href="https://www.dbmall.com/zg/fujian">福建</a>
					<a id="area_hainan" href="https://www.dbmall.com/zg/hainan">海南</a>
				</div>
				<div>
					<span>西北</span>
					<a id="area_shanxi" href="https://www.dbmall.com/zg/shanxi">陕西</a>
					<a id="area_xinjiang" href="https://www.dbmall.com/zg/xinjiang">新疆</a>
					<a id="area_gansu" href="https://www.dbmall.com/zg/gansu">甘肃</a>
					<a id="area_ningxia" href="https://www.dbmall.com/zg/ningxia">宁夏</a>
					<a id="area_qinghai" href="https://www.dbmall.com/zg/qinghai">青海</a>
				</div>
				<div>
					<span>西南</span>
					<a id="area_chongqing" href="https://www.dbmall.com/zg/chongqing">重庆</a>
					<a id="area_yunnan" href="https://www.dbmall.com/zg/yunnan">云南</a>
					<a id="area_guizhou" href="https://www.dbmall.com/zg/guizhou">贵州</a>
					<a id="area_xizang" href="https://www.dbmall.com/zg/xizang">西藏</a>
					<a id="area_sichuan" href="https://www.dbmall.com/zg/sichuan">四川</a>
				</div>
			</div>
-->

			<div class="menu-focus">
				<div>
					<span>华北</span>

					<a target="_blank" id="area_shanxii" href="https://www.dbmall.com/shanxii">山西馆</a>
					<a target="_blank" id="area_shandong" href="https://www.dbmall.com/shandong">山东馆</a>
					<a target="_blank" id="area_hebei" href="https://www.dbmall.com/hebei">河北馆</a>

				</div>
				<div>
					<span>东北</span>
					<a target="_blank" id="area_liaoning" href="https://www.dbmall.com/liaoning">辽宁馆</a>
					<a target="_blank" id="area_jilin" href="https://www.dbmall.com/jilin">吉林馆</a>
					<a target="_blank" id="area_heilongjiang" href="https://www.dbmall.com/heilongjiang">黑龙江馆</a>
				</div>
				<div>
					<span>华东</span>

					<a target="_blank" id="area_zhejiang" href="https://www.dbmall.com/zhejiang">浙江馆</a>
					<a target="_blank" id="area_anhui" href="https://www.dbmall.com/anhui">安徽馆</a>
					<a target="_blank" id="area_jiangxi" href="https://www.dbmall.com/jiangxi">江西馆</a>
					<a target="_blank" id="area_jiangsu" href="https://www.dbmall.com/jiangsu">江苏馆</a>
				</div>
				<div>
					<span>华中</span>
					<a target="_blank" id="area_hunan" href="https://www.dbmall.com/hunan">湖南馆</a>
					<a target="_blank" id="area_hubei" href="https://www.dbmall.com/hubei">湖北馆</a>
					<a target="_blank" id="area_henan" href="https://www.dbmall.com/henan">河南馆</a>
				</div>
				<div>
					<span>华南</span>
					<a target="_blank" id="area_guangdong" href="https://www.dbmall.com/guangdong">广东馆</a>
					<a target="_blank" id="area_guangxi" href="https://www.dbmall.com/guangxi">广西馆</a>
					<a target="_blank" id="area_fujian" href="https://www.dbmall.com/fujian">福建馆</a>
				</div>
				<div>
					<span>西北</span>
					<a target="_blank" id="area_shanxi" href="https://www.dbmall.com/shanxi">陕西馆</a>
					<a target="_blank" id="area_xinjiang" href="https://www.dbmall.com/xinjiang">新疆馆</a>
					<a target="_blank" id="area_gansu" href="https://www.dbmall.com/gansu">甘肃馆</a>
					<a target="_blank" id="area_ningxia" href="https://www.dbmall.com/ningxia">宁夏馆</a>
					<a target="_blank" id="area_qinghai" href="https://www.dbmall.com/qinghai">青海馆</a>
				</div>
				<div>
					<span>西南</span>
					<a target="_blank" id="area_guizhou" href="https://www.dbmall.com/guizhou">贵州馆</a>
					<a target="_blank" id="area_xizang" href="https://www.dbmall.com/xizang">西藏馆</a>
					<a target="_blank" id="area_sichuan" href="https://www.dbmall.com/sichuan">四川馆</a>
					<a target="_blank" id="area_yunnan" href="https://www.dbmall.com/yunnan">云南馆</a>
				</div>
				<div>
					<span>海外</span>
					<a target="_blank" id="area_australia" href="https://www.dbmall.com/australia">澳州馆</a>
				</div>
			</div>
		</div>
</div>

<div>

	</div>
<!-- E Menu -->

<script type="text/javascript">
    $(function(){
        var a_element_id = '';

		if(a_element_id == 'area_index' || a_element_id == 'area_all' || a_element_id == ""){

			if(a_element_id == 'area_index' || a_element_id == ""){
				//$('a[id='+a_element_id+']').prev().remove();
				$('a[id=area_index]').remove();
			}
			
		}else{
			$right_title = $('a[id=]').html();
			$("#logo_right_title").after('<span id="logo_right_title">'+$right_title+'</span>');
			$("#logo_right_title").remove();
		}
		
    	$(".menu a").each(function(){
    		
    		var id=$(this).attr("id");

    		if(id==a_element_id){
    			$(this).addClass('menu-select').siblings().removeClass('menu-select');
    			return;
    		}
  
    	});
    	
        
    	//全部地标展馆菜单
    	var mark = true;
    	
    	function leave(){
    		setTimeout(function(){
    			if(mark){
    				$('.menu a').first().css('backgroundImage','url(https://www.dbmall.com/shop/templates/default/images/new/menu_jt_1.png)');
    				$('.menu a').first().css('backgroundColor','#f71127');
    				$('.menu a').first().css('color','#f6eced');
    				$('.menu-focus').hide();
    			}
    		},100);
    	}
    	
    	$('.menu a').first()
    	.mouseover(function(){
    		$('.menu a').first().css('backgroundImage','url(https://www.dbmall.com/shop/templates/default/images/new/menu_jt_2.png)');
    		$('.menu a').first().css('backgroundColor','#f7f7f7');
    		$('.menu a').first().css('color','#f83240');
    		$('.menu-focus').show();
    	})
    	.mouseleave(function(){
    		leave();
    	});
    	
    	$('.menu-focus')
    	.mouseover(function(){
    		mark = false;
    	})
    	.mouseleave(function(){
    		mark = true;
    		leave();
    	});
 


    	
    });

    
</script>


<div class='fl'>


    
        <div class="search_box">
            <div class="search_tittle">
                <a href="https://www.dbmall.com">首页</a> &gt; <span>梨</span>
            </div>
            <div class="search_info">
                <!-- 搜索有结果 -->
                <div class="sort_box ">
					<c:if test="${sort == null}">
                        <a href="http://search.yangmall.com?keyword=${keyword}" class="sort_name on">综合</a>
					</c:if>
					<c:if test="${sort != null}">
						<a href="http://search.yangmall.com?keyword=${keyword}" class="sort_name">综合</a>
					</c:if>
					<c:if test="${sort == 'sales'}">
					<a href="http://search.yangmall.com?keyword=${keyword}&sort=sales" class="sort_name on">销量</a>
					</c:if>
					<c:if test="${sort != 'sales'}">
						<a href="http://search.yangmall.com?keyword=${keyword}&sort=sales" class="sort_name">销量</a>
					</c:if>
					<c:if test="${sort == 'price'}">
					<a href="http://search.yangmall.com?keyword=${keyword}&sort=price_desc" class="sort_name sort_ico on">价格</a>
					</c:if>
					<c:if test="${sort == 'price_desc'}">
						<a href="http://search.yangmall.com?keyword=${keyword}&sort=price" class="sort_name sort_ico down">价格</a>
					</c:if>
					<c:if test="${sort != 'price' && sort != 'price_desc'}">
						<a href="http://search.yangmall.com?keyword=${keyword}&sort=price" class="sort_name sort_ico">价格</a>
					</c:if>
				</div>

                <form id="search_page_form" action="https://www.dbmall.com/shop/index.php">
                    <input type="hidden" name="act" value="search" />
                    <input type="hidden" name="keyword" value="梨" />
                    <input type="hidden" name="sort" value="" />
                    <input type="hidden" name="in_ajax" value="2" />
                    <input id="search_cur_page" type="hidden" name="cur_page" value="1" />
                </form>


            </div>
        </div>

        <div>
        <!-- S 商品列表 -->

			<!--
        <div class="fl-goods" >
        -->
			<!--
        //第一次渲染时拿这一个来定位
    //渲染完就删除

-->


			<c:forEach items="${items}" var="item" varStatus="status">
			<a class="product_inner fl-goods" href="http://item.yangmall.com/${item.itemId}.html" mark="<c:if test="${(status.index+1) == 6}">1</c:if><c:if test="${(status.index+1) != 6}">0</c:if>">
				<img src="http://image.yangmall.com/360/${item.imageUrl}" alt="${item.title}">
				<div class="pro_msg">
					<div class="price_box">
						<span class="represent_price">￥${item.reprensentPrice}</span>
					</div>

						<c:if test="${item.yunfeiCost == '京东物流'}">
					<span class="flag">
						京东物流
						</span>
						</c:if>

				</div>
				<span class="name">${item.title}</span>
			</a>

			</c:forEach>
            <script type="text/javascript">

				//原理 获得所有数据 不分页
				//前端渲染
				//先渲染12个 在中间的地方打一个标记
				//屏幕滚动到超过标记时又渲染12个

				//获取所有数据 分批渲染 也就是是分批加载图片
<c:if test="${fn:length(items) >= total}">
                var flag = false;
				</c:if>
                <c:if test="${fn:length(items) < total}">
                var flag = true;
                </c:if>

    //屏幕滚动事件
    $(window).scroll(function(){

  	  if(!flag){//解除监听屏幕滚动事件
  		  $(this).unbind();
  		  return;
  	  }
  	  
  	  var $mark = $(".fl-goods[mark='1']");
  	  if($mark.length<1){
  		  return;
  	  }
  	  
  	  var srollPos = $(window).scrollTop();
  	  var mark_top = $mark.offset().top;
  	  if(srollPos > mark_top){
  		  $mark.attr("mark","0");
  		  loadOnlineGoods();
  		  
  	  } 
  	  
    });

    
		var SHOP_TEMPLATES_URL = 'https://www.dbmall.com/shop/templates/default';
		var site_url = 'http://item.yangmall.com';
		var img_site_url = 'https://img.dbmall.com/data/upload';
		var is_represent = '0';
		//var online_goods = eval('(' + '[{"goods_id":"4382","goods_name":"\u767e\u8349\u5473 \u51e4\u68a8\u9165300g\/\u76d2 \u997c\u5e72\u7cd5\u70b9 \u5c0f\u5403\u65e9\u9910\u7279\u4ea7\u4f11\u95f2\u96f6\u98df ","store_id":"421","goods_price":"17.70","goods_marketprice":"39.80","represent_price":"15.00","is_presell":"0","goods_title":"\u767e\u8349\u5473 \u51e4\u68a8\u9165300g\/\u76d2 \u997c\u5e72\u7cd5\u70b9 \u5c0f\u5403\u65e9\u9910\u7279\u4ea7\u4f11\u95f2\u96f6\u98df","goods_image":"http:\/\/img13.360buyimg.com\/n1\/jfs\/t5026\/55\/2069221094\/82679\/412ea8c2\/58f8886bNe2a89c21.jpg","goods_width_image":"http:\/\/img13.360buyimg.com\/n1\/jfs\/t5026\/55\/2069221094\/82679\/412ea8c2\/58f8886bNe2a89c21.jpg","produc_province":"\u6d59\u6c5f","produc_city":null,"goods_salenum":"0","jd":1},{"goods_id":"3307","goods_name":"\u82d7\u59d1\u5a18 \u523a\u68a8\u5e72 128g\u888b 128g\/\u888b","store_id":"312","goods_price":"22.00","goods_marketprice":"29.00","represent_price":"18.00","is_presell":"0","goods_title":"\u82d7\u59d1\u5a18\u523a\u68a8\u5e72","goods_image":"https:\/\/img.dbmall.com\/data\/upload\/shop\/store\/goods\/312\/312_05914486935904986_240.jpg","goods_width_image":"https:\/\/img.dbmall.com\/data\/upload\/shop\/store\/goods\/312\/312_05874998889790941_240.jpg","produc_province":"\u8d35\u5dde","produc_city":null,"goods_salenum":"5","jd":0},{"goods_id":"4506","goods_name":"\u6d77\u5357\u91d1\u94bb\u51e4\u68a8 7-8\u65a4\uff083-5\u4e2a\uff09","store_id":"436","goods_price":"38.80","goods_marketprice":"48.00","represent_price":"38.80","is_presell":"0","goods_title":"\u6d77\u5357\u91d1\u94bb\u51e4\u68a8","goods_image":"https:\/\/img.dbmall.com\/data\/upload\/shop\/store\/goods\/436\/436_06040759085410684_240.jpg","goods_width_image":"https:\/\/img.dbmall.com\/data\/upload\/shop\/store\/goods\/436\/436_06040759130841448_240.jpg","produc_province":"\u6d77\u5357","produc_city":null,"goods_salenum":"61","jd":0},{"goods_id":"4397","goods_name":"\u65b0\u7586\u5e93\u5c14\u52d2\u9999\u68a8 \u7cbe\u9009\u7279\u7ea7  \u51c0\u91cd2.5kg \u65b0\u9c9c\u6c34\u679c ","store_id":"421","goods_price":"83.90","goods_marketprice":"59.90","represent_price":"68.90","is_presell":"0","goods_title":"\u65b0\u7586\u5e93\u5c14\u52d2\u9999\u68a8 \u7cbe\u9009\u7279\u7ea7  \u51c0\u91cd2.5kg \u65b0\u9c9c\u6c34\u679c","goods_image":"http:\/\/img13.360buyimg.com\/n1\/jfs\/t18103\/215\/1928489569\/250517\/f5e9d5c5\/5adef3edN1cb3f811.jpg","goods_width_image":"http:\/\/img13.360buyimg.com\/n1\/jfs\/t18103\/215\/1928489569\/250517\/f5e9d5c5\/5adef3edN1cb3f811.jpg","produc_province":null,"produc_city":null,"goods_salenum":"3","jd":1}]' + ')');
		var online_goods = JSON.parse('[{"goods_id":"4382","goods_name":"\u767e\u8349\u5473 \u51e4\u68a8\u9165300g\/\u76d2 \u997c\u5e72\u7cd5\u70b9 \u5c0f\u5403\u65e9\u9910\u7279\u4ea7\u4f11\u95f2\u96f6\u98df ","store_id":"421","goods_price":"17.70","goods_marketprice":"39.80","represent_price":"15.00","is_presell":"0","goods_title":"\u767e\u8349\u5473 \u51e4\u68a8\u9165300g\/\u76d2 \u997c\u5e72\u7cd5\u70b9 \u5c0f\u5403\u65e9\u9910\u7279\u4ea7\u4f11\u95f2\u96f6\u98df","goods_image":"http:\/\/img13.360buyimg.com\/n1\/jfs\/t5026\/55\/2069221094\/82679\/412ea8c2\/58f8886bNe2a89c21.jpg","goods_width_image":"http:\/\/img13.360buyimg.com\/n1\/jfs\/t5026\/55\/2069221094\/82679\/412ea8c2\/58f8886bNe2a89c21.jpg","produc_province":"\u6d59\u6c5f","produc_city":null,"goods_salenum":"0","jd":1},{"goods_id":"3307","goods_name":"\u82d7\u59d1\u5a18 \u523a\u68a8\u5e72 128g\u888b 128g\/\u888b","store_id":"312","goods_price":"22.00","goods_marketprice":"29.00","represent_price":"18.00","is_presell":"0","goods_title":"\u82d7\u59d1\u5a18\u523a\u68a8\u5e72","goods_image":"https:\/\/img.dbmall.com\/data\/upload\/shop\/store\/goods\/312\/312_05914486935904986_240.jpg","goods_width_image":"https:\/\/img.dbmall.com\/data\/upload\/shop\/store\/goods\/312\/312_05874998889790941_240.jpg","produc_province":"\u8d35\u5dde","produc_city":null,"goods_salenum":"5","jd":0},{"goods_id":"4506","goods_name":"\u6d77\u5357\u91d1\u94bb\u51e4\u68a8 7-8\u65a4\uff083-5\u4e2a\uff09","store_id":"436","goods_price":"38.80","goods_marketprice":"48.00","represent_price":"38.80","is_presell":"0","goods_title":"\u6d77\u5357\u91d1\u94bb\u51e4\u68a8","goods_image":"https:\/\/img.dbmall.com\/data\/upload\/shop\/store\/goods\/436\/436_06040759085410684_240.jpg","goods_width_image":"https:\/\/img.dbmall.com\/data\/upload\/shop\/store\/goods\/436\/436_06040759130841448_240.jpg","produc_province":"\u6d77\u5357","produc_city":null,"goods_salenum":"61","jd":0},{"goods_id":"4397","goods_name":"\u65b0\u7586\u5e93\u5c14\u52d2\u9999\u68a8 \u7cbe\u9009\u7279\u7ea7  \u51c0\u91cd2.5kg \u65b0\u9c9c\u6c34\u679c ","store_id":"421","goods_price":"83.90","goods_marketprice":"59.90","represent_price":"68.90","is_presell":"0","goods_title":"\u65b0\u7586\u5e93\u5c14\u52d2\u9999\u68a8 \u7cbe\u9009\u7279\u7ea7  \u51c0\u91cd2.5kg \u65b0\u9c9c\u6c34\u679c","goods_image":"http:\/\/img13.360buyimg.com\/n1\/jfs\/t18103\/215\/1928489569\/250517\/f5e9d5c5\/5adef3edN1cb3f811.jpg","goods_width_image":"http:\/\/img13.360buyimg.com\/n1\/jfs\/t18103\/215\/1928489569\/250517\/f5e9d5c5\/5adef3edN1cb3f811.jpg","produc_province":null,"produc_city":null,"goods_salenum":"3","jd":1}]');
		//console.log(online_goods);
		var record_num = 0;
		//var page_size = 12;
				var page_size = ${pageSize};

		//var temp1 = parseInt(online_goods.length/page_size);

				var t = ${fn:length(items)};
                var temp1 = parseInt(t/page_size);
		var page =  (t%page_size==0)?temp1:parseInt(temp1+1);
		//var page_cur = 0;
                var page_cur = 1;

		function loadOnlineGoods(){//加载商品数据
            page_cur ++ ;

            $.ajax({
                url : 'http:search.yangmall.com/ajax?keyword=${keyword}&sort=${sort}'+'&pagesize='+page_size+'&page_cur='+page_cur,
                type : 'GET',
                dataType: "json",
                success : function(data){
                    var mark = 0;//mark标签，就录下拉就加载位置点
                    var index = 1;

                    //for(var i=start_num;i<record_num;i++){

                    var re = t-(page_cur-1)*page_size-1;
                    for(var i=0;i<page_size;i++){
                        //if(i > online_goods.length-1){

                        if(i> re){
                            flag = false;
                            return;
                        }

                        if(index == 6){
                            mark = 1;
                        }
                        setGoodsHtml(online_goods[i],mark);
                        mark = 0;
                        index++;
                    }
                }
            });


            /*
			var start_num = record_num;
			record_num = page_cur*page_size;

			var mark = 0;//mark标签，就录下拉就加载位置点
			var index = 1;

			for(var i=start_num;i<record_num;i++){
				if(i > online_goods.length-1){
					flag = false;
					return;
				}

				if(index == 6){
					mark = 1;
				}
				setGoodsHtml(online_goods[i],mark);
				mark = 0;
				index++;
			}

			*/
			
		}

		function setGoodsHtml(goods,mark){
			var imgurl = 'http://image.yangmall.com/360/'+goods.goods_image;
			
			var goods_price = goods.goods_price;
			var make_money = '';
			var href = site_url+'/'+goods.goods_id+'.html';
			var flag = '';
			var jd = goods.jd;
			/*if(is_represent == '1'){
				make_money = (goods.goods_price - goods.represent_price).toFixed(2);
				make_money = '<span class="make_price">/赚<strong>'+make_money+'</strong></span>';
				goods_price = '<span class="represent_price">￥'+goods.represent_price+'</span>';
			}else{
				goods_price = '<span class="goods_price">￥'+goods.goods_price+'</span>';
			}*/
			goods_price = '<span class="represent_price">￥'+goods.represent_price+'</span>';

			if(jd == '1'){
				flag = '<span class="flag">京东物流</span>'
			}
			//if(imgurl.indexOf("http") < 0)
			//	imgurl = img_site_url+'/shop/store/goods/'+goods.store_id+'/'+goods.goods_width_image;

			// var html = '<div onclick="window.open(\''+site_url+'/'+goods.goods_id+'.html\')" mark="'+mark+'" class="fl-goods">'+
			// 				'<div class="fl-goods-top"><span>来自 '+(goods.produc_province ? goods.produc_province:'' )+(goods.produc_city?('-'+goods.produc_city):'')+'</span></div>'+
			// 				'<div class="fl-goods-bg"></div>'+
			// 				'<img src="'+imgurl+'" alt="'+goods.goods_title+'"/>'+
			// 				'<div class="fl-goods-a">'+
			// 					'<div class="fl-goods-name">'+goods.goods_title+'</div>'+
			// 					'<div class="fl-goods-price"><span>￥</span><span>'+goods.goods_price+'</span></div>'+
			// 					'<a href="javascript:void(0);"><div class="fl-goods-add">购买</div></a>'+
			// 				'</div>'+
			// 			'</div>';

			var html = '<a class="product_inner fl-goods" href="'+href+'" mark="'+mark+'"><img src="'+imgurl+'" alt="'+goods.goods_title+'" /><div class="pro_msg"><div class="price_box">'+ goods_price + make_money+'</div>'+flag+'</div><span class="name">'+goods.goods_title+'</span></a>';
			//product_inner fl-goods是两个类

				    $(".fl-goods:last").after(html);

				    $('.fl-goods').unbind('mouseover');
				    $('.fl-goods').unbind('mouseleave');
				  //分类页面--展馆--分类
					// $('.fl-goods').mouseover(function(){
					// 	$(this).find('.fl-goods-price').hide();
					// 	$(this).find('.fl-goods-add').show();
					// 	$(this).find('.fl-goods-bg').fadeIn(300);
					// 	$(this).find('.fl-goods-name').css('color','#ffffff');
					// 	$(this).find('.fl-goods-a').css('backgroundImage','none');
					// }).mouseleave(function(){
					// 	$(this).find('.fl-goods-price').show();
					// 	$(this).find('.fl-goods-add').hide();
					// 	$(this).find('.fl-goods-bg').fadeOut(300);
					// 	$(this).find('.fl-goods-name').css('color','#000000');
					// 	$(this).find('.fl-goods-a').css('backgroundImage','url(https://www.dbmall.com/shop/templates/default/images/new/fl_04.png)');
					// });

		}
		
		//loadOnlineGoods();


		//$(".fl-goods:first").remove();
		//第一次渲染时拿这一个来定位
				//渲染完就删除


		
</script>


        </div>
        </div>
        <!-- E 商品列表 -->


    

    <div class="clear"></div>


</div>

<!-- S Footer -->
<div class="sidebar_dbmall">
    <div class="sidebar_dbmall_img"></div>
    <div class="sidebar_dbmall_main">
        <div class="sidebar_dbmall_main_body">
       <!--  	<div class="sidebar_dbmall_main_online" dir="">
		    	<a href="http://tb.53kf.com/webCompany.php?arg=9000225&style=2" target="_blank"></a>
		    </div>
		    <img src="https://www.dbmall.com/shop/templates/default/images/sidebar_dbmall4.png" /> -->
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
</div>
<div style="clear:both;"></div>

		<div class="foot">
			<div class="foot-1">
				<ul>
					<li><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_88.png"/><br>地标认证</li>
					<li><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_90.png"/><br>正宗原产</li>
					<li><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_92.png"/><br>品质保障</li>
					<li><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_95.png"/><br>价格优势</li>
					<li><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_98.png"/><br>售后无忧</li>
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
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=1" title="注册新用户" rel="nofollow" > 注册新用户                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=2" title="购物流程" rel="nofollow" > 购物流程                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=3" title="会员制度" rel="nofollow" > 会员制度                                            </a>
                                        </li>
                                                                    
                                                    </ul>
                    </div>
                                    <div>
                        <ul>
                                                            <li>
                                    配送方式                                </li>
                                
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=4" title="配送说明" rel="nofollow" > 配送说明                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=5" title="运费说明" rel="nofollow" > 运费说明                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=6" title="验货签收" rel="nofollow" > 验货签收                                            </a>
                                        </li>
                                                                    
                                                    </ul>
                    </div>
                                    <div>
                        <ul>
                                                            <li>
                                    支付方式                                </li>
                                
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=7" title="支付常见问题" rel="nofollow" > 支付常见问题                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=8" title="支付宝支付" rel="nofollow" > 支付宝支付                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=9" title="微信支付" rel="nofollow" > 微信支付                                            </a>
                                        </li>
                                                                    
                                                    </ul>
                    </div>
                                    <div>
                        <ul>
                                                            <li>
                                    售后服务                                </li>
                                
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=10" title="正品承诺" rel="nofollow" > 正品承诺                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=11" title="售后咨询" rel="nofollow" > 售后咨询                                            </a>
                                        </li>
                                                                            <li>
                                            <a target="_blank" href="https://www.dbmall.com/shop/index.php?act=article&op=show&article_id=12" title="退换货办理" rel="nofollow" > 退换货办理                                            </a>
                                        </li>
                                                                    
                                                    </ul>
                    </div>
                

                        <!-- E 底部 文章列表 -->

				</div>
				<div class="foot-2-b">
					<ul>
						<li><a><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_106.png"/><span>400-855-7128</span></a></li>
						<!-- <li><a href="http://tb.53kf.com/webCompany.php?arg=9000225&style=2" target="_blank" rel="nofollow"><img src="../shop/templates/default/images/new/dbmall_111.png"/><span>在线客服</span></a></li> -->
						<li><a href="http://weibo.com/360dbmall" target="_blank" rel="nofollow"><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_114.png"/><span>新浪微博</span></a></li>
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
				                                            <a target="_blank" href="http://www.360bot.com/" >指南针商品交易市场</a>
                     /                                         <a target="_blank" href="http://b2b.360bot.com/" >指南针现货交易</a>
                     /                                         <a target="_blank" href="http://news.360bot.com/" >指南针资讯</a>
                     /                                         <a target="_blank" href="http://kjs.aqsiq.gov.cn/dlbzcpbhwz" rel="nofollow">地理标志产品保护政府网</a>
                     /                                         <a target="_blank" href="http://www.51qiyiguo.com" >猕猴桃价格</a>
                     /                                         <a target="_blank" href="http://www.eczunyi.org.cn" >遵义电子商务行业协会</a>
                     /                                         <a target="_blank" href="http://yoyopt.cn" >优优辣品</a>
                                    			</div>
            			
			
						<div class="foot-4">
				<a href="https://www.dbmall.com">首页</a>/
				<a href="https://www.dbmall.com/shop/index.php?act=active&op=sitemap">网站地图</a>
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
                        url:"http://search.yangmall.com/hotwords",
                        type:"get",
                        dataType: "json",
                        success:function(result){
                            for(var i=0;i<result.length;i++){
                                $('div.keyword>ul').append('<li><a href="http://search.yangmall.com?keyword='+result[i]+'"'+' target="_blank">'+result[i]+'</a></li>');
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
            
            <script>

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
						$(".sidebar_dbmall_img").css("marginTop","-3px");
						/*$(".sidebar_dbmall_img").css("marginTop","0px");*/
					}else{
						$(".sidebar_dbmall_img").css("marginTop","3px");
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
			</script>
		</div>



</body>
</html>
