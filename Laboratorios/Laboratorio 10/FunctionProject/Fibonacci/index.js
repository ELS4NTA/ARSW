var bigInt = require("big-integer");

function fiboM(n, M) {
    return n <= 1 ? bigInt(n) : fiboMemo(n - 1, M).add(fiboMemo(n - 2, M));
}

function fiboMemo(n, M) {
    if (M[n] !== undefined) {
        return M[n];
    }
    M[n] = fiboM(n, M);
    return M[n];
}

module.exports = async function (context, req) {
    context.log('JavaScript HTTP trigger function processed a request.');

    let nth = req.body.nth;
    var M = {};

    if (nth < 0) {
        throw 'must be greater than 0';
    }

    let answer = fiboMemo(nth, M);

    context.res = {
        body: answer.toString()
    };
};