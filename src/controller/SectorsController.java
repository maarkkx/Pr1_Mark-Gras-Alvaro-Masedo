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

/**
 * Controlador per a la gestió de sectors
 * Gestiona les operacions CRUD de sectors i les seves validacions
 */
public class SectorsController {

    /**
     * Afegeix un nou sector al sistema
     * Demana les dades necessàries per crear un sector i les valida
     */
    public static void afegirSector(){
        Scanner scan = new Scanner(System.in);
        // Demana el nom de l'escola
        System.out.println("Introdueix el nom de l'escola on es troba el sector");
        String nomEscola = scan.nextLine();

        // Valida que l'escola existeixi
        int idEscola = obtenirIdEscola(nomEscola);
        while (idEscola == -1){
            System.out.println("Aquesta escola no exsisteix.");
            System.out.println("Introdueix el nom de l'escola on es troba el sector");
            nomEscola = scan.nextLine();
            obtenirIdEscola(nomEscola);
        }

        System.out.println("La escola és vàlida");
        int escola_id = obtenirIdEscola(nomEscola);

        // Demana el nom del sector i valida que no existeixi
        System.out.println("Escriu el nom del sector:");
        String nomSector = scan.nextLine();

        while (SectorNom(nomSector, escola_id)) {
            System.out.println("Aquest nom de sector ja existeix per aquesta escola.");
            System.out.println("Escriu un altre nom:");
            nomSector = scan.nextLine();
        }

        // Demana i valida les coordenades
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

        // Demana i valida l'aproximació
        System.out.println("Escriu una petita aproximació per arribar al Sector");
        String aproximacio = scan.nextLine();
        while (aproximacio == null || aproximacio.trim().isEmpty()){
            System.out.println("Error: torna a escriure l'aproximació del Sector.");
            aproximacio = scan.nextLine();
        }

        // Demana i valida la quantitat de vies
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

        // Demana i valida la popularitat
        System.out.println("\nEscriu la popularitat de la via (Alta, Mitjana o Baixa)");
        String popularitat = scan.nextLine();
        while (comprovarPopularitat(popularitat) == null){
            System.out.println("Error: La popularitat está mal introduida.");
            popularitat = scan.nextLine();
        }

        System.out.println("Popularitat introduída correctament" + popularitat);
        String popularitatMajus = popularitat.substring(0, 1).toUpperCase() + popularitat.substring(1).toLowerCase();

        // Demana i valida la restricció
        System.out.println("\nEscriu la data de restricció (YYYY-MM-DD) o deixa en blanc si no n'hi ha:");
        String restriccio = comprobarRestriccio(scan.nextLine());
        if (restriccio == null) {
            System.out.println("No s'ha introduit cap restricció");
        } else {
            System.out.println("Data de restricció introduïda correctament: " + restriccio);
        }

        // Obté el número de sector
        int seguentSectorNum = obtenirSectorNum(escola_id);

        // Crea l'objecte Sector
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

        // Crida al DAO per crear el sector
        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        dao.crear(newSector);
        System.out.println("El sector s'ha afegit correctament.");
    }

    /**
     * Actualitza un sector existent
     * Demana les noves dades i les valida abans de fer l'actualització
     */
    public static void actualitzarSector(){
        Scanner scan = new Scanner(System.in);
        // Demana el nom de l'escola
        System.out.println("Introdueix el nou nom de l'escola on es troba el sector");
        String nomEscola = scan.nextLine();

        // Valida que l'escola existeixi
        int idEscola = obtenirIdEscola(nomEscola);
        while (idEscola == -1){
            System.out.println("Aquesta escola no exsisteix.");
            System.out.println("Introdueix el nou nom de l'escola on es troba el sector");
            nomEscola = scan.nextLine();
            obtenirIdEscola(nomEscola);
        }

        System.out.println("La escola és vàlida");
        int escola_id = obtenirIdEscola(nomEscola);

        // Demana el nou nom del sector i valida que no existeixi
        System.out.println("Escriu el nou nom del sector:");
        String nomSector = scan.nextLine();

        while (SectorNom(nomSector, escola_id)) {
            System.out.println("Aquest nom de sector ja existeix per aquesta escola.");
            System.out.println("Escriu un altre nom:");
            nomSector = scan.nextLine();
        }
        System.out.println("Nom afegit correctament.");

        // Demana i valida les noves coordenades
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

        // Demana i valida la nova aproximació
        System.out.println("Escriu una nova petita aproximació per arribar al Sector");
        String aproximacio = scan.nextLine();
        while (aproximacio == null || aproximacio.trim().isEmpty()){
            System.out.println("Error: torna a escriure una nova aproximació del Sector.");
            aproximacio = scan.nextLine();
        }

        // Demana i valida la nova quantitat de vies
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

        // Demana i valida la nova popularitat
        System.out.println("\nEscriu la popularitat de la via (Alta, Mitjana o Baixa)");
        String popularitat = scan.nextLine();
        while (comprovarPopularitat(popularitat) == null){
            System.out.println("Error: La popularitat está mal introduida.");
            popularitat = scan.nextLine();
        }

        System.out.println("Popularitat introduída correctament" + popularitat);
        String popularitatMajus = popularitat.substring(0, 1).toUpperCase() + popularitat.substring(1).toLowerCase();

        // Demana i valida la nova restricció
        System.out.println("\nEscriu una nova data de restricció (YYYY-MM-DD) o deixa en blanc si no n'hi ha:");
        String restriccio = comprobarRestriccio(scan.nextLine());
        if (restriccio == null) {
            System.out.println("No s'ha introduit cap restricció");
        } else {
            System.out.println("Data de restricció introduïda correctament: " + restriccio);
        }

        // Obté el número de sector
        int seguentSectorNum = obtenirSectorNum(escola_id);

        // Crea l'objecte Sector amb les noves dades
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

        // Crida al DAO per actualitzar el sector
        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        dao.llegirTot();
        dao.actualitzar(newSector);
        System.out.println("Sector actualitzat correctament");
    }

