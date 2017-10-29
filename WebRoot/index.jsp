<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/echarts.js"></script>
	<script type="text/javascript" src="js/dark.js"></script>
	<script type="text/javascript" src="js/shine.js"></script>
	<script type="text/javascript" src="js/macarons.js"></script>
		<script type="text/javascript" src="js/jquery-2.1.0.js"></script>
  <script type="text/javascript">
  $(document).ready(function(){

	  // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('main'),'macarons');
      // 指定图表的配置项和数据
      var option = {
          title: {
              text: '入门示例'
          },
          tooltip: {},
          legend: {
              data:['折线图']
          },
          xAxis: {
        	  name: 'x轴',
              data: [1,2,3,4,5,6]
          },
          yAxis: {
        	  name: 'y轴'
          },
          series: [{
              name: '折线图',
              type: 'line',
              data: [5, 20, 36, 10, 10, 20]
          }]
      };
	//scatter点
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
	  
	//------------------------
	      var myChart2 = echarts.init(document.getElementById('main2'),'dark');
	function randomData() {
    now = new Date(+now + oneDay);
    value = value + Math.random() * 21 - 10;
    return {
        name: now.toString(),
        value: [
            [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
            Math.round(value)
        ]
    }
}

var data = [];
var now = +new Date(1997, 9, 3);
var oneDay = 24 * 3600 * 1000;
var value = Math.random() * 1000;
for (var i = 0; i < 1000; i++) {
    data.push(randomData());
}

var option2 = {
    title: {
        text: '动态数据 + 时间坐标轴'
    },
    tooltip: {
        trigger: 'axis',
        formatter: function (params) {
            params = params[0];
            var date = new Date(params.name);
            return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
        },
        axisPointer: {
            animation: false
        }
    },
    xAxis: {
        type: 'time',
        splitLine: {
            show: false
        }
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
            show: false
        }
    },
    series: [{
        name: '模拟数据',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data
    }]
};
myChart2.setOption(option2);
setInterval(function () {

    for (var i = 0; i < 5; i++) {
        data.shift();
        data.push(randomData());
    }

    myChart2.setOption({
        series: [{
            data: data
        }]
    });
}, 1000);
//----------------------3异步加载

        var myChart3 = echarts.init(document.getElementById('main3'),'shine');
         // 显示标题，图例和空的坐标轴
         myChart3.setOption({
             title: {
                 text: '异步数据加载示例'
             },
             tooltip: {},
             legend: {
                 data:['动态折线异步加载']
             },
             xAxis: {
                 data: []
             },
             yAxis: {},
             series: [{
                 name: '动态折线异步加载',
                 type: 'line',
                 data: []
             }]
         });
         
        myChart3.showLoading();    //数据加载完之前先显示一段简单的loading动画
         
         var xs=[];    //（实际用来盛放X轴坐标值）
         var ys=[];    //（实际用来盛放Y坐标值）
         
        
    f=function(x,chart){
        	 $.ajax({
                 type : "post",
                 async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
                 url : "S1",    //请求发送到TestServlet处
                 data : {"n":x},
                 dataType : "json",        //返回数据形式为json
                 success : function(result) {
                     //请求成功时执行该函数内容，result即为服务器返回的json对象
                     if (result) {
                            for(var i=0;i<result.length;i++){       
                               xs.push(result[i].x);    //挨个取出类别并填入类别数组
                             }
                            for(var i=0;i<result.length;i++){       
                                ys.push(result[i].y);    //挨个取出销量并填入销量数组
                              }
                          chart.hideLoading();    //隐藏加载动画
                            chart.setOption({        //加载数据图表
                                xAxis: {
                                    data: xs
                                },
                                series: [{
                                    // 根据名字对应到相应的系列
                                    name: '动态折线异步加载',
                                    data: ys
                                }]
                            });
                            
                     }
                 
                },
                 error : function(errorMsg) {
                     //请求失败时执行该函数
                 alert("图表请求数据失败!");
               //  Chart.hideLoading();
                 }
            }) 
        	 
         }
		f(10,myChart3);
		//f(10,myChart);
	      // var c2 = setInterval("f(1,myChart)", 800);
	  
	   var c = setInterval(function(){
		   f(1,myChart3);
		   //f(1,myChart);
		   
	   }, 1000);
//---------------------------
  });

    </script>
  </head>
  
  <body>
    <!-- 第一个 DOM -->
    <div id="main" style="width: 600px;height:400px;"></div>
    <!-- 生成随机数据的 DOM -->
      <div id="main2" style="width: 600px;height:400px;"></div>  
      <!-- 异步加载数据的 DOM -->
      <div id="main3" style="width: 600px;height:400px;position: absolute;left: 700px;
      top: 0px"></div>
  </body>
</html>
