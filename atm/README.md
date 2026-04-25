🏧 Simulador de Cajero Automático (ATM)


Institución: Universidad Nacional de La Rioja (UNLaR) - DACEFyN
Asignatura: Programación III

Trabajo Práctico: Nº 1.2 - Unidad 1

👨‍💻 Integrantes del Equipo

* **Pablo Galarza**
* **Chaile Marisa**

📝 Descripción del Proyecto
Este repositorio contiene el código fuente de un simulador de Cajero Automático (ATM) desarrollado en Java. El objetivo educativo principal de este proyecto es introducir y aplicar conceptos de Programación Orientada a Objetos (POO) avanzada, tales como el manejo del estado, la inmutabilidad, el encapsulamiento estricto y el manejo de errores en sistemas transaccionales.

🚀 Funcionalidades Principales
El sistema cumple con los siguientes requisitos funcionales:

* **Gestión de Cuentas: Soporte para múltiples cuentas bancarias con control de estado (activas/inactivas) e inmutabilidad estricta en el número de cuenta.**

  Operaciones Bancarias:

* **Depósitos: Validación de montos positivos y actualización de saldo.**

* **Extracciones: Control de saldo disponible y validación de límite de extracción ($10,000 por operación).**

* **Transferencias: Operaciones atómicas entre cuentas registradas en el sistema.**

* **Consultas de Saldo: Operaciones de lectura que no modifican el estado de la cuenta.**

* **Historial y Auditoría: Registro detallado de cada operación utilizando StringBuilder. Cada registro incluye timestamp, tipo de transacción, monto y saldo resultante. Se limita la visualización a los últimos 10 movimientos.**

* **Interfaz de Usuario: Menú interactivo por consola implementado con switch expressions y validación robusta de entradas numéricas (InputMismatchException) para evitar cierres inesperados.**

🛡️ Manejo de Excepciones

El sistema implementa una jerarquía de excepciones personalizadas para controlar la lógica de negocio:

* **SaldoInsuficienteException**

* **LimiteExtraccionExcedidoException**

* **CuentaInactivaException**

🏗️ Arquitectura y Estructura de Paquetes

El proyecto respeta una arquitectura estricta dividida en capas funcionales:

* **unlar.edu.ar.model: Entidades del dominio (CuentaBancaria, Transaccion, TipoTransaccion).**

* **unlar.edu.ar.exception: Excepciones de negocio.**

* **unlar.edu.ar.service: Lógica principal y reglas de negocio (CajeroService).**

* **unlar.edu.ar.ui: Interfaz gráfica de consola (MenuCajeroUI).**

* **unlar.edu.ar.util: Herramientas de formateo de moneda y fechas.**

🛠️ Cómo ejecutar el proyecto

Clonar este repositorio en tu máquina local.

Abre el proyecto en tu IDE de preferencia (Visual Studio Code, IntelliJ IDEA, Eclipse).

Compila y ejecuta la clase principal Main.java.

El sistema iniciará automáticamente una simulación con 3 cuentas y 15 transacciones variadas (incluyendo el manejo de excepciones) para demostrar la funcionalidad completa.

Al finalizar la simulación, se abrirá el menú interactivo para operar el cajero de forma manual.


![alt text](image.png)

![alt text](image-1.png)
