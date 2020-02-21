var tableInfo = document.getElementById("tableInfo");  // 获取table中的内容
var totalRow = $("tableInfo").find("tr").length;  // 获取table行数
var pagesize = 5;   // 每页5条
var totalPage = Math.ceil(totalRow/pagesize);  // 计算出总页数=总条数/每页条数
var currentPage;   // 当前页
var startRow;
var lastRow;

// 定义一个换页的函数
function pagination(i){
    currentPage = i;
    startRow = (currentPage-1)*pagesize;  //每页显示第一条数据的行数
    lastRow = currentPage*pagesize;  // 每页显示的最后一条数据的行数
    document.getElementById("numPage").innerHTML="第"+currentPage+"页";

    if(lastRow>totalRow){
        lastRow=totalRow;// 如果最后一页的最后一条数据显示的行数大于总条数，就让最后一条的行数等于总条数
    }
    //将数据遍历出来
    for(var i=0; i<totalRow; i++){
        var hrow = tableInfo.rows[i];
        //获取每一行的数据
        if( i>=startRow && i<lastRow ){
            hrow.style.display="table-row";   // 将循环出来的每一行信息作为一个tr显示到页面
        }else{
            hrow.style.display="none";
        }
    }
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


function firstPage(){
    var i = 1;
    pagination(i);
}
function prevPage(){
    var i= currentPage-1;
    if(i<1) i=currentPage;
    pagination(i);
}
function pnextPage(){
    var i= currentPage+1;
    if(i>totalPage) i= currentPage;
    pagination(i);
}
function lastPage(){
    var i = totalPage;
    pagination(i);
}


$(function(){
    firstPage();

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


