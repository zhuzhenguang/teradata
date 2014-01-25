/**
 * Created by zhu on 14-1-22.
 */
$(function() {
    /*var data = {"from": "0", "rows": "10"};

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
    bindProductList();*/

    /**
     * 商品列表
     */
    var ProductListObject = function () {
        var initPage = {"from": 0, "rows": 15};
        var productBody = $('table#product-list tbody');
        var productList = $('#product-list');

        return {
            loading: true,
            loadingObject: $('.loading'),

            loadRemote: function () {
                var ajaxProp = {
                    url: ctx + "/product/list",
                    type: 'POST',
                    data: JSON.stringify(initPage),
                    contentType: "application/json",
                    dataType: "json"
                };
                var self = this;
                self.loadingObject.removeClass('hide');

                $.ajax(ajaxProp).done(function(data) {
                    var i, l = data.length;
                    if (l > 0 && productList.hasClass('hide')) {
                        $('.no-data').addClass('hide');
                        productList.removeClass('hide');
                    }
                    for (i = 0; i < l; i++) {
                        var tds = createTd(data[i]['businessNo']) +
                            createTd(data[i]['name'], true) +
                            createTd(data[i]['peoples']) +
                            createTd(data[i]['totalSum']) +
                            createTd(data[i]['totalProfit']);
                        productBody.append("<tr>" + tds + "</tr>");
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

                $('table#product-list a').unbind('click').click(function(e) {
                    e.preventDefault();

                    var productName = $(e.target).text();
                    $('h4#product-label').text(productName + "的购买清单");

                    var productId = $(e.target).parent('td').prev().text();
                    ProductSalesObject.loadRemote(productId);
                });
            }
        };
    }();

    ProductListObject.loadRemote();

    /**
     * 商品购买清单
     */
    var ProductSalesObject = function() {
        var initPage = {"from": 0, "rows": 10};
        var productBody = $('table#product-purchase-table tbody');
        var productSaleModal = $('#product-purchase-list');

        return {
            loading: true,
            loadingObject: $('.loading'),

            productId: '',

            loadRemote: function(productId) {
                var self = this;
                if (productId) {
                    self.productId = productId;
                    self.reset();
                }

                var saleData = {productId: self.productId, page: initPage};
                var ajaxProp = {
                    url: ctx + "/product/sale/list",
                    type: "POST",
                    data: JSON.stringify(saleData),
                    contentType: "application/json",
                    dataType: "json"
                };
                self.loadingObject.removeClass('hide');

                $.ajax(ajaxProp).done(function(data) {
                        var i, l = data.length;
                        for (i = 0; i < l; i++) {
                            var tds = createTd(data[i]['saleDate']) +
                                createTd(data[i]['user']['name']) +
                                createTd(data[i]['totalSum']) +
                                createTd(data[i]['count']);
                            productBody.append("<tr>" + tds + "</tr>");
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
                productBody.html('');
                initPage = {"from": 0, "rows": 10};
                $('.modal-body')[0].scrollTop = 0;
                productSaleModal.modal();
            }
        };

    }();

});