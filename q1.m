cvx_begin
    variable  x 
    variable  y 

    maximize ( 2*x + y ) 
    subject to
        for i = 1:5
            z = 2*i*x
        end
        -3*x + 2*y <= 5
        -x - 2*y <= -2
        5*x + 2*y <= 17
        z < 10
cvx_end
x
y

