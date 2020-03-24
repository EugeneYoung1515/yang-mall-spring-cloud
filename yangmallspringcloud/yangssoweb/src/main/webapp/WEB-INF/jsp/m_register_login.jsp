<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>注册和登录</title>
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


    <link rel="stylesheet" type="text/css" href="http://www.yangmall.com/static/pc/v2/src/css/lib/base.css">
    <link rel="stylesheet" type="text/css" href="http://www.yangmall.com/shop/templates/default/css/login.css?t=201803241025">
</head>
<body style="background:#fff; ">
    <!-- S Header -->
    <link rel="stylesheet" type="text/css" href="http://www.yangmall.com/shop/templates/default/css/dbmall_2016_02_23.css?t=201803241556" />
	<div class="logo login_top">
		<div>
			<div class="logo-1">
                <h1>
                    <a href="http://www.yangmall.com"><img src="http://www.yangmall.com/shop/templates/default/images/new/dbmall_03.svg" class="logo_ico" /></a>
                </h1>
                <img id="logo_right_title" src="http://www.yangmall.com/shop/templates/default/images/new/dbmall__06.png?t=201610111533">
			</div>
            <div class="flag_right">
                <img src="http://www.yangmall.com/shop/templates/default/images/login/flag_right.png"/>
            </div>
		</div>
		<div class="clear"></div>
	</div>
<!-- E Header -->
        <div class="login_container">
        <form class="login_box" id="sms_login_form"> 
            <h2>手机验证码登录</h2>
            <!-- 错误提示框 -->
            <div class="error_box" id="error_box">
                <p class="error_tip" id="error_tip_mobile"></p>
                <p class="error_tip" id="error_tip_sms_captcha"></p>
            </div>
            <div class="input_box phone_box">
                <input id="sms_login_input_mobile" maxlength="11" type="text" placeholder="请输入手机号" name="mobile" autocomplete="off" />
            </div>
            <div class="input_box code_box">
                <input id="sms_login_input_sms_captcha" maxlength="6" type="text" placeholder="请输入验证码" name="sms_captcha" autocomplete="off" />
                <a href="javascript:;" class="get_btn" id="sms_login_get_sms_but">获取验证码</a>
                <span id="sms_login_get_sms_timer"  class="get_btn count">45s</span>
            </div>
            <div class="verify_code_box ">
                <div class="verify_info">
                    <span>请点击图中倒立的文字</span>
                    <span class="right" onclick="refreshCaptcha()">换一张</span>
                </div>
                <div class="varify_code">
                    <div class="success-con" style="display:none">验证成功</div>
                    <img src="/index.php?act=seccode&op=captcha" alt="" id="id_captcha" name="id_captcha"  onclick="selectCaptcha()"><select id="word_select" name="word_select" style="display:none;"></select>
                </div>
            </div>
            <div class="btn_box">
                <a href="javascript:;" class="btn" id="sms_login_submit_but">登录</a>
                <!--
                <div class="tips">
                    <span class="line"></span>
                    <span class="tip">使用以下方式登录</span>
                </div>
                <a href="https://open.weixin.qq.com/connect/qrconnect?appid=wxd83940e0710bdf3f&redirect_uri=https%3a%2f%2fwww.dbmall.com%2fwechat%2findex.php%3fact%3dwechat_login%26op%3dwechat_login&response_type=code&scope=snsapi_login&state=dbmall" class="wechat">
                     <img src="../shop/templates/default/images/login/wechat.png"/>
                </a>
                -->
            </div>
        </form>
    </div>
    <!-- S Footer -->
<div class="sidebar_dbmall">
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
</div>
<div style="clear:both;"></div>
	<div class="footer_box">
		<div class="foot">
						<div class="foot-4">
				<a href="https://www.yangmall.com">首页</a>/
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
						$(".sidebar_dbmall_img").css("marginTop","30px");
						/*$(".sidebar_dbmall_img").css("marginTop","0px");*/
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
			</script>
		</div>
	</div>


