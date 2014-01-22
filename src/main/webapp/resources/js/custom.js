/**
 * Created by zhenguang.zhu on 14-1-21.
 */
$(function() {
    var initPage = {"from": 0, "rows": 20};

    var loading = true;

    function bindUserList(data) {
        $('table#user-list a').unbind('click').click(function(e) {
            e.preventDefault();
            var userName = $(e.target).text();
            $('#user-purchase-list').find('h4#purchaselist-label').text(userName);

            var userId = $(e.target).parent('td').prev().text();
            var saleData = {userId: userId, page: data};
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
                    $('#user-purchase-list').modal();
                });
        });
    }
    bindUserList(initPage);

    var loadUserData = function (data) {
        $.ajax({
            url: ctx + "/user/list",
            type: 'POST',
            data: JSON.stringify(data),
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
                bindUserList(initPage);
                loading = false;
            }
        );
    };

    function createTd(value, needAnchor) {
        if (needAnchor) {
            return "<td><a>" + value + "</a></td>";
        }
        return "<td>" + value + "</td>";
    }

    $('.display').scroll(function(e) {
        var container = $(this);
        if (!loading &&  container.height() + container[0].scrollTop >= container[0].scrollHeight) {
            loading = true;

            var from = initPage.from, rows = initPage.rows;
            initPage.from = parseInt(from + rows);
            loadUserData(initPage);
        }
    });

    loadUserData(initPage);

});
