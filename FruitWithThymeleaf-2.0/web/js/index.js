
function delFruit(id) {
    if (window.confirm("是否确认删除?")) {
        // location是对其地址栏对象
        // 给href复制, 并发请求
           window.location.href='del.do?id='+id;
    }
}


function page(curPage) {
    window.location.href="index?curPage="+curPage;
}