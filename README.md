# Acceso

## Requisitos
Para poder ejecutar este código es obligatorio tener (mínimo) JAVA 8 instalado y cargar el pom.xml (Descargar librerias Lombok y JCDP).

Para Lombok se requiere tener activado el **Annotation Processors**:

### IntelliJ
Settings -> Build, Exewcution, Deployment -> Compiler -> Annotation Processors

### NetBeans
Project Properties -> Build -> Compiling

### Eclipse
- Abre Java Compiler -> Annotation Processing. Check "Enable annotation processing".
- Abre Java Compiler -> Annotation Processing -> Factory Path. Check "Enable project specific settings".

## Ejecución

Ejecutar lo siguiente (desde la carpeta `acceso`) para ver el programa en funcionamiento:
```bash
cd build
java -jar ./Acceso-<version>.jar
```

## Info extra
Se que no estoy usando bien Git, ya que tendría que tener la rama `master` y la rama `development` (como míninmo) para desarrollar el código en la `development` y, al ser funcional, 
mandárselo a la `master` como pull request, pero de momento se queda así :kissing_heart:.