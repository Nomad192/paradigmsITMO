"use strict";

let multiFunc = (f) => (a, b) => (x, y, z) => f(a(x, y, z), b(x, y, z));

let add         = multiFunc((a, b) => a + b);
let subtract    = multiFunc((a, b) => a - b);
let multiply    = multiFunc((a, b) => a * b);
let divide      = multiFunc((a, b) => a / b);

let negate   =  (a)         =>  (x, y, z) =>    -a(x, y, z);    
let cnst     =  (value)     =>  (x, y, z) =>    value ;
let sinh     =  (a)         =>  (x, y, z) =>    Math.sinh(a(x, y, z));
let cosh     =  (a)         =>  (x, y, z) =>    Math.cosh(a(x, y, z));
let abs      =  (a)         =>  (x, y, z) =>    Math.abs(a(x, y, z));
let iff      =  (a, b, c)   =>  (x, y, z) =>    (a(x, y, z) < 0) ? c(x, y, z) : b(x, y, z);


let one      =  cnst(1);
let two      =  ()      =>  2;
let pi       =  ()      =>  Math.PI;
let e        =  ()      =>  Math.E;

let variable = (p) => {
    return (x, y, z) => {
        if (p === "x") {return x;}
        if (p === "y") {return y;}
        if (p === "z") {return z;}
    }
}