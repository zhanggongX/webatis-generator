layui.config({
    version: false
    ,debug: false
}).use(['element', 'table', 'layer', 'jquery', 'form'], function(){
    var element = layui.element,
        table = layui.table,
        layer = layui.layer,
        $ = layui.jquery,
        form = layui.form;

    table.render({
        id: 'dbList'
        ,elem: '#dbList'
        ,height: 332
        ,url: _contextPath + '/dbs/listByPager'
        ,page: true
        ,cols: [[
            {checkbox: true}
            ,{field: 'name', title: '数据库名'}
            ,{field: 'url', title: '数据库链接'}
            ,{field: 'port', title: '数据库端口'}
            ,{field: 'type', title: '数据库类型'}
            ,{field: 'updatedAt', title: '最后更新时间', sort: true}
            ,{fixed: 'right', width:150, align:'center', toolbar: '#dbListTool'}
        ]]
    });

    $("#submitBtn").click(function () {
        var vdbsName = $("#dbsName").val();
        table.reload('dbList', {
            where: {
                dbsName: vdbsName
            }
            ,page: {
                curr: 1
            }
        });
    });

    //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
    table.on('tool(dbList)', function(obj){
        var data = obj.data; //获得当前行数据
        var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        var tr = obj.tr; //获得当前行 tr 的DOM对象

        if(layEvent === 'del'){ //删除
            layer.confirm('真的删除行么', function(index){
                $.ajax({
                    type: 'GET',
                    url: _contextPath+"/dbs/deleted/"+obj.data.id,
                    success: function (result) {
                        if(result.code == 200){
                            layer.msg("删除成功");
                        }else {
                            layer.msg(result.message);
                        }
                    },
                    dataType: "json"
                });
                //删除对应行（tr）的DOM结构，并更新缓存
                obj.del();
                layer.close(index);
            });
        } else if(layEvent === 'edit'){ //编辑
            //do something
            /*layer.open({
                type: 1,
                content: '传入任意的文本或html' //这里content是一个普通的String
            });*/
            layer.open({
                type: 2,
                content: 'http://sentsin.com' //这里content是一个URL，如果你不想让iframe出现滚动条，你还可以content: ['http://sentsin.com', 'no']
            });
            //同步更新缓存对应的值
            /*obj.update({
                username: '123'
                ,title: 'xxx'
            });*/
        }
    });



});