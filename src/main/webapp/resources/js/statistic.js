/**
 * Created by zhu on 14-1-22.
 */
$(function() {
    var data = {"from": "0", "rows": "10"}, loadingObject = $('.loading'), noData = $('.no-data'), chart =$('#chart');

    $('#search_button').click(function(e) {
        var addressValue = $('#address').val();
        if ($.trim(addressValue) == '') {
            return;
        }

        loadingObject.removeClass('hide');

        $.ajax({
            url: ctx + "/statistic/data",
            type: 'POST',
            data: JSON.stringify({"address": addressValue, "page": data}),
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
                loadingObject.addClass('hide');

                if (l == 0) {
                    noData.removeClass('hide');
                    chart.remove();
                    $('<canvas id="chart" height="550" width="800"></canvas>').insertAfter(noData);
                } else {
                    noData.addClass('hide');
                    createChart(productNames, topArray, preArray);
                }
            }
        );
    });

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

    $('#address').autocomplete({
        source: ctx + "/address/list",
        //source: [{id:1,name:"Beijing"},{id:2,name:'北京'}],
        focus: function( event, ui ) {
            $( "#address" ).val( ui.item.name );
            return false;
        },
        select: function( event, ui ) {
            if (ui.item) {
                //console.log(ui.item.name);
                $('#address').val(ui.item.name);
                $('#search_button').click();
            }
            return false;
        }
    }).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
        return $( "<li>" )
            .append( "<a>" + item.name + "</a>" )
            .appendTo( ul );
    }

});