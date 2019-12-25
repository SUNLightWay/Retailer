define(['common', 'simditor'],function(common,Simditor){
	var productid = 10023;
	//1.获取产品类型参数
	function getParams(){
		console.log("getparams");
		$.ajax({
			"xhrFields":{withCredentials:true},
			"crossDomain":true,
			"url":baseUrl+"mgr/param/findptype.do",
			success:function(rs){
				//插入数据 
				if(rs.status==0){
					//原位置清空
					$("#productType").html("");
					//添加数据
					var tpl = $("#param_item_tpl").html(); 
					var func = Handlebars.compile(tpl);
					var data= rs.data;
					var result = func(data);
					$("#productType").html(result);
					$("#productType").prepend("<option selected value='-1'>请选择产品类型</option>");
				}
			}
		});
	}
	//2.为产品下拉绑定事件
	function dropDownEvent(){
		//绑定事件
		$("#productType").change(function(){
			var val = $("#productType").val();
			if(val!=-1){
				//显示配件类型
				$("#parts-type-container").css({"display":"block"});
				//根据产品类型查询配件类型
				loadPartsType(val);
			}else{
				//隐藏配件类型
				$("#parts-type-container").css({"display":"none"});
			}
		});	
	}
	
	//3.保存商品信息
	function saveBtn(){
		//绑定click事件
		$("#btn-save").click(function(){
			//获取参数
			var goodsName = $("#goodsName").val();
			var productType = $("#productType").val();
			var partsType = $("#partsType").val();
			var goodsPrice = $("#goodsPrice").val();
			var goodsStock = $("#goodsStock").val();
			var images = $("#images").val();
			var detail = $("#preview").html();
			$.ajax({
				"xhrFields":{withCredentials:true},
				"crossDomain":true,
				"url":baseUrl+"mgr/product/saveproduct.do",
				type:"post",
				data:{"name":goodsName,"productId":productType,"partsId":partsType,"detail":detail,"price":goodsPrice,
					"stock":goodsStock,"subImages":images},
				success:function(rs){
					if(rs.status==0){
						//成功是跳转首页
						alert("新增商品成功！");
						$(window).attr("location","starter.html");
					}else{
						//失败返回错误信息
						alert(rs.msg);
					}
				}
			});
		});
	}
	
	
	return {
		getParams:getParams,
		dropDownEvent:dropDownEvent,
		saveBtn:saveBtn
	};
	
	//根据产品类型加载配件类型
	function loadPartsType(productTypeId){
		$.ajax({
			"xhrFields":{withCredentials:true},
			"crossDomain":true,
			"url":baseUrl+"mgr/param/findchildren.do",
			data:{"id":productTypeId},
			success:function(rs){
				//插入数据 
				if(rs.status==0){
					//原位置清空
					$("#partsType").html("");
					//添加数据
					var tpl = $("#param_item_tpl").html();
					var func = Handlebars.compile(tpl);
					var data= rs.data;
					var result = func(data);
					$("#partsType").html(result);
				}
			}
		});
	}
});

//初始化编辑器
function initEditor(){
    var $preview, mobileToolbar, toolbar;
    //Simditor.locale = 'zh-CN';
    toolbar = [ 'title', 'bold', 'italic', 'underline', 'strikethrough',    
        'color', '|', 'ol', 'ul', 'blockquote', 'code', 'table', '|',    
        'link', 'image', 'hr' ];   
    Simditor.locale = 'zh-CN';//设置中文
    editor = new Simditor({
      textarea: $('#txt-content'),
      placeholder: '这里输入文字...',
      toolbar: toolbar,
      pasteImage: true,
      defaultImage: 'resources/simditor/assets/images/image.png',
      upload:{
        url: baseUrl+'mgr/product/pic_upload.do',
        xhrFields: {withCredentials: true},
        crossDomain: true,
        params: null, //键值对,指定文件上传接口的额外参数,上传的时候随文件一起提交  
        fileKey: 'files', //服务器端获取文件数据的参数名  
        connectionCount: 3,  
        leaveConfirm: '正在上传文件',  
      },
      success:function(data){
        alert(data);
      }
    });
}