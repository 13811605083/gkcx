
	/**单引号和双引号转义 @*/
    function escapeChar(value) {
    	//转换半角单引号
    	value = value.replace(/\'/g, "\\\'");
    	//转换半角双引号
    	value = value.replace(/\"/g, "&quot;");
        return value;
    }

/**表单序列化插件*/
(function($){
	　　$.fn.serializeObject=function(){
		  const o = {};
		  const a = this.serializeArray();
		  $.each(a, function() {
	　　　　　　if(o[this.name]){
	　　　　　　　　if(!o[this.name].push){
	　　　　　　　　　　o[this.name]=[o[this.name]];
	　　　　　　　　}
	　　　　　　　　o[this.name].push(this.value||'');
	　　　　　　}else{
	　　　　　　　　o[this.name]=this.value||'';
	　　　　　　}
	　　　　});
	　　return o;
	　　};
})(jQuery);    
    
$(function(){
    /**所有的输入框清掉缓存*/
	$("input[type='text']").attr("autocomplete","off");

	$("a").each(function(){
		const href = $(this).attr("href");
		if(!href){
			$(this).attr("href","javascript:void(0);");
		}
	});
});

	/**js生成UUID @*/
	function generateUUID() {
		let d = new Date().getTime();
		const uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
			const r = (d + Math.random() * 16) % 16 | 0;
			d = Math.floor(d / 16);
			return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
		});
		return uuid;
	};
	
	/**
	 * text框下显示ztree
	 * @param inputId 点击框的id
	 * @param ztreeId ztree的id
	 */
	function showMenu(inputId,ztreeId) {
		if(!$("#"+ztreeId).is(":hidden")){
			hideMenu(ztreeId);
			return;
		}
		let obj = $("#"+inputId);
		let cityOffset = obj.offset();
		$("#"+ztreeId).css("width",obj.width());
		$("#"+ztreeId).css({left:cityOffset.left + "px", top:cityOffset.top + obj.outerHeight() + "px"}).slideDown("fast");
		$("body").bind("mousedown", function(event){
			if($(event.target).parents("#"+ztreeId).length == 0){
				//hideMenu(ztreeId);
			}
		});
	}
	
	/**隐藏ztree框@*/
	function hideMenu(id) {
		$("#"+id).fadeOut("fast");
		$("body").unbind("mousedown");
	}
	
	/**关闭页面@*/
	function quiet(){
		try{window.opener.refreshGrid();}catch(e){
			try{window.parent.refreshGrid();}catch(e){}
		}
		window.opener = null;
		window.open("","_self");
		window.close();
	}
	
	/**清空table里input的值*/
	function resetInput(tableId){
		let i;
		const $table = $("#" + tableId);
		let text = $("input[type='text']", $table);
		for(i = 0; i<text.length; i++){
	    	$(text.eq(i)).val("");
	    }
		const select = $("select", $table);
		for(i = 0; i<select.length; i++){
	    	$(text.eq(i)).val("");
	    }
		text = $("input[type='hidden']", $table);
		for(i = 0; i<text.length; i++){
	    	$(text.eq(i)).val("");
	    }
		text = $("input[type='checkbox']", $table);
		for(i = 0; i<text.length; i++){
	    	text.eq(i).attr("checked",false);
	    }
		text = $("input[type='radio']", $table);
		for(i = 0; i<text.length; i++){
	    	text.eq(i).attr("checked",false);
	    }
	}

	/**判断账号是否存在（如账号，主键等）*/
	function isExist(url){
		let result = false;
		$.ajax({
			type:"get",
			url: url,
		   	async: false,
		   	success:function(data){
		   		if(!data.valid){ result = true;}
			}
		});
		return result;
	}
