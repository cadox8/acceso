package es.ivan.acceso.ems;

import es.ivan.acceso.ems.api.Medic;
import es.ivan.acceso.ems.database.Database;
import es.ivan.acceso.ems.database.queries.LoginQuery;
import es.ivan.acceso.ems.menu.EmsManagement;
import es.ivan.acceso.log.Log;
import es.ivan.acceso.old.files.PropertiesFiles;
import lombok.Getter;

import java.io.Console;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

public class Ems {

    @Getter
    private static Ems instance;

    // --- ---

    @Getter
    private Console console;

    @Getter
    private Database database;

    @Getter
    private Medic own;

    public Ems() {
        instance = this;

        try {
            this.console = System.console();
        } catch (NullPointerException e) {
            Log.stack(e.getStackTrace());
            Log.error("No estás con una consola soportada");
            System.exit(-1);
        }

        final PropertiesFiles mysqlConfig = new PropertiesFiles();
        final HashMap<String, String> values = new HashMap<>();

        values.put("host", "localhost");
        values.put("port", "3306");
        values.put("database", "ems");
        values.put("user", "root");
        values.put("password", "");

        mysqlConfig.saveFile("database", values);

        try {
            final Properties properties = mysqlConfig.getFileInfo("database");
            this.database = new Database(properties.getProperty("host"), properties.getProperty("port"), properties.getProperty("user"), properties.getProperty("password"), properties.getProperty("database"));
            this.database.openConnection();
        } catch (SQLException | ClassNotFoundException e) {
            Log.error("No se ha podido conectar con la Base de Datos");
            Log.stack(e.getStackTrace());
            System.exit(1);
        }

        Log.divWithBreak();
        Log.success("Bienvenido al sistema de gestión médica de LATAM");
        Log.normal("Para poder continuar, debe iniciar sesión en el sistema:");

        Log.putBreak(1);

        this.loginUser();
    }

    private void loginUser() {
        Log.normal("Usuario: ");
        final String username = this.console.readLine();

        Log.putBreak(1);

        Log.normal("Contraseña: ");
        final String password = new String(this.console.readPassword());

        this.own = new LoginQuery().login(username, password);

        Log.putBreak(1);

        if (this.own != null) {
            new EmsManagement(this, this.console).showMenu();
        } else {
            Log.warning("Usuario o contraseña incorrectos!");
            this.loginUser();
        }
    }

    public void logout() {
        this.own = null;
        Log.success("Te has desconectado correctamente");
        System.exit(0);
    }
}
