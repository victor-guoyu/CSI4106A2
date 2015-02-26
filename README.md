# Name: Yu Guo
# Student#: 6289928

This is a eclipse project, please import this project in to your eclipse in order to run it.

- Once the project is imported, you can simply run the project by clicking run.

- After the project is running, you will be asking which maze you would like to use. Please enter either 1 or 2 into the 

    _ - Empty, O - obstacle, S - Smiley face move path

    Maze 1:
    
                        _ _ _ O _ _ _
                        _ O _ _ O _ _
                        _ O _ _ O _ _ 
                        _ O O H O _ _ 
                        _ _ O _ _ _ _ 
                        _ _ O O O _ _ 
                        _ _ _ _ _ _ _ 
                        
    Maze 2:
    
                        _ _ _ O _ _ _ _ _ 
                        _ O _ _ O _ _ _ _ 
                        _ O _ _ O _ _ 
                        _ O O H O _ _ 
                        _ _ O _ _ _ _ 
                        _ _ O O O _ _ 
                        _ _ _ _ _ O _ 
                        _ _ _ _ _ _ _ _ _ 
                        _ _ _ _ _ _ _ _ _ 
                        

- After you have entered which maze you'd like to play, the program will ask you to enter the position for smily face from #1 - #4, the position is seperated by comma. For example if you want to place a smily face at row 3 column 4, you can enter 3, 4

- Here is a sample output from the program

        Please select the maze, enter 1 for the first maze, 2 for the second maze
        1
        Please enter the position for smiley face 1, separated by comma
         eg. 3, 4
        0,0
        Please enter the position for smiley face 2, separated by comma
         eg. 3, 4
        1,6
        Please enter the position for smiley face 3, separated by comma
         eg. 3, 4
        4,0
        Please enter the position for smiley face 4, separated by comma
         eg. 3, 4
        6,6
        E - Empty, O - obstacle, S - Smiley face move path 

        S S S O _ _ _ 
        _ O S S O _ _ 
        _ O _ S O _ _ 
        _ O O S O _ _ 
        _ _ O _ _ _ _ 
        _ _ O O O _ _ 
        _ _ _ _ _ _ _ 

        Position [0, 0] -> Position [0, 1] -> Position [0, 2] -> Position [1, 2] -> Position [1, 3] -> Position [2, 3] -> Position [3, 3]
        Total cost form Position [0, 0] to home: 6

        E - Empty, O - obstacle, S - Smiley face move path 

        _ _ _ O _ _ _ 
        _ O _ _ O S S 
        _ O _ _ O S _ 
        _ O O S O S _ 
        _ _ O S S S _ 
        _ _ O O O _ _ 
        _ _ _ _ _ _ _ 

        Position [1, 6] -> Position [1, 5] -> Position [2, 5] -> Position [3, 5] -> Position [4, 5] -> Position [4, 4] -> Position [4, 3] -> Position [3, 3]
        Total cost form Position [1, 6] to home: 7

        E - Empty, O - obstacle, S - Smiley face move path 

        _ _ _ O _ _ _ 
        _ O _ _ O _ _ 
        _ O _ _ O _ _ 
        _ O O S O _ _ 
        S S O S S S _ 
        _ S O O O S _ 
        _ S S S S S _ 

        Position [4, 0] -> Position [4, 1] -> Position [5, 1] -> Position [6, 1] -> Position [6, 2] -> Position [6, 3] -> Position [6, 4] -> Position [6, 5] -> Position [5, 5] -> Position [4, 5] -> Position [4, 4] -> Position [4, 3] -> Position [3, 3]
        Total cost form Position [4, 0] to home: 12

        E - Empty, O - obstacle, S - Smiley face move path 

        _ _ _ O _ _ _ 
        _ O _ _ O _ _ 
        _ O _ _ O _ _ 
        _ O O S O _ _ 
        _ _ O S S S S 
        _ _ O O O _ S 
        _ _ _ _ _ _ S 

        Position [6, 6] -> Position [5, 6] -> Position [4, 6] -> Position [4, 5] -> Position [4, 4] -> Position [4, 3] -> Position [3, 3]
        Total cost form Position [6, 6] to home: 6

        The winner is Smiley face at Position [0, 0]