<script type="text/javascript" src="http://www.yangmall.com/data/resource/js/jquery.js"></script>
<script type="text/javascript" src="http://www.yangmall.com/data/resource/js/jquery.validation.min.js"></script>
<script type="text/javascript" src="http://www.yangmall.com/data/resource/js/dbcaptcha.js"></script>
<script type="text/javascript" src="http://www.yangmall.com/wap/js/common.js"></script>
    <script>
        var is_mobile = !!navigator.userAgent.match(/mobile/i);
        var parent_id = '';
        //刷新验证码图片
        function refreshCaptcha(parentId) {
            parent_id = parentId;
            var url = '/index.php?act=seccode&op=captcha';
            if(is_mobile)
            {
                url = url + "&m=1";
                var img = document.getElementById("id_captcha");
                var imgW = img.width;
                var imgH = img.height;
                url = url + "&w="+imgW+"&h="+imgH;
            }
            $("#id_captcha").attr("src",url + "&r="+Math.random());
            clearSelect();
            $(".success-con").hide();
        }

        function clearSelect()
        {
            var parentObj = document.getElementById(parent_id);

            $("#word_select option").each(function (){
                var elid = $(this).val();
                var obj = document.getElementById(elid);
                if(parentObj != null)
                    parentObj.removeChild(obj);
                else
                    document.body.removeChild(obj);
            });
            $("#word_select").empty();
        }
        //$(function() {
        //$("#id_captcha").bind("click", function(ev) {
        function selectCaptcha(ev)
        {
            var oEvent = ev;
            if(!oEvent)
            {
                oEvent = window.event || arguments.callee.caller.arguments[0];
            }

            var number = $("#word_select option").length;
            var x = oEvent.clientX;
            var y = oEvent.clientY;

            var img = document.getElementById('id_captcha');    //获取图片的原点
            var nodex = getNodePosition(img)[0];			//原点x 与原点y
            var nodey = getNodePosition(img)[1];

            //var xserver = parseInt(x) - parseInt(nodex);
            //var yserver = parseInt(y) - parseInt(nodey);
            var xserver = oEvent.offsetX;
            var yserver = oEvent.offsetY;

            if (number >= 3) {
                return;
            }
            var id = xserver + "_" + yserver;
            $("#word_select").append("<option value='"+ id +"'>" + id + "</option>");

            var obj = document.createElement('span');
            obj.style.left = (parseInt(x) - 15) + 'px'; // 指定创建的span在文档中距离左侧的位置 左右移动15
            obj.style.top = (parseInt(y) - 15) + 'px';  // 指定创建的span在文档中距离顶部的位置
            //obj.style.border = '1px solid #3399ff';	// 设置边框
            obj.style.position = 'absolute';			// 为创建的span指定绝对定位
            obj.style.width = '6px';					// 指定宽度
            obj.style.height = '13px';					// 指定高度
            //obj.src = 'select.jpg';					// 图片路径
            //obj.style.opacity = '0.5';				// 透明度
            obj.id = id;
            obj.setAttribute("class", "badge");
            obj.style.backgroundColor = "#3399ff";
            obj.innerHTML = 'v';

            var parentObj = document.getElementById(parent_id);
            //移除选择
            $(obj).click(function(){
                $("#word_select option[value='"+$(this).attr("id")+"']").remove();
                if(parentObj != null)
                    parentObj.removeChild(this);
                else
                    document.body.removeChild(this);
            });

            if(parentObj != null)
                parentObj.appendChild(obj);
            else
                document.body.appendChild(obj);

            number = $("#word_select option").length;
            $("#error_tip_sms_captcha label").html('&nbsp;');

            if(number == 2 && !is_mobile)
                checkCaptcha();
        }
        //	});
        //})

        //校验方法
        //校验验证码1
        function checkCaptcha() {
            var array = [];
            $("#word_select option").each(function (){
                var val = $(this).val();
                if(val != ""){
                    array.push(val);
                }
            });

            $.ajax({
                type:"post",
                url:"/index.php?act=seccode&op=checkcaptcha",
                data : {
                    "data" : array,"mobile":$('#sms_login_input_mobile').val()
                },
                cache : false,
                dataType : "json",
                success : function(data) {
                    if(data.code == 1)
                    {
                        //alert(data.content);
                        if(is_mobile)
                        {
                            $.sDialog({
                                "skin": 'block',
                                "content": '验证失败，请重新验证:(',
                                "okBtn": false,
                                "cancelBtn": false,
                                "lock": false,
                            });
                        }
                        else
                        {
                            $("#error_tip_sms_captcha label").html('验证失败，请重新验证:(');
                            $("#error_tip_sms_captcha").show();
                            $('#error_box').show();

                        }
                        refreshCaptcha();
                    }
                    else
                    {
                        //alert("succ");
                        clearSelect();
                        $(".success-con").show();
                        $('#error_box').hide();
                        $("#error_tip_sms_captcha").hide();
                        $('.graph_popup').hide();

                        //sms_login_model.show_tips(response.msg);
                        sms_login_model.timer_second = 60;
                        sms_login_model.sent_timer();
                        if(is_mobile)
                        {
                            $.sDialog({
                                "skin": 'block',
                                "content": '验证码发送成功',
                                "okBtn": false,
                                "cancelBtn": false,
                                "lock": false,
                            });
                        }


                    }
                }
            });
        }

        function getNodePosition(node) {
            var top = left = 0;
            while (node) {
                if (node.tagName) {
                    top = top + node.offsetTop;
                    left = left + node.offsetLeft;
                    node = node.offsetParent;
                }
                else {
                    node = node.parentNode;
                }
            }
            return [left, top];
        }


        function showCaptcha()
        {
            var html = '<img id="id_captcha" name="id_captcha" src="/index.php?act=seccode&op=captcha" onclick="selectCaptcha()"/>';
            $("#captcha_data").html(html);
        }

        function isSelectCaptcha() {
            var number = $("#word_select option").length;
            if(number != 2)
            {
                $.sDialog({
                    "skin": 'block',
                    "content": '请选择倒立的文字',
                    "okBtn": false,
                    "cancelBtn": false,
                    "lock": false,
                });
                return false;
            }
            return true;
        }

        function getPosStr()
        {
            var pos = '';
            $("#word_select option").each(function (){
                var val = $(this).val();
                if(val != ""){
                    if(pos == '')
                        pos = val;
                    else
                        pos += ","+val;
                }
            });
            return pos;
        }
        function sendVoiceCode(){
            var v1 = sms_login_model.valid_mobile();
            if (!v1) return;
            if (v1) {
                $.ajax({
                    type: 'POST',
                    dataType: 'json',
                    url: SiteUrl + '/mobile/index.php?act=login&op=send_voice_code',
                    data: {mobile:$('#sms_login_input_mobile').val(),module_id:1},
                    success: function(response) {
                        if(response.state){
                            addcookie('firstlogin',1,1);
                            sms_login_model.timer_second = 60;
                            sms_login_model.sent_timer();
                            $('.tel_btn').html('<span>请留意接听 125909888587 的来电</span>');
                        }else{
                            $.sDialog({
                                "skin": 'block',
                                "content": response.msg,
                                "okBtn": false,
                                "cancelBtn": false,
                                "lock": false,
                            });
                            refreshCaptcha();
                        }
                    }
                });
            }
        }

    </script>
