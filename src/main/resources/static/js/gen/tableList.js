layui.config({
    version: false
    ,debug: false
}).use(['element', 'table', 'layer', 'jquery', 'form'], function(){
    var element = layui.element,
    table = layui.table,
    layer = layui.layer,
    $ = layui.jquery,
    form = layui.form;

    //加载数据库
    $.ajax({
        type: 'POST',
        url: _contextPath+"/dbs/listAll",
        contentType: "application/json;charset=utf-8",
        success: function (result) {
            if(result.code == 200){
                var vhtml = '<option value=""></option>';
                var result = JSON.parse(JSON.stringify(result.webatisDatabases));
                if(result.length >0 ){
                    for(var i=0; i<result.length; i++){
                        vhtml += '<option value="' + result[i].id + '">' + result[i].name +'</option>';
                    }
                }
                console.log(vhtml);
                $("#dbsName").html("");
                $("#dbsName").append(vhtml);
                form.render('select', 'dbsName');
            }else {
                layer.msg(result.message);
            }
        },
        dataType: "json"
    });

    table.render({
        id: 'tableList'
        ,elem: '#tableList'
        ,height: 332
        ,url: _contextPath + '/gen/listTableByPager'
        ,page: true
        ,cols: [[
            {checkbox: true}
            ,{field: 'tableName', title: '表名'}
            ,{field: 'engine', title: 'engine'}
            ,{field: 'tableComment', title: '备注'}
            ,{field: 'createTime', title: '创建时间', sort: true}
        ]]
    });

    $("#submitBtn").click(function () {
        var vTabelName = $("#tableName").val();
        table.reload('tableList', {
            where: {
                tableName: vTabelName
            }
            ,page: {
                curr: 1
            }
        });
    });

    $("#genCode").click(function () {
        var vurl = 'genCode';
        var values = new Array();
        var value = null;
        var checkStatus = table.checkStatus('tableList');
        for(var i = 0; i<checkStatus.data.length; i++){
            value = new Object();
            value.name = "tableName";
            value.value = checkStatus.data[i].tableName;
            values.push(value);
        }
        if(values.length <= 0){
            layer.msg("请至少选择一个表");
            return;
        }
        downloadFileByForm(vurl, values);
    });

    /**
     * ajax请求方式无法下载文件，所以模拟form提交
     * <p>
     * @author: 张弓
     * @date:
     * @version: 1.0.0
     *
     * @param params 所有的参数
     */
    function downloadFileByForm(url, params) {
        var form = $("<form></form>").attr("action", url).attr("method", "get");
        var vhtml = '';
        if(params && params.length > 0){
            for(var i=0; i<params.length; i++){
                form.append($("<input></input>").attr("type", "hidden").attr("name", params[i].name).attr("value", params[i].value));
            }
        }
        form.appendTo('body').submit().remove();
        console.log(form);
    }
});