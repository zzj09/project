$(function() {
    showtime()
    $.ajax({
        url: "/getUser",
        type: "get",
        ContentType:"application/json",
        dataType: "json",
        success: function (data) {
            if (data.status == 200) {
                var html = '';
                html = ' <span id="username">'+data.user.username+'：欢迎您   <a style="color: white" href="/loginout">注销</a></span>';
                $("#userinfo").html(html)
            }
        },
        error: function () {
            // alert("服务器发生错误");
        }
    });

});


function showtime() {
    nowtime = new Date();
    year = nowtime.getFullYear();
    month = nowtime.getMonth()+1;
    date = nowtime.getDate();
    var hour = nowtime.getHours();
    var time = '';
    time = '<span>'+ year + "年" + month + "月" + date + "日  " + nowtime.toLocaleTimeString() + '</span>';
    $("#time").html(time);
    //问候语
    var greet = '';
    if(hour>=6 && hour<12){
        greet = '<span>早上好，新的一天又开始啦！</span>';
        $("#greetings").html(greet);
    }else if (hour>=12 && hour<13){
        greet = '<span>中午好！</span>';
        $("#greetings").html(greet);
    }else if (hour>=13 && hour<18){
        greet = '<span>下午好！</span>';
        $("#greetings").html(greet);
    }else if (hour>=18 && hour<24){
        greet = '<span>晚上好，幸苦的一天结束啦！</span>';
        $("#greetings").html(greet);
    }else if (hour>=0 && hour<6){
        greet = '<span>凌晨了，该睡觉了！</span>';
        $("#greetings").html(greet);
    }
}
setInterval("showtime()",1000);



