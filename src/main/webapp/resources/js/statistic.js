/**
 * Created by zhu on 14-1-22.
 */
$(function() {
    var data = {"from": "0", "rows": "10"};

    $.ajax({
        url: ctx + "/statistic",
        type: 'POST',
        data: JSON.stringify({"address": "上海", "page": data}),
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
});