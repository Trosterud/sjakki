package s1.sjakki

class square {
 
  private var occupant: Option[RobotBody] = None
  
  /** Returns `true` if the floor square has no robot in it, `false` otherwise. */
  def isEmpty = occupant == None

  /** Returns the robot occupying the square, wrapped in an `Option`, or `None`, 
  * if there is no robot in the square. */
  def nappula: Option[RobotBody] = this.occupant

  /** Removes any robot from the square (if there was one there to begin with). */
  def clear(): Unit = {
    this.occupant = None
  }

  /** Adds the given robot to the square, if possible. If there is something
    * already in the square, a collision happens instead.
    * @param arrivee  the robot arriving in the square
    * @return `true` if `arrivee` was successfully placed in the square, `false` if a collision occurred */
 def addRobot(arrivee: RobotBody): Boolean = {
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