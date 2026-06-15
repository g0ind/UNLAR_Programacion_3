package com.inventory.smart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Clase principal de inicio para la aplicación de Inventario Inteligente.
 *
 * <p>Inicializa el contexto de Spring Boot y escanea las propiedades de configuración.</p>
 *
 * @author Grupo 3 — Inventario Inteligente
 * @since 1.0
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class SmartInventoryApplication {

	/**
	 * Constructor por defecto de la clase de inicio.
	 */
	public SmartInventoryApplication() {
	}

	/**
	 * Método de entrada de la aplicación.
	 *
	 * @param args argumentos de línea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(SmartInventoryApplication.class, args);
	}

}

