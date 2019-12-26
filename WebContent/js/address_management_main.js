require.config({
	 paths: {
			 "jquery": "jquery.min",
			 "handlebar": "handlebars-v4.0.11",
			 "ChineseDistricts": "distpicker.data",
			 "distpicker": "distpicker",
			 },
	 shim:  {
		 	'distpicker': ['jquery','ChineseDistricts'],
	 		}
 });

require(['jquery','handlebar','common','address_management'], 
		function (jquery,handlebar,common, address_management){
	$(function(){
		//加载登录用户信息
		common.getUserInfo();
		//用户登出
		common.loginOut();
		
		address_management.ready();
	});	
});