package sjakki

import o1.grid._

class bishop (name: String, attemptMove: Coords)  {

    var currentLocation = this.location
    var team = whites //or blacks

    def move() = {
      if (ONKO SIIRTO SÄÄNTÖJEN MUKAINEN) {
        if (this.world.elementAt(attemptMove).NAPPULA.team == this.team) {//jos halutussa ruudussa on oma nappula (NAPPULA = robotbody)
            println("Can't move. The square is already occupied by your own team member")
          } else if (this.world.elementAt(attemptMove).NAPPULA.team != this.team) {  //jos halutussa ruudussa on vihollis nappula (NAPPULA = robotbody)
            SYÖDÄÄN VASTUSTAJA
            this.location = attemptMove //LIIKUTAAN RUUTUUN
          } else if (!this.world.elementAt(attemptMove).NAPPULA.isDefined) { //ruutu on tyhjä(huomaa huutomerkki)
            this.location = attemptMove //LIIKUTAAN RUUTUUN
          } else println("Can't move!")
        } else ("Rule violation") //siirto ei ole sääntöjen mukainen
}


}
