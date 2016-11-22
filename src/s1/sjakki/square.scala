package s1.sjakki

class square {
 
  private var occupant: Option[piece] = None
  
  /** Returns `true` if the floor square has no piece in it, `false` otherwise. */
  def isEmpty = occupant == None

  /** Returns the piece occupying the square, wrapped in an `Option`, or `None`, 
  * if there is no piece in the square. */
  def nappula: Option[piece] = this.occupant

  /** Removes any piece from the square (if there was one there to begin with). */
  def clear(): Unit = {
    this.occupant = None
  }

  /** Adds the given piece to the square, if possible.
    * @param arrivee  the piece arriving in the square
    * @return `true` if `arrivee` was successfully placed in the square, else `false` */
 def addpiece(arrivee: piece): Boolean = {
   if (this.isEmpty) {
     this.occupant = Some(arrivee)
     true
   } else {
//     this.occupant.foreach(_.destroy())
     false
   }
 }
}  
  
}