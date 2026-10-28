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