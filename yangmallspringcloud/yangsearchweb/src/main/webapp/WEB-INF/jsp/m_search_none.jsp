<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML>
<head>
    <title>商城搜索_${keyword}</title>
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
			<div class="top-1">
									您好，欢迎来到					<a href="https://www.dbmall.com"  title="首页" alt="首页">
                    	地标商城</a>
                    	<a href="https://www.dbmall.com/shop/index.php?act=login&op=index">[登录]</a>
						<!--<a href="<?/*=urlShop('login','register')*/?>">[<?/*=$lang['nc_register']*/?>]</a>-->
						<a href="https://www.dbmall.com/shop/index.php?act=login&op=index">[注册]</a>
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

            <div class="search_info">
                <!-- 搜索无结果 hide -->
                                    <span class="info_red ">
										<c:if test="${none == true}">
										很抱歉，没有找到与“${keyword}”相关的商品
										</c:if>
																				<c:if test="${none == false}">
																					很抱歉，搜索关键词不能为空
																				</c:if>
									</span>
                            </div>
        </div>

        <!-- S 推荐商品 -->
                <!-- E 推荐商品 -->


    

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
