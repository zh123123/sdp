// 1m = 10px  (unitV * 1000/interval)
var unitV = 10                               //单位速度 
var interval = 100	                         //刷新间隔 
var v_coefficient = interval/1000           //速度系数
var unit_distance = unitV * 1000/interval   //1m的距离

initCanvas = function (list) {
    var canvas
    var ctx                 //上下文
    

    console.log("init")
    canvas = document.getElementById(list[0].eno)
    ctx = canvas.getContext('2d');
    
    
    
    new Boat(list,ctx)
};

var Boat = function (list,ctx) {
    //绘制工具
    this.ctx = ctx || document.getElementById(list[0].eno).getContext('2d');
    //图片路径
    this.src = '../static/image/boat.png';
    //画布的大小
    this.canvasWidth = this.ctx.canvas.width;
    this.canvasHeight = this.ctx.canvas.height;
    //数据列表
    this.list = list
    
    //画布所绘制的最大距离段数(一段即10m)
    this.number = Math.floor(this.canvasWidth / 100)
    
    //表示绘制的第index个最大段数
    this.distanceIndex = 0
    
    //起始距离 = this.number * this.distanceIndex
    this.distance = this.number * this.distanceIndex
    
    //速度
    this.speed = list[0].speed
    console.log(this.speed)
    
    //时间
    this.time = list[0].time
    
    this.index = 0
    //记录当前list的下标
    this.listIndex = 0
    //初始化方法
    this.init();
};
Boat.prototype.init = function(){
    var that = this
    //加载图片
    this.loadImage(function(image){
         //船的大小
        that.imageWidth = image.width;
        that.imageHeight = image.height;
        console.log(that.imageWidth)
        console.log(that.imageHeight)
        
         //相对原点
        that.x0 = that.distance;
        that.y0 = that.canvasHeight / 2 - that.imageHeight / 40
        
        //船相对原点的位置
        that.x = that.x0
        that.y = that.y0
        
        //2.默认绘制在原点
        that.ctx.drawImage(image,
            0,0,
            that.imageWidth,that.imageHeight,
            that.x0,that.y0,
            that.imageWidth/20,that.imageHeight/20);
        
        setInterval(function(){
            that.drawImage(image)
        },interval)
    })
}
//加载图片
Boat.prototype.loadImage = function (callback) {
    var image = new Image();
    //this.image = image
    image.onload = function () {
        callback && callback(image);
    };
    image.src = this.src;
};

/*绘制图片*/
Boat.prototype.drawImage = function (image) {
    this.index++
    if(this.index >= (1000/interval)){
        this.index = 0
        this.listIndex ++
    }
    this.speed = this.list[this.listIndex].speed * unitV
    this.time = this.list[this.listIndex].time
    document.getElementsByClassName(this.list[0].eno)[0].innerHTML = "速度:" + this.list[this.listIndex].speed
    																+ "  心率:" + this.list[this.listIndex].heartRate
    																+ "  时间:" + formatDate(this.list[this.listIndex].time)

    
    //清除画布
    this.ctx.clearRect(0,0,this.canvasWidth,this.canvasHeight);
    this.x = this.x + this.speed * v_coefficient
    
    this.ctx.drawImage(image,
        0,0,
        this.imageWidth,this.imageHeight,
        this.x -  this.x0,this.y,
        this.imageWidth/20,this.imageHeight/20);
        
    
        
        
//    this.ctx.strokeText("当前速度：" + this.speed,
//                           this.x0,this.y0 - this.imageHeight / 40 - 10) 
    
};

var changeInterval = function( num ){
    interval /= num
}


function formatDate(time,format='YY-MM-DD hh:mm:ss'){
    var date = new Date(time);

    var year = date.getFullYear(),
        month = date.getMonth()+1,//月份是从0开始的
        day = date.getDate(),
        hour = date.getHours(),
        min = date.getMinutes(),
        sec = date.getSeconds();
    var preArr = Array.apply(null,Array(10)).map(function(elem, index) {
        return '0'+index;
    });////开个长度为10的数组 格式为 00 01 02 03

    var newTime = format.replace(/YY/g,year)
                        .replace(/MM/g,preArr[month]||month)
                        .replace(/DD/g,preArr[day]||day)
                        .replace(/hh/g,preArr[hour]||hour)
                        .replace(/mm/g,preArr[min]||min)
                        .replace(/ss/g,preArr[sec]||sec);

    return newTime;         
}
