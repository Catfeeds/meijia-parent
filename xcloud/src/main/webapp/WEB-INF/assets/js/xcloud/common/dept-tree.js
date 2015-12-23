//此为在jsp中需要设置，作为根节点和公司参数使用.
var companyId = $("#companyId").val();
var shortName = $("#shortName").val();

var setting = {
	view: {
		dblClickExpand: dblClickExpand,
		addHoverDom: addHoverDom,
		removeHoverDom: removeHoverDom,
		selectedMulti: false
	},
	
	edit: {
		enable: true
	},
	
	data: { // 必须使用data  
        simpleData: {
            enable: true,
            idKey: "dept_id", // id编号命名 默认  
            pIdKey: "parent_id", // 父id编号命名 默认   
            rootPId: 0 // 用于修正根节点父节点数据，即 pIdKey 指定的属性值  
        }
    },
    
	async : {
		type : 'GET',
		enable : true,
		url : '/xcloud/company/get_depts.json',
		dataType : "json",
		autoParam : [ "parent_id"],
		otherParam : {
			"company_id" : companyId
		},

	},
	
	callback: {
		onAsyncSuccess: zTreeOnAsyncSuccess,
		onRename: zTreeOnRename,
		beforeRemove: beforeRemove,
		onClick: onClick
	}

};



var zNodes =[ {name:shortName, dept_id:"0", parent_id:"0",  isParent:true, open:"true"}];


function filter(treeId, parentNode, childNodes) {  
    if (!childNodes) return null;  
    for (var i = 0, l = childNodes.length; i < l; i++) {  
        childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');  
    }  
    return childNodes;  
}  
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
    
    var data= msg.data;
    var subTreeNodes = [];
    
    var item;
    for (i = 0; i < data.length; i++) {
    	item = data[i];
    	subTreeNodes.push({
    		dept_id : item.dept_id,// 节点id
    		name : item.name,// 节点名
    		parent : item.dept_id
    	});
    }
    console.log(subTreeNodes);
    var zTree = $.fn.zTree.getZTreeObj("detpTree");
    zTree.removeChildNodes(treeNode);
    zTree.addNodes(treeNode, subTreeNodes);
    
};

function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
	alert("数据出现异常。");
}

function dblClickExpand(treeId, treeNode) {
	return treeNode.level > 0;
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {

    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='添加' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(){
//        var zTree = $.fn.zTree.getZTreeObj("detpTree");
//        zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
//        return false;
    	$('#tree_tid_modal').val(treeNode.tId);
    	$('#parent_id_modal').val(treeNode.id);
    	$('#parent_name_modal').html(treeNode.name);
    	$('#dept-modal').modal();
    });
};





$('#dept-modal').on('close.modal.amui', function(e) {

   var deptParentId = $('#parent_id_modal').val();
   var deptName = $('#dept_name_modal').val();
   var treeTid = $('#tree_tid_modal').val();
   
   if (deptName == undefined) return true;
   if (deptName == "") return true;
   
   var params = {};
	params.company_id = companyId;
	params.paernt_id = deptParentId;
	params.name = deptName;
	
	$.ajax({
       type : "post",
       url : "/xcloud/company/add_dept.json",
       data : params,
       dataType : "json",
       async : false,
       success : function(rdata, textStatus) {
          if (rdata.status == "999") {
       	   		alert(rdata.msg);
       	   		return true;
          }
          
          if (rdata.status == "0") {
        	  var zTree = $.fn.zTree.getZTreeObj("detpTree");
        	  
        	  var parentTreeNode = zTree.getNodeByTId(treeTid);
        	  
        	  zTree.addNodes(parentTreeNode, {id:rdata.data.dept_id, pId:deptParentId, name:deptName});
        	  return true;
          }
       },
       error : function(XMLHttpRequest, textStatus, errorThrown) {
           
       },
       
   });   
   return true;
});


function removeHoverDom(treeId, treeNode) {

    $("#addBtn_"+treeNode.tId).unbind().remove();
};

function onClick(event, treeId, treeNode, clickFlag) {
//	showLog("[ "+getTime()+" onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag===1 ? "普通选中": (clickFlag===0 ? "<b>取消选中</b>" : "<b>追加选中</b>")) + ")");
	console.log(treeNode.dept_id);
	console.log("11111111111111");
//	var dept_id = treeNode.dept_id;
//	location.href = "/xcloud/staff/list?dept_id="+dept_id;
}	

function zTreeOnRename(event, treeId, treeNode, isCancel) {

	var params = {};
	params.company_id = companyId;
	params.dept_id = treeNode.dept_id;
	params.name = treeNode.name;
	
	console.log(params);
	$.ajax({
        type : "post",
        url : "/xcloud/company/edit_dept.json",
        data : params,
        dataType : "json",
        async : false,
        success : function(rdata, textStatus) {
           if (rdata.status == "999") {
        	   alert(rdata.msg);
        	   treeNode.name = rdata.data.name;
        	   var zTree = $.fn.zTree.getZTreeObj("detpTree");
        	   zTree.updateNode(treeNode);

           }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            
        },
        
    });
}

function beforeRemove(treeId, treeNode) {  
	
	if (confirm("确定要删除" + treeNode.name + "?")) {
		var params = {};
		params.company_id = companyId;
		params.dept_id = treeNode.dept_id;
		
		console.log(params);
		$.ajax({
	        type : "post",
	        url : "/xcloud/company/del_dept.json",
	        data : params,
	        dataType : "json",
	        async : false,
	        success : function(rdata, textStatus) {
	           if (rdata.status == "999") {
	        	   alert(rdata.msg);
	        	   return false;
	           }
	        },
	        error : function(XMLHttpRequest, textStatus, errorThrown) {
	            return false;
	        },
	        
	    });	
	} else {
		return false;
	}
	
}

$(document).ready(function() {
	$.fn.zTree.init($("#detpTree"), setting, zNodes);
	var zTree = $.fn.zTree.getZTreeObj("detpTree");
	var nodes = zTree.getNodes()[0];
	zTree.expandNode(nodes);
});
