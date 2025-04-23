import dao.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

import dao.SQLite.SQLiteEscaladorsDAO;
import model.Escaladors;
import view.*;
import controller.*;

public class Main {
    public static void main(String[] args) {
        Connection con = DBConnection.openCon();

        if (con != null) {
            System.out.println("Connexió amb la BD feta correctament.");
        } else {
            System.out.println("No s'ha pogut establir la connexió.");
        }

        Escaladors paco = new Escaladors(2314, "paco", "elmejor", 32, "6b+", "Via15-La Trifuerza", "gel");

        Controller.menu();
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("No s'ha pogut tancar la base de dades");
        }

    }
}
