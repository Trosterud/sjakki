package o1.robots

import o1.grid._

//Nappula = RobotBody
//Team = RobotBrain

class Queen(name: String, attemptMove: Coords, team: Team, nappula: Nappula) extends Team(name, nappula) {

  var currentLocation = this.location

  //Bishopista
  var checkSquare = this.location //tästä lähdetään liikkeelle ja tätä liikutetaan diagonaalisti jokaiseen ilmansuuntaan vuorotellen
  var diagonalCompass = Direction.ClockwiseDiagonal //Vectori jossa diagonaalit ilmansuunnat
  var compassIndex = 0 //käy läpi 0,1,2,3 eli kaikki diagonaalit
  var isOkToMove = false //tarkastuksen tulos, oletuksena FALSE
  var firstEnemy = false //pitää kirjaa onko kohdattu vihollinen ensimmäinen, eli estääe ettei vihun yli "hypätä" sallittuja liikkeitä laskiessa.

  //Towerista
  var compass = Direction.Clockwise //Vectori jossa ilmansuunnat

  //tarkastaa saako lähetti liikkua attemptMove:n suuntaan. Palauttaa true, jos checkSquare osuu attemptSquareen, eli attemptMove löytyy "sallitulta" alueelta.
  def canMove(attemptMove: Coords): Boolean = { //ONKO SIIRTO SÄÄNTÖJEN MUKAINEN, jos on niin palauttaa TRUE, jos ei niin FALSE
    //Käy läpi kaikki BISHOPIN ilmansuunnat
    while (compassIndex <= 3 && isOkToMove == false) {
      var diagonal = diagonalCompass(compassIndex)
      //Tämä pätkä liikuttaa checkSquarea diagonaalisti kunnes löytyy ruutu joka ei ole tyhjä (tai seinö?)
      do {
        checkSquare = checkSquare.neighbor(diagonal) //liikutetaan tarkastusruutua
        if (this.world.elementAt(attemptMove).nappula.get.team != this.team) { //jos checkSquaressa on vihu, niin muuttaa trueksi, jolloin otetaan seuraava ilmansuunta, mutta ensimmäinen kohdattu vihu on sallitulla liikealueella
          firstEnemy = true
        }
        if (this.world.elementAt(checkSquare) == attemptMove) { //ONKOHAN ATTEMPTMOVE "ELEMENTAT"???
          isOkToMove = true //attemptMove löytyi sallitulta alueelta. Palautetaan TRUE ja lopetetaan etsintä
        }
      } while ((this.world.elementAt(checkSquare).isEmpty || this.world.elementAt(attemptMove).nappula.get.team != this.team) && (this.firstEnemy == false)) //loopataan jos ruutu on tyhjä tai siinä on  ensimmäinen vihollinen
      //Ruudussa oli joku muu, tai ensimmäinen vihu, aloitetaan alusta uudella suunnalla
      checkSquare = this.location
      compassIndex = compassIndex + 1
      firstEnemy = false
    }
    //Käy läpi kaikki TOWERIN  ilmansuunnat
    compassIndex = 0
    while (compassIndex <= 3 && isOkToMove == false) {
      var ilmansuunta = compass(compassIndex)
      //Tämä pätkä liikuttaa checkSquarea kunnes löytyy ruutu joka ei ole tyhjä (tai seinö?)
      do {
        checkSquare = checkSquare.neighbor(ilmansuunta) //liikutetaan tarkastusruutua
        if (this.world.elementAt(attemptMove).nappula.get.team != this.team) { //jos checkSquaressa on vihu, niin muuttaa trueksi, jolloin otetaan seuraava ilmansuunta, mutta ensimmäinen kohdattu vihu on sallitulla liikealueella
          firstEnemy = true
        }
        if (this.world.elementAt(checkSquare) == attemptMove) { //ONKOHAN ATTEMPTMOVE "ELEMENTAT"???
          isOkToMove = true //attemptMove löytyi sallitulta alueelta. Palautetaan TRUE ja lopetetaan etsintä
        }
      } while ((this.world.elementAt(checkSquare).isEmpty || this.world.elementAt(attemptMove).nappula.get.team != this.team) && (this.firstEnemy == false)) //loopataan jos ruutu on tyhjä tai siinä on  ensimmäinen vihollinen
      //Ruudussa oli joku muu, tai ensimmäinen vihu, aloitetaan alusta uudella suunnalla
      checkSquare = this.location
      compassIndex = compassIndex + 1
      firstEnemy = false
    }
    isOkToMove //Palautetaan etsinnän tulos
  }

  def move() = {
    if (canMove(attemptMove)) {
      if (this.world.elementAt(attemptMove).nappula.get.team == this.team) { //jos halutussa ruudussa on oma nappula (NAPPULA = robotnappula)
        println("Can't move. The square is already occupied by your own team member")
      } else if (this.world.elementAt(attemptMove).nappula.get.team != this.team && this.world.elementAt(attemptMove).isEmpty) { //jos halutussa ruudussa on vihollis nappula (NAPPULA = robotnappula)
        this.world.elementAt(attemptMove).nappula.get.locationSquare.clear() //SYÖDÄÄN VASTUSTAJA, tämä ei taida toimia
        this.nappula.locationSquare.clear() //tyhjennetään lähtöruutu
        new Queen(name: String, attemptMove: Coords, team: Team, nappula: Nappula) //LIIKUTAAN RUUTUUN, eli ruutuun luodaan uusi nappula
      } else if (this.world.elementAt(attemptMove).isEmpty) { //ruutu on tyhjä
        this.nappula.locationSquare.clear() //tyhjennetään lähtöruutu
        new Queen(name: String, attemptMove: Coords, team: Team, nappula: Nappula) //LIIKUTAAN RUUTUUN
      } else println("Can't move!")
    } else ("Rule violation, can't move!") //siirto ei ole sääntöjen mukainen
  }

}
