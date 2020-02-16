$(function(){
    // 开始写 jQuery 代码...
    //$(selector).action()
    $.check=function(){
        var id=$("#id").text();
        var pwd=$("#pwd").text();
        if(id == "inj" &&  pwd== "123") {
            alert("shabi")
            return true;
        }else {
            alert("登录名或密码错误！");
            return false;
        }
    }

        if(!$.check()){
            $("#submit").click(function(){});
        }/**/



});

