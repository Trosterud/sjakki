package s1.sjakki

import o1.grid._

class King (name: String, attemptMove: Coords, team: Team) extends Nappula(team) {

  var currentLocation = this.location

  //Bishopista
    var checkSquare = this.location//tästä lähdetään liikkeelle ja tätä liikutetaan diagonaalisti jokaiseen ilmansuuntaan vuorotellen
    var diagonalCompass = Direction.ClockwiseDiagonal//Vectori jossa diagonaalit ilmansuunnat
    var compassIndex = 0 //käy läpi 0,1,2,3 eli kaikki diagonaalit
    var isOkToMove = false //tarkastuksen tulos, oletuksena FALSE
    var firstEnemy = false //pitää kirjaa onko kohdattu vihollinen ensimmäinen, eli estääe ettei vihun yli "hypätä" sallittuja liikkeitä laskiessa.

  //Towerista
    var compass = Direction.Clockwise//Vectori jossa ilmansuunnat


def canMove(attemptMove): Boolean = { //ONKO SIIRTO SÄÄNTÖJEN MUKAINEN, jos on niin palauttaa TRUE, jos ei niin FALSE
  //Bishopin ilmansuunnille
  while (compassIndex <= 3 && isOkToMove == false) {
    diagonal = diagonalCompass(compassIndex)
    checkSquare = checkSquare.neighbor(diagonal)//liikutetaan tarkastusruutua
            if (this.world.elementAt(checkSquare) == attemptMove) {
            isOkToMove = TRUE
            }
      checkSquare = this.location
      compassIndex = compassIndex + 1
  }
  //Towerin ilmansuunnille
  compassIndex = 0
  while (compassIndex <= 3 && isOkToMove == false) {
      ilmansuunta = compass(compassIndex)
      checkSquare = checkSquare.neighbor(ilmansuunta)//liikutetaan tarkastusruutua
              if (this.world.elementAt(checkSquare) == attemptMove) {
              isOkToMove = TRUE //attemptMove löytyi sallitulta alueelta. Palautetaan TRUE ja lopetetaan etsintä
              }
        checkSquare = this.location
        compassIndex = compassIndex + 1
  }
  isOkToMove //Palautetaan etsinnän tulos
}


  //"päämetodi" Tämä kutsuu canMove metodia ja liikuttaa nappulaa sinne, jos siirto on sääntöjen mukainen. Syö myös vastustajan nappulan tarvittaessa.
    def move() = {
      if (canMove(attemptMove)) {
            if (this.world.elementAt(attemptMove).NAPPULA.team == this.team) {//jos halutussa ruudussa on oma nappula (NAPPULA = robotbody)
            println("Can't move. The square is already occupied by your own team member")
          } else if (this.world.elementAt(attemptMove).NAPPULA.team != this.team) {  //jos halutussa ruudussa on vihollis nappula (NAPPULA = robotbody)
            this.world.elementAt(attemptMove).NAPPULA.remove//SYÖDÄÄN VASTUSTAJA
            this.location = attemptMove //LIIKUTAAN RUUTUUN
          } else if (this.world.elementAt(attemptMove).NAPPULA.isEmpty) { //ruutu on tyhjä
            this.location = attemptMove //LIIKUTAAN RUUTUUN
          } else println("Can't move!")
      } else ("Rule violation, can't move!") //siirto ei ole sääntöjen mukainen
  }






}
