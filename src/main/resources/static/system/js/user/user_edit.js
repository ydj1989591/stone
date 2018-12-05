layui.config({
    base:'/common/js/'
}).use(['form','layer','laydate','ax'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        ajax = layui.ax;

    var edit_url = '/user/edit'
        ,checkAccount_url = '/user/checkAccount'
    laydate.render({
        elem: "#birthday",
        format: "yyyy-MM-dd"
    })

    form.verify({
        account: function (value, item) {//value：表单的值、item：表单的DOM对象
            if(!new RegExp("^[a-zA-Z0-9_\u4e00-\u9fa5\\s·]+$").test(value)){
                return '用户名不能有特殊字符';
            }
            if(/(^\_)|(\__)|(\_+$)/.test(value)){
                return '用户名首尾不能出现下划线\'_\'';
            }
            if(/^\d+\d+\d$/.test(value)){
                return '用户名不能全为数字';
            }
            var checkAccountData = {
                account : value
            }
            if(!!$('#userId').val()){
                checkAccountData.userId = $('#userId').val()
            }

            var checkAccountFlag = true;
            ajax.syncPost(checkAccount_url, checkAccountData, function (res) {
                if(res.code == 200){
                    if(!res.data){
                        checkAccountFlag = false
                    }
                }
            })

            if(!checkAccountFlag){
                return '用户名已存在'
            }
        },
        dept: function (value, item) {//value：表单的值、item：表单的DOM对象
            var deptid = $(item).attr('data-hidden-deptid')
            if(!deptid){
                return '请选择部门';
            }
        }
    })

    form.on("submit(addUser)",function(data){
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        var deptid = $('#dept').attr('data-hidden-deptid')
        var data = {
            userId : $('#userId').val(),
            account: $('#account').val(),
            name: $('#name').val(),
            email: $('#email').val(),
            phone: $('#phone').val(),
            sex: $('input[name="sex"]:checked').val(),
            status: $('#status').val(),
            birthday: $('#birthday').val(),
            deptid: deptid
        }
        ajax.post(edit_url, data, function (res) {
            top.layer.close(index)
            if(res.code == 200){
                top.layer.msg("用户添加成功！");
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            }else{
                top.layer.msg("用户添加失败！，原因：" + res.message);
            }
        },function (data, ee) {

        }, true)

        return false;
    });


    function sleep(numberMillis) {
        var now = new Date();
        var exitTime = now.getTime() + numberMillis;
        while (true) {
            now = new Date();
            if (now.getTime() > exitTime)
                return;
        }
    }

    $("#J_close").click(function () {
        parent.layer.closeAll()
    })


})

// (function ($) {

    var setting = {
        async: {
            enable: true,
            url:"/department/queryTreeList",
            autoParam:["id", "name=n", "level=lv"],
            otherParam:{"name":""},
            type: "post",
            contentType:"application/json; charset=utf-8",
            dataFilter: filter
        },
        view: {
            dblClickExpand: false,
            selectedMulti: false
        },
        data:{
            simpleData: {
                enable: true,
                pIdKey:"pid"
            }
        },
        callback: {
            beforeClick: beforeClick,
            onClick: onClick
        }
    }

    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }

    function beforeClick(treeId, treeNode) {
        return true;
    }

    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getSelectedNodes(),
            v = "";
        nodes.sort(function compare(a,b){return a.id-b.id;});
        for (var i=0, l=nodes.length; i<l; i++) {
            v += (nodes[i].oldname|| nodes[i].name )+ ",";
        }
        if (v.length > 0 ) v = v.substring(0, v.length-1);
        var cityObj = $("#dept");
        cityObj.val(v);
        cityObj.attr('data-hidden-deptid', treeNode.id)
    }

    function showMenu() {
        var cityObj = $("#dept");
        var cityOffset = $("#dept").offset();
        $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

        $("body").bind("mousedown", onBodyDown);
    }
    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn"
            || event.target.id == "menuContent"
            || $(event.target).parents("#menuContent").length>0)) {
            hideMenu();
        }
    }

    $.fn.zTree.init($("#treeDemo"), setting);
    fuzzySearch('treeDemo','#dept',null,true); //初始化模糊搜索方法
    $('#dept').on('click', function () {
        showMenu();
    })

// })(jQuery);