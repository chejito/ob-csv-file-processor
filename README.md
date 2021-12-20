# Ejercicios Java
## Ejercicio 1.
Dado un fichero CSV de la siguiente forma:

```
correo@dominio.com,Nombre Completo,usuario
correo@dominio.com,Nombre Completo,usuario
correo@dominio.com,Nombre Completo,usuario
```
Debes hacer un programa en Java que procese el fichero y cree un ArrayList con todos los usuarios (para ver que funciona correctamente, imprime por pantalla el resultado).

Debes tener en cuenta:

1. No debe haber correos duplicados. Se debe mostrar un error por **stderr** indicando la línea y el correo que está duplicado.
2. Si hay una línea mal formada, se debe mostrar también por **stderr**, indicando el número de línea que está mal formada.
3. Se debe indicar el número de líneas procesadas correctamente por **stdout** y el número de errores.