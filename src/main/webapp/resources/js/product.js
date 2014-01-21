/**
 * Created by zhu on 14-1-22.
 */
$(function() {
    var data = {"from": "0", "rows": "10"};

    function bindProductList() {
        $('table#product-list a').unbind('click').click(function(e) {
            e.preventDefault();

            var productName = $(e.target).text();
            $('h4#product-label').text(productName);

            var productId = $(e.target).parent('td').prev().text();
            var saleData = {productId: productId, page: data};
            $.ajax({
                url: ctx + "/product/sale/list",
                type: "POST",
                data: JSON.stringify(saleData),
                contentType: "application/json",
                dataType: "json"
            }).done(function(data) {
                    var i, l = data.length, productBody = $('table#product-purchase-table tbody');
                    productBody.html('');
                    for (i = 0; i < l; i++) {
                        var tds = createTd(data[i]['saleDate']) +
                            createTd(data[i]['user']['name']) +
                            createTd(data[i]['totalSum']) +
                            createTd(data[i]['count']);
                        productBody.append("<tr>" + tds + "</tr>");
                    }
                    $('#product-purchase-list').modal();
                });

        });
    }
    bindProductList();

    $.ajax({
        url: ctx + "/product/list",
        type: 'POST',
        data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json"
    }).done(function(data) {
            var i, l = data.length, userBody = $('table#product-list tbody');
            for (i = 0; i < l; i++) {
                var tds = createTd(data[i]['businessNo']) +
                    createTd(data[i]['name'], true) +
                    createTd(data[i]['peoples']) +
                    createTd(data[i]['totalSum']) +
                    createTd(data[i]['totalProfit']);
                userBody.append("<tr>" + tds + "</tr>");
            }
            //console.log(data);
            bindProductList();
        }
    );

    function createTd(value, needAnchor) {
        if (needAnchor) {
            return "<td><a>" + value + "</a></td>";
        }
        return "<td>" + value + "</td>";
    }



});