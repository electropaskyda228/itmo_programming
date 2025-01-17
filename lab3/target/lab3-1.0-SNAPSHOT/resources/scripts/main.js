points = [];
canvas = document.getElementById("canvas");
ctx = canvas.getContext('2d');

let R = 2;
let width = canvas.width;
let height = canvas.height;
let riska = height / 6;


// Check valid R
function validR(r_field) {
    return (r_field <= 5 && r_field >= 1) && r_field != null && !isNaN(Number(r_field));
}

// Check valid X
function validX(x_field) {
    return (x_field >= -3 && x_field <= 3) && x_field != null && !isNaN(Number(x_field));
}

// Check valid Y
function validY(y_field) {
    return (y_field > -5 && y_field < 3) && y_field != null && !isNaN(Number(y_field));
}

// change R
document.querySelectorAll(".r_change").forEach(button => {
    button.addEventListener("click", function () {
        const newR = parseInt(this.innerText);
        if (!validR(newR)) {
            alert("Wrong R. Use a number between 1 and 5 inclusive");
            return;
        }
        document.querySelectorAll(".r_change").forEach((button) => {
            button.style.background = "linear-gradient(white, #f0f0f0)";
        });
        this.style.backgroundColor = "red";

        updateImage(newR);
        updatePoints(newR);
        R = newR;

    });
});

function updateImage(R) {
    let rect_field = document.getElementById("rect_field");
    rect_field.setAttribute("width", R * riska);
    rect_field.setAttribute("x", width / 2);
    rect_field.setAttribute("height", R * riska);

    let triangle_field = document.getElementById("triangle_field");
    triangle_field.setAttribute("points", "" + width / 2 + " " + height / 2 + ", " + (width / 2 - R * riska) + " " + height / 2 + ", " + width / 2 + " " + (height / 2 - R * riska));

    let triangle_cycle_field = document.getElementById("triangle_cycle_field");
    triangle_cycle_field.setAttribute("points", "" + width / 2 + " " + height / 2 + ", " + (width / 2 - R * riska / 2) + " " + height / 2 + ", " + width / 2 + " " + (height / 2 + R * riska / 2));

    let cycle_field = document.getElementById("cycle_field");
    cycle_field.setAttribute("d", "M " + (width / 2 - R * riska / 2) + "," + height / 2 + " A " + R * riska / 2 + "," + R * riska / 2 + " 0 0 0 " + width / 2 + "," + (height / 2 + R * riska / 2));
}

// Work with points
canvas.addEventListener("click", function (event) {
    const rect = this.getBoundingClientRect();
    const x = event.x - rect.left;
    const y = event.y - rect.top;

    addNewPoint(x, y, R);

    drawAllPoints();

    sendDataToServer((x - width / 2) / riska, (height / 2 - y) / riska, R);
});

function sendDataToServer(x, y, r) {
    document.getElementById("svg-form:xValue").value = x;
    document.getElementById("svg-form:yValue").value = y;
    document.getElementById("svg-form:rValue").value = r;
    document.getElementById("svg-form:svgClickButton").click();
}

function clearCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
}

function addNewPoint(x, y, r) {
    points.push({
        x: x,
        y: y,
        R: r,
        color: (cheeeck(x, y, r) ? "green" : "blue"),
        radius: 5
    });
}

function cheeeck(x, y, r) {
    x = (x - width / 2) / riska;
    y = -(y - height / 2) / riska;
    if (x >= 0 && x <= r && y <= 0 && y >= -r) return true;
    if (x <= 0 && x >= -r && y >= 0 && y <= r && (r - y >= -x)) return true;
    return (x <= 0 && y <= 0 && (x * x + y * y <= r * r / 4));
}

function drawAllPoints() {
    clearCanvas();
    points.forEach(point => {
        ctx.beginPath();
        ctx.arc(point.x, point.y, point.radius, 0, Math.PI * 2);
        ctx.fillStyle = point.color;
        ctx.fill();
        ctx.closePath();
    });
}

function updatePoints(newR) {
    points = points.map(point => ({
        x: (point.x - width / 2) * newR / point.R + width / 2,
        y: (point.y - height / 2) * newR / point.R + height / 2,
        R: newR,
        color: point.color,
        radius: point.radius
    }));

    drawAllPoints();
}

// Work with formData
function checkValidForm() {
    return validY(document.getElementById("coords:y_enter").value) && validX(document.getElementById("coords:x_enter_input").value) && validR(R);
}

function alertErrorData() {
    let message = "Error: ";

    let xValue = document.getElementById("coords:x_enter").innerText;
    if (xValue == null) {
        message += "You haven't chosen an option for x.\n";
    }

    let yValue = document.getElementById("coords:y_enter").value;
    if (yValue == null) {
        message += "You haven't written a value for y.\n";
    }
    else if (yValue <= -5 || yValue >= 3 || isNaN(Number(yValue)) || yValue === "") {
        message += "The coordinate y has to be between -5 and 3 not inclusive.\n";
    }

    alert(message);
}

function updatePlane(newR) {
    const table = document.getElementById("table-answer");
    for (let i = 1; i < table.rows.length; i++) {
        const row = table.rows[i];
        if (row.cells[0].innerText) {
            addNewPoint(width / 2 + row.cells[0].innerText.trim() * riska, height / 2 - row.cells[1].innerText.trim() * riska, row.cells[2].innerText.trim());
        }
    }
    updateImage(newR);
    updatePoints(newR);
    R = newR;
}

window.addEventListener("pageshow", () => {updatePlane(R)});

document.getElementById("coords:submit_coords").addEventListener("click", (event) => {
    if (!checkValidForm()) {
        alertErrorData();
    } else {
        addNewPoint(width / 2 + document.getElementById("coords:x_enter_input").value * riska, height / 2 - document.getElementById("coords:y_enter").value * riska, R);
        drawAllPoints();
    }
});

