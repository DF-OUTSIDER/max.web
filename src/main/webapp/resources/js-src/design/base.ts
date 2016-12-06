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

    draw() {
        console.log(`Position:x=${this.position.posX},y=${this.position.posY}`);
    }
}

class Circle extends BaseGraph {
    r: number = 0;

    constructor(position: BasePosition, r: number) {
        super(position);
        this.r = r;
    }
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

