import dao.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

import dao.SQLite.SQLiteEscaladorsDAO;
import dao.SQLite.SQLiteSectorsDAO;
import model.Escaladors;
import model.Sectors;
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

        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        Sectors sector = new Sectors(12321, 2, 4, "mierdaputrefacta", "41.2314, 1.2315", "cerca de mis cojones", 5, "Alta", "2026-04-11");


        Controller.menu();
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("No s'ha pogut tancar la base de dades");
        }

    }
}
