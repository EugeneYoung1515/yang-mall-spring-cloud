<!doctype html>
<head>
    <title>买特色美食上地标商城 DBMALL.COM</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="baidu-site-verification" content="v2F5V8od4b" />
    <meta name="keywords" content="中国地理标志产品商城,地标产品,地理标志产品,高品质食材,土特产网购,特产网购商城,地标商城">
    <meta name="description" content="地标商城以中国地理标志产品为核心,以提供高品质食材为出发点,提供新鲜地道的地方特产,让消费者足不出户就可以享受到真正安全美味的高品质食材.各种特产食材都蕴含当地丰富的自然与人文元素,满足客户对特定地域的特色食品和高品质食材的需求.">
    <meta name="baidu-site-verification" content="fRauzmw0Uk" />
    <!-- <link rel="stylesheet" type="text/css" href="https://www.dbmall.com/shop/templates/default/css/dbmall_2016_02_23.css"/> -->
    <link rel="stylesheet" type="text/css" href="shop/resource/css/swiper-3.4.2.min.css"/>
    <script type="text/javascript" src="shop/resource/js/swiper-3.4.2.min.js"></script>
</head>
<body>


<!-- S Header -->

<link rel="stylesheet" type="text/css" href="shop/templates/default/css/dbmall_2016_02_23.css" />
<script type="text/javascript" src="data/resource/js/jquery.js"></script>
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
                <li class="top-2-phone"><a href="https://www.dbmall.com/wap"><img src="shop/templates/default/images/new/dbmall_03.jpg"/>手机版</a></li>
                <li>/</li>
                <li><a href="https://www.dbmall.com/shop/index.php?act=member_order">我的订单</a></li>
                <li>/</li>
                <li><a href="https://www.dbmall.com/shop/index.php?act=member_goodsbrowse&op=list">我的足迹</a></li>
                <li>/</li>
                <li class="top-2-kffw">
                    客户服务<img class="top-2-kffw-jt" src="shop/templates/default/images/new/dbmall_06.jpg"/>
                    <ul>
                        <li><a href="https://www.dbmall.com/shop/index.php?act=article&op=article&ac_id=1">帮助中心</a></li>
                        <li><a href="https://www.dbmall.com/shop/index.php?act=article&op=article&ac_id=4">售后服务</a></li>
                    </ul>
                </li>
                <li>/</li>
                <li class="top-2-gywm">
                    关注我们<img class="top-2-gywm-jt" src="shop/templates/default/images/new/dbmall_06.jpg"/>
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
                <input class="search_info" type="text" name="keyword" placeholder="搜索商品" value="" style="color:black;">
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
    <div class="banner">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <#list list as ad>
                <div class="swiper-slide">
                    <a href="${ad.link}" target='_blank'>
                        <img src="adimage/${ad.imageUrl}" alt="${ad.title}" width="100%;">
                    </a>
                </div>
                </#list>
                <!--
                <div class="swiper-slide">
                    <a href="https://www.dbmall.com/4791.html" target='_blank'>
                        <img src="https://img.dbmall.com/data/upload/home/06148064600850913.jpg" alt="金枕" width="100%;">
                    </a>
                </div>
                <div class="swiper-slide">
                    <a href="https://www.dbmall.com/4793.html" target='_blank'>
                        <img src="https://img.dbmall.com/data/upload/home/06147710725631921.jpg" alt="火龙果" width="100%;">
                    </a>
                </div>
                <div class="swiper-slide">
                    <a href="https://www.dbmall.com/4506.html" target='_blank'>
                        <img src="https://img.dbmall.com/data/upload/home/06147736518608739.jpg" alt="凤梨" width="100%;">
                    </a>
                </div>
                <div class="swiper-slide">
                    <a href="https://www.dbmall.com/4352.html" target='_blank'>
                        <img src="https://img.dbmall.com/data/upload/home/06147710725600101.jpg" alt="蜜瓜" width="100%;">
                    </a>
                </div>
                <div class="swiper-slide">
                    <a href="https://www.dbmall.com/4801.html" target='_blank'>
                        <img src="https://img.dbmall.com/data/upload/home/06149708355370615.jpg" alt="金枕" width="100%;">
                    </a>
                </div>
                -->
            </div>
            <!-- Add Pagination -->
            <div class="swiper-pagination"></div>
        </div>
    </div>

    <!-- 优惠券 -->
    <!-- <div class="ticket_box" >
        <a href="javascript:AOPGetVoucher('88')"><img src="https://www.dbmall.com/shop/templates/default/images/voucherimages/tips_01.png"/></a>
        <a href="javascript:AOPGetVoucher('87')"><img src="https://www.dbmall.com/shop/templates/default/images/voucherimages/tips_02.png"/></a>
        <a href="javascript:AOPGetVoucher('86')"><img src="https://www.dbmall.com/shop/templates/default/images/voucherimages/tips_03.png"/></a>
        <a href="javascript:AOPGetVoucher('85')"><img src="https://www.dbmall.com/shop/templates/default/images/voucherimages/tips_04.png"/></a>
    </div> -->
    <!-- 消息通知 -->
    <!-- <div class="inform">
        <span class="inform_ico"></span>
        <span>【温馨提醒】尊敬的用户，国庆中秋节期间，由于部分商家不能正常发货，下单前请知悉相关商品发货信息（以页面说明为准）。感谢您的理解和支持，祝您假期愉快！</span>
    </div>
