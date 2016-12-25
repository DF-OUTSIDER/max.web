/**
 * base graph
 */
interface BasePosition {
    posX: number;
    posY: number;
}

abstract class BaseGraph {
    position: BasePosition;

    constructor(position: BasePosition) {
        this.position = position;
    }

    move(pos: BasePosition) {
        this.position.posX += pos.posX;
        this.position.posY += pos.posY;
    }

    draw(ctx: CanvasRenderingContext2D) {
        console.log(`Position:x=${this.position.posX},y=${this.position.posY}`);
    }
}

class Circle extends BaseGraph {
    r: number = 0;
    movable: boolean;
    ctx: CanvasRenderingContext2D;

    constructor(position: BasePosition, r: number) {
        super(position);
        this.r = r;
        window.addEventListener('mousedown', this.mouseDown);
        window.addEventListener('mousemove', this.mouseMove);
        window.addEventListener('mouseup', this.mouseUp);
    }

    draw(ctx: CanvasRenderingContext2D) {
        this.ctx = ctx;
        ctx.beginPath();
        ctx.arc(this.position.posX, this.position.posY, this.r, 0, Math.PI * 2);
        ctx.fillStyle = 'green';
        ctx.fill();
        ctx.stroke();
    }

    mouseDown = (event: MouseEvent) => {
        this.movable = true;
    };

    mouseMove = (event: MouseEvent) => {
        if (this.movable) {
            this.position.posX = event.clientX;
            this.position.posY = event.clientY;
            this.draw(this.ctx);
        }
    };

    mouseUp = (event: MouseEvent) => {
        this.movable = false;
    };
}

class Rectangle extends BaseGraph {
    width: number;
    height: number;

    constructor(position: BasePosition, width: number, height: number) {
        super(position);
        this.width = width;
        this.height = height;
    }
}

