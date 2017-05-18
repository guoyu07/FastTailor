/***
 *	JFinalShop Register JavaScript
 *
 *	http://www.jfinalshop.com
 *
 *	Copyright (c) 2014 JFinalShop. All rights reserved.
 **/

$().ready( function() {	
	var $allCheck = $("#allCheck");// 全选复选框
	var $idsCheck = $('[name=ids]:checkbox');// ID复选框
	var $deleteButton = $("#deleteButton");// 删除按钮
	var $searchButton =  $("#searchButton");// 查询按钮
	var $getPriceButton = $("#getPriceButton");
	 // 全选  
     $allCheck.click(function(){
    	 var isChecked = $idsCheck.is(":checked"); 
    	 if (isChecked == false) {
 			$idsCheck.prop('checked',true);
 			$deleteButton.prop("disabled", false);
 			$getPriceButton.prop("disabled",false);
 		} else {
 			$idsCheck.prop('checked',false);
 			$deleteButton.prop("disabled", true);
 			$getPriceButton.prop("disabled",true);
 		}
     });  

   // 无复选框被选中时,删除按钮不可用
 	$idsCheck.click( function() {
 		var $idsChecked = $("[name='ids']:checked");
 		if ($idsChecked.size() > 0) {
 			$deleteButton.prop("disabled", false);
 			$getPriceButton.prop("disabled",false);
 		} else {
 			$deleteButton.prop("disabled", true);
 			$getPriceButton.prop("disabled",true);
 		}
 	});
 	
 	// 查找
	$searchButton.click( function() {
		$pageNumber.val("1");
		$listForm.submit();
	});
		
});
	  
// 批量删除
function deleteAll(url) {
	var $idsCheckedCheck = $("[name='ids']:checked");
	var $deleteButton = $("#deleteButton");// 删除按钮
	var ids = $idsCheckedCheck.serialize();
	if (confirm('您确定要删除吗？') == true) {
		
			$.ajax({
				url: url,
				data: ids,
				dataType: "json",
				async: false,
				beforeSend: function(data) {
					$deleteButton.prop("disabled", true)
				},
				success: function(data) {
					$deleteButton.prop("disabled", false)
					if (data.status == "success") {
						$idsCheckedCheck.parent().parent().remove();
					
					
					}
					alert(data.message);
					//$.message(data.status, data.message);
				}
			});
	}
	location.reload();
}  	




//批量删除
function getPriceAll(url,id) {
	var $idsCheckedCheck = $("[name='ids']:checked");
	var $getPriceButton = $("#getPriceButton");// 删除按钮
	var ids = $idsCheckedCheck.serialize();
	if (confirm('确定向选中厂家发起询价吗？') == true) {
		
			$.ajax({
				url: url,
				data: ids +'&searchId='+id,
				dataType: "json",
				async: false,
				beforeSend: function(data) {
					$getPriceButton.prop("disabled", true)
				},
				success: function(data) {
					$getPriceButton.prop("disabled", false)
					if (data.status == "success") {
						$idsCheckedCheck.parent().parent().remove();
					}
					alert(data.message);
					$('#allCheck').attr("checked",false);
					$idsCheckedCheck.attr("checked",false);
					  
					//$.message(data.status, data.message);
				}
			});
		
	}
	
}  	


//批量删除
function deleteOne(url, id) {
	var $deleteButton = $("#deleteButtonOne");// 删除按钮
	if (confirm('您确定要删除吗？') == true) {		
			$.ajax({
				url: url,
				data: 'id=' + id,
				dataType: "json",
				async: false,				
				success: function(data) {
					alert(data.message);
					//$.message(data.status, data.message);
				}
			});		
	}
	location.reload();
}  	

