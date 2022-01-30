function delFruit(fid) {
    if (confirm("是否确认删除？")) {
        // window的地址栏有href属性 获取后 更新为：del.do?fid=#
        window.location.href = 'fruit.do?fid=' + fid + '&operate=del';
    }

}

function page(pageNo) {
    window.location.href = "fruit.do?pageNo=" + pageNo;
}