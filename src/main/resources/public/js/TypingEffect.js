var i = 0;
var text = "“If you are working on something that you really care about, you don’t have to be pushed. The vision pulls you.” – Steve Jobs";
var speed = 60;

function TypeWriter(){
    if(i < text.length){
        document.getElementById("quoteText").innerHTML += text.charAt(i);
        i++;
        setTimeout(TypeWriter, speed);
    }
}

$(document).ready(function () {
    TypeWriter();
})