function changePage(){
    var pageData=[];
    var i;
    $("tr[name='row']").each(function(){
        for(i=0;i< $("#tableInfo").find("tr").length;i++){
            pageData[i]=$("tr[name='row']");
        }
    });
    var Count = pageData.length;//记录条数
    var PageSize=3;//设置每页示数目
    var PageCount=Math.ceil(Count/PageSize);//计算总页数
    var currentPage =1;//当前页，默认为1。
    //造个简单的分页按钮
    for(i=1;i<=PageCount;i++){
        var pageN='<a selectPage="'+i+'" >第'+i+'页</a>';
        $("#numPage").append(pageN);
    }
    //显示默认页（第一页）
    for(i=(currentPage-1)*PageSize;i<PageSize*currentPage;i++){
        $("#tableInfo").append(pageData[i]);
    }

    //显示选择页的内容
    $("a").click(function(){
        var selectPage=$(this).attr("selectPage");
        $("#tableInfo").html("");
        for(i=(selectPage-1)*PageSize;i<PageSize*selectPage;i++){
            $("#tableInfo").append(pageData[i]);
        }
    });


    $.ajax({
        url:"",
        type:"post",
        data:{
            //表格的数据
        },
        datatype:"json",
        success:function (data) {
            location.reload();
            alert("传输成功！")
        },
        error:function (msg) {
            alert("传输失败！")
        }
    })


}



function deleteLogic() {
    var checkNum = $("input[name='ID']:checked").length;
    var id=$(":input[type='hidden'][id=ASSID]").val();
    if(checkNum==0){
        alert("至少选择一项");
        return;
    }
    if(confirm("确定要删除吗？")){
        var checkList = new Array();
        $("input[name='ID']:checked").each(function () {
            checkList.push($(this).val())
        });
    }
    $.ajax({
        url:"/deleteAll/"+id,
        type:"post",
        data:{
            checkList:checkList.toString()
        },
        datatype:"json",
        success:function (data) {
            location.reload();
            alert("删除成功！")
        },
        error:function (msg) {
            alert("删除失败！")
        }
    })
}



$(function(){
   // changePage();

    $(".change").dblclick(function(event){

        if($(this).children("input").length > 0)
            return false;
        //td中已经有了input,则不需要响应点击事件
        var tdObj = $(this);
        var preText = tdObj.html();
        //得到当前文本内容
        var inputObj = $("<input type='text' />");
        //创建一个文本框元素
        tdObj.html("");
        //清空td中的所有元素

        inputObj.width(tdObj.width())
            //设置文本框样式与td相同
            .height(tdObj.height())
            .css({border:"0px"})
            .val(preText)
            .appendTo(tdObj)
            //把创建的文本框插入到tdObj子节点的最后
            .trigger("select");
            //选中
        inputObj.keyup(function(event){
            if(13 == event.which)
            //用户按下回车
            {
                var text = $(this).val();
                tdObj.html(text);
            }
            else if(27 == event.which)
            //ESC键
            {
                tdObj.html(preText);
            }
        });
        //已进入编辑状态后，不再处理click事件
        inputObj.click(function(){
            return false;
        });
    });

});


