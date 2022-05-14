$(function() {
    var show_num = [];
    draw(show_num);

    $("#canvas").click(function(){
        draw(show_num);
    })

    $("#sub1").click(function(){
        var val = $("#check").val().toLowerCase();
        var num = show_num.join("");

        if(val==''){
            alert('请输入验证码！');
        }else if(val == num){
            $.ajax({
                url: "/login",
                type: "post",
                ContentType:"application/json",
                dataType: "json",
                //序列化
                data: $('#myform1').serialize(),
                success: function (data) {
                    //后台传回来的信息弹框
                    login_clear();
                    alert(data.msg);
                    if(data.status==200){
                        //登录成功跳转
                        location.href = "index.html";
                    }
                },
                error: function () {
                    alert("服务器发生错误");
                }
            });
            draw(show_num);
        }else{
            alert('验证码错误！请重新输入！');
            $("#check").val('');
            draw(show_num);
        }
    });

    //创建正则表达式对象,验证邮箱
    var emailPatt = /^[a-z\d]+(\.[a-z\d]+)*@([\da-z](-[\da-z])?)+(\.{1,2}[a-z]+)+$/;

    $("#sub2").click(function(){
        if ($("#username2").val() !=""){
            if ($("#password").val()!=""){
                if ($("#email").val()!=""){
                    if (!emailPatt.test($("#email").val())){
                        alert("邮箱格式不正确！")
                        $("#email").val("");
                    }else{
                        if ($("#password2_1").val()==$("#password2_2").val()){
                            $.ajax({
                                url: "/register",
                                type: "post",
                                ContentType:"application/json",
                                dataType: "json",
                                //序列化
                                data: $('#myform2').serialize(),
                                success: function (data) {
                                    //后台传回来的信息弹框
                                    register_clear();
                                    alert(data.msg);
                                    if(data.status==200){
                                        //注册成功跳转
                                        location.href = "login.html";
                                    }
                                },
                                error: function () {
                                    alert("服务器发生错误");
                                }
                            });
                        }else{
                            alert("两次密码不相符！请重新输入！")
                            register_clear();
                        }
                    }
                }else{
                    alert("邮箱为空！")
                    register_clear();
                }
            }else{
                alert("输入密码为空！")
                register_clear();
            }
        }else{
            alert("用户名为空！")
            register_clear();
        }
    });

    $("#sub_update").click(function(){
        location.href="update.html";
    })

    $("#sub_index").click(function () {
        location.href="index.html";
    })

    function register_clear() {
        $("#username2").val("");
        $("#password2_1").val("");
        $("#password2_2").val("");
        $("#email").val("");
    }
    function login_clear() {
        $("#username1").val("");
        $("#password1").val("");
        $("#check").val("");
    }
});

//生成并渲染出验证码图形
function draw(show_num) {
    var canvas_width=$('#canvas').width();
    var canvas_height=$('#canvas').height();
    var canvas = document.getElementById("canvas");//获取到canvas的对象
    //Canvas.getContext(contextID)
    // 参数 contextID 指定了您想要在画布上绘制的类型。当前唯一的合法值是 "2d"，它指定了二维绘图.
    var context = canvas.getContext("2d");//获取到canvas画图的环境
    canvas.width = canvas_width;
    canvas.height = canvas_height;
    var sCode = "a,b,c,d,e,f,g,h,i,j,k,m,n,p,q,r,s,t,u,v,w,x,y,z,A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
    //split() 方法用于把一个字符串分割成字符串数组。    参数为：stringObject.split(separator,howmany)
    //separator	必需。字符串或正则表达式，从该参数指定的地方分割 stringObject。
    var aCode = sCode.split(",");
    var aLength = aCode.length;//获取到数组的长度

    for (var i = 0; i < 4; i++) {  //这里的for循环可以控制验证码位数
        var j = Math.floor(Math.random() * aLength);//获取到随机的索引值
        var deg = Math.random() - 0.5; //产生一个随机弧度
        var txt = aCode[j];//得到随机的一个内容
        show_num[i] = txt.toLowerCase();
        var x = 10 + i * 20;//文字在canvas上的x坐标
        var y = 20 + Math.random() * 8;//文字在canvas上的y坐标
        context.font = "bold 23px 微软雅黑";

        context.translate(x, y);
        context.rotate(deg);

        context.fillStyle = randomColor();
        context.fillText(txt, 0, 0);

        context.rotate(-deg);
        context.translate(-x, -y);
    }
    for (var i = 0; i <= 5; i++) { //验证码上显示线条
        context.strokeStyle = randomColor();
        context.beginPath();
        context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
        context.stroke();
    }
    for (var i = 0; i <= 30; i++) { //验证码上显示小点
        context.strokeStyle = randomColor();
        context.beginPath();
        var x = Math.random() * canvas_width;
        var y = Math.random() * canvas_height;
        context.moveTo(x, y);
        context.lineTo(x + 1, y + 1);
        context.stroke();
    }
}

//得到随机的颜色值
function randomColor() {
    var r = Math.floor(Math.random() * 256);
    var g = Math.floor(Math.random() * 256);
    var b = Math.floor(Math.random() * 256);
    return "rgb(" + r + "," + g + "," + b + ")";
}

