let buttonIncrement = document.getElementById("btn-increment");
let buttonDecrement = document.getElementById("btn-decrement");
let buttonReset = document.getElementById("btn-reset");
let counterText = document.getElementById("counter");

let count = parseInt(localStorage.getItem("count")) || 0;
let highscore = parseInt(localStorage.getItem("highscore")) || 0;
let lowscore = parseInt(localStorage.getItem("lowscore")) || 0;

document.getElementById("highscore").textContent = highscore;
document.getElementById("lowscore").textContent = lowscore;

counterText.textContent = count;


// achievements
const achievementList = document.getElementById("achievements-list");
const savedAchievements = JSON.parse(localStorage.getItem("achievements"));

const achievements = [
    {name: "First click", threshold: 1, unlocked: false},
    {name: "10 clicks", threshold: 10, unlocked: false},
    {name: "67 clicks", threshold: 67, unlocked: false},
    {name: "100 clicks", threshold: 100, unlocked: false}
];

if (savedAchievements) {
    savedAchievements.forEach(ach => {
        const originalAchievements = achievements.find(a => a.name === ach.name);
        if (originalAchievements){
            originalAchievements.unlocked = ach.unlocked;
        }

        if (ach.unlocked) {
            const p = document.createElement("p");
            p.textContent = ach.name;
            achievementList.appendChild(p);
        }
    })
}


function checkAchievements() {
    achievements.forEach(ach => {
        if (!ach.unlocked && count >= ach.threshold) {
            ach.unlocked = true;

            const p = document.createElement("p");
            p.textContent = ach.name;
            achievementList.appendChild(p);

            localStorage.setItem("achievements", JSON.stringify(achievements));
        }
    })
}


// HTML interactions
buttonIncrement.addEventListener("click", function () {
    console.log("click increment");
    count++;
    counterText.textContent = count;
    localStorage.setItem("count", count);

    if (count > highscore){
        highscore = count;
        localStorage.setItem("highscore", highscore);
        document.getElementById("highscore").textContent = highscore;
    }

    checkAchievements();

})

buttonDecrement.addEventListener("click", function () {
    console.log("click decrement")
    count--;
    counterText.textContent = count;
    localStorage.setItem("count", count);

    if (count < lowscore){
        lowscore = count;
        localStorage.setItem("lowscore", lowscore);
        document.getElementById("lowscore").textContent = lowscore;
    }
})

buttonReset.addEventListener("click", function () {
    console.log("reset");
    count = 0;
    counterText.textContent = count;
    localStorage.setItem("count", count);
})



