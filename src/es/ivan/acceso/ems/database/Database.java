package es.ivan.acceso.ems.database;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database class
 *
 * @author cadox8
 */
public class Database {

    private final String user, database, password, port, hostname;
    protected Connection connection;

    public Database(String hostname, String username, String database) {
        this(hostname, username, "", database);
    }
    public Database(String hostname, String username, String password, String database) {
        this(hostname, "3306", username, password, database);
    }
    public Database(String hostname, String port, String username, String password, String database) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
    }

    public boolean checkConnection() throws SQLException {
        return this.connection != null && !this.connection.isClosed();
    }

    public boolean closeConnection() throws SQLException {
        if (this.connection == null) return false;
        this.connection.close();
        return true;
    }

    public Connection openConnection() throws SQLException, ClassNotFoundException {
        if (this.checkConnection()) return this.connection;
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mysql://" + this.hostname + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.user, this.password);
        return this.connection;
    }
}
