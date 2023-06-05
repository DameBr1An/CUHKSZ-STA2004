import random

def findInPans(a,list1,list2):
    global g_GuessInputValidity
    if str(a) in list1:            #judge whether the odd ball is in left pan
        print('The scale shows: Left pan is down')
    elif str(a) in list2:            #judge whether the odd ball is in right pan
        print('The scale shows: Right pan is down')
    else:                    #judge whether the odd ball is not in the scale
        print('The scale is balanced')
    g_GuessInputValidity = False

def judgeAnswers(a,b):
    global g_flag
    global g_cntNumber
    try:
        if int(a) == b:          #judge whether the guess is right
            g_flag = True
            print('\nCongratulations!!!! Scale usage count: '+str(g_cntNumber))
        else:
            print('\nYour answer is not correct!!!!')
    except:
        print('\nLet us try again!')

def next():
    global g_gameEnd
    nextStepValidity = False
    while not nextStepValidity:
        nextStep = input("You can feel free to quit(input '1') or start a new game(input '2'):")    
        if nextStep == '1':
            g_gameEnd = True
            nextStepValidity = True
        elif nextStep == "2":
            nextStepValidity = True

print("Welcome to Brian's odd-ball game!\n"+
"You are given an even number of balls,labeled,and among\n"+
"the balls one is heavier than the rest, called the odd ball.\n\n"+

"Your goal is to find out which one is the odd one.\n"+
"You are given a weighing scale!\n\n"+

"Good luck and have fun!\n")

g_gameEnd = False
while not g_gameEnd:
    while True:           #repeat until a valid input
        EnterNumber = input('Enter the number of balls for the game? ')
        if EnterNumber.isdigit():
            if eval(EnterNumber)%2 == 0 and eval(EnterNumber) > 0:
                TotalBalls = eval(EnterNumber)
                break
        print('Invalid input!!!\n')

    OddBall = random.randint(1,TotalBalls)         #create a odd ball number
    g_cntNumber = 0              #record the guess counts

    g_flag = False             #judge whether the player has found the answer yet

    while not g_flag:
        print('\nYou are rompted to enter the balls\n'+
        'to be placed on the pans of the scale,\n'+
        'seperate each ball identifier with one\n'+
        'minimum space, e.g. 1 2 3\n')
        
        InputValidity = False          #judge whether the player has entered valid number to judge
        while not InputValidity:
            left = input('Enter the ball identifier(s) to be placed on left pan: ')
            right = input('Enter the ball identifier(s) to be placed on right pan: ')
            LeftList = left.split()
            RightList = right.split()
            
            if len(LeftList) == len(RightList) and len(set(LeftList+RightList)) == len(LeftList+RightList):  #judge whether two sides have same balls or inputs contain repeat number
                InputValidity = True
                for i in (LeftList+RightList):
                    try:       #avoid invalid inputs, such as letter
                        if int(i) < 1 or int(i) > TotalBalls:       #judge whether the input number does not exist
                            InputValidity = False
                    except:
                        InputValidity = False
            
            if not InputValidity:         #enter again
                print('\nYour inputs for left:"'+left+'", right:"'+right+'"\n\n'+
                'Invalid input!!!\n\n'+
                'Please ensure correct ball identifiers (1-'+str(TotalBalls)+')\n'+
                'are entered on each pan, no duplicate balls on either\n'+
                'or both pans. Both pans should have the same number of\n'+
                'balls and must have at least one ball.')
        
        findInPans(OddBall,LeftList,RightList)
        g_cntNumber += 1

        while not g_GuessInputValidity:          #repeat until a valid guess input
            GuessAnswer = input('Enter the odd ball number or press Enter to weigh: ')
            
            try:
                if 0 < int(GuessAnswer) < TotalBalls+1:
                    g_GuessInputValidity = True
            except:    
                g_GuessInputValidity = False
                if GuessAnswer == '':
                    g_GuessInputValidity = True
        
        judgeAnswers(GuessAnswer,OddBall)
    
    next()
    
if __name__ == '__main__':
    input("Please feel free to enter anything to quit! Bye!")
