function isIntersect(point, circle) {
    console.log(point.x);
    console.log(point.y);
    console.log(circle.x);
    console.log(circle.y);
    return Math.sqrt((point.x - circle.x) ** 2 + (point.y - circle.y) ** 2) < circle.radius;
}

function main() {
    var canvas = document.getElementById("c1");
    var ctx = canvas.getContext("2d");

    var interval = 50;
    const circles = [{
            x: 1 * interval,
            y: 40,
            radius: 20,
            color: 'rgb(255,0,0)',
            i: "a/aindex.html"
        },
        {
            x: 2 * interval,
            y: 40,
            radius: 20,
            color: 'rgb(255,0,0)',
            i: "b/bindex.html"
        },
        {
            x: 3 * interval,
            y: 40,
            radius: 20,
            color: 'rgb(255,0,0)',
            i: "c/cindex.html",
        }
    ];

    circles.forEach(circle => {
        ctx.beginPath();
        ctx.arc(circle.x, circle.y, circle.radius, 0, 2 * Math.PI, false);
        ctx.fillStyle = circle.color;
        ctx.fill();
    });

    var rect = canvas.getBoundingClientRect();

    canvas.addEventListener('mousemove', (e) => {
        const pos = {
            x: e.clientX - rect.left,
            y: e.clientY - rect.top
        };
        circles.forEach(circle => {
            if (isIntersect(pos, circle)) {
                ctx.beginPath();
                ctx.arc(circle.x, circle.y, circle.radius, 0, 2 * Math.PI, false);
                ctx.fillStyle = 'rgb(0,255,0)';
                ctx.fill();
            } else {
                ctx.beginPath();
                ctx.arc(circle.x, circle.y, circle.radius, 0, 2 * Math.PI, false);
                ctx.fillStyle = circle.color;
                ctx.fill();
            }
        });
    });

    canvas.addEventListener('click', (e) => {
        const pos = {
            x: e.clientX - rect.left,
            y: e.clientY - rect.top
        };
        circles.forEach(circle => {
            if (isIntersect(pos, circle)) {
                window.open(circle.i, "_self")
            }
        });
    });

}