$(function(){

    // 开始写 jQuery 代码...
    //$(selector).action()
    $.ajax({
        url:'',
        type:'get',
        dataType:'json',
        success:function(data){
            //方法中传入的参数data为后台获取的数据
            for(i in data.data) //data.data指的是数组，数组里是对象，i为数组的索引
            {
                var tr;
                tr="<td>"+data.data[i].name+"</td>"
                    +"<td>"+data.data[i].id+"</td>"
                    +"<td>"+data.data[i].submit_time+"</td>"
                    +"<td>"+data.data[i].end_time+"</td>"
                    +"<td >"+"<a href='#'>删除</a>"+"&nbsp"+"&nbsp"+ "<a href='#'>下载</a>"+"&nbsp"+"&nbsp"+"<a href='#'>标记</a>"+"</td>"
                $(".table").append('<tr>'+tr+'</tr>')
            }
        }

    })

});