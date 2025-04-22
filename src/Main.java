import dao.DBConnection;
import java.sql.Connection;
import view.*;
import controller.*;

public class Main {
    public static void main(String[] args) {
        Connection con = DBConnection.openCon(DBConnection.dbPath);

        if (con != null) {
            System.out.println("Connexió amb la BD feta correctament.");
        } else {
            System.out.println("No s'ha pogut establir la connexió.");
        }

        Controller.menu();
    }
}
