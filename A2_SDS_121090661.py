import random
import turtle

boundary = turtle.Screen()     #Create the screen
boundary.setup(430,630,600,100)    
boundary.bgcolor('white')
boundary.title('Flipping Color')
boundary.tracer(0)

colorList = ['red','blue','yellow','purple','orange']     #Use this list[i] to represent different color
colorSequence = []      #Use this list to store the color of tiles
board = []      #Use this list to store each Turtle as tile
gameOver = False      #judge whether the game is over

t = turtle.Turtle()   #Use object Turtle to create tiles
t.shape('square')
t.shapesize(3,3,2)
t.penup()
t.goto(-128,200)

rowScope = [range(-158,-97),range(-94,-33),range(-30,31),range(34,95),range(98,159)]       #range of clickable tiles
colScope = [range(-86,-25),range(-22,39),range(42,103),range(106,167),range(170,231)]       #range of clickable tiles
setScope = range(-230,-171)     #range of clickable color set

def createBoard():      #Use clone() to multiply tile objects
    for i in range(5):       
        for j in range(5):
            board.append(t.clone())
            randomNumber = random.randint(0,4)      #create a random number representing color
            colorSequence.append(randomNumber)      #store the number mentioned above
            board[i*5+j].color('white',colorList[randomNumber])
            board[i*5+j].stamp()    #Use stamp() to show the tile on screen
            t.fd(64)
        t.right(90)
        t.fd(64)
        t.right(90)
        t.fd(320)
        t.right(180)

def createColorSet():   #Use clone() to multiply color set objects
    t.goto(-128,-200)
    t.shapesize(3,3,3)
    for i in range(5):
        t.color('black',colorList[i])
        t.stamp()
        t.fd(64)
    turtle.penup()
    turtle.goto(-110,-140)

def createWhiteSquare():    #Create a white rectangle to block reports
    t.goto(0,-130)
    t.shapesize(3,20,3)
    t.color('white')
    t.stamp()


def flipColor(row, col, orig, to):      #The logic of flipping color (using recursion)
    
    if orig == to:
        return colorSequence
    if row < 0 or row > 4:
        return
    if col < 0 or col > 4:
        return

    idx = row*5 + col
    if colorSequence[idx] != orig:
        return

    colorSequence[idx] = to
    flipColor(row-1, col,  orig, to)
    flipColor(row+1, col,  orig, to)
    flipColor(row, col-1,  orig, to)
    flipColor(row, col+1,  orig, to)
    
    return 

def fxn(x,y):
    global g_rows
    global g_columns
    global g_origin
    global g_selectedColor
    global k
    global colorSequence
    global gameOver

    for i in rowScope:      #judge whether the mouse click is on tiles
        if x in i:
            for j in colScope:
                if y in j:
                    number = int(5*((230-y)//64) + (x+158)//64)     #Calculate the number of tiles
                    for k in range(25):
                            if k == number:
                                board[k].color('black',colorList[colorSequence[k]])     #Turn the selected tile border black
                                board[k].stamp()
                                g_rows = int((230-y)//64)       #Record the number of rows of the tile
                                g_columns = int((x+158)//64)       #Record the number of columns of the tile
                                g_origin = colorSequence[k]     #record the color of the tile
                            else:
                                board[k].color('white',colorList[colorSequence[k]])     #Turn the other tiles border white
                                board[k].stamp()

    if y in setScope:       #judge whether the mouse click is on color set
        for i in rowScope:
            if x in i:
                try:
                    g_selectedColor = int((x+158)//64)      #Calculate the number of colors
                    flipColor(g_rows, g_columns, g_origin, g_selectedColor)     #The function of flipping color
                    if not gameOver:
                                createWhiteSquare()     #Create a white rectangle to block reports
                    for j in range(25):
                        board[j].color('white',colorList[colorSequence[j]])     #Turn the tiles borders white
                        board[j].stamp()
                    if len(set(colorSequence)) == 1:    #Judge whether the game is over
                        gameOver = True
                        turtle.goto(-70,-140)
                        turtle.write('Congratulations!',font=("Arial", 16))
                except:
                    turtle.write('Please choose a tile first',font=("Arial", 16))        

if __name__ == '__main__':
    createBoard()
    createColorSet()
    boundary.onclick(fxn)
    boundary.mainloop()