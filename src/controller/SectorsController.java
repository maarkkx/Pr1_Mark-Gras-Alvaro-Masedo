package controller;
import dao.DBConnection;
import dao.SQLite.SQLiteSectorsDAO;
import model.Sectors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SectorsController {
    SQLiteSectorsDAO sectorsDAO = new SQLiteSectorsDAO();
    Connection con = DBConnection.openCon();

    public static void afegirSector(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Introdueix el nom de l'escola on es troba el sector");
        String nomEscola = scan.nextLine();

        int idEscola = obtenirIdEscola(nomEscola);
        while (idEscola == -1){
            System.out.println("Aquesta escola no exsisteix.");
            System.out.println("Introdueix el nom de l'escola on es troba el sector");
            nomEscola = scan.nextLine();
            obtenirIdEscola(nomEscola);
        }

        System.out.println("La escola és vàlida");
        int escola_id = obtenirIdEscola(nomEscola);

        System.out.println("Quin és el nom del sector?");
        String nomSector = scan.nextLine();

        System.out.println("Escriu les coordenades de aquest Sector: ");
        System.out.println("Han de tenir aquest format: (00.0000, 00.0000)");
        String coordenades = scan.nextLine();

        while (!comprovarCoordenades(coordenades)) {
            System.out.println("Les coordenades no són vàlides");
            System.out.println("Escriu les coordenades de aquest Sector: ");
            System.out.println("Han de tenir aquest format: (00.0000, 00.0000)");
            coordenades = scan.nextLine();
        }

        System.out.println("Les coordenades són vàlides");

        System.out.println("Escriu una petita aproximació per arribar al Sector");
        String aproximacio = scan.nextLine();

        int qtVies = -1;
        while (qtVies < 0) {
            System.out.println("Escriu la quantitat de vies per aquest sector (ha de ser un número positiu):");
            if (scan.hasNextInt()) {
                qtVies = scan.nextInt();
                if (qtVies < 0) {
                    System.out.println("La quantitat de vies ha de ser un número positiu.");
                }
            } else {
                System.out.println("Error: La quantitat de vies ha de ser un número.");
                scan.next();
            }
        }

        while (!comprovarVies(escola_id, qtVies)) {
            System.out.println("No hi ha prou vies disponibles per afegir o el número de vies no és vàlid.");
            System.out.println("Escriu la quantitat de vies per aquest sector (ha de ser un número positiu):");
            qtVies = scan.nextInt();
        }

        System.out.println("Les vies es poden afegir al sector.");

        System.out.println("\nEscriu la popularitat de la via (Alta, Mitjana o Baixa)");
        String popularitat = scan.nextLine();
        String popularitatComprovada = comprovarPopularitat(popularitat);
        while (popularitatComprovada == null){
            System.out.println("Error: La popularitat está mal introduida.");
            popularitat = scan.nextLine();
            popularitatComprovada = comprovarPopularitat(popularitat);
        }

        System.out.println("Popularitat introduída correctament");

        System.out.println("\nEscriu la data de restricció (YYYY-MM-DD) o deixa en blanc si no n'hi ha:");
        String restriccio = comprobarRestriccio(scan.nextLine());
        if (restriccio == null) {
            System.out.println("No s'ha introduit cap restricció");
        } else {
            System.out.println("Data de restricció introduïda correctament: " + restriccio);
        }

        int seguentSectorNum = obtenirSectorNum(escola_id);

        Sectors newSector = new Sectors(
                0,
                escola_id,
                seguentSectorNum,
                nomSector,
                coordenades,
                aproximacio,
                qtVies,
                popularitat,
                restriccio
        );

        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        dao.crear(newSector);
        System.out.println("El sector s'ha afegit correctament.");
    }

    public static int obtenirIdEscola(String nomEscola){
        String sql = "SELECT escola_id FROM escoles WHERE nom = ?";

        try(Connection con = DBConnection.openCon();
            PreparedStatement stmt = con.prepareStatement(sql)){

            stmt.setString(1, nomEscola);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getInt("escola_id");
            } else {
                return -1;
            }

        } catch (SQLException e) {
            System.out.println("Error al comprobar la escuela: " + e.getMessage());
            return -1;
        }
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

        return vies_restants >= vies_a_afegir;
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

    public static String comprovarPopularitat(String popularitat){
        if (popularitat == null || popularitat.trim().isEmpty()) {
            return null;
        }

        popularitat = popularitat.substring(0,1).toUpperCase() + popularitat.substring(1).toLowerCase();

        if (popularitat.equals("Alta") || popularitat.equals("Baixa") || popularitat.equals("Mitjana")){
            return popularitat;
        } else {
            return null;
        }
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
