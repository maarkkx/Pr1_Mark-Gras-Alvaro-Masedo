import dao.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

import dao.SQLite.SQLiteEscaladorsDAO;
import dao.SQLite.SQLiteEscolesDAO;
import dao.SQLite.SQLiteSectorsDAO;
import dao.SQLite.SQLiteViesDAO;
import model.Escaladors;
import model.Escoles;
import model.Sectors;
import model.Vies;
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

        SQLiteViesDAO dao = new SQLiteViesDAO();
        Vies via = new Vies(231241, 1, 2, 3, 2, 26, "calvo", "NE", "Apta", "C", 17);
        dao.eliminar(via);

        Controller.menu();
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("No s'ha pogut tancar la base de dades");
        }

    }
}
