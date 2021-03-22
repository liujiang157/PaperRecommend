$(function () {
    //´¦ÀíËÑË÷
    $("#search_btn").click(function () {
        var search_text = $("#search_text").val();
        window.location.href = "/search?search=" + search_text;
    });

    $("#search_text").keydown(function (e) {
        if (e.keyCode == 13) {
            var search_text = $("#search_text").val();
            window.location.href = "/search?search=" + search_text;
        }
    });

});