    /**
     * Elimina un sector del sistema
     */
    public static void eliminarSector(){
        // Crea un objecte Sector temporal (aquest mètode sembla estar en desenvolupament)
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

        // Crida al DAO per eliminar el sector
        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        dao.llegirTot();
        dao.eliminar(newSector);
    }

    /**
     * Mostra les dades d'un sector específic
     */
    public static void llistarUn(){
        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        dao.llegir();
    }

    /**
     * Mostra les dades de tots els sectors
     */
    public static void llistarTot(){
        SQLiteSectorsDAO dao = new SQLiteSectorsDAO();
        dao.llegirTot();
    }

    /**
     * Obté l'ID d'una escola a partir del seu nom
     * @param nomEscola El nom de l'escola a buscar
     * @return L'ID de l'escola o -1 si no existeix
     */
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

    /**
     * Comprova si un nom de sector ja existeix per a una escola
     * @param nomSector El nom del sector a comprovar
     * @param escolaId L'ID de l'escola
     * @return true si el nom ja existeix, false en cas contrari
     */
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

    /**
     * Valida el format de les coordenades
     * @param coordenades Les coordenades a validar
     * @return true si el format és correcte, false en cas contrari
     */
    public static boolean comprovarCoordenades(String coordenades){
        String regex = "^\\d{2}\\.\\d{4},\\d{2}\\.\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(coordenades);

        return matcher.matches();
    }

    /**
     * Comprova si hi ha suficients vies disponibles per a un sector
     * @param escola_id L'ID de l'escola
     * @param vies_a_afegir Quantitat de vies a afegir
     * @return true si hi ha suficients vies disponibles, false en cas contrari
     */
    public static boolean comprovarVies(int escola_id, int vies_a_afegir) {
        int vies_totals_sectors = obtenirViesTotalsPerSector(escola_id);
        int vies_disponibles = ObtenirViesDisponiblesPerEscoles(escola_id);
        int vies_restants = vies_disponibles - vies_totals_sectors;

        return vies_restants >= vies_a_afegir && !quantitatViesRepetida(escola_id, vies_a_afegir);
    }

    /**
     * Obté el total de vies assignades als sectors d'una escola
     * @param escola_id L'ID de l'escola
     * @return El total de vies assignades
     */
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

    /**
     * Obté el total de vies disponibles d'una escola
     * @param escola_id L'ID de l'escola
     * @return El total de vies disponibles
     */
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

    /**
     * Comprova si una quantitat de vies ja existeix per a una escola
     * @param escola_id L'ID de l'escola
     * @param vies_a_afegir Quantitat de vies a comprovar
     * @return true si la quantitat ja existeix, false en cas contrari
     */
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

    /**
     * Valida el valor de popularitat
     * @param popularitat El valor a validar
     * @return El valor validat o null si no és vàlid
     */
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

    /**
     * Valida el format de la data de restricció
     * @param restriccio La data a validar
     * @return La data validada o null si no és vàlida
     */
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

    /**
     * Obté el següent número de sector per a una escola
     * @param escola_id L'ID de l'escola
     * @return El següent número de sector disponible
     */
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

    /**
     * Demana un número i mostra els sectors amb més vies disponibles que aquest número
     */
    public static void demanarNumConsulta9() {
        System.out.println("Escriu la qt de vies disponibles minimes: ");
        Scanner scan = new Scanner(System.in);
        int num = scan.nextInt();
        scan.nextLine();
        SQLiteSectorsDAO.sectorsMesViesDisp(num);
    }
}