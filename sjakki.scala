/**


Miten halutaan tehdä sjakki?

- eri sääntöjä ja twistejä jossain vaiheesa.

grid hyvä alkupaikka?
->
class sjakkilauta (eli RobotWorld)
  def vuorot // kuten RobotWorld
->
Square

abstract class nappula

class jokunappula extends nappula {
  val musta/valkoinen muuttujina


*/
class ChessBoard(boardWidth: Int = 8, boardLength: Int = 8) extends Grid[Square](boardWidth, boardLength) {

private var whites = Buffer[]()//pieces
private var blacks = Buffer[]()

for (x <- 0 until this.width; y <- 0 until this.height) { // Note to students: this is one way of looping through both x and y coordinates. You could achieve the same by using two separate for loops and nesting the "y loop" within the "x loop".
  if (y == 1) {
    this.update(new Coords(x,y), Pawn(black))
  } else if (y == 6) {
    this.update(new Coords(x,y), Pawn(white))
  }
}
this.update(new Coords(0,0), Tower(white))
this.update(new Coords(7,7), Tower(white))
this.update(new Coords(7,0), Tower(white))
this.update(new Coords(0,7), Tower(white))
this.update(new Coords(1,0), Knight(white))
this.update(new Coords(6,0), Knight(white))
this.update(new Coords(1,7), Knight(white))
this.update(new Coords(6,7), Knight(white))
this.update(new Coords(2,0), Bishop(white))
this.update(new Coords(5,0), Bishop(white))
this.update(new Coords(2,7), Bishop(white))
this.update(new Coords(5,7), Bishop(white))
this.update(new Coords(3,0), Queen(white))
this.update(new Coords(4,0), King(white))
this.update(new Coords(3,7), Queen(black))
this.update(new Coords(4,7), King(black))





}
