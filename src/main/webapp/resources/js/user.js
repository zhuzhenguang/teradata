/**
 * Created by zhenguang.zhu on 14-1-21.
 */
$(function() {
    /**
     * 用户列表
     */
    $.UserListObject = function () {
        var initPage = {"from": 0, "rows": 15};
        var userList = $('#user-list');

        return {
            loading: true,
            loadingObject: $('.loading'),

            loadRemote: function () {
                this.loadingObject.removeClass('hide');
                var ajaxProp = {
                    url: ctx + "/user/list",
                    type: 'POST',
                    data: JSON.stringify(initPage),
                    contentType: "application/json",
                    dataType: "json"
                };
                var self = this;

                $.ajax(ajaxProp).done(function(data) {
                    var i, l = data.length, userBody = $('table#user-list tbody');
                    if (l > 0 && userList.hasClass('hide')) {
                        $('.no-data').addClass('hide');
                        userList.removeClass('hide');
                    }
                    for (i = 0; i < l; i++) {
                        var tds = createTd(data[i]['businessNo']) +
                            createTd(data[i]['name'], true) +
                            createTd(data[i]['address']) +
                            createTd(data[i]['birthday']) +
                            createTd(data[i]['sex']);
                        userBody.append("<tr>" + tds + "</tr>");
                    }
                    self.loading = false;
                    self.loadingObject.addClass('hide');
                    self.register();
                });
            },

            register: function() {
                var self = this;

                containerScroll($('.display'), self, function() {
                    self.loading = true;
                    var from = initPage.from, rows = initPage.rows;
                    initPage.from = parseInt(from + rows);
                    self.loadRemote();
                });

                $('table#user-list a').unbind('click').click(function(e) {
                    e.preventDefault();

                    var userName = $(e.target).text();
                    $('#user-purchase-list').find('h4#purchaselist-label').text(userName + "的购物清单");

                    var userId = $(e.target).parent('td').prev().text();
                    UserSalesObject.loadRemote(userId);
                });
            }
        };
    }();

    $.UserListObject.loadRemote();

    /**
     * 用户购买清单
     */
    var UserSalesObject = function() {
        var initPage = {"from": 0, "rows": 10};
        var userBody = $('table#user-purchase-table tbody');
        var userSaleModal = $('#user-purchase-list');

        return {
            loading: true,
            loadingObject: $('.loading'),
            userId: '',

            loadRemote: function(userId) {
                var self = this;
                if (userId) {
                    self.userId = userId;
                    self.reset();
                }
                this.loadingObject.removeClass('hide');

                var saleData = {userId: self.userId, page: initPage};
                var ajaxProp = {
                    url: ctx + "/user/sale/list",
                    type: "POST",
                    data: JSON.stringify(saleData),
                    contentType: "application/json",
                    dataType: "json"
                };

                $.ajax(ajaxProp).done(function(data) {
                        var i, l = data.length;
                        for (i = 0; i < l; i++) {
                            var tds = createTd(data[i]['saleDate']) +
                                createTd(data[i]['product']['name']) +
                                createTd(data[i]['sum']) +
                                createTd(data[i]['count']);
                            userBody.append("<tr>" + tds + "</tr>");
                        }
                        self.loading = false;
                        self.loadingObject.addClass('hide');

                        containerScroll($('.modal-body'), self, function() {
                            self.loading = true;
                            var from = initPage.from, rows = initPage.rows;
                            initPage.from = parseInt(from + rows);
                            self.loadRemote();
                        }, 40);
                    }
                );
            },

            reset: function() {
                userBody.html('');
                initPage = {"from": 0, "rows": 10};
                $('.modal-body')[0].scrollTop = 0;
                userSaleModal.modal();
            }
        };

    }();

    /**
     * 弹出上传窗口
     */
    $('#uploadExcel').click(function(e) {
        e.preventDefault();
        $('#upload-excle-container').modal();
    });

    /**
     * 上传文件
     */
    $('#upload-excel').click(function (e) {
        e.preventDefault();
        var file = $("input[type='file']");
        if (!file.val() || $.trim(file.val()) == '' ||
            (!/(\.xls)$/.test(file.val()) && !/(\.xlsx)$/.test(file.val()))) {
            return;
        }
        $('#upload-excle-container').modal('hide');
        $('#upload-load').show();
        $('#uploadForm').submit();
    });

});
