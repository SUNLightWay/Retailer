define(['common'], function (common) {
    var parentId = common.getParam('parentId');
    var pid = common.getParam('paramId');
    var paramname = common.getParam('paramName');

    //1.获取产品类型参数
    function getParams() {
    	console.log("parentId: " + parentId);
    	console.log("pid:" + pid);
    	console.log("paramname:" + paramname)
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
                    var num = result.indexOf('value="' + parentId + '"');
                    console.log(num);
                    if (parentId == 0) {
                        //父级类型不显示
                        $("#parent").prepend("<option selected value='0'></option>");
                    } else {
                        //显示父级类型
                        var flg = "selected";
                        var first = result.substring(0, num);
                        var third = result.substring(num);
                        var newstr = first + flg + third;
                        $("#parent").html(newstr);
                    }
                } else {
                    //失败弹出错误信息
                    alert(rs.msg);
                }
            }
        });
        $("#paramName").val(paramname);
    }


    //2. 保存
    function saveUpdate() {
        $("#btn-save").click(function () {
            var name = $("#paramName").val();
            $.ajax({
                "xhrFields": {withCredentials: true},
                "crossDomain": true,
                "url": baseUrl + "mgr/param/updateparam.do",
                data: {"name": name, "parent_Id": parentId, "id": pid},
                type: "post",
                dataType: 'json',
                success: function (rs) {
                    if (rs.status == 0) {
                        //跳转页面
                    	alert("修改类型参数成功");
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
        saveUpdate: saveUpdate
    };
});