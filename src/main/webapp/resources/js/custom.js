/**
 * Created by zhenguang.zhu on 14-1-21.
 */
$(function() {
    var data = {"from": "0", "rows": "10"};

    function bindUserList() {
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
    bindUserList();

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
            bindUserList();
        }
    );

    function createTd(value, needAnchor) {
        if (needAnchor) {
            return "<td><a>" + value + "</a></td>";
        }
        return "<td>" + value + "</td>";
    }

});
