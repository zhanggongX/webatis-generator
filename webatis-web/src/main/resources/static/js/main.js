layui.config({
    version: false
    , debug: false
}).use(['element', 'layer', 'jquery'], function () {
    var element = layui.element,
        $ = layui.jquery;

    //获取用户名
    $.ajax({
        type: 'GET',
        url: _contextPath + "/userName",
        success: function (result) {
            if (result.code == 200) {
                var userHtml = $("#user").html();
                $("#user").html(userHtml + result.userName);
            } else {
                layer.msg(result.message);
            }
        },
        dataType: "json"
    });
});