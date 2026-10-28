import ManiobrasTrenes .*

// --- CASOS DE PRUEBA PROPIOS: aplicarMovimiento ---
// Caso 1: Movimiento hacia la vía Uno con n > 0 (traslado parcial estándar)
val estado1 = (List(1, 2, 3, 4, 5), List(6), List(7))
val res1 = aplicarMovimiento(estado1, Uno(2))
assert(res1 == (List(1, 2, 3), List(4, 5, 6), List(7)))

// Caso 2: Movimiento hacia la vía Dos solicitando más vagones de los que hay (n > longitud de principal)
val estado2 = (List('x', 'y'), List('z'), List())
val res2 = aplicarMovimiento(estado2, Dos(5))
assert(res2 == (List(), List('z'), List('x', 'y')))

// Caso 3: Regreso desde la vía Uno hacia principal con n < 0
val estado3 = (List("a", "b"), List("c", "d", "e"), List("f"))
val res3 = aplicarMovimiento(estado3, Uno(-2))
assert(res3 == (List("a", "b", "c", "d"), List("e"), List("f")))

// Caso 4: Regreso desde la vía Dos solicitando más vagones de los existentes (|n| > longitud de dos)
val estado4 = (List(10, 20), List(30), List(40, 50))
val res4 = aplicarMovimiento(estado4, Dos(-10))
assert(res4 == (List(10, 20, 40, 50), List(30), List()))

// Caso 5: Movimiento neutro con n = 0 (el estado debe permanecer idéntico)
val estado5 = (List('m', 'n', 'o'), List('p'), List('q', 'r'))
val res5 = aplicarMovimiento(estado5, Uno(0))
assert(res5 == estado5)


// --- CASOS DE PRUEBA PROPIOS: aplicarMovimientos ---

// Caso 1: Maniobra vacía (0 movimientos -> retorna lista con 1 solo estado: el inicial)
val eInicial1 = (List(1, 2, 3), List(), List())
val resM1 = aplicarMovimientos(eInicial1, List())
assert(resM1 == List(eInicial1))

// Caso 2: Maniobra con movimientos neutros Uno(0) y Dos(0)
val eInicial2 = (List('a', 'b'), List('c'), List('d'))
val movs2 = List(Uno(0), Dos(0))
val resM2 = aplicarMovimientos(eInicial2, movs2)
assert(resM2 == List(eInicial2, eInicial2, eInicial2))

// Caso 3: Secuencia alternada entre vía Uno y vía Dos
val eInicial3 = (List("x", "y", "z"), List[String](), List[String]())
val movs3 = List(Uno(1), Dos(1))
val resM3 = aplicarMovimientos(eInicial3, movs3)
assert(resM3 == List(
  (List("x", "y", "z"), List(), List()),
  (List("x", "y"), List("z"), List()),
  (List("x"), List("z"), List("y"))
))

// Caso 4: Movimiento de ida y regreso (debe regresar al estado de partida)
val eInicial4 = (List(10, 20, 30), List(40), List())
val movs4 = List(Uno(2), Uno(-2))
val resM4 = aplicarMovimientos(eInicial4, movs4)
assert(resM4.last == eInicial4)
assert(resM4.length == 3)

// Caso 5: Secuencia combinada con movimientos que exceden el tamaño disponible
val eInicial5 = (List('p'), List('q'), List('r'))
val movs5 = List(Dos(5), Uno(-5))
val resM5 = aplicarMovimientos(eInicial5, movs5)
assert(resM5 == List(
  (List('p'), List('q'), List('r')),
  (List(), List('q'), List('p', 'r')),
  (List('q'), List(), List('p', 'r'))
))