$(function(){
	$("#table").dataTable({
		"autoWidth":false, 	//禁止自动计算宽度
		"paging":true,		//分页
		"ordering":false, 	//排序
		"info":false,		//统计信息
		"searching":false,	//取消搜索
		"dom":'<"#tool-container"><"top" t> <"botton" lp>', //dom
		"sPaginationType":"full_numbers",
		"ajax":{
			"url":"http://localhost:8080/mall/mgr/user/finduser.do",
		},
		"columns":[
			{"data":"id"},
			{"data":"name"},
			{"data":"age"},
			{"data":"sex"},
		],
		"oLanguage"			:{
			"oProcessing":"正在加载数据……",
			"sLengthMenu":"每页显示_MENU_条记录",
			"sZeroRecords":"抱歉，没有找到",
			"sInfo":"从_START_到_END_/共_TOTAL_条记录",
			"sInfoEmpty":"没有数据",
			"sInfoFiltered":"(从_MAX_条数据中检索)",
			"sZeroRecords":"没有检索到数据",
			"sSearch":"模糊查询",
			"oPaginate":{
				"sFirst":"首页",
				"sPrevious":"前一页",
				"sNext":"后一页",
				"sLast":"尾页"
			}
		}
	});
});