layui.define(['jquery','layer'], function (exports) {

    var $ = layui.jquery,
        layer = layui.layer;

    AJAX = {

        syncPost: function (url, data, success, error) {
            this.post(url, data, success, error, false)
        },

        /**
         * 初始化加载完成执行方法
         */
        post: function (url, data, success, error, async) {

            setTimeout(function(){

            },30000)
            if(async == undefined) {
                async = true
            }
            this.url = url;
            this.type = "post";
            this.data = data;
            this.dataType = "json";
            this.async = async;
            this.success = success;
            this.error = error;
            this.contentType = "application/json; charset=utf-8";

            if (this.url.indexOf("?") == -1) {
                this.url = this.url + "?jstime=" + new Date().getTime();
            } else {
                this.url = this.url + "&jstime=" + new Date().getTime();
            }

            var me = this;

            $.ajax({
                type: this.type,
                url: this.url,
                dataType: this.dataType,
                async: this.async,
                data: JSON.stringify(this.data),
                contentType: this.contentType,
                beforeSend: function (data) {

                },
                success: function (data) {
                    if (!!me.success) {
                        me.success(data);
                    }
                },
                error: function (data) {
                    if (!!me.error) {
                        me.error(data);
                    }
                    top.layer.msg("系统异常", {icon: 5});
                },
                complete: function (data) {
                    //top.layer.close(index)
                }
            });
        }
}


    exports('ax', AJAX);
})
