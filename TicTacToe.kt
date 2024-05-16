//Assumptions:
//Game board is a char matrix, inner list is rows, outer list is columns.
//Possible vals are ' ', 'X', 'O'
//Data validation doesn't matter
//Players are playing by actual Tic-Tac-Toe rules, e.g. O is not going twice in a row

class TicTacToe(){
    //A real class would have a bunch of members and functions here
    //Making a companion class so I don't have to actually initialize anything

        val charMatrix: Array<Array<Char>> = arrayOf(
        arrayOf('a', 'b', 'c'),
        arrayOf('d', 'e', 'f'),
        arrayOf('g', 'h', 'i')
    )
    companion object {
        fun checkIfWin(gameboard: Array<Array<Char>>): Boolean{
            //Check horizontals
            gameboard.forEach{ row ->
             	if(row.all{it == row[0]}) {
                  	println(row[0] + " wins on the horizontal!")
                    return true
                }
            }

            //Check verticals
            for(col in 0..2){
                if(gameboard[0][col] == gameboard[1][col] &&
                        gameboard[1][col] == gameboard[2][col]){
                    println(gameboard[0][col] + " wins on the vertical!")
                    return true
                }
            }

            //Check diagonals
            if(gameboard[0][0] == gameboard[1][1] &&
                        gameboard[1][1] == gameboard[2][2]){
                    println(gameboard[0][0] + " wins on the diagonal!")
                    return true
            }
            if(gameboard[0][2] == gameboard[1][1] &&
                        gameboard[1][1] == gameboard[2][0]){
                    println(gameboard[0][2] + " wins on the diagonal!")
                    return true
            }

            println("Meow. Cat's game!")
            return false
        }
    }
}

fun main() {
    //Just doing it this way for quick testing
    val vertWin: Array<Array<Char>> = arrayOf(
        arrayOf('X', ' ', ' '),
        arrayOf('X', 'O', ' '),
        arrayOf('X', 'O', ' ')
    )
    TicTacToe.checkIfWin(vertWin)

    val horizWin: Array<Array<Char>> = arrayOf(
        arrayOf('X', 'X', 'X'),
        arrayOf(' ', 'O', ' '),
        arrayOf('O', ' ', ' ')
    )
    TicTacToe.checkIfWin(horizWin)

    val diagWin: Array<Array<Char>> = arrayOf(
        arrayOf('O', 'X', 'X'),
        arrayOf(' ', 'O', ' '),
        arrayOf(' ', ' ', 'O')
    )
    TicTacToe.checkIfWin(diagWin)

    val noWin: Array<Array<Char>> = arrayOf(
        arrayOf('O', 'X', 'O'),
        arrayOf('O', 'X', 'O'),
        arrayOf('X', 'O', 'X')
    )
    TicTacToe.checkIfWin(noWin)
}
