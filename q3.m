V = [2 1 3 2 1 4 2];
W = [2 0.5 0.5 0.1 0.5 1 1.5];
C = [3 2];
cvx_begin
    variable  X(7,2) binary;
    maximize (sum(sum(V * X)))
    subject to
        sum( W * X(:, 1) ) <= C(1)
        sum( W * X(:, 2) ) <= C(2)
        sum(X(1, :)) <= 1
        sum(X(2, :)) <= 1
        sum(X(3, :)) <= 1
        sum(X(4, :)) <= 1
        sum(X(5, :)) <= 1
        sum(X(6, :)) <= 1
        sum(X(7, :)) <= 1
cvx_end
X


