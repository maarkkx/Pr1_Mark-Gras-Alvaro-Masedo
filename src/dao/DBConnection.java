package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    /**
     * Connectar amb una BD indicada en el paràmetre 'url'.
     * @return Connexió amb la BD o 'null' si no s'ha pogut connectar
     */
    public static Connection openCon() {
        String url = "jdbc:sqlite:BaseDades.db";
        Connection con = null;

        try {
            con = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return con;
    }
}
