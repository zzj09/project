window.onload=function(){
    $(function () {
        $('#sub').click(function () {
            if ($("#newpassword").val()==$("#newpassword2").val()) {
                if ($("#password").val()!=$("#newpassword").val()){
                    $.ajax({
                        url: "./update",
                        type: "post",
                        ContentType: "application/json",
                        dataType: "json",
                        data: $("#myform").serialize(),
                        success: function (data) {
                            alert(data.msg);
                            clear();
                            $("#user").val("");
                            if (data.status == 200) {
                                window.location.href = "/login.html";
                            }
                        },
                        error: function () {
                            alert("服务器发生错误");
                        }
                    });
                }else{
                    alert("旧密码不能与新密码相同！");
                    clear();
                }
            }else{
                alert("两次密码输入不相符！");
                clear();
            }
        });

        $("#sub_login").click(function(){
            location.href="login.html";
        })
    });

    function clear() {
        $("#password").val("");
        $("#newpassword").val("");
        $("#newpassword2").val("");
    }
}