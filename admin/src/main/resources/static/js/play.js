function Searchpaper() {
    var searchname = $('#searchname').val();
    window.location.href = "/genres?search=" + searchname;
}


$(document).ready(function () {
    var realdata = [];
    var myPlaylist = new jPlayerPlaylist({
        jPlayer: "#jplayer_N",
        cssSelectorAncestor: "#jp_container_N"
    }, realdata, {
        playlistOptions: {
            enableRemoveControls: true,
            autoPlay: false
        },
        swfPath: "js/jPlayer",
        supplied: "webmv, ogv, m4v, oga, mp3",
        smoothPlayBar: true,
        keyEnabled: true,
        audioFullScreen: false
    });

    $(document).on($.jPlayer.event.pause, myPlaylist.cssSelector.jPlayer, function () {
        $('.paperbar').removeClass('animate');
        $('#' + myPlaylist.current).removeClass('active');
    });

    $(document).on($.jPlayer.event.play, myPlaylist.cssSelector.jPlayer, function () {
        $('.paperbar').addClass('animate');
        $('#' + myPlaylist.current).addClass('active');
    });


    $(document).on('click', '.jp-play-add', function (e) {
        var $this = $(e.target);
        if (!$this.is('a')) $this = $this.closest('a');
        var songName = $this.children()[0].innerText;
        var songartist = $this.children()[1].innerText;
        var songAddress = $this.children()[2].innerText;
        var newsong = {
            "title": songName,
            "artist": songartist,
            "mp3": songAddress
        };
        for (var i = 0; i < myPlaylist.playlist.length; i++) {
            if (newsong.title == myPlaylist.playlist[i].title)
                return;
        }
        myPlaylist.add(newsong);
    });


    $(document).on('click', '.jp-play-delete', function (e) {
        var $this = $(e.target);
        if (!$this.is('a')) $this = $this.closest('a');
        var songName = $this.children()[0].innerText;
        var song = {
            "name": songName,
        };
        url = "deletelike.do";
        $.ajax({
            type: "POST",
            url: url,
            data: song,
            success: function (data) {
                $("#list-like").html(data);
            },
            error: function () {
            }
        });

    });


    $(document).on('click', '.jp-play-me', function (e) {
        var $this = $(e.target);
        if (!$this.is('a')) $this = $this.closest('a');
        var songName = $this.children()[0].innerText;
        var songartist = $this.children()[1].innerText;
        var songAddress = $this.children()[2].innerText;
        var newsong = {
            "title": songName,
            "artist": songartist,
            "mp3": songAddress
        };
        var flag = true;
        var index = -1;
        for (var i = 0; i < myPlaylist.playlist.length; i++) {
            if (newsong.title == myPlaylist.playlist[i].title) {
                flag = false;
                index = i;
            }

        }
        if (flag) {
            myPlaylist.add(newsong);
            index = myPlaylist.playlist.length - 1;
        }
        $('.jp-play-me').not($this).removeClass('active');
        $this.toggleClass('active');
        if (!$this.hasClass('active')) {
            myPlaylist.pause();
        } else {
            if (index == myPlaylist.current) {
                myPlaylist.play();
            } else {
                myPlaylist.play(index)
                var currentsong = myPlaylist.playlist[index];
                url = "recordPlay.do";
                $.ajax({
                    type: "GET",
                    url: url,
                    data: currentsong,
                });
            }
        }
    });


    $(document).on('click', '#likethesong', function (e) {
        var $this = $(e.target);
        if (!$this.is('a')) $this = $this.closest('a');
        var songName = $this.children()[0].innerText;
        var url;
        if ($this.hasClass('active')) {
            url = "addlikeSong.do";
        } else {
            url = "deletelikeSong.do";
        }
        var song = {
            "name": songName,
        };
        console.log(url)
        $.ajax({
            type: "POST",
            url: url,
            data: song,
        });
    });


    $(document).on('click', '.list-group-item', function (e) {
        var $this = $(e.target);
        if (!$this.is('a')) $this = $this.closest('a');

        $this.addClass('active').siblings().removeClass('active');
        var songType = $this.attr('name');
        var url = "refresh.do";
        var Type = {
            "songType": songType
        };
        $.ajax({
            type: "GET",
            url: url,
            data: Type,
            async: true,
            success: function (data) {
                $("#genres-list").html(data);
            },
            error: function () {
            }
        });

    });





});
