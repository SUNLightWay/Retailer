//首页广告位
$(function () {
   $(".slide_box").slide({mainCell:".bd ul",effect:"leftLoop",autoPlay:true});
});


$(function () {
    // 获取需要修正的高度
    function getOffsetHeight(object) {
        // 获取屏幕的可视高度
        var clientHeight = $(window).height();
        // 当前滚动条高度
        var scrollTop = $(document).scrollTop();
        // 当前元素滚动高度
        var obj_top = object.offset().top;
        // 当前元素底部距离滚动高度
        var height = object.outerHeight() + obj_top - scrollTop;
        return clientHeight - height;
    }

    //鼠标划入时
    $('.iten').hover
    (
        function () {
            var children_div = $(this).children('div');

            var t_this = $(this);
            if (t_this.find(".sub_category .sub_view").text() == "") {
                // t_this.find(".subCategory .subView").html("<img src=\"images/lodding.gif\" alt=\"加载中...\"/>");
            }
            //有子分类时才显示
            t_this.find('.item_hd').css({
                border:'none'
            })
            t_this.find('.item_hd').prev().css({
                border:'none'
            })
            t_this.prev().find('.item_hd').css({
                border:'none'
            });
            children_div.show();
            $(this).addClass('selected');

        },
        //鼠标移除时
        function () {
            var children_div = $(this).children('div');
            //children_div.css({
            //    top: children_div.data('origin_top')
            //})
            $(this).find('.item_hd').css({
                'border-bottom':'1px dotted white'
            })
            $(this).prev().find('.item_hd').css({
                'border-bottom':'1px dotted white'
            })

            children_div.hide();

            $(this).removeClass('selected');
        }
    );
    $('.iten:nth-child(1)').css("margin-top", "0");
});



//楼层导航切换
$(function(){
   $('.floor1-nav').click(function(){
       $('.floor1-nav').removeClass('hoverA');
       $(this).addClass('hoverA');
       $('.floor1-content').hide();
       var box =  $(this).attr('data-type') + '-box';
       $('div.floor1-content.'+box).show();
   })

    $('.floor2-nav').click(function(){
        $('.floor2-nav').removeClass('hoverA');
        $(this).addClass('hoverA');
        $('.floor2-content').hide();
        var box =  $(this).attr('data-type') + '-box';
        $('div.floor2-content.'+box).show();
    })

    $('.floor3-nav').click(function(){
        $('.floor3-nav').removeClass('hoverA');
        $(this).addClass('hoverA');
        $('.floor3-content').hide();
        var box =  $(this).attr('data-type') + '-box';
        $('div.floor3-content.'+box).show();
    })
})