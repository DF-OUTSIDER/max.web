class Greeter {
    constructor(public greeting: string) {
    }

    greet() {
        return "<h1>" + this.greeting + "</h1>";
    }
}

var greeter = new Greeter("HELLO,WORLD");

$(function () {
    $('body').append(greeter.greet());
});