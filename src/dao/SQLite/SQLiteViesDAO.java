package dao.SQLite;
import dao.DBConnection;
import model.CRUD;
import model.Vies;

import java.sql.*;
import java.util.Scanner;

public class SQLiteViesDAO implements CRUD<Vies> {

    // Crea una nova via a la base de dades
    @Override
    public void crear(Vies vies) {
        String sql = "INSERT INTO vies (sector_id, ancoratge_id, tipus_roca_id, escalador_id, via_num, nom, orientacio, estat, tipus, llargada) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            // Estableix els valors de la nova via a la consulta SQL
            stmt.setInt(1,vies.getIdSector());
            stmt.setInt(2,vies.getIdAncoratge());
            stmt.setInt(3,vies.getIdTipusRocas());
            stmt.setInt(4,vies.getIdEscalador());
            stmt.setInt(5,vies.getNumVia());
            stmt.setString(6,vies.getNom());
            stmt.setString(7,vies.getOrientacio());
            stmt.setString(8,vies.getEstat());
            stmt.setString(9,vies.getTipus());
            stmt.setInt(10,vies.getLlargada());

            // Executa la consulta i afegeix la via a la base de dades
            stmt.executeUpdate();
            System.out.println("S'ha afegit correctament la via");

            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
        }
    }

    // Actualitza una via existent a la base de dades
    @Override
    public void actualitzar(Vies vies) {
        Scanner scan = new Scanner(System.in);
        String sql = "UPDATE vies SET sector_id = ?, ancoratge_id = ?, tipus_roca_id = ?, escalador_id = ?, via_num = ?, nom = ?, orientacio = ?, estat = ?, tipus= ?, llargada = ? WHERE via_id = ?";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){

            // Demana la ID de la via que es vol actualitzar
            System.out.println("Escriu la ID de la via que vols actualitzar: ");
            int via_id = scan.nextInt();
            scan.nextLine();

            // Comprova si la via existeix abans d'actualitzar
            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM vies WHERE via_id = ?");
            check.setInt(1, via_id);
            ResultSet rs = check.executeQuery();

            if(rs.next() && rs.getInt(1) > 0){

                // Si existeix, actualitza la via amb els nous valors
                stmt.setInt(1,vies.getIdSector());
                stmt.setInt(2,vies.getIdAncoratge());
                stmt.setInt(3,vies.getIdTipusRocas());
                stmt.setInt(4,vies.getIdEscalador());
                stmt.setInt(5,vies.getNumVia());
                stmt.setString(6,vies.getNom());
                stmt.setString(7,vies.getOrientacio());
                stmt.setString(8, vies.getEstat());
                stmt.setString(9, vies.getTipus());
                stmt.setInt(10, vies.getLlargada());
                stmt.setInt(11, via_id);
                stmt.executeUpdate();
                System.out.println("S'ha actualitzat la via correctament");
            } else {
                System.out.println("La via no existeix, prova amb un altre id de via");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al modificar dades a la base de dades");
        }
    }

    // Elimina una via de la base de dades
    @Override
    public void eliminar(Vies vies) {
        Scanner scan = new Scanner(System.in);
        String sql = "DELETE FROM vies WHERE via_id = ?";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){

            // Demana la ID de la via que es vol eliminar
            System.out.println("Escriu la ID de la via que vols eliminar: ");
            int via_id = scan.nextInt();
            scan.nextLine();

            // Comprova si la via existeix abans d'eliminar
            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM vies WHERE via_id = ?");
            check.setInt(1, via_id);
            ResultSet rs = check.executeQuery();

            if(rs.next() && rs.getInt(1) == 1){

                // Si existeix, elimina la via
                stmt.setInt(1, via_id);
                stmt.executeUpdate();
                System.out.println("Via eliminada correctament.");
            } else {
                System.out.println("La via no existeix, prova amb un altre id de via");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al eliminar dades a la base de dades");
        }
    }

    // Llegeix una via específica de la base de dades per la seva ID
    @Override
    public void llegir() {
        Scanner scan = new Scanner(System.in);
        String sql = "SELECT * FROM vies WHERE via_id = ?";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){

            // Demana la ID de la via que es vol llegir
            System.out.println("Escriu la ID de la via que vols comprovar: ");
            int via_id = scan.nextInt();
            scan.nextLine();

            // Comprova si la via existeix abans de mostrar les dades
            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM vies WHERE via_id = ?");
            check.setInt(1,via_id);
            ResultSet rs = check.executeQuery();

            if(rs.next() && rs.getInt(1) == 1){
                stmt.setInt(1,via_id);
                ResultSet rs2 = stmt.executeQuery();

                // Mostra les dades de la via trobada
                System.out.printf("Sector ID: %-5d Escalador ID: %-5d Núm Via: %-5d Nom: %s Orientació: %-5s Estat: %-10s Tipus: %-2s Llargada: %-3d\n",
                        rs2.getInt("sector_id"),
                        rs2.getInt("escalador_id"),
                        rs2.getInt("via_num"),
                        rs2.getString("nom"),
                        rs2.getString("orientacio"),
                        rs2.getString("estat"),
                        rs2.getString("tipus"),
                        rs2.getInt("llargada"));
            } else {
                System.out.println("La via no existeix, prova amb un altre id de via");
            }
            stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llegir dades a la base de dades");
        }
    }

    // Llegeix totes les vies de la base de dades
    public void llegirTot() {
        String sql = "SELECT * FROM vies";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();

            // Mostra totes les vies a la base de dades
            while(rs.next()){
                System.out.printf("Via ID: %-5d Sector ID: %-5d Ancoratge ID: %-5d Tipus roca ID: %-5d Escalador ID: %-5d Núm Via: %-5d Nom: %-30s Orientació: %-5s Estat: %-18s Tipus: %-2s Llargada: %-3d\n",
                        rs.getInt("via_id"),
                        rs.getInt("sector_id"),
                        rs.getInt("ancoratge_id"),
                        rs.getInt("tipus_roca_id"),
                        rs.getInt("escalador_id"),
                        rs.getInt("via_num"),
                        rs.getString("nom"),
                        rs.getString("orientacio"),
                        rs.getString("estat"),
                        rs.getString("tipus"),
                        rs.getInt("llargada"));
            }
        } catch (SQLException e) {
            System.out.println("Error al llegir dades a la base de dades");
        }
    }

    // Llegeix totes les vies d'una escola específica
    public void totesViesEscola() {
        Connection con = DBConnection.openCon();
        try {
            Scanner scan = new Scanner(System.in);

            // Consulta per obtenir vies d'una escola amb estat "Apta"
            PreparedStatement ps = con.prepareStatement("SELECT * FROM vies WHERE sector_id = (SELECT sector_id FROM sectors WHERE escola_id = (SELECT escola_id FROM escoles WHERE nom = ?)) AND estat = \"Apta\";");

            SQLiteEscolesDAO dao = new SQLiteEscolesDAO();
            dao.llegirTot();// Mostra totes les escoles
            System.out.println("Escriu el nom de la escola: ");
            String nom = scan.nextLine();
            ps.setString(1, nom);

            ResultSet rs = ps.executeQuery();

            // Mostra les vies d'aquesta escola
            while(rs.next()){
                System.out.printf("Via ID: %-5d Sector ID: %-5d Ancoratge ID: %-5d Tipus roca ID: %-5d Escalador ID: %-5d Núm Via: %-5d Nom: %-30s Orientació: %-5s Estat: %-18s Tipus: %-2s Llargada: %-3d\n",
                        rs.getInt("via_id"),
                        rs.getInt("sector_id"),
                        rs.getInt("ancoratge_id"),
                        rs.getInt("tipus_roca_id"),
                        rs.getInt("escalador_id"),
                        rs.getInt("via_num"),
                        rs.getString("nom"),
                        rs.getString("orientacio"),
                        rs.getString("estat"),
                        rs.getString("tipus"),
                        rs.getInt("llargada"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void viesRangDificultat() {
        Scanner scan = new Scanner(System.in);
        Connection con = DBConnection.openCon();
        String nivell = ""; // Variable per emmagatzemar el primer rang de dificultat

        // Sol·licita el primer número del rang de dificultat (entre 4 i 9)
        System.out.println("Escull la primera dificultat del rang, primer un numero del 4 al 9: ");
        int niv = scan.nextInt();
        scan.nextLine();

        while (niv < 4 && niv > 9) {
            System.out.println("El nivell ha de ser un numero del 4 al 9, torna a intentar-ho: ");
            niv = scan.nextInt();
            scan.nextLine();
        }

        if (niv == 4) {
            nivell += 4;
            System.out.println("Dificultat 4 o 4+? (No escriguis res en cas de 4 o escriu nomes + en cas de 4+): ");
            nivell += scan.nextLine();
        } else if (niv == 5) {
            nivell += 5;
            System.out.println("Dificultat 5 o 5+? (No escriguis res en cas de 5 o escriu nomes + en cas de 5+): ");
            nivell += scan.nextLine();
        } else if (niv == 6) {
            nivell += 6;
            System.out.println("Dificultat 6a, 6b o 6c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 7) {
            nivell += 7;
            System.out.println("Dificultat 7a, 7b o 7c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 8) {
            nivell += 8;
            System.out.println("Dificultat 8a, 8b o 8c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 9) {
            nivell += 9;
            System.out.println("Dificultat 9a, 9b o 9c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        }

        // Sol·licita el segon número del rang de dificultat (entre 4 i 9)
        String nivell2 = "";
        System.out.println("Escull la segona dificultat del rang, primer un numero del 4 al 9: ");
        int niv2 = scan.nextInt();
        scan.nextLine();
        while (niv2 < 4 && niv2 > 9) {
            System.out.println("El nivell ha de ser un numero del 4 al 9, torna a intentar-ho: ");
            niv2 = scan.nextInt();
            scan.nextLine();
        }

        if (niv2 == 4) {
            nivell2 += 4;
            System.out.println("Dificultat 4 o 4+? (No escriguis res en cas de 4 o escriu nomes + en cas de 4+): ");
            nivell2 += scan.nextLine();
        } else if (niv2 == 5) {
            nivell2 += 5;
            System.out.println("Dificultat 5 o 5+? (No escriguis res en cas de 5 o escriu nomes + en cas de 5+): ");
            nivell2 += scan.nextLine();
        } else if (niv2 == 6) {
            nivell2 += 6;
            System.out.println("Dificultat 6a, 6b o 6c? (Escriu nomes la lletra en minuscula)");
            nivell2 += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell2 += scan.nextLine();
        } else if (niv2 == 7) {
            nivell2 += 7;
            System.out.println("Dificultat 7a, 7b o 7c? (Escriu nomes la lletra en minuscula)");
            nivell2 += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell2 += scan.nextLine();
        } else if (niv2 == 8) {
            nivell2 += 8;
            System.out.println("Dificultat 8a, 8b o 8c? (Escriu nomes la lletra en minuscula)");
            nivell2 += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell2 += scan.nextLine();
        } else if (niv2 == 9) {
            nivell2 += 9;
            System.out.println("Dificultat 9a, 9b o 9c? (Escriu nomes la lletra en minuscula)");
            nivell2 += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell2 += scan.nextLine();
        }
        try {
            PreparedStatement ps = con.prepareStatement("SELECT v.nom AS via_nom, d.nom AS grau, s.nom AS sector_nom, e.nom AS escola_nom FROM vies v JOIN trams t ON v.via_id = t.via_id JOIN dificultats d ON t.dificultat_id = d.dificultat_id JOIN sectors s ON v.sector_id = s.sector_id JOIN escoles e ON s.escola_id = e.escola_id WHERE d.nom BETWEEN ? AND ? ORDER BY d.nom;");
            ps.setString(1, nivell);
            ps.setString(2, nivell2);
            ResultSet rs = ps.executeQuery();

            // Mostra els resultats de la consulta
            System.out.printf("%-30s %-8s %-20s %-20s\n", "Nom Via", "Grau", "Sector", "Escola");
            while (rs.next()) {
                System.out.printf("%-30s %-8s %-25s %-25s\n",
                        rs.getString("via_nom"),
                        rs.getString("grau"),
                        rs.getString("sector_nom"),
                        rs.getString("escola_nom"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Filtra i mostra totes les vies segons el seu estat.
     * l'usuari pot triar:
     *   1 – Apta
     *   2 – En construcció
     *   3 – Tancada
     * Demana la selecció per consola, tradueix l'opció a l'estat corresponent,
     * fa la consulta i imprimeix cada via trobada.
     */
    public void estatVies() {
        Connection con = DBConnection.openCon();
        Scanner scan = new Scanner(System.in);
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM vies WHERE estat = ?;");
            System.out.println("Quin estat de via vols buscar? 1-Apta 2-En construcció 3-Tancada");
            String input = "";
            int num = scan.nextInt();
            scan.nextLine();
            switch (num) {
                case 1:
                    input = "Apta";
                    break;

                case 2:
                    input = "Construcció";
                    break;

                case 3:
                    input = "Tancada";
                    break;
            }
            ps.setString(1, input);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.printf("Sector ID: %-5d Escalador ID: %-5d Núm Via: %-5d Nom: %-30s Orientació: %-5s Estat: %-18s Tipus: %-2s Llargada: %-3d\n",
                        rs.getInt("sector_id"),
                        rs.getInt("escalador_id"),
                        rs.getInt("via_num"),
                        rs.getString("nom"),
                        rs.getString("orientacio"),
                        rs.getString("estat"),
                        rs.getString("tipus"),
                        rs.getInt("llargada"));
            }
            con.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Mostra les 3 vies més llargues disponibles d'una escola determinada.
     * @param nom Nom de l'escola per filtrar (ha d'existir a la taula escoles).
     * Es fa una subconsulta per obtenir els sector_id de l'escola,
     * després es filtren les vies per aquests sectors i es ordenen per llargada desc,
     * limitant el resultat a 3.
     */
    public static void sectorsMesViesDisp(String nom) {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM vies WHERE sector_id IN(SELECT sector_id FROM sectors WHERE escola_id = (SELECT escola_id FROM escoles WHERE nom = ?)) ORDER BY llargada DESC LIMIT 3");
            ps.setString(1, nom);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                System.out.printf("Via ID: %-5d Sector ID: %-5d Ancoratge ID: %-5d Tipus roca ID: %-5d Escalador ID: %-5d Núm Via: %-5d Nom: %-30s Orientació: %-5s Estat: %-18s Tipus: %-2s Llargada: %-3d\n",
                        rs.getInt("via_id"),
                        rs.getInt("sector_id"),
                        rs.getInt("ancoratge_id"),
                        rs.getInt("tipus_roca_id"),
                        rs.getInt("escalador_id"),
                        rs.getInt("via_num"),
                        rs.getString("nom"),
                        rs.getString("orientacio"),
                        rs.getString("estat"),
                        rs.getString("tipus"),
                        rs.getInt("llargada"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
