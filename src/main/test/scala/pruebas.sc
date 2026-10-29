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

// --- CASOS DE PRUEBA PROPIOS: definirManiobra ---
// ========================================
// CASO 1: Trenes idénticos (caso trivial)
// ========================================
val t1_1 = List('a', 'b', 'c')
val t2_1 = List('a', 'b', 'c')
val maniobra1 = definirManiobra(t1_1, t2_1)
// Esperado: Nil (no se necesitan movimientos)
val verificacion1 = aplicarMovimientos((t1_1, Nil, Nil), maniobra1)
// Debe devolver: List((List('a','b','c'), Nil, Nil))

// ========================================
// CASO 2: Tren de un solo vagón
// ========================================
val t1_2 = List('x')
val t2_2 = List('x')
val maniobra2 = definirManiobra(t1_2, t2_2)
// Esperado: Nil
val verificacion2 = aplicarMovimientos((t1_2, Nil, Nil), maniobra2)
// Debe devolver: List((List('x'), Nil, Nil))

// ========================================
// CASO 3: Reversión completa del tren
// ========================================
val t1_3 = List('a', 'b', 'c')
val t2_3 = List('c', 'b', 'a')
val maniobra3 = definirManiobra(t1_3, t2_3)
val verificacion3 = aplicarMovimientos((t1_3, Nil, Nil), maniobra3)

// ========================================
// CASO 4: Intercambio de dos vagones adyacentes
// ========================================
val t1_4 = List('a', 'b', 'c')
val t2_4 = List('b', 'a', 'c')
val maniobra4 = definirManiobra(t1_4, t2_4)
val verificacion4 = aplicarMovimientos((t1_4, Nil, Nil), maniobra4)
// El último estado debe ser: (List('b','a','c'), Nil, Nil)

// ========================================
// CASO 5: Rotación circular
// ========================================
val t1_5 = List('a', 'b', 'c', 'd', 'e')
val t2_5 = List('e', 'a', 'b', 'c', 'd')
val maniobra5 = definirManiobra(t1_5, t2_5)
val verificacion5 = aplicarMovimientos((t1_5, Nil, Nil), maniobra5)
// El último estado debe ser: (List('e','a','b','c','d'), Nil, Nil)

// ========================================
// CASO 6: Tren con números (no solo caracteres)
// ========================================
val t1_6 = List(1, 2, 3, 4)
val t2_6 = List(4, 3, 2, 1)
val maniobra6 = definirManiobra(t1_6, t2_6)
val verificacion6 = aplicarMovimientos((t1_6, Nil, Nil), maniobra6)
// El último estado debe ser: (List(4,3,2,1), Nil, Nil)

// ========================================
// CASO 7: Reordenamiento complejo
// ========================================
val t1_7 = List('a', 'b', 'c', 'd', 'e', 'f')
val t2_7 = List('f', 'e', 'd', 'c', 'b', 'a')
val maniobra7 = definirManiobra(t1_7, t2_7)
val verificacion7 = aplicarMovimientos((t1_7, Nil, Nil), maniobra7)
// El último estado debe ser: (List('f','e','d','c','b','a'), Nil, Nil)

// ========================================
// CASO 8: Intercambio de extremos
// ========================================
val t1_8 = List('a', 'b', 'c', 'd')
val t2_8 = List('d', 'b', 'c', 'a')
val maniobra8 = definirManiobra(t1_8, t2_8)
val verificacion8 = aplicarMovimientos((t1_8, Nil, Nil), maniobra8)
// El último estado debe ser: (List('d','b','c','a'), Nil, Nil)