$(function(){
    // 开始写 jQuery 代码...Json.stringify()
    //$(selector).action()
    //取值 放到sessionStorage里 然后传输
    $(".bottom").click(function(){
        var title=$("#utTitle").val();
        //var requirement=$(".rte1").val();
        var startt=$("#stime").val();
        var endt=$("#etime").val();
        var num=Math.floor((Math.random()*100000)+100000);
        localStorage.setItem("title",title);
        //localStorage.setItem("requirement",requirement);
        localStorage.setItem("startt",startt);
        localStorage.setItem("endt",endt);
        localStorage.setItem("num",num);
        //window.location.href="../2/作业查看.html.html";
        window.open ("../2/作业查看.html.html", "newwindow", "height=100, width=400, toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no")

    });



    //alert(1)
});

