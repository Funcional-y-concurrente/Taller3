package object ManiobrasTrenes {
  type Vagon = Any
  type Tren = List[Vagon]
  type Estado = (Tren, Tren, Tren)

  trait Movimiento
  case class Uno(n: Int) extends Movimiento
  case class Dos(n: Int) extends Movimiento

  type Maniobra = List[Movimiento]

  // 1. Aplicar movimiento usando reconocimiento de patrones
  def aplicarMovimiento(e: Estado, m: Movimiento): Estado = {
    (e, m) match {
      case (estado, Uno(0)) => estado
      case (estado, Dos(0)) => estado

      case ((principal, uno, dos), Uno(n)) if n > 0 =>
        val k = n.min(principal.length)
        val splitIdx = principal.length - k
        val vagonesAMover = principal.takeRight(k)
        val nuevoPrincipal = principal.take(splitIdx)
        val nuevoUno = vagonesAMover ++ uno
        (nuevoPrincipal, nuevoUno, dos)

      case ((principal, uno, dos), Uno(n)) if n < 0 =>
        val k = (-n).min(uno.length)
        val vagonesAMover = uno.take(k)
        val nuevoUno = uno.drop(k)
        val nuevoPrincipal = principal ++ vagonesAMover
        (nuevoPrincipal, nuevoUno, dos)

      case ((principal, uno, dos), Dos(n)) if n > 0 =>
        val k = n.min(principal.length)
        val splitIdx = principal.length - k
        val vagonesAMover = principal.takeRight(k)
        val nuevoPrincipal = principal.take(splitIdx)
        val nuevoDos = vagonesAMover ++ dos
        (nuevoPrincipal, uno, nuevoDos)

      case ((principal, uno, dos), Dos(n)) if n < 0 =>
        val k = (-n).min(dos.length)
        val vagonesAMover = dos.take(k)
        val nuevoDos = dos.drop(k)
        val nuevoPrincipal = principal ++ vagonesAMover
        (nuevoPrincipal, uno, nuevoDos)

      case (estado, _) => estado
    }
  }

  // 2. Aplicar múltiples movimientos mediante recursividad estructural
  def aplicarMovimientos(e: Estado, movs: Maniobra): List[Estado] = {
    movs match {
      case Nil => List(e)
      case m :: resto =>
        val siguienteEstado = aplicarMovimiento(e, m)
        e :: aplicarMovimientos(siguienteEstado, resto)
    }
  }

  def definirManiobra(t1: Tren, t2: Tren): Maniobra = {
    require(
      t1.toSet == t2.toSet && t1.length == t2.length,
      "Los trenes deben tener los mismos vagones (sin duplicados)"
    )

    if (t1 == t2) return Nil


    val movInicial: Maniobra = if (t1.isEmpty) Nil else List(Uno(t1.length))
    val (p0, u0, d0) = aplicarMovimiento((t1, Nil, Nil), movInicial.headOption.getOrElse(Uno(0)))

    def extraer(v: Vagon, uno: Tren, dos: Tren): (Maniobra, Tren, Tren) = {
      def loop(u: Tren, d: Tren, acc: Maniobra): (Maniobra, Tren, Tren) = u match {
        case Nil =>
          throw new IllegalStateException(s"Vagón $v no encontrado en uno")
        case h :: tail =>
          if (h == v) (acc, u, d)
          else loop(tail, h :: d, acc ++ List(Uno(-1), Dos(1)))
      }
      loop(uno, dos, Nil)
    }

    def devolver(dos: Tren): Maniobra =
      List.fill(dos.length)(List(Dos(-1), Uno(1))).flatten

    
    def construir(principal: Tren, uno: Tren, dos: Tren, objetivo: Tren): Maniobra = {
      objetivo match {
        case Nil => Nil
        case v :: resto =>
          val (movsExtraer, unoConV, dosConBuffer) = extraer(v, uno, dos)

          // Sacar v de uno → va al final del principal
          val movsSacar: Maniobra = List(Uno(-1))

          // Devolver los vagones de dos a uno
          val movsDevolver: Maniobra = devolver(dosConBuffer)

          // Estado resultante (para la recursión)
          val principalNuevo = principal :+ v
          val unoNuevo = dosConBuffer.reverse ++ unoConV.tail
          val movsResto = construir(principalNuevo, unoNuevo, Nil, resto)

          movsExtraer ++ movsSacar ++ movsDevolver ++ movsResto
      }
    }

    movInicial ++ construir(p0, u0, d0, t2)
  }
}