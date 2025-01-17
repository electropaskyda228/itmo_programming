let timeSpace = 10000;
document.addEventListener("DOMContentLoaded",function() {
    updateTime();
    setInterval(updateTime, timeSpace);
});

function updateTime() {
    let currentDate = new Date();
    let hours = currentDate.getHours();
    let minutes = currentDate.getMinutes();
    let seconds = currentDate.getSeconds();

    if (hours < 10) { hours = "0" + hours; }
    if (minutes < 10) { minutes = "0" + minutes; }
    if (seconds < 10) { seconds = "0" + seconds; }

    document.getElementById("time").innerText = hours + ":" + minutes + ":" + seconds;
}