import dao.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

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

        Controller.menu();
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("No s'ha pogut tancar la base de dades");
        }

    }
}
