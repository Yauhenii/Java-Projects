function isIntersect(point, submenu) {
    console.log("cl");
    console.log(point.x);
    console.log(point.y);
    console.log(submenu.x);
    console.log(submenu.y);
    return (submenu.x <= point.x && (submenu.x + submenu.width) >= point.x) && (submenu.y <= point.y && (submenu.y + submenu.height) >= point.y);
}

function main() {
    var canvas = document.getElementById("c1");
    var ctx = canvas.getContext("2d");
    var rect = canvas.getBoundingClientRect();

    var width = rect.width / 6;
    var height = rect.height / 3;
    const submenus = [{
            x: 0 * width,
            y: height,
            color: 'rgb(209, 239, 131)',
            i: "../pages/main-page.html",
            width: width,
            height: height,
            text: 'О нас'
        },
        {
            x: 1 * width,
            y: height,
            color: 'rgb(209, 239, 131)',
            i: "../pages/catalog-page.html",
            width: width,
            height: height,
            text: 'Каталог'
        },
        {
            x: 2 * width,
            y: height,
            color: 'rgb(209, 239, 131)',
            i: "../pages/take-page.html",
            width: width,
            height: height,
            text: 'Забрать'
        },
        {
            x: 3 * width,
            y: height,
            color: 'rgb(209, 239, 131)',
            i: "../pages/contacts-page.html",
            width: width,
            height: height,
            text: 'Контакты'
        },
        {
            x: 4 * width,
            y: height,
            color: 'rgb(209, 239, 131)',
            i: "https://www.pitomec.ru/forum",
            width: width,
            height: height,
            text: 'Форум'
        },
        {
            x: 5 * width,
            y: height,
            color: 'rgb(209, 239, 131)',
            i: "https://ru.wikipedia.org/wiki/Приют_для_бездомных_животных",
            width: width,
            height: height,
            text: 'Вики'
        }
    ];

    submenus.forEach(submenu => {
        ctx.beginPath();
        ctx.rect(submenu.x, submenu.y, width, height);
        ctx.fillStyle = submenu.color;
        ctx.fill();
        ctx.fillStyle = 'rgb(255, 0, 0)';
        ctx.font = "16px Bradley Hand";
        ctx.fillText(submenu.text, submenu.x + 10, submenu.y + 2 * height / 3);
    });

    canvas.addEventListener('mousemove', (e) => {
        const pos = {
            x: e.clientX - rect.left,
            y: e.clientY - rect.top
        };
        submenus.forEach(submenu => {
            if (isIntersect(pos, submenu)) {
                ctx.beginPath();
                ctx.rect(submenu.x, submenu.y, width, height);
                ctx.fillStyle = 'rgb(166, 188, 109)';
                ctx.fill();
                ctx.fillStyle = 'rgb(255, 0, 0)';
                ctx.font = "16px Bradley Hand";
                ctx.fillText(submenu.text, submenu.x + 10, submenu.y + 2 * height / 3);
            } else {
                ctx.beginPath();
                ctx.rect(submenu.x, submenu.y, width, height);
                ctx.fillStyle = 'rgb(209, 239, 131)';
                ctx.fill();
                ctx.fillStyle = 'rgb(255, 0, 0)';
                ctx.font = "16px Bradley Hand";
                ctx.fillText(submenu.text, submenu.x + 10, submenu.y + 2 * height / 3);
            }
        });
    });

    canvas.addEventListener('click', (e) => {
        const pos = {
            x: e.clientX - rect.left,
            y: e.clientY - rect.top
        };
        submenus.forEach(submenu => {
            if (isIntersect(pos, submenu)) {
                window.open(submenu.i, "_self")
            }
        });
    });

}