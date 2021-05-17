# TicTacToe

Written TicTacToe on my nephew's demand so they can play this on their free time.
This implementation allows us to play TicTacToe of any grid length in addition to standard 3X3

Game can be played in Single player mode against System or in 2 player mode.
When played in Single player mode, keep in mind that you are playing against dumbest System ever. It even hangs while finding a appropriate position sometimes! :)

Board size can be changed passing different size to constructor of Board

Input is taken as *horizontal,vertical* cordinates format

e.g.: 
```    Lucky is playing with pin 'O'
    Enter input : 
    1,2             // <--- horizontally on 1st grid, vertically on 2nd grid
    Board is 
    ┏━━━┳━━━┳━━━┳━━━┓
    ┃   ┃ O ┃   ┃   ┃
    ┣━━━╋━━━╋━━━╋━━━┫
    ┃   ┃   ┃   ┃   ┃
    ┣━━━╋━━━╋━━━╋━━━┫
    ┃   ┃   ┃   ┃   ┃
    ┣━━━╋━━━╋━━━╋━━━┫
    ┃   ┃   ┃   ┃   ┃
    ┗━━━┻━━━┻━━━┻━━━┛
```
