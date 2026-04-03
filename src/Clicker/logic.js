let button = document.getElementById("btn");
let count = 0;
let counterText = document.getElementById("counter");

button.addEventListener("click", function () {
    console.log("click");
    count++;
    counterText.textContent = count;
});



