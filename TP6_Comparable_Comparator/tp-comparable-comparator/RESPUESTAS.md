# RESPUESTAS.md — TP Comparable, Comparator y polimorfismo

## Pregunta 1
**¿Por qué `Collections.sort()` no compila cuando le pasamos una `List<Estudiante>`? ¿Qué contrato exige Java que nuestra clase no está cumpliendo?**

`Collections.sort()` tiene la firma `<T extends Comparable<? super T>> void sort(List<T> list)`. Ese bound genérico exige que el tipo `T` implemente `Comparable`, porque el algoritmo de ordenamiento necesita poder comparar dos elementos cualesquiera de la lista para decidir su posición relativa. Como `Estudiante` es un POJO plano (sin implementar `Comparable`), no cumple ese contrato y el compilador rechaza la llamada antes de generar ningún bytecode: es un error de compilación, no de ejecución, porque Java necesita la garantía de "sé compararme" en tiempo de chequeo de tipos.

## Pregunta 2
**¿Por qué elegiste el atributo `promedio` como orden natural? ¿Qué pasaría si mañana un nuevo requisito pide ordenar por `cantidadMateriasAprobadas`? ¿Modificarías `compareTo`? ¿Qué consecuencias tendría?**

Elegimos `promedio` porque representa el criterio de mérito académico más general y es el que más sentido tiene como comportamiento "por defecto" de un `Estudiante` en la mayoría de los contextos del sistema.

Si apareciera el requisito de ordenar por `cantidadMateriasAprobadas`, **no modificaríamos `compareTo`**. Hacerlo rompería silenciosamente todo el código existente que depende del orden natural por promedio (como colecciones `TreeSet` o búsquedas binarias). La solución correcta es crear un `Comparator` externo para el nuevo criterio y dejar `compareTo` intacto.