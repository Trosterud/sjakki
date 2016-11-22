package s1.sjakki

import o1.grid._
import scala.collection.mutable.Buffer

/**
- eri sääntöjä ja twistejä jossain vaiheesa.
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



  /** Causes the next robot to take a turn. The turn then immediately passes to the next robot. 
    * @see [[nextRobot]]
    * @see [[RobotBody.takeTurn]] */
  def advanceTurn() = {
    robots(turn).takeTurn
    if (turn == robots.length - 1) {
      turn = 0
    } else {
      turn += 1
    }
    this.nextRobot
  }
  
    
  
  
  /** Causes all the robots in the world to take a turn, starting with the one whose turn it is next.
    * (After this is done, the robot who originally was next up, will be that once again.)
    * @see [[nextRobot]]
    * @see [[advanceTurn]]
    * @see [[RobotBody.takeTurn]] */
  def advanceFullTurn() = {
    var length = robots.length
    while (length > 0) {
      this.advanceTurn
      length -= 1
    }    
  }





}
