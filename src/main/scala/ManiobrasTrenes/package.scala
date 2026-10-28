package object ManiobrasTrenes {
  type Vagon = Any
  type Tren  = List[Vagon]
  type Estado = (Tren, Tren, Tren)

  trait Movimiento
  case class Uno(n: Int) extends Movimiento
  case class Dos(n: Int) extends Movimiento

  type Maniobra = List[Movimiento]

  def aplicarMovimientio(e: Estado, m: Movimiento): Estado = {
    0
  }

  def aplicarMovimientos(e: Estado, move: Maniobra): List[Estado] = {
    0
  }

  def definirManiobra(t1: Tren, t2: Tren): Maniobra = {
    0
  }
}