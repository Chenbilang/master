/**
 * 增删改查
 */
var method = "";
var listParam = "";
var saveParam = "";
$(function() {
		
		$('#grid').datagrid({
			url: name+'_getList'+listParam,
			columns: columns,
					/**
					 * 单元格formatter(格式化器)函数，带3个参数： value：字段值。 rowData：行记录数据。最重要的
					 * rowIndex: 行索引。
					 */
			// 只允许选中一行
			singleSelect: true,
			// 显示分页工具条
			pagination: true,
			// 初始化的页数
			pageNumber: 1,
			// 每页显示记录数:
			pageSize: 5,
			// 分页工具条中下拉列表中的值：
			pageList: [5, 10],
			// 隔行换色
			striped: true,
			toolbar: [{

				text: '新增',
				iconCls: 'icon-add',

				handler: function() {
					// 弹出窗口:
					method = "add";
					$('#editDlg').dialog('open');
					$('#editForm').form('clear');
				}
			},'-',{
				text: '导出',
				iconCls: 'icon-excel',
				handler: function(){
					var formData = $('#searchForm').serializeJSON();
					//下载文件
					$.download(name + "_export" + listParam,formData);
				}
			},'-',{
				text: '导入',
				iconCls: 'icon-save',
				handler: function(){
					$('#importDlg').dialog('open');
				}
			}]
		});
		

		$('#editDlg').dialog({
			title: '部门编辑',
			width: 300,
			height: 200,
			closed: true, // 窗口是是否为关闭状态, true：表示关闭
			modal: true // 模式窗口
		});

		$('#btnSearch').bind('click', function update() {
			var formData = $('#searchForm').serializeJSON();
			// 重新加载数据:
			// load方法将form表单的数据发到getList方法里，然后更新数据
			$("#grid").datagrid('load', formData);
		});

		$('#btnSave').bind('click', function() {
			var isValid = $('#editForm').form('validate');
			if(isValid == false){
				return;
			}
			// 将表单数据转换成json，利用jquery提交给后台
			var formData = $('#editForm').serializeJSON();
			$.ajax({
				// 判断使用保存方法还是修改方法
				url: name+'_' + method+saveParam,
				data: formData,
				dataType: 'json',
				type: 'post',
				success: function(rtn) {
					// info显示的图标图像
					$.messager.alert("提示", rtn.message, 'info', function() {
						// 成功的话，我们要关闭窗口
						$('#editDlg').dialog('close');
						// 刷新表格数据
						$('#grid').datagrid('reload');
					});
				}
			});
		});

		//判断是否有导入的功能
		var importForm = document.getElementById('importForm');
		if(importForm){
			$('#importDlg').dialog({
				title:'导入数据',
				width:330,
				height:106,
				modal:true,
				closed:true,
				buttons:[
				    {
				    	text: '导入',
				    	handler:function(){
				    		$.ajax({
				    			url: name + '_doImport',
				    			data:new FormData($('#importForm')[0]),
				    			type:'post',
				    			/*
				    			 * processData和ContentType要求发送的数据是boolean和string类型
				    			 * 把他们都设置成false，发送dom树信息
				    			 */
				    			processData:false,
				    			contentType:false,
				    			dataType:'json',
				    			success:function(rtn){
				    				$.messager.alert('提示',rtn.message,'info',function(){
				    					if(rtn.success){
				    						$('#importDlg').dialog('close');
				    						$('#importForm').form('clear');
				    						$('#grid').datagrid('reload');
				    					}
				    				});
				    			}
				    		});
				    	}
				    }
				]
			});
		}
	});
	

	/**
	 * 删除部门
	 */
	
function del(uuid) {
	$.messager.confirm("确认", "确认要删除吗？", function(yes) {
		if (yes) {
			$.ajax({
				url: name+'_delete?id=' + uuid,
				dataType: 'json',
				type: 'post',
				success: function(rtn) {
					$.messager.alert("提示", rtn.message, 'info', function() {
						// 刷新表格数据
						$('#grid').datagrid('reload');
					});
				}
			});
		}
	});
}

/**
 * 修改部门
 */
function edit(uuid) {
	// 弹出窗口
	$('#editDlg').dialog('open');

	// 清空表单内容
	$('#editForm').form('clear');
	method = "update";
	// 加载数据
	$('#editForm').form('load', name+'_get?id=' + uuid);
}

