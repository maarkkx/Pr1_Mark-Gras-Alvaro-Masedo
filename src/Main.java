import dao.DBConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBConnection.openCon(DBConnection.dbPath);

        if (conn != null) {
            System.out.println("Connexió amb la BD feta correctament.");
        } else {
            System.out.println("No s'ha pogut establir la connexió.");
        }
    }
}
