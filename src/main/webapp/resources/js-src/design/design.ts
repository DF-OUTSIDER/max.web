/**
 * design main
 */

$(() => {
    let circle: Circle = new Circle({posX: 10, posY: 10}, 10);
    circle.move({posX: 15, posY: 15});
    circle.draw();
});