package es.ivan.acceso.ems;

import es.ivan.acceso.ems.api.Medic;
import es.ivan.acceso.ems.database.Database;
import es.ivan.acceso.ems.database.queries.LoginQuery;
import es.ivan.acceso.ems.menu.EmsManagement;
import es.ivan.acceso.log.Log;
import lombok.Getter;

import java.io.Console;
import java.sql.SQLException;
import java.util.Arrays;

public class Ems {

    @Getter private static Ems instance;

    // --- ---

    @Getter private final Console console;

    @Getter private Database database;

    @Getter private Medic own;

    public Ems() {
        instance = this;

        this.console = System.console();

        try {
            this.database = new Database("localhost", "root", "ems");
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
        Log.success("Ter has desconectado correctamente");
        System.exit(0);
    }
}
