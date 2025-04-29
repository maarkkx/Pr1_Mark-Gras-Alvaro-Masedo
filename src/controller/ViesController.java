package controller;

import dao.DBConnection;

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
        while (tipusVia(tipus) == null){
            System.out.println("Error: El tipus está mal introduït.");
            tipus = scan.nextLine();
        }

        System.out.println("Tipus introduït correctament: " + tipus);
        String tipusMajus = tipus.substring(0, 1).toUpperCase() + tipus.substring(1).toLowerCase();

        int ancoratgeId = ancoratgeValid(scan, tipusMajus);

        System.out.println("Introdueix el nom del tipus de roca");
        String nomRoca = scan.nextLine();

        int idRoca = obtenirRocaID(nomRoca);
        while (idRoca == -1){
            System.out.println("Aquest tipus de roca no exsisteix.");
            System.out.println("Introdueix el nom del tipus de roca");
            nomRoca = scan.nextLine();
            obtenirRocaID(nomRoca);
        }

        System.out.println("El tipus de roca és vàlid");
    }


    public static int obtenirIDSector(String nomSector){
        String sql = "SELECT sector_id FROM sectors WHERE nom = ?";

        try(Connection con = DBConnection.openCon();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, nomSector);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getInt("sector_id");
            } else {
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("Error al comprobar el sector: " + e.getMessage());
            return -1;
        }
    }

    public static String tipusVia(String tipus){
        if (tipus == null || tipus.trim().isEmpty()) {
            return null;
        }

        tipus = tipus.substring(0, 1).toUpperCase();

        if (tipus.equals("C") || tipus.equals("E") || tipus.equals("G")){
            return tipus;
        } else {
            return null;
        }
    }

    public static int ancoratgeValid(Scanner scan, String tipusVia){
        Map<String,List<String>> ancoratgesValids = new HashMap<>();

        ancoratgesValids.put("G", Arrays.asList("friends", "tascons", "bagues", "pitons", "tricams", "bigbros"));
        ancoratgesValids.put("E", Arrays.asList("spits", "parabolts", "químics"));
        ancoratgesValids.put("C", Arrays.asList("friends", "tascons", "bagues", "pitons", "tricams", "bigbros", "spits", "parabolts", "químics"));

        while(true){
            System.out.println("Introdueix el tipus d'ancoratge");
            String ancoratgeNom = scan.nextLine().trim().toLowerCase();

            int ancoratgeID = obtenirAncoratgeID(ancoratgeNom);
            if (ancoratgeID == -1){
                System.out.println("L'ancoratge introduït no existeix");
                continue;
            }

            List<String> valids = ancoratgesValids.get(tipusVia.toUpperCase());
            if (!valids.contains(ancoratgeNom)){
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













    public static boolean SectorNom(String nomSector, int escolaId) {
        String sql = "SELECT COUNT(*) FROM sectors WHERE nom = ? AND escola_id = ?";
        Connection con = DBConnection.openCon();

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nomSector);
            stmt.setInt(2, escolaId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al comprovar el nom del sector a la base de dades");
        }

        return false;
    }

    public static boolean comprovarCoordenades(String coordenades){
        String regex = "^\\d{2}\\.\\d{4},\\d{2}\\.\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(coordenades);

        return matcher.matches();
    }

    public static boolean comprovarVies(int escola_id, int vies_a_afegir) {
        int vies_totals_sectors = obtenirViesTotalsPerSector(escola_id);
        int vies_disponibles = ObtenirViesDisponiblesPerEscoles(escola_id);
        int vies_restants = vies_disponibles - vies_totals_sectors;

        return vies_restants >= vies_a_afegir && !quantitatViesRepetida(escola_id, vies_a_afegir);
    }

    public static int obtenirViesTotalsPerSector(int escola_id) {
        int totalVies = 0;
        String sql = "SELECT SUM(vies_qt) FROM sectors WHERE escola_id = ?";

        try (Connection con = DBConnection.openCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, escola_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalVies = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtenir el total de vies per sector.");
        }
        return totalVies;
    }

    public static int ObtenirViesDisponiblesPerEscoles(int escola_id) {
        int viesDisponibles = 0;
        String sql = "SELECT vies_qt FROM escoles WHERE escola_id = ?";

        try (Connection con = DBConnection.openCon(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, escola_id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                viesDisponibles = rs.getInt("vies_qt");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener vías disponibles en la escuela.");
        }
        return viesDisponibles;
    }

    public static boolean quantitatViesRepetida(int escola_id, int vies_a_afegir) {
        String sql = "SELECT COUNT(*) FROM sectors WHERE escola_id = ? AND vies_qt = ?";
        try (Connection con = DBConnection.openCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, escola_id);
            stmt.setInt(2, vies_a_afegir);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al comprobar si la quantitat de vies ya existeix.");
        }
        return false;
    }

    public static String comprobarRestriccio(String restriccio) {
        if (restriccio.trim().isEmpty()) {
            return null;
        }

        String regex = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(restriccio);

        if (matcher.matches()){
            return restriccio;
        } else {
            return null;
        }
    }

    public static int obtenirSectorNum (int escola_id){
        String sql = "SELECT MAX(sector_num) FROM sectors WHERE escola_id = ?";
        int sectorNum = 1;

        try (Connection con = DBConnection.openCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, escola_id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int maxSectorNum = rs.getInt(1);
                if (maxSectorNum > 0) {
                    sectorNum = maxSectorNum + 1;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error obtenir el seguent SectorNum: " + e.getMessage());
        }

        return sectorNum;
    }
}
