/**
 * Created by zhu on 14-1-24.
 */
function createTd(value, needAnchor) {
    if (needAnchor) {
        return "<td><a>" + value + "</a></td>";
    }
    return "<td>" + value + "</td>";
}

function containerScroll(container, loadingObject, callback, offset) {
    container.unbind('scroll').scroll(function(e) {
        /*console.log(container.height());
         console.log(container[0].scrollTop);
         console.log(container[0].scrollHeight);*/
        offset = offset || 0;
        if (!loadingObject.loading &&
            container.height() + container[0].scrollTop + offset >= container[0].scrollHeight) {
            callback();
        }
    });
}