layui.config({
    base:'/common/js/'
}).use(['form','layer','table','laytpl', 'ax'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table,
        ajax = layui.ax;

    var deleteUser_url = '/user/deleteUser',
        useableUser_url = '/user/useableUser';
    //用户列表
    var tableIns = table.render({
        elem: '#userList',
        url : '/user/list',
        method: 'post',
        contentType: 'application/json',
        parseData: function(res){
            return {
                "code": res.code, //解析接口状态
                "msg": res.message, //解析提示文本
                "count": res.data.total, //解析数据长度
                "data": res.data.records //解析数据列表
            }
        },
        response:{
            statusCode:200 //规定成功的状态码，默认：0
        },
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [3,10,15,20,25],
        limit : 20,
        id : "userListTable",
        autoSort: false,
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'account', title: '用户名', minWidth:100, align:"center",sort:true},
            {field: 'name', title: '姓名', align:'center'},
            {field: 'email', title: '用户邮箱', minWidth:200, align:'center',templet:function(d){
                return '<a class="layui-blue" href="mailto:'+d.email+'">'+d.email+'</a>';
            }},
            {field: 'sex', title: '用户性别', align:'center', templet: function (d) {
                    if(d.sex == 1){
                        return '男'
                    }else if(d.sex == 2){
                        return '女'
                    }
                    return '未知'
                }},
            {field: 'phone', title: '手机号', align:'center'},
            {field: 'status', title: '用户状态',  align:'center',templet:function(d){
                if(d.status == 1){
                    return '启用'
                }else if(d.status == 2){
                    return '冻结';
                }else if(d.status == 3){
                    return '删除'
                }
                return '未知';
            }
            },
            {field: 'createtime', title: '注册时间', align:'center',minWidth:150},
            {title: '操作', minWidth:175, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });

    //搜索【此功能需要后台配合，所以暂时没有动态效果演示】
    $(".search_btn").on("click",function(){
        table.reload("userListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userId: $("#userId").val()  //用户Id
                ,name: $("#name").val()
                ,account: $("#account").val()
                ,phone: $("#phone").val()
                ,email: $("#email").val()
            }
        })

    });

    //添加用户
    function addUser(userId){
        var url = "/user/toEdit"
        if(!!userId){
            url += "?userId=" + userId
        }
        var index = layui.layer.open({
            title : "添加用户",
            type : 2,
            content : url,
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);
        window.sessionStorage.setItem("index",index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize",function(){
            layui.layer.full(window.sessionStorage.getItem("index"));
        })
    }
    $(".addNews_btn").click(function(){
        addUser();
    })

    //列表操作
    table.on('tool(userList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addUser(data.id);
        }else if(layEvent === 'usable'){ //启用禁用
            var _this = $(this),
                usableText = "是否确定禁用此用户？",
                btnText = "已禁用",
                status = 2;
            if(_this.text()=="已冻结") {
                usableText = "是否确定启用此用户？",
                btnText = "已启用",
                status = 1;
            }
            layer.confirm(usableText,{
                icon: 3,
                title:'系统提示',
                cancel : function(index){
                    layer.close(index);
                }
            },function(index){
                layer.close(index);
                var useableUserData = {
                    userId: data.id,
                    status: status
                }
                ajax.post(useableUser_url, useableUserData, function (res) {
                    if(res.code == 200){
                        layer.msg(btnText + "成功！")
                        tableIns.reload();
                    }else{
                        layer.msg(btnText + "失败！原因："+ res.message)
                    }

                })
            },function(index){
                layer.close(index);
            });
        }
    });
})
