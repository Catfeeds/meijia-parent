/*var setting = {
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		selectedMulti : false
	},
	edit : {
		enable : true,
		editNameSelectAll : true,
		showRemoveBtn : showRemoveBtn,
		showRenameBtn : showRenameBtn
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeDrag : beforeDrag,
		beforeEditName : beforeEditName,
		beforeRemove : beforeRemove,
		beforeRename : beforeRename,
		onRemove : onRemove,
		onRename : onRename
	}
};
var log, className = "dark";
function beforeDrag(treeId, treeNodes) {
	return false;
}
function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; "
			+ treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	return confirm("Start node '" + treeNode.name + "' editorial status?");
}
function beforeRemove(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	showLog("[ " + getTime() + " beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
			+ treeNode.name);
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	return confirm("Confirm delete node '" + treeNode.name + "' it?");
}
function onRemove(e, treeId, treeNode) {
	showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
			+ treeNode.name);
}
function beforeRename(treeId, treeNode, newName, isCancel) {
	className = (className === "dark" ? "" : "dark");
	showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime()
			+ " beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
			+ (isCancel ? "</span>" : ""));
	if (newName.length == 0) {
		alert("Node name can not be empty.");
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		setTimeout(function() {
			zTree.editName(treeNode)
		}, 10);
		return false;
	}
	return true;
}
function onRename(e, treeId, treeNode, isCancel) {
	showLog((isCancel ? "<span style='color:red'>" : "") + "[ " + getTime()
			+ " onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name
			+ (isCancel ? "</span>" : ""));
}
function showRemoveBtn(treeId, treeNode) {
	return !treeNode.isFirstNode;
}
function showRenameBtn(treeId, treeNode) {
	return !treeNode.isLastNode;
}
function showLog(str) {
	if (!log)
		log = $("#log");
	log.append("<li class='" + className + "'>" + str + "</li>");
	if (log.children("li").length > 8) {
		log.get(0).removeChild(log.children("li")[0]);
	}
}
function getTime() {
	var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
			.getSeconds(), ms = now.getMilliseconds();
	return (h + ":" + m + ":" + s + " " + ms);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn)
		btn.bind("click", function() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.addNodes(treeNode, {
				id : (100 + newCount),
				pId : treeNode.id,
				name : "new node" + (newCount++)
			});
			return false;
		});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};
function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}*/

/*$(document).ready(function() {
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	$("#selectAll").bind("click", selectAll);
});*/

/*var newCount = 1;
function addHoverDom(treeId, treeNode) {
	console.log("addHoverDom");
	var sObj = $("#" + treeNode.tId + "_span");
	console.log(sObj);
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0)
		return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
			+ "' title='add node' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.tId);
	if (btn)
		btn.bind("click", function() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.addNodes(treeNode, {
				id : (100 + newCount),
				pId : treeNode.id,
				name : "new node" + (newCount++)
			});
			return false;
		});
};
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.tId).unbind().remove();
};

function dblClickExpand(treeId, treeNode) {
	return treeNode.level > 0;
}*/
// var zNodes =[];
		var setting = {
			async: {
				type : 'GET',
				enable: true,
				url:'/xcloud/interface-zTree/zTree-nodes.json',
				autoParam:["id", "name=n", "level=lv"],
				otherParam:{"otherParam":"zTreeAsyncTest"},
				dataFilter: filter
			}
		
		};

		function filter(treeId, parentNode, childNodes) {
			/*if (!childNodes) return null;
			for (var i=0, l=childNodes.length; i<l; i++) {
				childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
			}*/
			/* for ($i=1; $i<5; $i++) {
			    	$nId = $pId.$i;
			    	$nName = $pName."n".$i;
			    	
			    	$deptId = $pId.$i;
			    	$name = $pName.$i;
			    	
			    	echo : "{ id:'".$deptId"',	name:'".$Name"',	isParent:"
			    	(( $pLevel < "2" && ($i%2)!=0)?"true":"false")
			    	($pCheck==""?"":((($pLevel < "2" && ($i%2)!=0)?", halfCheck:true":"")
			    			($i==3?", checked:true":"")))"}";
			    	if ($i<4) {
			    		echo ",";
			    	}
			    }*/
			return childNodes;
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting);
		});
/*$.ajax({
	type : 'GET',
	url : '/xcloud/interface-zTree/zTree-nodes.json',
	dataType : "json",
	success : function(treeNode) {
		zNodes = treeNode; // 取得ajax数据
		//console.log(zNodes);
		var height = document.documentElement.clientHeight - 115; // 设定高度
		$("#navigatorTree").height(height);
		$.fn.zTree.init($("#navigatorTree"), setting, zNodes);
		zTree = $.fn.zTree.getZTreeObj("navigatorTree"); // 初始化树
		rMenu = $("#rMenu");
		//$("#treeDemo").val(zNodes);
		$.fn.zTree.init($("#treeDemo"), setting, zNodes); // 初始化右键
		//console.log(zNodes);
	},
// error: doError
});*/

/*
 * var zNodes =[ { id:1, pId:0, name:"parent node 1", open:true}, { id:11,
 * pId:1, name:"leaf node 1-1"}, { id:12, pId:1, name:"leaf node 1-2"}, { id:13,
 * pId:1, name:"leaf node 1-3"}, { id:2, pId:0, name:"parent node 2",
 * open:true}, { id:21, pId:2, name:"leaf node 2-1"}, { id:22, pId:2, name:"leaf
 * node 2-2"}, { id:23, pId:2, name:"leaf node 2-3"}, { id:3, pId:0,
 * name:"parent node 3", open:true }, { id:31, pId:3, name:"leaf node 3-1"}, {
 * id:32, pId:3, name:"leaf node 3-2"}, { id:33, pId:3, name:"leaf node 3-3"} ];
 */
