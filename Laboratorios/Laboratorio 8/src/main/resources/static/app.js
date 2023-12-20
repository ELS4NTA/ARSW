var app = (function () {

    class Point{
        constructor(x,y){
            this.x=x;
            this.y=y;
        }        
    }
    
    var stompClient = null;

    var addPointToCanvas = function (point) {        
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        ctx.beginPath();
        ctx.arc(point.x, point.y, 1, 0, 2 * Math.PI);
        ctx.stroke();
    };

    var addPolygonToCanvas = function (polygon) {
        var canvas = document.getElementById("canvas");
        var ctx = canvas.getContext("2d");
        ctx.beginPath();
        ctx.moveTo(polygon[0].x, polygon[0].y);
        for (var i = 1; i < polygon.length; i++) {
            ctx.lineTo(polygon[i].x, polygon[i].y);
        }
        ctx.closePath();
        ctx.stroke();
    };
    
    var getMousePosition = function (evt) {
        canvas = document.getElementById("canvas");
        var rect = canvas.getBoundingClientRect();
        return {
            x: evt.clientX - rect.left,
            y: evt.clientY - rect.top
        };
    };


    var connectAndSubscribe = function () {
        console.info('Connecting to WS...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);
        
        var topicId = document.getElementById("topicId").value;
        //subscribe to /topic/TOPICXX when connections succeed
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/newpoint.' + topicId, function (eventbody) {
                var newPoint = JSON.parse(eventbody.body);
                addPointToCanvas(newPoint);
                // alert("New point: " + newPoint.x + " " + newPoint.y);
            });
            stompClient.subscribe('/topic/newpolygon.' + topicId, function (eventbody) {
                var newPolygon = JSON.parse(eventbody.body);
                addPolygonToCanvas(newPolygon);
            });
        });

    };
    
    

    return {

        init: function () {
            var can = document.getElementById("canvas");
            if(window.PointerEvent) {
                can.addEventListener("click", function (evt) {
                    var offset = getMousePosition(evt);
                    app.publishPoint(offset.x, offset.y)
                });
            }
            //websocket connection
            var connectBtn = document.getElementById("connectButton");
            connectBtn.addEventListener('click', function () {
                var ctx = can.getContext("2d");
                ctx.clearRect(0, 0, canvas.width, canvas.height);
                connectAndSubscribe();
            });
        },

        publishPoint: function(px,py){
            var pt=new Point(px,py);
            console.info("publishing point at "+pt);
            var topicId = document.getElementById("topicId").value;
            //publicar el evento
            stompClient.send("/app/newpoint." + topicId, {}, JSON.stringify(pt)); 
        },

        disconnect: function () {
            if (stompClient !== null) {
                stompClient.disconnect();
            }
            setConnected(false);
            console.log("Disconnected");
        }
    };

})();