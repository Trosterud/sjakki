package o1.robots

import o1.grid._

//Nappula = RobotBody
//Team = RobotBrain

class Pawn(name: String, attemptMove: Coords, team: Team, nappula: Nappula) extends Team(name, nappula) {

  var currentLocation = this.location

  var checkSquare = this.location//tästä lähdetään liikkeelle ja tätä liikutetaan diagonaalisti jokaiseen ilmansuuntaan vuorotellen
  var diagonalCompass = Direction.ClockwiseDiagonal//Vectori jossa diagonaalit ilmansuunnat
  var compassIndex = 0 //käy läpi 0,3 eli koillinen ja luode
  var isOkToMove = false //tarkastuksen tulos, oletuksena FALSE

  //ensimmäistä siirtoa varten
  var compass = Direction.Clockwise
  var isFirstMove = true


//tarkastaa saako PAWN liikkua attemptMove:n suuntaan. Palauttaa true, jos checkSquare osuu attemptSquareen, eli attemptMove löytyy "sallitulta" alueelta.
  def canMove(attemptMove: Coords): Boolean = { //ONKO SIIRTO SÄÄNTÖJEN MUKAINEN, jos on niin palauttaa TRUE, jos ei niin FALSE
  //Käy läpi ensimmäisen siirron
  if (isFirstMove == true) {
  var upwards = compass(0)
  checkSquare = checkSquare.neighbor(upwards)
    if (this.world.elementAt(checkSquare) == attemptMove || this.world.elementAt(checkSquare.neighbor(upwards)) == attemptMove) { //katsoo kaksi ensimmäistä ruutua
      isOkToMove = true //attemptMove löytyi sallitulta alueelta. Palautetaan TRUE ja lopetetaan etsintä
      isFirstMove = false
    }
} else {
  //Käy läpi koillisen ja luoteen
  checkSquare = this.location
  while (compassIndex <= 3 && isOkToMove == false) { //tämä loopataan kahdesti, koska kaksi ilmansuuntaa
        var diagonal = diagonalCompass(compassIndex)
        checkSquare = checkSquare.neighbor(diagonal) //Tämä pätkä liikuttaa checkSquarea diagonaalisti yhden kerran
              if (this.world.elementAt(checkSquare) == attemptMove && this.world.elementAt(attemptMove).nappula.get.team != this.team) {
              isOkToMove = true //attemptMove löytyi sallitulta alueelta. Palautetaan TRUE ja lopetetaan etsintä
              }
        checkSquare = this.location
        compassIndex = compassIndex + 3 //tarkastetaan vain koillinen ja luode
  }
  //tarkastaa liikkumisen suoraan ylöspäin
  if (isOkToMove == false) {
    var upwards = compass(0)//pohjoinen
    checkSquare = checkSquare.neighbor(upwards)
      if (this.world.elementAt(checkSquare) == attemptMove) {
        isOkToMove = true
      }
  }
  }
  //Palautetaan etsinnän tulos
  isOkToMove
  }

  def move() = {
    if (canMove(attemptMove)) {
      if (this.world.elementAt(attemptMove).nappula.get.team == this.team) { //jos halutussa ruudussa on oma nappula (NAPPULA = robotnappula)
        println("Can't move. The square is already occupied by your own team member")
      } else if (this.world.elementAt(attemptMove).nappula.get.team != this.team && this.world.elementAt(attemptMove).isEmpty) { //jos halutussa ruudussa on vihollis nappula (NAPPULA = robotnappula)
        this.world.elementAt(attemptMove).nappula.get.locationSquare.clear() //SYÖDÄÄN VASTUSTAJA, tämä ei taida toimia
        this.nappula.locationSquare.clear() //tyhjennetään lähtöruutu
        new Pawn(name: String, attemptMove: Coords, team: Team, nappula: Nappula) //LIIKUTAAN RUUTUUN, eli ruutuun luodaan uusi nappula
      } else if (this.world.elementAt(attemptMove).isEmpty) { //ruutu on tyhjä
        this.nappula.locationSquare.clear() //tyhjennetään lähtöruutu
        new Pawn(name: String, attemptMove: Coords, team: Team, nappula: Nappula) //LIIKUTAAN RUUTUUN
      } else println("Can't move!")
    } else ("Rule violation, can't move!") //siirto ei ole sääntöjen mukainen
  }

}
