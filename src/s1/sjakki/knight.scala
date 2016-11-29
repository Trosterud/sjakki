package s1.sjakki

import o1.grid._

class Knight (name: String, attemptMove: Coords, team: Team) extends Nappula(team) {


  //Bishopista
    var checkSquare = this.location//tästä lähdetään liikkeelle ja tätä liikutetaan diagonaalisti jokaiseen ilmansuuntaan vuorotellen
    var compassIndex = 0 //käy läpi 0,1,2,3 eli kaikki diagonaalit
    var isOkToMove = false //tarkastuksen tulos, oletuksena FALSE
    var firstEnemy = false //pitää kirjaa onko kohdattu vihollinen ensimmäinen, eli estääe ettei vihun yli "hypätä" sallittuja liikkeitä laskiessa.

  //Towerista
    var compass = Direction.Clockwise//Vectori jossa ilmansuunnat


//Liikutetaan checkSquare:a siten, että tarkastetaan onko attemptMove checkSquare ja jos on niin palautetaan lopuksi true.
    def canMove(attemptMove): Boolean = { //ONKO SIIRTO SÄÄNTÖJEN MUKAINEN, jos on niin palauttaa TRUE, jos ei niin FALSE
      //Towerin ilmansuunnille
      while (compassIndex <= 3 && isOkToMove == false) {
          ilmansuunta = compass(compassIndex)
          checkSquare = checkSquare.neighbor(ilmansuunta)//liikutetaan tarkastusruutua
          checkSquare = checkSquare.neighbor(ilmansuunta)//liikutetaan tarkastusruutua
          //tarkastetaan oikean puoleiset ruudut
          oikealle = (compassIndex + 1) % compass.length
          checkSquare = checkSquare.neighbor(compass(oikealle))
            if (this.world.elementAt(checkSquare) == attemptMove) {
            isOkToMove = TRUE //attemptMove löytyi sallitulta alueelta. Palautetaan TRUE ja lopetetaan etsintä
            }
          //tarkastetaan vasemman puoleiset ruudut
          vasemmalle = (compassIndex + 3) % compass.length
            if (this.world.elementAt(checkSquare) == attemptMove) {
            isOkToMove = TRUE //attemptMove löytyi sallitulta alueelta. Palautetaan TRUE ja lopetetaan etsintä
            }
          //aloitetaan uusi ilmansuunta
            checkSquare = this.location
            compassIndex = compassIndex + 1
      }
      isOkToMove //Palautetaan etsinnän tulos
    }



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
