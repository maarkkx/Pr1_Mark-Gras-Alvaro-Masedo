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

        System.out.println("Escriu el nom del sector:");
        String nomSector = scan.nextLine();

        while (SectorNom(nomSector, escola_id)) {
            System.out.println("Aquest nom de sector ja existeix per aquesta escola.");
            System.out.println("Escriu un altre nom:");
            nomSector = scan.nextLine();
        }

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
        while (aproximacio == null || aproximacio.trim().isEmpty()){
            System.out.println("Error: torna a escriure l'aproximació del Sector.");
            aproximacio = scan.nextLine();
        }

        int qtVies = -1;
        while (qtVies < 0) {
            System.out.println("Escriu la quantitat de vies per aquest sector (ha de ser un número positiu):");
            if (scan.hasNextLine()) {
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
        while (comprovarPopularitat(popularitat) == null){
            System.out.println("Error: La popularitat está mal introduida.");
            popularitat = scan.nextLine();
        }

        System.out.println("Popularitat introduída correctament" + popularitat);
        String popularitatMajus = popularitat.substring(0, 1).toUpperCase() + popularitat.substring(1).toLowerCase();

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
                popularitatMajus,
                restriccio
        );

        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        dao.crear(newSector);
        System.out.println("El sector s'ha afegit correctament.");
    }

    public static void actualitzarSector(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Introdueix el nou nom de l'escola on es troba el sector");
        String nomEscola = scan.nextLine();

        int idEscola = obtenirIdEscola(nomEscola);
        while (idEscola == -1){
            System.out.println("Aquesta escola no exsisteix.");
            System.out.println("Introdueix el nou nom de l'escola on es troba el sector");
            nomEscola = scan.nextLine();
            obtenirIdEscola(nomEscola);
        }

        System.out.println("La escola és vàlida");
        int escola_id = obtenirIdEscola(nomEscola);

        System.out.println("Escriu el nou nom del sector:");
        String nomSector = scan.nextLine();

        while (SectorNom(nomSector, escola_id)) {
            System.out.println("Aquest nom de sector ja existeix per aquesta escola.");
            System.out.println("Escriu un altre nom:");
            nomSector = scan.nextLine();
        }
        System.out.println("Nom afegit correctament.");


        System.out.println("\nEscriu les noves coordenades de aquest Sector: ");
        System.out.println("Han de tenir aquest format: (00.0000, 00.0000)");
        String coordenades = scan.nextLine();

        while (!comprovarCoordenades(coordenades)) {
            System.out.println("Les coordenades no són vàlides");
            System.out.println("Escriu les noves coordenades de aquest Sector: ");
            System.out.println("Han de tenir aquest format: (00.0000, 00.0000)");
            coordenades = scan.nextLine();
        }

        System.out.println("Les coordenades són vàlides");

        System.out.println("Escriu una nova petita aproximació per arribar al Sector");
        String aproximacio = scan.nextLine();
        while (aproximacio == null || aproximacio.trim().isEmpty()){
            System.out.println("Error: torna a escriure una nova aproximació del Sector.");
            aproximacio = scan.nextLine();
        }

        int qtVies = -1;
        while (qtVies < 0) {
            System.out.println("Escriu la nova quantitat de vies per aquest sector (ha de ser un número positiu):");
            if (scan.hasNextLine()) {
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
        while (comprovarPopularitat(popularitat) == null){
            System.out.println("Error: La popularitat está mal introduida.");
            popularitat = scan.nextLine();
        }

        System.out.println("Popularitat introduída correctament" + popularitat);
        String popularitatMajus = popularitat.substring(0, 1).toUpperCase() + popularitat.substring(1).toLowerCase();

        System.out.println("\nEscriu una nova data de restricció (YYYY-MM-DD) o deixa en blanc si no n'hi ha:");
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
                popularitatMajus,
                restriccio
        );

        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        dao.llegirTot();
        dao.actualitzar(newSector);
        System.out.println("Sector actualitzat correctament");
    }

    public static void eliminarSector(){
        Sectors newSector = new Sectors(
                1,
                2,
                2,
                "a",
                "E",
                "ad",
                2,
                "A",
                "2"
        );

        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        dao.llegirTot();
        dao.eliminar(newSector);
    }

    public static void llistarUn(){
        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        dao.llegir();
    }

    public static void llistarTot(){
        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        dao.llegirTot();
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
            System.out.println("Error al comprobar l'escola: " + e.getMessage());
            return -1;
        }
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

    public static String comprovarPopularitat(String popularitat){
        if (popularitat == null || popularitat.trim().isEmpty()) {
            return null;
        }

        popularitat = popularitat.substring(0, 1).toUpperCase() + popularitat.substring(1).toLowerCase();

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
