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
	
	/***
	 * 设置分页html   
	 * @param total_page
	 */
	function setPageHtml(total_page,phtmlId,methodName,curr_page){
		var page;
		if(typeof(currPage)=='undefined'){
			page=curr_page;
		}else{
			page=currPage
		}
		$(".pclass").remove();
		var html;
		if(total_page<=5){
			//总页数小于等于5   有多少展示多少
			for(var p=0;p<total_page;p++){
				if((p+1)==page){html='<li class="pclass"><a style="background-color:red;" onclick="'+methodName+'('+(p+1)+')" href="javascript:;">'+(p+1)+'</a></li>';}
				else{html='<li class="pclass"><a onclick="'+methodName+'('+(p+1)+')" href="javascript:;">'+(p+1)+'</a></li>';}
				$("#"+phtmlId).before(html);
			}
		}else{
			//总页数大于5  显示离当前页最近的4个以及 第一个与最后一个
			var number=5;
			var result=getPageData(1, total_page, page, number);
			if(result[0]!=1){
				//展示第一页
				html='<li class="pclass"><a onclick="'+methodName+'(1)" href="javascript:;">1</a></li>';$("#next").before(html);
			}
			if(result[0]-1>1){
				html='<li class="pclass"><a href="javascript:;">...</a></li>';$("#next").before(html);
			}
			for(var p=0;p<number;p++){
				if(result[p]==page){html='<li class="pclass"><a style="background-color:red;" onclick="'+methodName+'('+result[p]+')" href="javascript:;">'+result[p]+'</a></li>';}
				else{html='<li class="pclass"><a onclick="'+methodName+'('+result[p]+')" href="javascript:;">'+result[p]+'</a></li>';}
				$("#"+phtmlId).before(html);
			}
			if(total_page-result[result.length-1]>1){
				html='<li class="pclass"><a href="javascript:;">...</a></li>';
				$("#"+phtmlId).before(html);
			}
			if(result[result.length-1]!=total_page){
				//展示最后一页
				html='<li class="pclass"><a onclick="'+methodName+'('+total_page+')" href="javascript:;">'+total_page+'</a></li>';
				$("#"+phtmlId).before(html);
			}
		}
	}