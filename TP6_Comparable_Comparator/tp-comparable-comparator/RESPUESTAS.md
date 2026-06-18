# RESPUESTAS.md — TP Comparable, Comparator y polimorfismo

## Pregunta 1
**¿Por qué `Collections.sort()` no compila cuando le pasamos una `List<Estudiante>`? ¿Qué contrato exige Java que nuestra clase no está cumpliendo?**

`Collections.sort()` tiene la firma `<T extends Comparable<? super T>> void sort(List<T> list)`. Ese bound genérico exige que el tipo `T` implemente `Comparable`, porque el algoritmo de ordenamiento necesita poder comparar dos elementos cualesquiera de la lista para decidir su posición relativa. Como `Estudiante` es un POJO plano (sin implementar `Comparable`), no cumple ese contrato y el compilador rechaza la llamada antes de generar ningún bytecode: es un error de compilación, no de ejecución, porque Java necesita la garantía de "sé compararme" en tiempo de chequeo de tipos.

## Pregunta 2
**¿Por qué elegiste el atributo `promedio` como orden natural? ¿Qué pasaría si mañana un nuevo requisito pide ordenar por `cantidadMateriasAprobadas`? ¿Modificarías `compareTo`? ¿Qué consecuencias tendría?**

Elegimos `promedio` porque representa el criterio de mérito académico más general y es el que más sentido tiene como comportamiento "por defecto" de un `Estudiante` en la mayoría de los contextos del sistema.

Si apareciera el requisito de ordenar por `cantidadMateriasAprobadas`, **no modificaríamos `compareTo`**. Hacerlo rompería silenciosamente todo el código existente que depende del orden natural por promedio (como colecciones `TreeSet` o búsquedas binarias). La solución correcta es crear un `Comparator` externo para el nuevo criterio y dejar `compareTo` intacto.

## Pregunta 3
**`Comparable` nos ata a un único criterio de ordenamiento. ¿Qué problemas de diseño introduce esto si nuestro sistema necesitara ordenar la misma lista de estudiantes de 4 formas distintas según el contexto? Relacioná tu respuesta con SRP y OCP.**

Si solo tuviéramos `Comparable`, para soportar 4 criterios distintos tendríamos que reescribir `compareTo` cada vez que cambia el requisito, lo cual viola el **principio abierto/cerrado (OCP)**: la clase `Estudiante` debería estar cerrada a modificación, pero cada nuevo criterio de orden nos obliga a modificar su código fuente. Además, la clase terminaría cargando con una responsabilidad que no le corresponde (saber compararse de múltiples formas según el contexto), violando el **principio de responsabilidad única (SRP)**.

## Pregunta 4
**Explicá qué es un overflow de enteros, por qué el "truco de la resta" lo provoca, qué parte del contrato de `Comparator` rompe, y por qué `Integer.compare()` no sufre este problema.**

Un overflow de enteros ocurre cuando el resultado de una operación aritmética excede el rango representable por el tipo de datos (para `int`, de `-2,147,483,648` a `2,147,483,647`). La operación `Integer.MAX_VALUE - (-1)` produce matemáticamente `2,147,483,648`, valor que desborda el límite positivo de un entero de 32 bits con signo y "da la vuelta" convirtiéndose en `Integer.MIN_VALUE` (un número negativo).

Este resultado erróneo rompe el contrato de `Comparator`, ya que el signo devuelto es inconsistente con el orden real de los elementos. Esto viola la propiedad transitiva y simétrica del ordenamiento. 

`Integer.compare(a, b)` evita este desborde porque no realiza restas; en su lugar, utiliza operadores lógicos relacionales comparando directamente las magnitudes (equivalente a `(a < b) ? -1 : ((a == b) ? 0 : 1)`).