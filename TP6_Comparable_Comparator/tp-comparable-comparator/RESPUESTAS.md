# RESPUESTAS.md — TP Comparable, Comparator y polimorfismo

## Pregunta 1
**¿Por qué `Collections.sort()` no compila cuando le pasamos una `List<Estudiante>`? ¿Qué contrato exige Java que nuestra clase no está cumpliendo?**

`Collections.sort()` tiene la firma `<T extends Comparable<? super T>> void sort(List<T> list)`. Ese bound genérico exige que el tipo `T` implemente `Comparable`, porque el algoritmo de ordenamiento necesita poder comparar dos elementos cualesquiera de la lista para decidir su posición relativa. Como `Estudiante` es un POJO plano (sin implementar `Comparable`), no cumple ese contrato y el compilador rechaza la llamada antes de generar ningún bytecode: es un error de compilación, no de ejecución, porque Java necesita la garantía de "sé compararme" en tiempo de chequeo de tipos.
