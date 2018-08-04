
function initMap() {
    var id, cantidad = 0;
//Indica las opciones para llamar al GPS.
    var opcionesGPS = {
        enableHighAccuracy: true,
        timeout: 5000,
        maximumAge: 0
    }

    $(document).ready(function(){
        console.log("Geolocalización Activada");
        //Obteniendo la referencia directa.
        navigator.geolocation.getCurrentPosition(function(geodata){
            var coordenadas = geodata.coords;
            var uluru = {lat: coordenadas.latitude, lng: coordenadas.longitude};

            var map = new google.maps.Map(document.getElementById("gpsPos"), {zoom:4, center: uluru});

            var marker = new google.maps.Marker({position: uluru, map: map});
            /*$("#gpsPos").text("Latitud: "+coordenadas.latitude+", Longitud: "+coordenadas.longitude+", Precisión: "+coordenadas.accuracy+" metros");*/
        }, function(){
            $("#gpsPos").text("No permite el acceso del API GEO");
        }, opcionesGPS);

    });
}