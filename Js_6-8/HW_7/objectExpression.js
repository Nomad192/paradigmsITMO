function superFunc (f, func) {
	return function SuperClass (...argv) {
		this.evaluate = function(...argvUnknowns) {
			let result = [];
			for (const a in argv) {
				result[a] = (argv[a]).evaluate(...argvUnknowns);
			}
			return f(...result);
		};
		this.toString = function() { 
			let string = ''
			for (const a of argv) {
				string += (a.toString() + ' ')
			}
			string += (func)
			return string
		};
	}
}

let Add 		= superFunc(((a, b) => a + b), "+");
let Subtract 	= superFunc(((a, b) => a - b), "-");
let Multiply 	= superFunc(((a, b) => a * b), "*");
let Divide 		= superFunc(((a, b) => a / b), "/");
let Negate 		= superFunc(((a) => -a), "negate");
let Min3 		= superFunc(((...argv) => Math.min(... argv)), "min3");
let Max5 		= superFunc(((...argv) => Math.max(... argv)), "max5");

function Const (a) {
	this.evaluate = function(...argvUnknowns) {return a};
	this.toString = function()  {return (a + "");};
} 

function unknownToNumber (string) {
	if   	(string === 'x') {return 0;}
	else if (string === 'y') {return 1;}
	else if (string === 'z') {return 2;}
	else {return -1;}
}

function Variable (a) {
	this.evaluate = function(...argvUnknowns) {
		return argvUnknowns[unknownToNumber(a)];
	};
	this.toString = function()  {return a};
}

function multi_pop(stack, n)
{
	let result = [];
	for (let i = 0; i < n; i++)
		result.unshift(stack.pop());
	return result;
}

function parse(input) {
	let split = input.trim().split(/\s+/);
	//console.log(split);

	let stack = [];

	for (let i = 0; i < split.length; i++)
	{
		//console.log(split[i]);
		switch(split[i])
		{
			case "+": stack.push(new Add        (... multi_pop(stack, 2))); break;
			case "-": stack.push(new Subtract	(... multi_pop(stack, 2))); break;
			case "/": stack.push(new Divide		(... multi_pop(stack, 2))); break;
			case "*": stack.push(new Multiply	(... multi_pop(stack, 2))); break;
			case "min3": stack.push(new Min3	(... multi_pop(stack, 3))); break;
			case "max5": stack.push(new Max5	(... multi_pop(stack, 5))); break;

			case "negate": stack.push(new Negate(stack.pop())); break;			
			default:
			{
				let p = split[i];
				if(p === "x" || p === "y" || p === "z")
					stack.push(new Variable(p));
				else
					stack.push(new Const(parseInt(p)));
				break;
			}
		}
		//console.log(stack);		
	}
	return stack.pop();
}