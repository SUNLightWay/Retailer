$(function () {
   //切换图片
   $(".productPictureTableMain ul li img").click(function(){
   		//去掉其他图片的选中样式
   		$(".productPictureTableMain ul li img").removeClass("productPictureSelected");
   		$(this).addClass("productPictureSelected");
   		var imgSrc=$(this).attr("src");
   		$(".productPictureImg").attr("src",imgSrc);
   });
});

$(function () {
   //切换tab
  	$(".productTabBar li").click(function(){
  		$(".productTabBar li").removeClass("productTab-selected");
  		$(this).addClass("productTab-selected");
  		var index = $(this).attr("data-index");
  		
  		$(".productTabContents li").css("display","none");
  		$(".productTabContents").find("li").eq(index).css("display","block");
  	});
});

