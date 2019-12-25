define(['common'], function (common) {
    //1.获取产品类型参数
    function getParams() {
        $.ajax({
            "xhrFields": {withCredentials: true},
            "crossDomain": true,
            "url": baseUrl + "mgr/param/findptype.do",
            success: function (rs) {
                if (rs.status == 0) {
                    //清空数据
                    $("#parent").html("");
                    //预加载模板添加数据
                    var tpl = $("#param_item_tpl").html();
                    var func = Handlebars.compile(tpl);
                    var data = rs.data;
                    var result = func(data);
                    $("#parent").html(result);
                    $("#parent").prepend("<option selected value='0'>请选择父类型</option>");
                } else {
                    //失败弹出错误信息
                    alert(rs.msg);
                }
            }
        });
    }

    //2.保存
    function saveBtn() {
        $("#btn-save").click(function () {
            var parentId = $("#parent").val();
            var name = $("#paramName").val();
            //删除用户
            $.ajax({
                "xhrFields": {withCredentials: true},
                "crossDomain": true,
                "url": baseUrl + "mgr/param/saveparam.do",
                data: {"name": name, "parent_id": parentId},
                type: "post",
                dataType: 'json',
                success: function (rs) {
                    if (rs.status == 0) {
                        //跳转页面                    	
                    	alert("新增成功");
                        $(window).attr("location", "param_list.html");
                    } else {
                        //失败弹出错误信息
                        alert(rs.msg);
                    }
                }
            });
        })
    }

    return {
        getParams: getParams,
        saveBtn: saveBtn
    };
});