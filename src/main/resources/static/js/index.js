$(document).ready(function() {
		getEnoList()
		$("#_submit").click(function() {
			var formData = new FormData();
			formData.append("file", $('#file')[0].files[0])
			$.ajax({
				url : "/sdp/txtprocess/txtToDb",
				type : "POST",
				/**
				 *必须false才会自动加上正确的Content-Type
				 */
				data : formData,
				contentType : false,
				/**
				 * 必须false才会避开jQuery对 formdata 的默认处理
				 * XMLHttpRequest会对 formdata 进行正确的处理
				 */
				processData : false,
				success : function(res) {
					console.log(res)
					var status = res.status;
					if (status == 200) {
						$("#msg").html("上传成功！");
						getEnoList()
					} else if (status == 500) {
						$("#msg").html(res.msg);
					}

				}
			})
		})

		$(".group").click(function() {

			$("#" + this.name).toggle()
			var icon = $("." + this.name).html()
			icon = icon == "+" ? "-" : "+";
			$("." + this.name).html(icon)
		})

		$("#generate").click(function(){
            var checkedList = $("input[name='selectEno']")
            var enos = ""
            for(var i = 0; i < checkedList.length;i++){
                if(checkedList[i].checked)
                	enos += checkedList[i].value + ","
            }
            console.log(enos)
            $.ajax({
                url : "/sdp/txtprocess/getCheckedList",
				type : "POST",
				data:{
					enos:enos
				},
				success : function(res) {
					var status = res.status;
					if (status == 200) {
                        var data = res.data
                        console.log(data)
                        //清空canvas_container
                        $("#canvas_container").html("")
                        //生成Canvas
                        for(var i = 0 ; i < data.length;i++){
                            generateCanvas(data[i])
                        }
						
					} else if (status == 500) {
						
					}
                }
            })
        })
        
        /**
         * 获取设备号列表
         */
		function getEnoList(){
			$("#open_close_list").html("")
			$.ajax({
				url : "/sdp/txtprocess/getEnoList",
				type : "POST",
				success : function(res) {
					var status = res.status;
					if (status == 200) {
						var ul = $("#open_close_list")
						var list = res.data
						for (var i = 0; i < list.length; i++) {
							ul.append("<li class='list-group-item'><input type='checkbox' name='selectEno' value='"+ list[i] +"'> "+ list[i] +" </li>")		
						}

					} else if (status == 500) {
						
					}
				}
			}) 
		}
		
		function generateCanvas(list){
			$("#canvas_container").append("设备号：" + list[0].eno)
			$("#canvas_container").append("<span style='float:right' class='" + list[0].eno+ "'> </span>")
            $("#canvas_container").append("<canvas width='1200'  height='200' id='" + list[0].eno+ "'></canvas>")
            //绘图
            initCanvas(list)
		}
		
})