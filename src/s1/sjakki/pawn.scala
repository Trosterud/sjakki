package s1.sjakki

import o1.grid._

class Pawn (name: String, attemptMove: Coords, team: Team) extends Nappula(team) {


  var currentLocation = this.location

  var checkSquare = this.location//tästä lähdetään liikkeelle ja tätä liikutetaan diagonaalisti jokaiseen ilmansuuntaan vuorotellen
  var diagonalCompass = Direction.ClockwiseDiagonal//Vectori jossa diagonaalit ilmansuunnat
  var compassIndex = 0 //käy läpi 0,3 eli koillinen ja luode
  var isOkToMove = false //tarkastuksen tulos, oletuksena FALSE

  //ensimmäistä siirtoa varten
  var compass = Direction.Clockwise
  var isFirstMove = true


//tarkastaa saako PAWN liikkua attemptMove:n suuntaan. Palauttaa true, jos checkSquare osuu attemptSquareen, eli attemptMove löytyy "sallitulta" alueelta.
  def canMove(attemptMove): Boolean = { //ONKO SIIRTO SÄÄNTÖJEN MUKAINEN, jos on niin palauttaa TRUE, jos ei niin FALSE
  //Käy läpi ensimmäisen siirron
  if (isFirstMove == true) {
  upwards = compass(0)
  checkSquare = checkSquare.neighbor(upwards)
    if (this.world.elementAt(checkSquare) == attemptMove || this.world.elementAt(checkSquare.neighbor(upwards))) { //katsoo kaksi ensimmäistä ruutua
      isOkToMove = TRUE //attemptMove löytyi sallitulta alueelta. Palautetaan TRUE ja lopetetaan etsintä
      isFirstMove = false
    }
} else {
  //Käy läpi koillisen ja luoteen
  checkSquare = this.location
  while (compassIndex <= 3 && isOkToMove == false) { //tämä loopataan kahdesti, koska kaksi ilmansuuntaa
        diagonal = diagonalCompass(compassIndex)
        checkSquare = checkSquare.neighbor(diagonal) //Tämä pätkä liikuttaa checkSquarea diagonaalisti yhden kerran
              if (this.world.elementAt(checkSquare) == attemptMove && this.world.elementAt(checkSquare).NAPPULA.team != this.team ) {
              isOkToMove = TRUE //attemptMove löytyi sallitulta alueelta. Palautetaan TRUE ja lopetetaan etsintä
              }
        checkSquare = this.location
        compassIndex = compassIndex + 3 //tarkastetaan vain koillinen ja luode
  }
  //tarkastaa liikkumisen suoraan ylöspäin
  if (isOkToMove == false) {
    upwards = compass(0)//pohjoinen
    checkSquare = checkSquare.neighbor(upwards)
      if (this.world.elementAt(checkSquare) == attemptMove) {
        isOkToMove = TRUE
      }
  }
  }
  //Palautetaan etsinnän tulos
  isOkToMove
  }


  //"päämetodi" Tämä kutsuu canMove metodia ja liikuttaa nappulaa sinne, jos siirto on sääntöjen mukainen. Syö myös vastustajan nappulan tarvittaessa.
      def move() = {
        if (canMove(attemptMove)) {
              if (this.world.elementAt(attemptMove).NAPPULA.team == this.team) {//jos halutussa ruudussa on oma nappula (NAPPULA = robotbody)
              println("Can't move. The square is already occupied by your own team member")
            } else if (this.world.elementAt(attemptMove).NAPPULA.team != this.team && this.world.elementAt(checkSquare.neighbor(diagonalCompass(0))) || this.world.elementAt(checkSquare.neighbor(diagonalCompass(3))) ) {  //vihollinen ja viistossa oikealle tai vasemmalle)
              this.world.elementAt(attemptMove).NAPPULA.remove//SYÖDÄÄN VASTUSTAJA
              this.location = attemptMove //LIIKUTAAN RUUTUUN
            } else if (this.world.elementAt(attemptMove).NAPPULA.isEmpty && this.world.elementAt(checkSquare.neighbor(compass(0)))) { //ruutu on tyhjä ja se on ylöspäin
              this.location = attemptMove //LIIKUTAAN RUUTUUN
            } else println("Can't move!")
        } else ("Rule violation, can't move!") //siirto ei ole sääntöjen mukainen
  }




}
