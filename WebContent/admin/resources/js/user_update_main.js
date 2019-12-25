require.config({
    paths: {
        "jquery": "/mall/admin/adminlte/bower_components/jquery/dist/jquery.min",
        "bootstrap": "/mall/admin/adminlte/bower_components/bootstrap/dist/js/bootstrap.min",
        "datatables.net": "/mall/admin/adminlte/bower_components/datatables.net/js/jquery.dataTables.min",
        "bsdataTables": "/mall/admin/adminlte/bower_components/datatables.net-bs/js/dataTables.bootstrap.min",
        "adminlte": "/mall/admin/adminlte/dist/js/adminlte.min",
        //空缺：没文件（电商平台后台交互js实现开发讲解倒数第三个2:53）
        "simple-module":"/mall/admin/resources/simditor/assets/scripts/module",
	     "uploader":"/mall/admin/resources/simditor/assets/scripts/uploader",
	     "hotkeys":"/mall/admin/resources/simditor/assets/scripts/hotkeys",
	     "simditor":"/mall/admin/resources/simditor/assets/scripts/simditor",
	     "webuploader":"/mall/admin/resources/webuploader/dist/webuploader"
    },
    shim: {
        'bootstrap': ['jquery'],
        'bsdataTables': ['bootstrap'],
        'fnReloadAjax': ['jquery', 'datatables.net'],
        'adminlte': ['bootstrap']
    }
});

require(['jquery', 'bootstrap', 'datatables.net', 'bsdataTable',
        'adminlte', 'simple-module', 'uploader', 'hotkeys', 'simditor',
        'webuploader', 'common', 'user_update', 'fnReloadAjax'],
    function (jquery, bootstrap, datatables_net, bsdataTable,
              adminlte, simple_module, uploader, hotkeys, simditor,
              webuploader, common, user_update, fnReloadAjax) {
        $(function () {
            //用户校验
            common.userCalibration();
            //1.获取用户参数
            user_update.getUserInfo();
            //2，保存用户信息
            user_update.saveUser();
        });
    });
