package s1.sjakki

class square {
  
  
  
package o1.robots

/** The trait `Square` represents squares in a robot world as an abstract concept.
  *
  * A square object is potentially mutable: its state can change as robots enter and exit it.
  *
  * Two concrete kinds of squares have been implemented: `Floor`s and `Wall`s.
  *
  * @see [[Floor]]
  * @see [[Wall]] */
Class Square {

  private var occupant: Option[RobotBody] = None
  
  /** Returns `true` if the floor square has no robot in it, `false` otherwise. */
  def isEmpty = occupant == None

    /** Returns the robot occupying the square, wrapped in an `Option`, or `None`, 
    * if there is no robot in the square. */
  def nappula: Option[RobotBody] = this.occupant

  /** Removes any robot from the square (if there was one there to begin with). */
  def clear() = {
    this.occupant = None
  }

  /** Adds the given robot to the square, if possible. If there is something
    * already in the square, a collision happens instead.
    * @param arrivee  the robot arriving in the square
    * @return `true` if `arrivee` was successfully placed in the square, `false` if a collision occurred */
  def addRobot(arrivee: RobotBody): Boolean

}





  


  

  
  /** Adds the given robot to the floor square, if possible. The arrival will fail,
    * if the square already had an occupant. In that case, the occupant of the 
    * square is destroyed (but the one that crashed into it stays intact).
    * @param arrivee  the robot attempting to arrive in the square
    * @return `true` if `arrivee` was successfully placed in the square, `false` if a collision occurred */
 def addRobot(arrivee: RobotBody) = {
   if (this.isEmpty) {
     this.occupant = Some(arrivee)
     true
   } else {
     this.occupant.foreach(_.destroy())
     false
   }
 }

}
  
  
  
  
}