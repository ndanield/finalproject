<#macro base>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Banagreen</title>
        <link rel="icon" type="image/x-icon"  href="/images/favicon.ico">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css" integrity="sha384-O8whS3fhG2OnA5Kas0Y9l3cfpmYjapjI0E4theH4iuMD+pLhbf6JI0jIMfYcK3yZ" crossorigin="anonymous">
        <link  href="/css/lumen.css" rel="stylesheet"/>
        <#--<link  href="https://bootswatch.com/4/lumen/bootstrap.min.css" rel="stylesheet"/>-->
        <link rel="stylesheet" type="text/css" href="/css/main.css">

        <script type="text/javascript" src="/js/jquery-3.3.1.js" ></script>
    </head>
    <body id="${ bodyBackground!"" }">

        <#nested>

        <script type="text/javascript" src="/js/popper.min.js"></script>
        <script type="text/javascript" src="/js/bootstrap.js"></script>
        <script type="text/javascript" src="/js/myGpsLocator.js"></script>
        <script type="text/javascript" src="/js/friendRequestPopover.js"></script>
        <script type="text/javascript" src="/js/city.js"></script>
        <script type="text/javascript" src="/js/jquery.validate.js"></script>
        <script type="text/javascript" src="/js/jquery.validate.additional-methods.js"></script>
        <script type="text/javascript" src="/js/register.js"></script>
        <script src="/js/postValidation.js"></script>
        <script src="/js/myAjax.js"></script>
        <script async defer
            src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDcn110_QgbCgAWXpEjN0stLtvBt9ZtqFw&callback=initMap">
        </script>

    </body>
</html>

</#macro>
