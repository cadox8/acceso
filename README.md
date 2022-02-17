# Acceso

Este proyecto ha sido realizado para la asignatura de Acceso a Datos de 2º DAM.

## Tabla de contenidos
- [Requisitos](#requisitos)
  - [IntelliJ](#intellij)
  - [NetBeans](#netbeans)
  - [Eclipse](#eclipse)
- [Configuración](#configuración)
- [Ejecución](#ejecución)
- [Changelog](https://github.com/cadox8/acceso/blob/master/CHANGELOG.md)
- [Licencia](#licencia)

## Requisitos
Para poder ejecutar este código es obligatorio tener (mínimo) JAVA 8 instalado y cargar el pom.xml (Descargar librerias Lombok y JCDP).

Para Lombok se requiere tener activado el **Annotation Processors**:

### IntelliJ
Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors

### NetBeans
Project Properties -> Build -> Compiling

### Eclipse
- Abre Java Compiler -> Annotation Processing. Check "Enable annotation processing".
- Abre Java Compiler -> Annotation Processing -> Factory Path. Check "Enable project specific settings".

## Configuración
Para poder ejecutar el programa de base de datos, primero debemos configurar el archivo `.properties` generador automáticamente por el programa. Para ello debemos ir a la carpeta `files/properties` y allí se encontrará el archivo `database.properties`. Una vez rellenado con la configuración de la base de datos (MySQL/MariaDB) iniciamos el programa de nuevo y podremos acceder.

El esquema de la DB está en el archivo `Localhost-2022_02_01_17_55_19-dump.sql`.


## Ejecución
Descargar la versión deseada [desde aquí](https://cadox8.github.io/acceso/).

Ejecutar lo siguiente para ver el programa en funcionamiento:
```bash
java -jar ./Acceso.jar
```

**AVISO** Para poder entrar en el apartado de la conexión con la base de datos debe usarse
una consola real, no es válida la consola de los IDE.

## Licencia

MIT License

Copyright (c) 2022 Iván (cadox8) Rica

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
