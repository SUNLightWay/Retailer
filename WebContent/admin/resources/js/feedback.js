define(['common'], function (common) {
    //1.获取订单编号
    var orderNo = common.getParam("orderNo");

    //提交反馈
    function submitFeedback() {
        $.ajax({
            "xhrFields": {withCredentials: true},
            "crossDomain": true,
            "url": common.baseUrl + "mgr/order/getdetail.do",
            data: {"orderNo": orderNo},
            type: "post",
            success: function (rs) {
                //判断查询是否成功
                if (rs.status == 0) {
                    //订单信息
                    $("#orderNo-container").html(rs.data.orderNo);
                    $("#created-container").html(rs.data.created);
                    $("#deliveryName-container").html(rs.data.deliveryName);
                    $("#statusDesc-container").html(rs.data.statusDesc);
                    $("#typeDesc-container").html(rs.data.typeDesc);
                    $("#amount-container").html(rs.data.amount);
                    //商品列表
                    initTable(rs.data.orderItems);
                } else {
                    //失败弹出错误信息
                    alert(rs.msg);
                }
            }
        });
    }

    return {
        getOrderDetail: getOrderDetail
    };
});