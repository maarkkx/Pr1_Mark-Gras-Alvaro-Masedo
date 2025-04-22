package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    public static String dbPath = "jdbc:sqlite:BaseDades.db";

    /**
     * Connectar amb una BD indicada en el paràmetre 'url'.
     * @param url Protocol i ruta cap a la BD<br>Exemple: "jdbc:sqlite:prova.db"
     * @return Connexió amb la BD o 'null' si no s'ha pogut connectar
     */
    public static Connection openCon(String url) {
        Connection con = null;

        try {
            con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return con;
    }
}
