$(function(){

    // 开始写 jQuery 代码...
    //$(selector).action()
    settable();

});
function settable(){
    var tit=JSON.parse(localStorage.getItem("title"));
    //var require=JSON.parse(localStorage.getItem("requirement"));
    var start=JSON.parse(localStorage.getItem("startt"));
    var end=JSON.parse(localStorage.getItem("endt")) ;
    var numb=JSON.parse(localStorage.getItem("num") );

    var html="";
    html+= "<tr class='myList'>"
    html+="<td style=\"width:120px;\" >"+numb+"</td>"
    html+="<td style=\"width:120px;\">"+tit+"</td>"
    //html+="<td>"+require+"</td>"
    html+="<td style=\"width:120px;\">"+start+"</td>"
    html+="<td style=\"width:120px;\">"+end+"</td>"
    html+="<td style=\"width:120px;\">"+"<a>删除</a>"+"&nbsp"+"&nbsp"+ "<a>修改</a>"+"</td>"
    html+="</tr>"
    $(".table").html(html);

    $(".myList").wrap("<table id='myList'></table>")
    //alert(html)
    //怎么不让现在的值覆盖原来的，并且自动调用
    //分页功能 删除修改功能
    //提交功能
    function addLine(){
        var node= "<tr>"
        +"<td style=\"width:120px;\" >"+numb+"</td>"
        +"<td style=\"width:120px;\">"+tit+"</td>"
        //html+="<td>"+require+"</td>"
        +"<td style=\"width:120px;\">"+start+"</td>"
        +"<td style=\"width:120px;\">"+end+"</td>"
        +"<td style=\"width:120px;\">"+"<a>删除</a>"+"&nbsp"+"&nbsp"+ "<a>修改</a>"+"</td>"
        +"</tr>";

        $("#myList").append(node);
    }
    addLine();


}