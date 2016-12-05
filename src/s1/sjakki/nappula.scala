package o1.robots

import o1.grid._

class Nappula(val world: RobotWorld, initialLocation: Coords, initialFacing: Direction) {

  /** the robot's team (if it has one) */
  var team: Option[Team] = None              // most-recent holder (it is possible for robot to change teams, but this is only done in Chapter 12.1)
  private var coordinates = initialLocation         // gatherer: changes in relation to the old location
  private var isIntact = true                       // flag: can be broken or repaired
  private var facesTowards = initialFacing          // most-recent holder (can be changed arbitrarily)


  /** Returns the coordinates that indicate the robot's current location in the robot world. */
  def location = this.coordinates

  
  /** Returns the square the robot is currently in. */
  def locationSquare = this.world(this.location)


  /** Returns a square that neighbors the robot's current location in the given direction. */
  def neighboringSquare(direction: Direction) = this.world(this.location.neighbor(direction))


  /** Returns the direction the robot is currently facing in. */
  def facing = this.facesTowards


  /** Turns the robot to face in the specified direction. */
  def spinTowards(newFacing: Direction) = {
    this.facesTowards = newFacing
  }


  /** Turns the robot 90 degrees clockwise. */
  def spinClockwise() = {
    this.spinTowards(facing.clockwise)
  }


  /** Causes the robot to malfunction (typically as the result of a collision).
    * A broken robot does not do anything during its turns.
    * @see [[fix]] */
  def destroy() = {
    this.isIntact = false
  }


  //tämä "syö" shakkinappulan
  def remove() = {
    this.locationSquare.clear()
  }



  /** Repairs a broken robot. The robot can now start taking its turns normally.
    * @see [[destroy]] */
  def fix() = {
    this.isIntact = true
  }


  /** Relocates the robot within its current world to the square next to the robot's
    * current location, in the given direction. The direction does not necessarily have
    * to be the same one that the robot is originally facing in.
    *
    * This method turns the robot to face in the direction it moves in.
    *
    * Two robots can never be in the same square; neither can a robot and a wall. If the
    * robot's would-be location is not empty, a collision occurs instead and the robot
    * does not change locations (but still turns to face whatever it collided with).
    *
    * If the moving robot collides with a wall, the robot itself breaks. If a moving robot
    * collides with another robot, the other robot breaks and the moving robot stays intact.
    *
    * @return `true` if the robot successfully changed locations, `false` if it
    *         did not (even if it changed facing) */
  def moveTowards(direction: Direction) = {
    this.spinTowards(direction)
    val targetCoordinates = this.location.neighbor(direction)
    val targetSquare = this.world(targetCoordinates)
    val managedToMove = targetSquare.addRobot(this)
    if (managedToMove) {
      this.locationSquare.clear()
      this.coordinates = targetCoordinates
    }
    managedToMove
  }


  /** Gives the robot a turn to act.
    *
    * A robot that is not mobile (as defined by `isMobile`) does not do anything during
    * its turn. A teamless robot likewise does nothing. A mobile robot with a team
    * consults its team to find out what to do. It does this by calling the team's
    * `moveBody` method.
    *
    * @see [[Team.moveBody]] */
  def takeTurn() = {
    if (this.isMobile && this.team != None) {
      this.team.foreach(_.moveBody())//.flatMap(_.team)//.getOrElse(None)//.foreach(println))//.moveBody()//TÄHÄN PALATAAN VIELÄ
      // call the team's moveBody method (if there is a team)
    }
  }


  /** Determines whether the robot is currently mobile or not. A robot is not mobile if it
    * is broken or stuck. Otherwise, the robot is considered mobile. */
  def isMobile = this.isIntact && !this.isStuck


  /** Determines whether the robot is currently broken or not, that is, if it has been
    * broken with the `destroy` method and not fixed since. */
  def isBroken = !this.isIntact


  /** Determines whether the square neighboring the robot in the given direction contains
    * a permanently unpassable obstacle (a wall, that is). Another robot is not considered an
    * unpassable obstacle for this purpose. */
  def canMoveTowards(direction: Direction) = {
    if (this.neighboringSquare(direction).isUnpassable) {// == Wall) {
      false
    } else true
  }


  /** Determines whether the robot is stuck or not, that is, if there are any squares around
    * the robot that don't contain unpassable obstacles (walls). Only the nearest squares in
    * the four main compass directions are considered. If there is a wall in all directions,
    * the robot is considered stuck. Another robot is not considered an unpassable obstacle
    * for this purpose.
    * @see [[canMoveTowards]] */
  def isStuck = {
    var stepper = 0
    for (x <- 1 to 4) {
      this.spinClockwise()
      if (!this.canMoveTowards(facing)) {
         stepper = stepper + 1
    }
    }
    if (stepper == 4) true else false
  }

}
