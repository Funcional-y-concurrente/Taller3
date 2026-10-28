package object ManiobrasTrenes {
  type Vagon = Any
  type Tren  = List[Vagon]
  type Estado = (Tren, Tren, Tren)

  trait Movimiento
  case class Uno(n: Int) extends Movimiento
  case class Dos(n: Int) extends Movimiento

  type Maniobra = List[Movimiento]

  def aplicarMovimiento(e: Estado, m: Movimiento): Estado = {
    (e, m) match {
      // Si n == 0, ningún vagón cambia de posición
      case (estado, Uno(0)) => estado
      case (estado, Dos(0)) => estado

      // Uno(n) con n > 0:
      // Mueve los n vagones más a la derecha de 'principal' hacia 'uno'
      case ((principal, uno, dos), Uno(n)) if n > 0 =>
        val k = n.min(principal.length)
        val splitIdx = principal.length - k
        val vagonesAMover = principal.takeRight(k)
        val nuevoPrincipal = principal.take(splitIdx)
        val nuevoUno = vagonesAMover ++ uno
        (nuevoPrincipal, nuevoUno, dos)

      // Uno(n) con n < 0:
      // Mueve los |n| vagones más a la izquierda de 'uno' hacia 'principal'
      case ((principal, uno, dos), Uno(n)) if n < 0 =>
        val k = (-n).min(uno.length)
        val vagonesAMover = uno.take(k)
        val nuevoUno = uno.drop(k)
        val nuevoPrincipal = principal ++ vagonesAMover
        (nuevoPrincipal, nuevoUno, dos)

      // Dos(n) con n > 0:
      // Mueve los n vagones más a la derecha de 'principal' hacia 'dos'
      case ((principal, uno, dos), Dos(n)) if n > 0 =>
        val k = n.min(principal.length)
        val splitIdx = principal.length - k
        val vagonesAMover = principal.takeRight(k)
        val nuevoPrincipal = principal.take(splitIdx)
        val nuevoDos = vagonesAMover ++ dos
        (nuevoPrincipal, uno, nuevoDos)

      // Dos(n) con n < 0:
      // Mueve los |n| vagones más a la izquierda de 'dos' hacia 'principal'
      case ((principal, uno, dos), Dos(n)) if n < 0 =>
        val k = (-n).min(dos.length)
        val vagonesAMover = dos.take(k)
        val nuevoDos = dos.drop(k)
        val nuevoPrincipal = principal ++ vagonesAMover
        (nuevoPrincipal, uno, nuevoDos)
    }
  }

  def aplicarMovimientos(e: Estado, move: Maniobra): List[Estado] = {
    0
  }

  def definirManiobra(t1: Tren, t2: Tren): Maniobra = {
    0
  }
}