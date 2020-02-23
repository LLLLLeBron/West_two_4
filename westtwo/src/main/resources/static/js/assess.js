function changePage(){
    var pageData=[];
    var i;
    $("tr[name='row']").html().each(function(){
        for(i=0;i< $("#tableInfo").find("tr").length;i++){
            pageData[i]='$("tr[name=\'row\']")';
            console.log($("tr[name='row']"));
        }
    });
   /*for(i=1;i<$("#tableInfo").find("tr").length;i++){
    var data=
        '<tr name="row" th:each="homework:${homeworks}">'+
           '<td th:text="${homework.getNumber()}"></td>'+
           '<td th:text="${homework.getName()}"></td>'+
           '<td th:text="${#dates.format(homework.getTime(),'yyyy-MM-dd HH:mm:ss')}"></td>'+
           '<div th:switch="${homework.getFlag()}" >
           <td th:case="0" th:text="${status0}"> </td>
           <td th:case="1" th:text="${status1}"> </td>
           <td th:case="2" th:text="${status2}"> </td>
           </div>'
           '<td>
           <a class="btn btn-sm btn-primary green" th:href="@{/download/}+${assess.getId()}+'/'+${homework.getId()}">下载</a>
           <a class="btn btn-sm btn-primary red" th:href="@{/homeworkDelete/}+${assess.getId()}+'/'+${homework.getId()}">删除</a>
           <a class="btn btn-sm btn-primary white" th:href="@{/homeworkPass/}+${assess.getId()}+'/'+${homework.getId()}">通过</a>
           <a class="btn btn-sm btn-primary black" th:href="@{/homeworkCull/}+${assess.getId()}+'/'+${homework.getId()}">淘汰</a>
           </td>'
           '<td><input type="checkbox" name = "ID" id="ID" class = "put" th:value = "${homework.getId()}"></td>'
           '</tr>'
       pageData.push(data);
    }*/
    var Count = pageData.length;//记录条数
    var PageSize=2;//设置每页示数目
    var PageCount=Math.ceil(Count/PageSize);//计算总页数
    var currentPage =1;//当前页，默认为1。
    var end='</tbody>';//本页结束
    var head=
        '<thead><tr class="reItemM">'+
        '<td>学号</td>'+
        '<td>姓名</td>'+
        '<td>提交时间</td>'+
        '<td>审核状态</td>'+
        '<td>操作</td>'+
        '<td><a class="btn btn-sm btn-primary red" onclick="deleteLogic();">批量删除</a></td> <a class="btn btn-sm btn-primary green" onclick="downloadLogic();">批量下载</a>' +
    '</tr></thead><tbody id="tableInfo">';//表头

    //分页按钮
    for(i=1;i<=PageCount;i++){
        var pageN='<a id="al" href="#" selectPage="'+i+'" >第'+i+'页</a>';
        $("#numPage").append(pageN);
    }
    //显示第一页
    $('#table').empty().append(head);
    for(i=(currentPage-1)*PageSize;i<PageSize*currentPage;i++){
        $("#table").append(pageData[i]);
    }
    $("#table").append(end);
    //显示选择页的内容
    $("#al").click(function(){
        var selectPage=$(this).attr("selectPage");
        $("#table").html("");
        $('#table').append(head);
        for(i=(selectPage-1)*PageSize;i<PageSize*selectPage;i++){
            $("#table").append(pageData[i]);
        }
        $("#table").append(end);
    });


    /*$.ajax({
        url:"",
        type:"post",
        data:{
            //表格的数据
        },
        datatype:"json"

    })*/


}

function downloadLogic() {
    var checkNum = $("input[name='ID']:checked").length;
    var id=$(":input[type='hidden'][id=ASSID]").val();

    if(checkNum==0){
        alert("至少选择一项");
        return;
    }

    if(confirm("确定要下载吗？")){
        var checkList = new Array();
        $("input[name='ID']:checked").each(function () {
            checkList.push($(this).val())
        });
    }

    $.ajax({
        url:"/downloadAll/"+id,
        type:"post",
        data:{
            checkList:checkList.toString()
        },
        datatype:"json",
        success:function (data) {
            location.reload();
            alert("下载成功！")
        },
        error:function (msg) {
            alert("下载失败！")
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
    changePage();

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


