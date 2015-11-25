var setting = {
	view : {
		dblClickExpand: dblClickExpand,
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		selectedMulti : false,

	},
	
	data : {
		simpleData : {
			enable : true
		}
	},
	edit : {
		enable : false
	}
};

//后续通过注入后台数据实现
var zNodes =[
             { id:1, pId:0, name:"美家生活", open:true},
             { id:11, pId:1, name:"未分配"},
             { id:12, pId:1, name:"行政部"},
             { id:13, pId:1, name:"销售部"},
             { id:14, pId:1, name:"技术部"},
             { id:15, pId:1, name:"总经办"}
         ];



$.fn.zTree.init($("#treeDemo"), setting, zNodes);


var newCount = 1;
function addHoverDom(treeId, treeNode) {
	console.log("addHoverDom");
    var sObj = $("#" + treeNode.tId + "_span");
    console.log(sObj);
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='add node' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(){
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
        return false;
    });
};
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};

function dblClickExpand(treeId, treeNode) {
	return treeNode.level > 0;
}