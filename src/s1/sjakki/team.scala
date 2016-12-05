package o1.robots

import o1.grid._

abstract class Team(initialName: String, val nappula: Nappula) {

  private var nameIfAny: Option[String] = None
  this.name = initialName


  /** Changes the robot's name to the given one.
    *
    * Note to students: In Scala, a method whose name ends in an underscore and an
    * equals sign -- like this one's -- can be called using a special syntax.
    * For instance, this method can be called either with the statement `bot.name_=("Bob")` 
    * or simply with an assignment statement: `bot.name = "Bob"`. You won't find many
    * uses for this yet in this introductory course, but it's nice to know nevertheless. */
  def name_=(newName: String) = {
    this.nameIfAny = if (newName.trim.isEmpty) None else Some(newName)
  }


  /** Returns the name of the robot. If the robot's name has been set to the empty
    * string or contains only whitespace, returns `"Incognito"` instead. */
  def name = this.nameIfAny.getOrElse("Incognito")


  /** Moves the robot. What this means in practice depends on the type (subclass) of the
    * robot. This method assumes that it is not called if the robot is broken or stuck. */
  def moveBody()


  /** Returns the world that this robot is located in. */
  def world = this.nappula.world


  /** Returns the location of this robot in its robot world. */
  def location = this.nappula.location


  /** Returns the direction this robot is facing in. */
  def facing = this.nappula.facing


  /** Returns the coordinates that point at the square that is immediately in front
    * of this robot. */
  def locationInFront = this.location.neighbor(facing)


  /** Returns the square that is immediately in front of this robot. */
  def squareInFront = this.nappula.neighboringSquare(facing)//locationSquare


  /** Returns the team of the robot immediately in front of this robot.
    * The team is returned in an `Option`; `None` is returned if there is
    * no robot in that square or if the robot that is there has no team. */
  def robotInFront = {//eli jos vastapäätä on robotti ja sillä on aivot
    this.squareInFront.robot.flatMap { x => x.team }//x viittaa robottiin

    /*if (squareInFront.isEmpty || squareInFront.robot.get.team.isEmpty) {//Jos kysytty ruutu on tyhjä, eli ei löydy vartaloa
      None
    } else squareInFront.robot.get.team*/
  }


  /** Moves the robot one square forwards, if there is nothing there.
    * If there is any obstacle in the square, does ''not'' move, so
    * this method never causes collisions.
    * @return `true` if the move was successful, `false` if it was blocked */
  def moveCarefully() = {
    if (squareInFront.isEmpty) {//jos edessä oleva ruutu on tyhjä
    this.nappula.moveTowards(facing)
    true
    } else false
  }


  /** Returns a textual representation of the robot (which is the robot's name). */
  override def toString = this.name







}
