<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul>
    <li class="receive_add addr_item">
        <input value="0" nc_type="addr" id="add_addr" type="radio" name="addr">
        <label for="add_addr">使用新地址</label>
    </li>
    <div id="add_addr_box"><!-- 存放新增地址表单 --></div>
</ul>
<div class="hr16"> <a id="hide_addr_list" class="ncc-btn ncc-btn-red" href="javascript:void(0);">保存收货人信息</a></div>
<script type="text/javascript">
    function delAddr(id){
        $('#addr_list').load(SITEURL+'/index.php?act=buy&op=load_addr&id='+id);//这个要不要改
    }
    $(function(){
        //$('#add_addr_box').load(SITEURL+'/addaddress');


        function addAddr() {
            $('#add_addr_box').load(SITEURL+'/addaddress');
        }

        $('input[nc_type="addr"]').on('click',function(){
            if ($(this).val() == '0') {
                $('.address_item').removeClass('ncc-selected-item');
                $('#add_addr_box').load(SITEURL+'/addaddress');
            } else {
                $('.address_item').removeClass('ncc-selected-item');
                $(this).parent().addClass('ncc-selected-item');
                $('#add_addr_box').html('');
            }
        });
        $('#hide_addr_list').on('click',function(){
            if ($('input[nc_type="addr"]:checked').val() == '0'){
                submitAddAddr();
            } else {
                if ($('input[nc_type="addr"]:checked').size() == 0) {
                    return false;
                }
                var city_id = $('input[name="addr"]:checked').attr('city_id');
                var area_id = $('input[name="addr"]:checked').attr('area_id');
                var addr_id = $('input[name="addr"]:checked').val();
                var true_name = $('input[name="addr"]:checked').attr('true_name');
                var address = $('input[name="addr"]:checked').attr('address');
                var phone = $('input[name="addr"]:checked').attr('phone');
                showShippingPrice(city_id,area_id);
                hideAddrList(addr_id,true_name,address,phone);
            }
            $('.sycard-2').click();
        });
        if ($('input[nc_type="addr"]').size() == 1){
            $('#add_addr').attr('checked',true);
            addAddr();
        }
    });
</script>