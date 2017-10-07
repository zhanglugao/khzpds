<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>科幻作品大赛</title>
    <jsp:include page="../common/open_inc.jsp"></jsp:include>
    <meta content='width=device-width,initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
    <style type="text/css" media="screen">
        html, body {
            height: 100%;
        }

        body {
            margin: 0;
            padding: 0;
            overflow: auto;
        }

        #flashContent {
            display: none;
        }
    </style>
    <!--首先引入相关的js文件-->
    <script type="text/javascript" src="/js/swfobject/swfobject.js"></script>
    <script type="text/javascript" src="/js/flexpaper_flash.js"></script>
    <!--配置-->
    <script type="text/javascript">
            var  swfVersionStr = "10.0.0";
        <!-- To use express install, set to playerProductInstall.swf, otherwise the empty string. -->
        var xiSwfUrlStr = "/js/playerProductInstall.swf";
        var flashvars = {
            SwfFile: escape("${filePath}"),
            Scale: 0.6,
            ZoomTransition: "easeOut",
            ZoomTime: 0.5,
            ZoomInterval: 0.1,
            FitPageOnLoad: false,
            FitWidthOnLoad: true,
            PrintEnabled: true,
            FullScreenAsMaxWindow: false,
            ProgressiveLoading: true,
            PrintToolsVisible: true,
            ViewModeToolsVisible: true,
            ZoomToolsVisible: true,
            FullScreenVisible: true,
            NavToolsVisible: true,
            CursorToolsVisible: true,
            SearchToolsVisible: true,
            localeChain: "en_US"
        };
        var params = {}
        params.quality = "high";
        params.bgcolor = "#ffffff";
        params.allowscriptaccess = "sameDomain";
        params.allowfullscreen = "true";
        var attributes = {};
        attributes.id = "FlexPaperViewer";
        attributes.name = "FlexPaperViewer";
        var height=window.screen.height;
        var width=window.screen.width;
        swfobject.embedSWF(
            "/js/FlexPaperViewer.swf", "flashContent",
            width-100,height-100,
            swfVersionStr, xiSwfUrlStr,
            flashvars, params, attributes);
        swfobject.createCSS("#flashContent", "display:block;text-align:center;");
    </script>

</head>
<body>
<div style="position:absolute;left:10px;top:10px;">
    <div id="flashContent" style="text-align:center;">
        <p>
            To view this page ensure that Adobe Flash Player version
            10.0.0 or greater is installed.
        </p>
        <script type="text/javascript">
            var pageHost = ((document.location.protocol == "https:") ? "https://" : "http://");
            document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='"
                + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>");
        </script>
    </div>
    <%--<div id="errNoDocument" style="padding-top:10px;">
        Can't see the document? Running FlexPaper from your local directory? Make sure you have added FlexPaper as
        trusted. You can do that at <a
            href="http://www.macromedia.com/support/documentation/en/flashplayer/help/settings_manager04a.html#119065">Adobe's
        website</a>.
    </div>--%>
</div>
</body>
</html>