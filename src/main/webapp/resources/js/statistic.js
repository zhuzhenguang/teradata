/**
 * Created by zhu on 14-1-22.
 */
$(function() {
    var data = {"from": "0", "rows": "10"};

    $.ajax({
        url: ctx + "/statistic/data",
        type: 'POST',
        data: JSON.stringify({"address": "上海", "page": data}),
        contentType: "application/json",
        dataType: "json"
    }).done(function(data) {
            console.log(data);
            var topProducts = data['topProducts'], preProducts = data['preProducts'],
                productKeys = data['productNames'], productNames = [],
                topArray = [], preArray = [];
            var i, l = productKeys.length;
            for (i = 0; i < l; i++) {
                var prop = productKeys[i];
                topArray.push(topProducts[prop]['totalProfit']);
                var preProduct = preProducts[prop];
                if (preProduct) {
                    preArray.push(preProduct['totalProfit']);
                } else {
                    preArray.push(0);
                }
                productNames.push(topProducts[prop]['name']);
            }

            /*for (var prop in topProducts) {
                topArray.push(topProducts[prop]['totalProfit']);
                var preProduct = preProducts[prop];
                if (preProduct) {
                    preArray.push(preProduct['totalProfit']);
                } else {
                    preArray.push(0);
                }
                productNames.push(topProducts[prop]['name']);
            }*/
            createChart(productNames, topArray, preArray);
        }
    );

    function createChart(products, maxProfits, previousProfits) {
        var chartData = {
            labels: products,
            datasets: [
                {
                    fillColor: 'rgba(220,220,220,0.5)',
                    strokeColor : "rgba(220,220,220,1)",
                    data: maxProfits
                },
                {
                    fillColor: 'rgba(151,187,205,0.5)',
                    strokeColor : "rgba(151,187,205,1)",
                    data: previousProfits
                }
            ]
        };

        new Chart(document.getElementById('chart').getContext('2d')).Bar(chartData);
    }

    var addressPopover = $('.popover').popover({container :'body', trigger: 'focus'});
    $('#address').unbind('focus').focus(function(e) {
        $('#addressList').popover('show');
    }).unbind('blur').blur(function(e) {
        $('#addressList').popover('hide');
    });

});