# Exercises Spring 5

## Ejercicio 1
MyShoppingCart es una aplicación simplificada que permite a un usuario añadir productos a su carrito y comprar.
Para ello prevé de un conjunto de funcionalidades para:
+ añadir, quitar y vaciar items del carrito
+ calcular el balance
+ comprar

Necesitamos introducir el framework Spring en la aplicación base para obtener sus beneficios. Para ello:
- Añade la configuración Spring basada en anotaciones y haz wiring con los distintos componentes.
- Revisa y pasa los tests para comprobar cada capa en este orden: persistencia, servicios.
- Modifica la configuración del servicio para que use métodos para crear beans.
- Añade un perfil de desarrollo (dev) y otro de producción (prod). Haz que usen los archivos de propiedades correspondientes.
- Modifica los tests para probar los distintos perfiles.


## Ejercicio 2
Actualiza el proyecto MyShoppingCart para usar Spring JPA.
- Genera el wiring Spring-JPA simplificado
- Anota las entidades para configurar los mapeos con las tablas de bb.dd. Obvia las relaciones entre entidades.
- Reemplaza las clases de repositorio por
- Revisa y pasa los tests para comprobar que la capa de persistencia valida correctamente.

## Ejercicio 3
Actualiza el proyecto MyShoppingCart para configurar la relación entre entidades.
- Actualiza las entidades para que se generen las relaciones entre ellas de manera adecuada.
- Actualiza las configuración de cascada y de lazyness según sea pertienente.
- Respeta las relaciones de herencia.

## Ejercicio 4
Actualiza el proyecto MyShoppingCart para usar Spring Data.
- Añade repositorios del tipo JPARepository
- Actualiza el wiring
- Genera un repositorio JPA personalizado.