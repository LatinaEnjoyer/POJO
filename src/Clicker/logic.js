let button = document.getElementById("btn");
let count = 0;
let counterText = document.getElementById("counter");
let clickSound = new Audio("dry-fart.mp3");

button.addEventListener("click", function () {
    console.log("click");
    count++;
    counterText.textContent = count;
    clickSound.play();
});



