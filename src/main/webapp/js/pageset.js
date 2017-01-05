	/**
	* min最小值固定1 max总页数
	* cur当前页 number直接展示几页
	*/
	function getPageData(min,max,cur,number){
		//分页最小值
		var set=(number-1)/2;
		var result=[];
		//第一种情况 中间
		if(cur-set>=min&&cur+set<=max){
			for(var i=0;i<number;i++){result[i]=cur+i-set;}
		}
		//第二种  小于min
		if(cur-set<min){
			var offset=cur-min;  
			for(var i=0;i<number;i++){result[i]=cur+i-offset;}
		}
		//第三种 大于max
		if(cur+set>max){
			var offset=max-cur;  
			for(var i=0;i<number;i++){result[i]=cur+i-(2*set)+offset;}
		}
		return result;
	}
	 
	function call(functionName,param){ 
		eval("this."+functionName+"("+param+")"); 
	} 
	
	//跳页
    function jumpPageMethod(methodName,total_page,page,phtmlId){
    	var jumpPageInput=$("#"+phtmlId+"Input").val();
    	var jumpPageNum=parseInt(jumpPageInput);
    	if(isNaN(jumpPageNum)||jumpPageNum>total_page||jumpPageNum==page){
    		$("#"+phtmlId+"Input").val(page);
    	}else{
    		call(methodName,jumpPageNum);
    	}
    }
	
	/***
	 * 设置分页html   
	 * @param total_page
	 */
	function setPageHtml(total_count,phtmlId,methodName,curr_page,page_size){
		if(total_count==0){
			layer.msg("查询无结果",{icon:5});
		}
		var total_page = (total_count % page_size == 0 ? total_count / page_size
				   : Math.ceil(total_count / page_size));
		var page;
		page=curr_page;
		$("#"+phtmlId).html("");
		var previous=1;
		if(page>1){
			previous=page-1;
		}
		var next=total_page;
		if(page<total_page){
			next=page+1;
		}
		var html2='<div class="box-footer clearfix"><ul class="pagination pagination-sm no-margin">'+
			'<li id="'+phtmlId+'Previous" onclick="'+methodName+'('+previous+')"><a href="javascript:;" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>'+
			'<li id="'+phtmlId+'Next" onclick="'+methodName+'('+next+')"><a href="javascript:;" aria-label="Next"><span aria-hidden="true">&raquo;</span></a></li>'+
			'<li>&nbsp;&nbsp;<label style="margin-top:5px;">共'+total_count+'条，每页'+page_size+'条</label><div style="width:80px;float:right;margin-left:5px;" class="input-group input-group-sm"><input onchange="jumpPageMethod(\''+methodName+'\','+total_page+','+page+',\''+phtmlId+'\')" id="'+phtmlId+'Input" class="form-control" type="text"><span class="input-group-btn">'+
			'<button class="btn btn-info btn-flat" type="button">Go!</button></span></div></li></ul></div>';
		$("#"+phtmlId).html(html2);
		$("#"+phtmlId+"Input").val(page);
		$("."+phtmlId+"class").remove();
		var html;
		if(total_page<=5){
			//总页数小于等于5   有多少展示多少
			for(var p=0;p<total_page;p++){
				if((p+1)==page){html='<li class="'+phtmlId+'class"><a style="background-color:#3585ec;color:white;" onclick="'+methodName+'('+(p+1)+')" href="javascript:;">'+(p+1)+'</a></li>';}
				else{html='<li class="'+phtmlId+'class"><a onclick="'+methodName+'('+(p+1)+')" href="javascript:;">'+(p+1)+'</a></li>';}
				$("#"+phtmlId+"Next").before(html);
			}
		}else{
			//总页数大于5  显示离当前页最近的4个以及 第一个与最后一个
			var number=5;
			var result=getPageData(1, total_page, page, number);
			if(result[0]!=1){
				//展示第一页
				html='<li class="'+phtmlId+'class"><a onclick="'+methodName+'(1)" href="javascript:;">1</a></li>';$("#"+phtmlId+"Next").before(html);
			}
			if(result[0]-1>1){
				html='<li class="'+phtmlId+'class"><a href="javascript:;">...</a></li>';$("#"+phtmlId+"Next").before(html);
			}
			for(var p=0;p<number;p++){
				if(result[p]==page){html='<li class="'+phtmlId+'class"><a style="background-color:#3585ec;color:white;" onclick="'+methodName+'('+result[p]+')" href="javascript:;">'+result[p]+'</a></li>';}
				else{html='<li class="'+phtmlId+'class"><a onclick="'+methodName+'('+result[p]+')" href="javascript:;">'+result[p]+'</a></li>';}
				$("#"+phtmlId+"Next").before(html);
			}
			if(total_page-result[result.length-1]>1){
				html='<li class="'+phtmlId+'class"><a href="javascript:;">...</a></li>';
				$("#"+phtmlId+"Next").before(html);
			}
			if(result[result.length-1]!=total_page){
				//展示最后一页
				html='<li class="'+phtmlId+'class"><a onclick="'+methodName+'('+total_page+')" href="javascript:;">'+total_page+'</a></li>';
				$("#"+phtmlId+"Next").before(html);
			}
		}
	}