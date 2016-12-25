/**
 * design main
 */

$(() => {
    let canvas: HTMLCanvasElement = <HTMLCanvasElement>document.getElementById('canvas');
    if (canvas.getContext) {
        let ctx: CanvasRenderingContext2D = canvas.getContext('2d');

        let circle: Circle = new Circle({posX: 10, posY: 10}, 10);
        circle.move({posX: 15, posY: 15});
        circle.draw(ctx);
    }
});