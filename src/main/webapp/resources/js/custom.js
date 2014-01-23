/**
 * Created by zhenguang.zhu on 14-1-21.
 */
$(function() {
    var userLoading = true, historyLoading = true;

    var bindUserList = (function () {
        var initPage = {"from": 0, "rows": 15};

        return function() {
            var loadRemote = function() {
                $('table#user-list a').unbind('click').click(function() {
                    return function(e) {
                        e.preventDefault();

                        var userName = $(e.target).text();
                        $('#user-purchase-list').find('h4#purchaselist-label').text(userName + "的购物清单");

                        var userId = $(e.target).parent('td').prev().text();
                        var saleData = {userId: userId, page: initPage};
                        $.ajax({
                            url: ctx + "/user/sale/list",
                            type: "POST",
                            data: JSON.stringify(saleData),
                            contentType: "application/json",
                            dataType: "json"
                        }).done(function(data) {
                                var i, l = data.length, userBody = $('table#user-purchase-table tbody');
                                userBody.html('');
                                for (i = 0; i < l; i++) {
                                    var tds = createTd(data[i]['saleDate']) +
                                        createTd(data[i]['product']['name']) +
                                        createTd(data[i]['sum']) +
                                        createTd(data[i]['count']);
                                    userBody.append("<tr>" + tds + "</tr>");
                                }
                                historyLoading = false;
                                $('#user-purchase-list').modal();
                            }
                        );
                    };
                });
            };

            containerScroll($('.modal-body'), historyLoading, function() {
                historyLoading = true;
                var from = initPage.from, rows = initPage.rows;
                initPage.from = parseInt(from + rows);
                loadRemote();
            });

            loadRemote();
        };
    })();
    //bindUserList();

    var loadUserData = (function () {
        var initPage = {"from": 0, "rows": 20};

        return function() {
            var loadRemote = function() {
                $.ajax({
                    url: ctx + "/user/list",
                    type: 'POST',
                    data: JSON.stringify(initPage),
                    contentType: "application/json",
                    dataType: "json"
                }).done(function(data) {
                        var i, l = data.length, userBody = $('table#user-list tbody');
                        for (i = 0; i < l; i++) {
                            var tds = createTd(data[i]['businessNo']) +
                                createTd(data[i]['name'], true) +
                                createTd(data[i]['address']) +
                                createTd(data[i]['birthday']) +
                                createTd(data[i]['sex']);
                            userBody.append("<tr>" + tds + "</tr>");
                        }
                        //console.log(data);
                        bindUserList();
                        userLoading = false;
                    }
                );
            };

            /*$('.display').unbind('scroll').scroll(function(e) {
                var container = $(this);
                if (!loading &&  container.height() + container[0].scrollTop >= container[0].scrollHeight) {
                    loading = true;

                    var from = initPage.from, rows = initPage.rows;
                    initPage.from = parseInt(from + rows);
                    loadRemote();
                }
            });*/

            containerScroll($('.display'), userLoading, function() {
                userLoading = true;
                var from = initPage.from, rows = initPage.rows;
                initPage.from = parseInt(from + rows);
                loadRemote();
            });

            loadRemote();
        };
    })();

    function createTd(value, needAnchor) {
        if (needAnchor) {
            return "<td><a>" + value + "</a></td>";
        }
        return "<td>" + value + "</td>";
    }

    function containerScroll(container, loadingObject, callback) {
        container.unbind('scroll').scroll(function(e) {
            console.log(loadingObject.loading);
            if (!loadingObject.loading && container.height() + container[0].scrollTop >= container[0].scrollHeight) {
                callback();
            }
        });
    }

    //loadUserData();

    var UserListObject = function () {
        var initPage = {"from": 0, "rows": 20};

        return {
            loading: true,

            loadRemote: function () {
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
                    for (i = 0; i < l; i++) {
                        var tds = createTd(data[i]['businessNo']) +
                            createTd(data[i]['name'], true) +
                            createTd(data[i]['address']) +
                            createTd(data[i]['birthday']) +
                            createTd(data[i]['sex']);
                        userBody.append("<tr>" + tds + "</tr>");
                    }
                    //console.log(data);
                    //bindUserList();
                    self.loading = false;
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

    UserListObject.loadRemote();

    var UserSalesObject = function() {
        var initPage = {"from": 0, "rows": 20};

        return {
            loading: true,

            loadRemote: function(userId) {
                var saleData = {userId: userId, page: initPage}, self = this;
                var ajaxProp = {
                    url: ctx + "/user/sale/list",
                    type: "POST",
                    data: JSON.stringify(saleData),
                    contentType: "application/json",
                    dataType: "json"
                };

                $.ajax(ajaxProp).done(function(data) {
                        console.log(data);
                        var i, l = data.length, userBody = $('table#user-purchase-table tbody');
                        userBody.html('');
                        for (i = 0; i < l; i++) {
                            var tds = createTd(data[i]['saleDate']) +
                                createTd(data[i]['product']['name']) +
                                createTd(data[i]['sum']) +
                                createTd(data[i]['count']);
                            userBody.append("<tr>" + tds + "</tr>");
                        }
                        self.loading = false;
                        self.register();
                        $('#user-purchase-list').modal();
                    }
                );
            },

            register: function() {
                var self = this;

                containerScroll($('.modal-body'), self, function() {
                    self.loading = true;
                    var from = initPage.from, rows = initPage.rows;
                    initPage.from = parseInt(from + rows);
                    self.loadRemote();
                });
            }
        }

    }();

});
