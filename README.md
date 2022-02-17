# Acceso

# Tabla de contenidos
- [Requisitos](#requisitos)
  - [IntelliJ](#intellij)
  - [NetBeans](#netbeans)
  - [Eclipse](#eclipse)
- [Ejecución](#ejecucin)

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

## Ejecución

Ejecutar lo siguiente (desde la carpeta `acceso`) para ver el programa en funcionamiento:
```bash
cd build
java -jar ./Acceso.jar
```

**AVISO** Para poder entrar en el apartado de la conexión con la base de datos debe usarse
una consola real, no es válida la consola de los IDE.