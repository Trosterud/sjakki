package sjakki

import o1.grid._

class Bishop (name: String, attemptMove: Coords)  {

    var currentLocation = this.location
    var team = whites //or blacks

    var checkSquare = this.location
    var diagonalCompass = Direction.ClockwiseDiagonal//Vectori jossa diagonaalit ilmansuunnat
    var compassIndex = 0 //käy läpi 0,1,2,3 eli kaikki diagonaalit
    var isOkToMove = false //oletuksena FALSE


def canMove(attemptMove): Boolean = { //ONKO SIIRTO SÄÄNTÖJEN MUKAINEN, jos on niin palauttaa TRUE, jos ei niin FALSE
//Käy läpi kaikki ilmansuunnat
while (compassIndex <= 3 && isOkToMove == false) {
    diagonal = diagonalCompass(compassIndex)
      //Tämä pätkä liikuttaa checkSquarea diagonaalisti kunnes löytyy ruutu joka ei ole tyhjä (tai seinö?)
      do {
        checkSquare = checkSquare.neighbor(diagonal)
          if (this.world.elementAt(checkSquare) == attemptMove) { //ONKOHAN ATTEMPTMOVE "ELEMENTAT"???
          isOkToMove = TRUE //attemptMove löytyi sallitulta alueelta. Palautetaan TRUE ja lopetetaan etsintä
          }
      } while (this.world.elementAt(checkSquare).isEmpty)
      //Ruudussa oli joku muu, aloitetaan alusta uudella suunnalla
      checkSquare = this.location
      compassIndex = compassIndex + 1
}
isOkToMove //Palautetaan etsinnän tulos
}


    def move() = {
      if (canMove) {
            if (this.world.elementAt(attemptMove).NAPPULA.team == this.team) {//jos halutussa ruudussa on oma nappula (NAPPULA = robotbody)
            println("Can't move. The square is already occupied by your own team member")
          } else if (this.world.elementAt(attemptMove).NAPPULA.team != this.team) {  //jos halutussa ruudussa on vihollis nappula (NAPPULA = robotbody)
            this.world.elementAt(attemptMove).NAPPULA.remove//SYÖDÄÄN VASTUSTAJA
            this.location = attemptMove //LIIKUTAAN RUUTUUN
          } else if (!this.world.elementAt(attemptMove).NAPPULA.isDefined) { //ruutu on tyhjä(huomaa huutomerkki)
            this.location = attemptMove //LIIKUTAAN RUUTUUN
          } else println("Can't move!")
      } else ("Rule violation, can't move!") //siirto ei ole sääntöjen mukainen
}


}