-->
    <div class="sy-left">
        <ul>
            <li><a href="#xp">新品</a></li>
            <li><a href="#ts">特色</a></li>
            <li><a href="#zl">珍礼</a></li>
            <li><a href="#zb">滋补</a></li>
            <li><a href="#ly">粮油</a></li>
            <li><a href="#cy">茶叶</a></li>
            <li><a href="#mj">美酒</a></li>
            <li><a href="#gs">果蔬</a></li>
            <li><a href="#ls">零食</a></li>


        </ul>
    </div>
    <!-- 导航图  暂时隐藏-->
    <!--  <div class="sy-body1">
              </div>-->
    <!-- 特价优惠精选 -->
    <div class="gq_bg">
        <div class="sy-body2">
            <div><img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_26.png"/></div>
            <!-- <div><a href="https://www.dbmall.com/shop/index.php?act=active&op=doubletenth"><img src="https://www.dbmall.com/shop/templates/default/images/new/sy_cpic.jpg"/></a></div> -->
            <a target="_blank" class="animate" href="https://www.dbmall.com/2865.html"><img src="https://img.dbmall.com/data/upload/home/06004345358500093.jpg" alt=""/>
                <p>活动价:￥<span>12.6 </span></p>
            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/3307.html"><img src="https://img.dbmall.com/data/upload/home/06004345358597157.jpg" alt=""/>
                <p>活动价:￥<span>18.0 </span></p>
            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4789.html"><img src="https://img.dbmall.com/data/upload/home/06135812885796355.jpg" alt=""/>
                <p>活动价:￥<span>51.0 </span></p>
            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/2860.html"><img src="https://img.dbmall.com/data/upload/home/06004345358640514.jpg" alt=""/>
                <p>活动价:￥<span>20.5 </span></p>
            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4725.html"><img src="https://img.dbmall.com/data/upload/home/06141829476621085.jpg" alt=""/>
                <p>活动价:￥<span>9.9 </span></p>
            </a>
        </div>
    </div>
    <!-- 新品上市 -->
    <a name="xp"></a>
    <div class="ssy_bg">
        <div class="sy-body3">
            <div class="sy-body3-a">
                <img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_34.png"/>
                <h2 class="h_sort">新品上市</h2>
            </div>
            <div class="sy-body3-b">
                <a target="_blank" class="animate" href="https://www.dbmall.com/4807.html"><img src="https://img.dbmall.com/data/upload/home/06148793651147807.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="https://www.dbmall.com/4721.html"><img src="https://img.dbmall.com/data/upload/home/06124648963772339.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="https://www.dbmall.com/4676.html"><img src="https://img.dbmall.com/data/upload/home/06129763700581581.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="https://www.dbmall.com/4745.html"><img src="https://img.dbmall.com/data/upload/home/06140984749775030.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="http://www.dbmall.com/2396.html"><img src="https://img.dbmall.com/data/upload/home/05846168643273947.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="https://www.dbmall.com/4780.html"><img src="https://img.dbmall.com/data/upload/home/06148793651165182.jpg" alt=""/></a>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <!-- <div class="ad_img_box">
        <a href="http://www.dbmall.com/g458.html" target="_blank" class="ad_img"></a>
    </div> -->
    <!-- 地标特色 -->
    <a name="ts"></a>
    <div class="ssy_bg">
        <div class="sy-body4">
            <div class="sy-body4-a">
                <img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_34.png"/>
                <h2 class="h_sort">地标特色</h2>
            </div>
            <div class="sy-body4-b">
                <a target="_blank" class="animate" href="http://www.dbmall.com/4329.html"><img src="https://img.dbmall.com/data/upload/home/06009664287198998.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="https://www.dbmall.com/4297.html"><img src="https://img.dbmall.com/data/upload/home/05984425852268057.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="http://www.dbmall.com/4294.html"><img src="https://img.dbmall.com/data/upload/home/05984427847872669.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="http://www.dbmall.com/511.html"><img src="https://img.dbmall.com/data/upload/home/05846175559661062.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="http://www.dbmall.com/1347.html"><img src="https://img.dbmall.com/data/upload/home/05846175559669381.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="https://www.dbmall.com/4298.html"><img src="https://img.dbmall.com/data/upload/home/05984423797348633.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="https://www.dbmall.com/4245.html"><img src="https://img.dbmall.com/data/upload/home/05957023216798147.jpg" alt=""/></a>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <!-- 地标珍礼 -->
    <a name="zl"></a>
    <div class="ssy_bg">
        <div class="sy-body5">
            <div class="sy-body5-a">
                <img src="https://www.dbmall.com/shop/templates/default/images/new/dbmall_34.png"/>
                <h2 class="h_sort">地标珍礼</h2>
                <a target="_blank" href="http://www.dbmall.com/dibiaozhenpin">查看更多>></a></div>
            <div class="sy-body5-b">
                <a target="_blank" class="animate" href="https://www.dbmall.com/3958.html"><img src="https://img.dbmall.com/data/upload/home/05981177657624381.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="http://www.dbmall.com/4086.html"><img src="https://img.dbmall.com/data/upload/home/05981177657637485.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="http://www.dbmall.com/3663.html"><img src="https://img.dbmall.com/data/upload/home/05981177657645986.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="http://www.dbmall.com/4180.html"><img src="https://img.dbmall.com/data/upload/home/05981177657663867.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="https://www.dbmall.com/4008.html"><img src="https://img.dbmall.com/data/upload/home/05975114073068115.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="https://www.dbmall.com/1503.html"><img src="https://img.dbmall.com/data/upload/home/05981177657676095.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="http://www.dbmall.com/3999.html"><img src="https://img.dbmall.com/data/upload/home/05981177657670998.jpg" alt=""/></a>
                <a target="_blank" class="animate" href="https://www.dbmall.com/4388.html"><img src="https://img.dbmall.com/data/upload/home/06034763885237686.jpg" alt=""/></a>
                <div class="clear"></div>
            </div>
        </div>
    </div>
    <!-- 地标珍品-->
    <a name="zb"></a>
    <div class="ssy_bg">
        <div class="sy-body6">
            <h2>
                <a target="_blank" href="https://www.dbmall.com/dibiaozhenpin"><img src="https://img.dbmall.com/data/upload/home/05845578317462296.jpg" alt=""/></a>
            </h2>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4373.html">
                <img src="https://img.dbmall.com/data/upload/home/06034703701011660.jpg" alt="烤鸭"/>

                <div class="price_box">￥123.8 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4158.html">
                <img src="https://img.dbmall.com/data/upload/home/05987233082791730.jpg" alt="莲子"/>

                <div class="price_box">￥30.5 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4318.html">
                <img src="https://img.dbmall.com/data/upload/home/05989774347368840.jpg" alt="奶酪"/>

                <div class="price_box">￥17.2 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4334.html">
                <img src="https://img.dbmall.com/data/upload/home/06009682119882288.jpg" alt="豆豉"/>

                <div class="price_box">￥13.0 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/1446.html">
                <img src="https://img.dbmall.com/data/upload/home/05926763308728042.jpg" alt="广东陈李济新会陈皮"/>

                <div class="price_box">￥110.9 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/2628.html">
                <img src="https://img.dbmall.com/data/upload/home/05926763308728081.jpg" alt="德福隆壹品珍菌A套餐"/>

                <div class="price_box">￥85.3 </div>

            </a>
            <div class="clear"></div>
        </div>
    </div>
    <!-- 粮油副食 -->
    <a name="ly"></a>
    <div class="ssy_bg">
        <div class="sy-body6">
            <h2>
                <a target="_blank" href="https://www.dbmall.com/liangyoufushi"><img src="https://img.dbmall.com/data/upload/home/05845578655238632.jpg" alt=""/></a>
            </h2>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4098.html">
                <img src="https://img.dbmall.com/data/upload/home/05973393675569504.jpg" alt="陈克明面条"/>

                <div class="price_box">￥11.8 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/3811.html">
                <img src="https://img.dbmall.com/data/upload/home/05973393675642790.jpg" alt="福临门菜籽油"/>

                <div class="price_box">￥68.9 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4099.html">
                <img src="https://img.dbmall.com/data/upload/home/05973393675668054.jpg" alt="意大利面"/>

                <div class="price_box">￥19.1 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/1893.html">
                <img src="https://img.dbmall.com/data/upload/home/05926763764407002.jpg" alt="贵州牛肉辣椒酱"/>

                <div class="price_box">￥43.8 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/3593.html">
                <img src="https://img.dbmall.com/data/upload/home/05973395513233941.jpg" alt="五常大米"/>

                <div class="price_box">￥123.0 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4100.html">
                <img src="https://img.dbmall.com/data/upload/home/05973394397567737.jpg" alt="农心经典八连包"/>

                <div class="price_box">￥35.2 </div>

            </a>
            <div class="clear"></div>
        </div>
    </div>
    <!-- 酒水茶饮-->
    <a name="cy"></a>
    <div class="ssy_bg">
        <div class="sy-body6">
            <h2>
                <a target="_blank" href="https://www.dbmall.com/jiushuichayin"><img src="https://img.dbmall.com/data/upload/home/05845577868520707.jpg" alt=""/></a>
            </h2>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4345.html">
                <img src="https://img.dbmall.com/data/upload/home/06009672234771685.jpg" alt="茶礼盒"/>

                <div class="price_box">￥49.1 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/1354.html">
                <img src="https://img.dbmall.com/data/upload/home/05926762509014118.jpg" alt="贵州绥阳金银花茶"/>

                <div class="price_box">￥108.0 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/2486.html">
                <img src="https://img.dbmall.com/data/upload/home/06150746979044152.jpg" alt="贵州指南针迎宾酒"/>

                <div class="price_box">￥668.0 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/2484.html">
                <img src="https://img.dbmall.com/data/upload/home/05926762509024883.jpg" alt="贵州福朋红色遵义纪念酒"/>

                <div class="price_box">￥228.0 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/1441.html">
                <img src="https://img.dbmall.com/data/upload/home/05926762509020460.jpg" alt="广东陈李济即冲普洱茶茶粉"/>

                <div class="price_box">￥33.6 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/1443.html">
                <img src="https://img.dbmall.com/data/upload/home/05926762509046153.jpg" alt="广东陈李济即冲红茶茶粉"/>

                <div class="price_box">￥34.3 </div>

            </a>
            <div class="clear"></div>
        </div>
    </div>
    <!-- 山珍海味 -->
    <a name="mj"></a>
    <div class="ssy_bg">
        <div class="sy-body6">
            <h2>
                <a target="_blank" href="https://www.dbmall.com/shanzhenhaiwei"><img src="https://img.dbmall.com/data/upload/home/05845577325664370.jpg" alt=""/></a>
            </h2>
            <a target="_blank" class="animate" href="https://www.dbmall.com/3743.html">
                <img src="https://img.dbmall.com/data/upload/home/06009674069237779.jpg" alt="扇贝"/>

                <div class="price_box">￥19.2 </div>
                <
            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4078.html">
                <img src="https://img.dbmall.com/data/upload/home/05973409195316876.jpg" alt="墨鱼仔"/>

                <div class="price_box">￥23.2 </div>
                <
            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4076.html">
                <img src="https://img.dbmall.com/data/upload/home/05957026726799786.jpg" alt="干贝"/>

                <div class="price_box">￥69.0 </div>
                <
            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4220.html">
                <img src="https://img.dbmall.com/data/upload/home/05973416052018601.jpg" alt="南美白虾"/>

                <div class="price_box">￥33.0 </div>
                <
            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4080.html">
                <img src="https://img.dbmall.com/data/upload/home/05973416052044190.jpg" alt="小八爪鱼"/>

                <div class="price_box">￥24.4 </div>
                <
            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4010.html">
                <img src="https://img.dbmall.com/data/upload/home/05973416052063491.jpg" alt="麻辣小龙虾"/>

                <div class="price_box">￥57.0 </div>
                <
            </a>
            <div class="clear"></div>
        </div>
    </div>
    <!-- 生鲜果蔬 -->
    <a name="gs"></a>
    <div class="ssy_bg">
        <div class="sy-body6">
            <h2>
                <a target="_blank" href="https://www.dbmall.com/shengxianguoshu"><img src="https://img.dbmall.com/data/upload/home/05845576632888621.jpg" alt=""/></a>
            </h2>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4304.html">
                <img src="https://img.dbmall.com/data/upload/home/05981220395408963.jpg" alt="碧根果"/>

                <div class="price_box">￥25.7 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4156.html">
                <img src="https://img.dbmall.com/data/upload/home/05966279574088175.jpg" alt="张君雅点心面"/>

                <div class="price_box">￥23.3 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/3129.html">
                <img src="https://img.dbmall.com/data/upload/home/05973389920620688.jpg" alt="威化饼"/>

                <div class="price_box">￥27.6 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4272.html">
                <img src="https://img.dbmall.com/data/upload/home/06009675148513236.jpg" alt="脆脆鲨"/>

                <div class="price_box">￥38.1 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4210.html">
                <img src="https://img.dbmall.com/data/upload/home/05960287399879828.jpg" alt="烤肠"/>

                <div class="price_box">￥34.2 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4190.html">
                <img src="https://img.dbmall.com/data/upload/home/05942115230165040.jpg" alt="枣夹核桃"/>

                <div class="price_box">￥31.7 </div>

            </a>
            <div class="clear"></div>
        </div>
    </div>
    <!-- 休闲零食 -->
    <a name="ls"></a>
    <div class="ssy_bg">
        <div class="sy-body6">
            <h2>
                <a target="_blank" href="https://www.dbmall.com/xiuxianlingshi">
                    <img src="https://img.dbmall.com/data/upload/home/05845575637322207.jpg" alt=""/>
                </a>
            </h2>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4255.html">
                <img src="https://img.dbmall.com/data/upload/home/05961325433288284.jpg" alt="碧根果"/>

                <div class="price_box">￥23.3 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4193.html">
                <img src="https://img.dbmall.com/data/upload/home/05961325433424736.jpg" alt="张君雅点心面"/>

                <div class="price_box">￥15.7 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4217.html">
                <img src="https://img.dbmall.com/data/upload/home/05961325433456238.jpg" alt="威化饼"/>

                <div class="price_box">￥9.9 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4405.html">
                <img src="https://img.dbmall.com/data/upload/home/06034759601428824.jpg" alt="脆脆鲨"/>

                <div class="price_box">￥34.0 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4393.html">
                <img src="https://img.dbmall.com/data/upload/home/06034759601474536.jpg" alt="烤肠"/>

                <div class="price_box">￥30.7 </div>

            </a>
            <a target="_blank" class="animate" href="https://www.dbmall.com/4177.html">
                <img src="https://img.dbmall.com/data/upload/home/05961329473445296.jpg" alt="枣夹核桃"/>

                <div class="price_box">￥24.6 </div>

            </a>
            <div class="clear"></div>
        </div>
    </div>
    <div style="clear:both;"></div>
    <!--S新闻资讯  -->


    <div class="sy-body7">
        <div>
            <div class="sy-body7-a">
                <span>美食资讯<a href="https://www.dbmall.com/index.php?act=article&op=articleClassShow&type=food" target='_blank'>更多></a></span>
                <span>公司动态<a href="https://www.dbmall.com/index.php?act=article&op=articleClassShow&type=company" target='_blank'>更多></a></span>
            </div>
            <div class="sy-body7-b">
                <ul>
                    <li><a href="index.php?act=article&op=index1&artic_id=534" target='_blank'><span>云南人参果有什么好处</span><span>2018.12.27</span></a></li>
                    <li><a href="index.php?act=article&op=index1&artic_id=533" target='_blank'><span>蓝莓的功效与作用 这类人千万别吃蓝莓</span><span>2018.12.05</span></a></li>
                    <li><a href="index.php?act=article&op=index1&artic_id=532" target='_blank'><span>车厘子有什么营养价值</span><span>2018.12.03</span></a></li>
                    <li><a href="index.php?act=article&op=index1&artic_id=531" target='_blank'><span>沙田柚的功效与作用 吃沙田柚的好处</span><span>2018.11.19</span></a></li>
                    <li><a href="index.php?act=article&op=index1&artic_id=530" target='_blank'><span>牛油果的营养价值</span><span>2018.11.02</span></a></li>
                    <li><a href="index.php?act=article&op=index1&artic_id=529" target='_blank'><span>秋季滋补适合吃什么</span><span>2018.11.01</span></a></li>
                </ul>
                <ul>
                    <li><a href="index.php?act=article&op=index1&artic_id=535" target='_blank'><span>喜报！深圳华夏地标电子商务有限公司荣获深圳市高新技术企业认定</span><span>2019.02.14</span></a></li>
                    <li><a href="index.php?act=article&op=index1&artic_id=506" target='_blank'><span>地标商城生鲜革命——深入泰国水果基地 打通产品供应链“最后一公里”</span><span>2018.07.31</span></a></li>
                    <li><a href="index.php?act=article&op=index1&artic_id=504" target='_blank'><span>吃货们颤抖吧！地标商城泰国一级金枕榴莲进军20个省市</span><span>2018.07.12</span></a></li>
                    <li><a href="index.php?act=article&op=index1&artic_id=503" target='_blank'><span>广西特色美食节——地标商城深挖地方美食，推动地标特色食品建设</span><span>2018.07.12</span></a></li>
                    <li><a href="index.php?act=article&op=index1&artic_id=485" target='_blank'><span>地标商城荣获“2018社交电商最佳品牌”奖项</span><span>2018.05.29</span></a></li>
                    <li><a href="index.php?act=article&op=index1&artic_id=483" target='_blank'><span>汇智聚力 创享未来——地标商城受邀参加广东省湖北松滋商会二届二次会员大会</span><span>2018.05.22</span></a></li>
                </ul>
            </div>
            <div class="clear"></div>
        </div>
    </div>

    <!--E新闻资讯  -->
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
        <script type="text/javascript" src="data/resource/js/jquery-2.1.1.min.js"></script>

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

    <script type="text/javascript">
        $(function(){
            //首页banner轮播--首页--分类
            var length = $(".swipe-wrap>div").length;
            if(length>1){
                for(var i=0;i<length;i++){
                    $(".banner ul").append("<li></li> ");
                }
            }
            var swiper = new Swiper('.swiper-container', {
                pagination: '.swiper-pagination',
                paginationClickable: true,
                autoplaying:true,
                autoplay:3000,
                effect : 'fade',
                autoplayDisableOnInteraction : false,
            });

            //全部地标展馆菜单--菜单
            var mark = true;

            function leave(){
                setTimeout(function(){
                    if(mark){
                        $('.menu-focus').hide();
                    }
                },100);
            }

            $('.menu a').first().mouseover(function(){
                $('.menu-focus').show();
            });

            $('.menu a').first().mouseover(function(){
                mark = false;
            }).mouseleave(leave);

            $('.menu-focus').mouseleave(function(){
                mark = true;
                leave();
            });

            //动画 --首页
            /* var width = null;
            var height = null;
            $('.animate').mouseover(function(){
                width = parseFloat($(this).width());
                height = parseFloat($(this).height());
                $(this).find('img').stop().animate({
                    width:(width+3)+'px',
                    height:(height+3)+'px'
                    marginLeft: '-1px',
                    marginTop: '-1px'
                },300);
            });

            $('.animate').mouseleave(function(){
                $(this).find('img').stop().animate({
                    width:width+'px',
                    height:height+'px',
                    marginLeft: '0px',
                    marginTop: '0px'
                },300);
            }); */
            //侧边显示与隐藏--首页
            $('.sy-left ul').css('top',($(window).height()-$('.sy-left ul').height())/2+'px');
            $(window).scroll(function(){
                var stop = $(window).scrollTop();
                if($(".xq").html()!=null && $(".xq").html()!=""){
                    var a = false;
                    setTimeout(function(){
                        a = true;
                        if(a && stop>500){
                            $('.xq-body-buy').stop().animate({
                                top:'0px'
                            },200);
                        }else{
                            $('.xq-body-buy').stop().animate({
                                top:'-95px'
                            },200);
                        }
                    },50,function(){
                        a = false;
                    });
                }
                var index = null;

                if(stop>500){
                    index = 100;
                }

                if(stop>$('a[name=xp]').position().top-300){
                    index = 1;
                }

                if(stop>$('a[name=ts]').position().top-300){
                    index = 2;
                }

                if(stop>$('a[name=zl]').position().top-300){
                    index = 3;
                }

                if(stop>$('a[name=zb]').position().top-300){
                    index = 4;
                }

                if(stop>$('a[name=ly]').position().top-300){
                    index = 5;
                }

                if(stop>$('a[name=cy]').position().top-300){
                    index = 6;
                }

                if(stop>$('a[name=mj]').position().top-300){
                    index = 7;
                }

                if(stop>$('a[name=gs]').position().top-400){
                    index = 8;
                }

                if(stop>$('a[name=ls]').position().top-400){
                    index = 9;
                }

                if(stop>$('.foot').position().top-890){
                    index = null;
                }

                if(index!=null){
                    $('.sy-left ul li a').css('color','#505458');
                    $('.sy-left ul li').eq(index-1).find('a').css('color','#c54d52');
                    $('.sy-left ul').show();
                }else{
                    $('.sy-left ul').hide();
                }
            });

        })
    </script>

</body>


<!-- 优惠卷领取 -->
<script type="text/javascript" src="static/pc/v2/src/js/mod/aop/voucher_lingqu.js"></script>

</html>