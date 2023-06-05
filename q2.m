V = [2 1 3 2 2];
S = [1 1 0
    0 0 1
    1 0 1
    0 1 1
    0 1 0];
B = [1 2 3];
cvx_begin
    variable  A(1,5) nonnegative;
    maximize sum(V .* A)
    subject to
        for i = 1:5
            A(i)*i <= 10
        end
        sum(A * S(:, 1) ) <= B(1)
        sum(A * S(:, 2) ) <= B(2)
        sum(A * S(:, 3) ) <= B(3)
cvx_end
A

