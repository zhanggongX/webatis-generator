layui.config({
    version: false
    ,debug: false
}).use(['element', 'table', 'layer', 'jquery', 'form'], function(){
    var element = layui.element,
        table = layui.table,
        layer = layui.layer,
        $ = layui.jquery,
        form = layui.form;

    form.on('submit(formSubmit)', function(data){
        console.log(JSON.stringify(data.field));
        var opType = $("#opType").val();
        $.ajax({
            type: 'POST',
            url: _contextPath+"/dbs/"+opType,
            contentType: "application/json;charset=utf-8",
            data: JSON.stringify(data.field),
            success: function (result) {
                console.log(result.code)
                if(result.code == 200){
                    layer.msg("保存/更新成功");
                }else {
                    layer.msg(result.message);
                }
            },
            dataType: "json"
        });
        return false;
    });
});