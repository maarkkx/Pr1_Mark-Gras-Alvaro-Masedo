package controller;

import dao.DBConnection;
import dao.SQLite.SQLiteSectorsDAO;
import dao.SQLite.SQLiteViesDAO;
import model.Sectors;
import model.Vies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViesController {
    public static void afegirVia() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Introdueix el nom del Sector on es troba la via");
        String nom_Sector = scan.nextLine();

        int id_Sector = obtenirIDSector(nom_Sector);
        while (id_Sector == -1) {
            System.out.println("Aquest sector no exsisteix.");
            System.out.println("Introdueix el nom del Sector on es troba la via");
            nom_Sector = scan.nextLine();
            obtenirIDSector(nom_Sector);
        }

        System.out.println("El sector és vàlid");

        System.out.println("\nEscriu el tipus de via");
        String tipus = scan.nextLine();
        while (tipusVia(tipus) == null) {
            System.out.println("Error: El tipus está mal introduït.");
            tipus = scan.nextLine();
        }

        System.out.println("Tipus introduït correctament: " + tipus);
        String tipusMajus = tipus.substring(0, 1).toUpperCase() + tipus.substring(1).toLowerCase();

        int ancoratgeId = ancoratgeValid(scan, tipusMajus);

        System.out.println("Introdueix el nom del tipus de roca");
        String nomRoca = scan.nextLine();

        int idRoca = obtenirRocaID(nomRoca);
        while (idRoca == -1) {
            System.out.println("Aquest tipus de roca no exsisteix.");
            System.out.println("Introdueix el nom del tipus de roca");
            nomRoca = scan.nextLine();
            obtenirRocaID(nomRoca);
        }

        System.out.println("El tipus de roca és vàlid");

        System.out.println("\nIntrodueix el nom del Escalador");
        String nomEscalador = scan.nextLine();

        int idEscalador = obtenirEscaladorID(nomEscalador);
        while (idEscalador == -1) {
            System.out.println("Aquest escalador no exsisteix.");
            System.out.println("Introdueix el nom del Escalador");
            nomEscalador = scan.nextLine();
            obtenirEscaladorID(nomEscalador);
        }

        System.out.println("El nom de l'escalador és vàlid");

        int seguentViaNum = obtenirViaNum(id_Sector);

        System.out.println("\nEscriu el nom de la via:");
        String nomVia = scan.nextLine();

        while (ViaNom(nomVia, id_Sector, seguentViaNum)) {
            System.out.println("Aquest nom de via ja existeix per aquesta escola.");
            System.out.println("Escriu un altre nom:");
            nomVia = scan.nextLine();
        }
        System.out.println("Nom afegit correctament.");

        System.out.println("\nEscriu la orientació de la via (N,S,E,O,SO,SE,NO,NE");
        String orientacio = scan.nextLine();

        while (comprobarOrientacio(orientacio) == null) {
            System.out.println("Aquesta orientació es incorrecta.");
            System.out.println("Escriu la orientació de la via (N,S,E,O,SO,SE,NO,NE");
            orientacio = scan.nextLine();
        }
        System.out.println("orientacio afegida correctament.");
        String orientacioFormat = orientacio.toUpperCase();

        System.out.println("\nEscriu l'estat de la via (Apta, Construcció o Tancada)");
        String estat = scan.nextLine();
        while (comprovarEstat(estat) == null) {
            System.out.println("Error: L'estat està mal introduït.");
            System.out.println("Escriu l'estat de la via (Apta, Construcció o Tancada)");
            estat = scan.nextLine();
        }

        int llargadaVia = demanarLlargada(tipus, scan);

        Vies newVia = new Vies(
                0,
                id_Sector,
                ancoratgeId,
                idRoca,
                idEscalador,
                seguentViaNum,
                nomVia,
                orientacioFormat,
                estat,
                tipus,
                llargadaVia
        );

        SQLiteViesDAO dao = new SQLiteViesDAO();
        dao.crear(newVia);
        System.out.println("La Via s'ha afegit correctament.");
    }

    public static void actualitzarVia() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Introdueix el nou nom del Sector on es troba la via");
        String nom_Sector = scan.nextLine();

        int id_Sector = obtenirIDSector(nom_Sector);
        while (id_Sector == -1) {
            System.out.println("Aquest sector no exsisteix.");
            System.out.println("Introdueix el nou nom del Sector on es troba la via");
            nom_Sector = scan.nextLine();
            obtenirIDSector(nom_Sector);
        }

        System.out.println("El sector és vàlid");

        System.out.println("\nEscriu el nou tipus de via");
        String tipus = scan.nextLine();
        while (tipusVia(tipus) == null) {
            System.out.println("Error: El nou tipus está mal introduït.");
            tipus = scan.nextLine();
        }

        System.out.println("Tipus introduït correctament: " + tipus);
        String tipusMajus = tipus.substring(0, 1).toUpperCase() + tipus.substring(1).toLowerCase();

        int ancoratgeId = ancoratgeValid(scan, tipusMajus);

        System.out.println("Introdueix el nou nom del tipus de roca");
        String nomRoca = scan.nextLine();

        int idRoca = obtenirRocaID(nomRoca);
        while (idRoca == -1) {
            System.out.println("Aquest tipus de roca no exsisteix.");
            System.out.println("Introdueix el nou nom del tipus de roca");
            nomRoca = scan.nextLine();
            obtenirRocaID(nomRoca);
        }

        System.out.println("El tipus de roca és vàlid");

        System.out.println("\nIntrodueix el nou nom del Escalador");
        String nomEscalador = scan.nextLine();

        int idEscalador = obtenirEscaladorID(nomEscalador);
        while (idEscalador == -1) {
            System.out.println("Aquest escalador no exsisteix.");
            System.out.println("Introdueix el nou nom del Escalador");
            nomEscalador = scan.nextLine();
            obtenirEscaladorID(nomEscalador);
        }

        System.out.println("El nom de l'escalador és vàlid");

        int seguentViaNum = obtenirViaNum(id_Sector);

        System.out.println("\nEscriu el nou nom de la via:");
        String nomVia = scan.nextLine();

        while (ViaNom(nomVia, id_Sector, seguentViaNum)) {
            System.out.println("Aquest nom de via ja existeix per aquesta escola.");
            System.out.println("Escriu un altre nom:");
            nomVia = scan.nextLine();
        }
        System.out.println("Nom afegit correctament.");

        System.out.println("\nEscriu la nova orientació de la via (N,S,E,O,SO,SE,NO,NE");
        String orientacio = scan.nextLine();

        while (comprobarOrientacio(orientacio) == null) {
            System.out.println("Aquesta orientació es incorrecta.");
            System.out.println("Escriu la nova orientació de la via (N,S,E,O,SO,SE,NO,NE");
            orientacio = scan.nextLine();
        }
        System.out.println("orientacio afegida correctament.");
        String orientacioFormat = orientacio.toUpperCase();

        System.out.println("\nEscriu el nou estat de la via (Apta, Construcció o Tancada)");
        String estat = scan.nextLine();
        while (comprovarEstat(estat) == null) {
            System.out.println("Error: L'estat està mal introduït.");
            System.out.println("Escriu el nou estat de la via (Apta, Construcció o Tancada)");
            estat = scan.nextLine();
        }

        int llargadaVia = demanarLlargada(tipus, scan);

        Vies newVia = new Vies(
                0,
                id_Sector,
                ancoratgeId,
                idRoca,
                idEscalador,
                seguentViaNum,
                nomVia,
                orientacioFormat,
                estat,
                tipus,
                llargadaVia
        );

        SQLiteViesDAO dao = new SQLiteViesDAO();
        dao.llegirTot();
        dao.actualitzar(newVia);
        System.out.println("La Via s'ha actualitzat correctament.");
    }

    public static void eliminarVia(){
        Vies newVia = new Vies(
                1,
                2,
                2,
                2,
                2,
                2,
                "a",
                "A",
                "2",
                "E",
                2
        );

        SQLiteViesDAO dao = new SQLiteViesDAO();
        dao.llegirTot();
        dao.eliminar(newVia);
    }

    public static void llistarUnaVia(){
        SQLiteViesDAO dao = new SQLiteViesDAO();
        dao.llegir();
    }

    public static void llistarTotesVies(){
        SQLiteViesDAO dao = new SQLiteViesDAO();
        dao.llegirTot();
    }

    public static int obtenirIDSector(String nomSector) {
        String sql = "SELECT sector_id FROM sectors WHERE nom = ?";

        try (Connection con = DBConnection.openCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nomSector);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("sector_id");
            } else {
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("Error al comprobar el sector: " + e.getMessage());
            return -1;
        }
    }

    public static String tipusVia(String tipus) {
        if (tipus == null || tipus.trim().isEmpty()) {
            return null;
        }

        tipus = tipus.substring(0, 1).toUpperCase();

        if (tipus.equals("C") || tipus.equals("E") || tipus.equals("G")) {
            return tipus;
        } else {
            return null;
        }
    }

    public static int ancoratgeValid(Scanner scan, String tipusVia) {
        Map<String, List<String>> ancoratgesValids = new HashMap<>();

        ancoratgesValids.put("G", Arrays.asList("friends", "tascons", "bagues", "pitons", "tricams", "bigbros"));
        ancoratgesValids.put("E", Arrays.asList("spits", "parabolts", "químics"));
        ancoratgesValids.put("C", Arrays.asList("friends", "tascons", "bagues", "pitons", "tricams", "bigbros", "spits", "parabolts", "químics"));

        while (true) {
            System.out.println("Introdueix el tipus d'ancoratge");
            String ancoratgeNom = scan.nextLine().trim().toLowerCase();

            int ancoratgeID = obtenirAncoratgeID(ancoratgeNom);
            if (ancoratgeID == -1) {
                System.out.println("L'ancoratge introduït no existeix");
                continue;
            }

            List<String> valids = ancoratgesValids.get(tipusVia.toUpperCase());
            if (!valids.contains(ancoratgeNom)) {
                System.out.println("Aquest tipus d'ancoratge no és vàlid per a una via de tipus " + tipusVia);
                continue;
            }

            return ancoratgeID;
        }
    }

    public static int obtenirAncoratgeID(String nom) {
        String sql = "SELECT ancoratge_id FROM ancoratges WHERE LOWER(nom) = ?";
        try (Connection con = DBConnection.openCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nom.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("ancoratge_id");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar l'ancoratge per nom.");
        }
        return -1;
    }


    public static int obtenirRocaID(String nom) {
        String sql = "SELECT roca_id FROM tipus_roques WHERE LOWER(nom) = ?";
        try (Connection con = DBConnection.openCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nom.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("roca_id");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la roca per nom.");
        }
        return -1;
    }

    public static int obtenirEscaladorID(String nom) {
        String sql = "SELECT escalador_id FROM escaladors WHERE LOWER(nom) = ?";
        try (Connection con = DBConnection.openCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nom.toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("escalador_id");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar l'Escalador per nom.");
        }
        return -1;
    }

    public static int obtenirViaNum(int sector_id) {
        String sql = "SELECT MAX(via_num) FROM vies WHERE sector_id = ?";
        int viaNum = 1;

        try (Connection con = DBConnection.openCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, sector_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int maxViaNum = rs.getInt(1);
                if (maxViaNum > 0) {
                    viaNum = maxViaNum + 1;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error obtenir el seguent ViaNum: " + e.getMessage());
        }

        return viaNum;
    }

    public static boolean ViaNom(String nomVia, int sector_id, int seguentViaNum) {
        String sql = "SELECT COUNT(*) FROM vies WHERE nom = ? AND sector_id = ?";
        Connection con = DBConnection.openCon();
        String regex = "^Via(\\d{1,2})-([A-Z][a-zA-Z]*)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(nomVia);

        if (!matcher.matches()) {
            System.out.println("El nom de la via no compleix el format correcte (ViaX-Nom).");
            return false;
        }

        int numeroVia = Integer.parseInt(matcher.group(1));
        if (numeroVia != seguentViaNum) {
            System.out.println("El número de la via no coincideix amb el número esperat: " + seguentViaNum);
            return false;
        }

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nomVia);
            stmt.setInt(2, sector_id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al comprovar el nom de la via a la base de dades");
        }

        return false;
    }

    public static String comprobarOrientacio(String orientacio) {
        if (orientacio == null || orientacio.trim().isEmpty()) {
            return null;
        }

        orientacio = orientacio.toUpperCase();

        if (orientacio.equals("N") ||
                orientacio.equals("S") ||
                orientacio.equals("E") ||
                orientacio.equals("O") ||
                orientacio.equals("SE") ||
                orientacio.equals("SO") ||
                orientacio.equals("NE") ||
                orientacio.equals("NO")) {
            return orientacio;
        } else {
            return null;
        }
    }

    public static String comprovarEstat(String estat) {
        if (estat == null || estat.trim().isEmpty()) {
            return null;
        }

        estat = estat.substring(0, 1).toUpperCase() + estat.substring(1).toLowerCase();

        if (estat.equals("Apta") || estat.equals("Construcció") || estat.equals("Tancada")) {
            return estat;
        } else {
            return null;
        }
    }

    public static int demanarLlargada(String tipus, Scanner scan) {
        int llargada = -1;

        while (true) {
            try {
                System.out.println("Escriu la llargada de la via (en metres):");
                System.out.println("Si es via Clàssica, introdueix un valor entre 15 i 30 metres");
                System.out.println("Si es via de Gel, introdueix un valor entre 15 i 30 metres");
                System.out.println("Si es via Esportiva, introdueix un valor entre 5 i 30 metres");
                llargada = Integer.parseInt(scan.nextLine());

                if (tipus.equalsIgnoreCase("C") || tipus.equalsIgnoreCase("G")) {
                    if (llargada >= 15 && llargada <= 30) {
                        break;
                    } else {
                        System.out.println("La llargada ha de ser entre 15 i 30 per a via Clàssica o Gel.");
                    }
                } else if (tipus.equalsIgnoreCase("E")) {
                    if (llargada >= 5 && llargada <= 30) {
                        break;
                    } else {
                        System.out.println("La llargada ha de ser entre 5 i 30 per a via Esportiva.");
                    }
                } else {
                    System.out.println("Tipus de via no reconegut.");
                    break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Introdueix un número vàlid.");
            }
        }

        return llargada;
    }
}