<script type="text/javascript">

    jQuery.validator.addMethod("isMobile", function(value, element) {
        var length = value.length;
        var mobile = /^(1[0-9]{10})$/;
        return this.optional(element) || (length == 11 && mobile.test(value));
    }, "请正确填写您的手机号码");


    var sms_login_model =
    {
        timer_second : 0,
        timer : null,
        refund_url : '',
        base_url : 'https://www.dbmall.com/shop/index.php?act=sms_login',
        is_display_dialog : false,
        referer_url : 'https://www.dbmall.com/shop/index.php?act=search&keyword=%E5%8E%A6%E9%97%A8',
        login_succ_callback : null,
        sent_timer : function(){
            if(this.timer_second>1)
            {
                this.timer_second--;
                $("#sms_login_get_sms_timer").html("倒计时："+this.timer_second).show();
                $("#sms_login_get_sms_but").hide();
                //极验按钮
                $("#popup-submit").hide();
                timer = setTimeout("sms_login_model.sent_timer()",1000);
            }else{
                $("#sms_login_get_sms_timer").hide();
                $("#sms_login_get_sms_but").show();
                //极验按钮
                $("#popup-submit").show();
                sms_login_model.sms_sending = false;
                clearTimeout(this.timer);
				refreshCaptcha();
            }
        },
        refresh_img_captache : function(){
            $("#sms_login_input_img_captcha").val('');
            $("#img_captcha").attr('src',this.base_url+"&op=make_img_captcha&nchash=98e4f55e&t="+Math.random());
        },
        show_message : function($msg,$type){
            $("#error_tip_sms_captcha").html('<label>'+$msg+'</label>');
        },

        open_dialog : function($callback)
        {
            this.login_succ_callback = $callback;
            //检查登录状态
            $.get(this.base_url+"&op=check_login",null,function(response){
                if(response.code==200)
                {
                    sms_login_model.close_dialog();
                    sms_login_model.login_succ_callback();
                }else
                {
                    if(!this.is_display_dialog)
                        sms_login_model.display_dialog();
                }
            });
        },
        display_dialog : function(){
            //更换验证码
            // $("#img_captcha").click(function(){
            //     sms_login_model.refresh_img_captache();
            // });

            //前端校验
            $("#sms_login_form").validate({
                rules: {
                    mobile : {
                        required : true,
                        isMobile : true
                    },
                     sms_captcha : {
                        required:true,
                        minlength: 6,
                        maxlength: 6
                    }
                },
                messages: {
                     mobile  : {
                        required : '手机号码不能为空',
                        isMobile : '手机号码格式不正确'
                    },
                    sms_captcha : {
                        required : '手机动态密码不能为空',
                        minlength:'手机动态密码为6位',
                        maxlength:'手机动态密码为6位'
                    }
                },
                errorPlacement : function(error,element){
                     var name = $(element).attr('name');
                     $("#error_tip_"+name).html('');
                     $("#error_tip_"+name).append(error);
                      $('#error_box').show();
                },
                success:function(){
                    if($("#error_tip_mobile label").html() || $("#error_tip_sms_captcha label").html()){
                        $('#error_box').show();
                     }else{
                         $('#error_box').hide();
                     }
                }
            });

            //发送短信验证码
            $("#sms_login_get_sms_but").click(function(){

                //前端校验
                if($('#sms_login_input_mobile').val()=='')
                {
                    $('#error_tip_mobile').html("请输入正确手机号码");
                    $('#error_box').show();
                }else{
					//addcookie('firstlogin',1,1);
                    $('.verify_code_box').show();
                    refreshCaptcha();
                    var data = '';
                    /*$('#id_captcha').click(function(){
                        data = selectCaptcha(); 
                    })*/
                    var post_data = $("#sms_login_form").serialize();
                    if(!sms_login_model.sms_sending){
                        sms_login_model.sms_sending = true;
                        $.post(sms_login_model.base_url+"&op=send_sms",post_data,function(response){
                            if(response.code==200)
                            {
                                sms_login_model.show_message(response.msg,'succ');
                                sms_login_model.timer_second = 60;
                                sms_login_model.sent_timer();
                            }else{
                                sms_login_model.show_message(response.msg,'error');
                                sms_login_model.sms_sending = false;
                            }
                        });
                    }
                }
                return false;

            });

            //提交注册
            $("#sms_login_submit_but").click(function(){
             
                //前端校验
                if($("#sms_login_form").valid()){
                    var post_data = $("#sms_login_form").serialize();

                    $.post(sms_login_model.base_url+"&op=login_o_register",post_data,function(response){
                        if(response.code==200)
                        {
                            sms_login_model.show_message(response.msg,'succ');
                            window.location.href = sms_login_model.referer_url;
                        }else{
                            sms_login_model.show_message(response.msg,'error');
                        }
                    });
                }
                return false;
            });
        }
    };
    sms_login_model.display_dialog();

</script>
</body>
</html>
