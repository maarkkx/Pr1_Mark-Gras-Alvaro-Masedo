package controller;
import dao.DBConnection;
import dao.SQLite.SQLiteViesDAO;
import model.Vies;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViesController {
    /**
     * Mètode per afecgir noves Vies a la base de dades
     * Recull dades que li pasa l'usuari
     * Validar les entrades
     */
    public static void afegirVia() {
        Scanner scan = new Scanner(System.in);

        //Nom Sector (retorna ID)
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

        //Tipus de Via. (Retorna ID)
        System.out.println("\nEscriu el tipus de via (Clàssica, Esportiva, Gel)");
        String tipus = scan.nextLine();
        while (tipusVia(tipus) == null) {
            System.out.println("Error: El tipus está mal introduït.");
            tipus = scan.nextLine();
        }

        System.out.println("Tipus introduït correctament: " + tipus);
        String tipusMajus = tipus.substring(0, 1).toUpperCase() + tipus.substring(1).toLowerCase();//Format per introduir el tipus de via

        //Crida la funció per validar l'ancoratge segons el tipus de Via.
        int ancoratgeId = ancoratgeValid(scan, tipusMajus);

        //Demana tipus de roca (Retorna ID)
        System.out.println("Introdueix el nom del tipus de roca: (Conglomerat, Granit, Arenisca, Calcària, Gel, Altres");        String nomRoca = scan.nextLine();

        int idRoca = obtenirRocaID(nomRoca);
        while (idRoca == -1) {
            System.out.println("Aquest tipus de roca no exsisteix.");
            System.out.println("Introdueix el nom del tipus de roca");
            nomRoca = scan.nextLine();
            obtenirRocaID(nomRoca);
        }

        System.out.println("El tipus de roca és vàlid");

        //Demana àlies de l'Escalador (Retorna ID)
        System.out.println("\nIntrodueix l'àlies del Escalador");
        String nomEscalador = scan.nextLine();

        int idEscalador = obtenirEscaladorID(nomEscalador);
        while (idEscalador == -1) {
            System.out.println("Aquest escalador no exsisteix.");
            System.out.println("\nIntrodueix l'àlies del Escalador");
            nomEscalador = scan.nextLine();
            obtenirEscaladorID(nomEscalador);
        }

        System.out.println("El nom de l'escalador és vàlid");

        //Crida la funció que calcula automàticament el número de Via
        int seguentViaNum = obtenirViaNum(id_Sector);

        //Comproba que el nom de la Via tingui un format
        String nomVia;
        Pattern pattern = Pattern.compile("^Via(\\d{1,2})-([A-Z][a-zA-Z]*)$"); //Regex

        while (true) {
            System.out.println("Introdueix el nom de la via (format: ViaX-Nom):");
            nomVia = scan.nextLine().trim();

            Matcher matcher = pattern.matcher(nomVia);
            if (!matcher.matches()) {
                System.out.println("El nom de la via no compleix el format correcte (ViaX-Nom).");
                continue;
            }

            //Comprova que al nom s'ha introduit correctament el número de la Via.
            int numeroVia = Integer.parseInt(matcher.group(1));
            if (numeroVia != seguentViaNum) {
                System.out.println("El número de la via ha de ser " + seguentViaNum);
                continue;
            }

            if (ViaNomExisteix(nomVia, id_Sector)) {
                System.out.println("Aquest nom de via ja existeix per aquest sector.");
                continue;
            }

            break;
        }

        //Orientació
        System.out.println("\nEscriu la orientació de la via (N,S,E,O,SO,SE,NO,NE");
        String orientacio = scan.nextLine();

        while (comprobarOrientacio(orientacio) == null) {
            System.out.println("Aquesta orientació es incorrecta.");
            System.out.println("Escriu la orientació de la via (N,S,E,O,SO,SE,NO,NE");
            orientacio = scan.nextLine();
        }
        System.out.println("orientacio afegida correctament.");
        String orientacioFormat = orientacio.toUpperCase(); //Dona format a la orientació (Majuscula)

        //Demana llargada de la via i crida a la funció per validar segons el tipus de la via.
        System.out.println("\nEscriu l'estat de la via (Apta, Construcció o Tancada)");
        String estat = scan.nextLine();
        while (comprovarEstat(estat) == null) {
            System.out.println("Error: L'estat està mal introduït.");
            System.out.println("Escriu l'estat de la via (Apta, Construcció o Tancada)");
            estat = scan.nextLine();
        }
        String estatModificat = estat.substring(0, 1).toUpperCase() + estat.substring(1).toLowerCase(); //Dona el format a l'estat de la Via

        //Demana llargada de la via i crida a la funció per validar segons el tipus de la via.
        int llargadaVia = demanarLlargada(tipus, scan);

        //Creació Objecte per afegir una via
        Vies newVia = new Vies(
                0,
                id_Sector,
                ancoratgeId,
                idRoca,
                idEscalador,
                seguentViaNum,
                nomVia,
                orientacioFormat,
                estatModificat,
                tipus,
                llargadaVia
        );

        SQLiteViesDAO dao = new SQLiteViesDAO();
        dao.crear(newVia);
        System.out.println("La Via s'ha afegit correctament.");
    }

    /**
     * Mètode per actualitzar una via existent. L'usuari introdueix noves dades
     * i es comproven abans d'actualitzar.
     */
    public static void actualitzarVia() {
        Scanner scan = new Scanner(System.in);

        //Nom Sector (retorna ID)
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

        //Tipus de Via. (Retorna ID)
        System.out.println("\nEscriu el nou tipus de via: (Esportiva, Clàssica, Gel");
        String tipus = scan.nextLine();
        while (tipusVia(tipus) == null) {
            System.out.println("Error: El nou tipus está mal introduït.");
            tipus = scan.nextLine();
        }

        System.out.println("Tipus introduït correctament: " + tipus);
        String tipusMajus = tipus.substring(0, 1).toUpperCase() + tipus.substring(1).toLowerCase();//Format per introduir el tipus de via

        //Crida la funció per validar l'ancoratge segons el tipus de Via.
        int ancoratgeId = ancoratgeValid(scan, tipusMajus);

        //Demana tipus de roca (Retorna ID)
        System.out.println("Introdueix el nou nom del tipus de roca: (Conglomerat, Granit, Arenisca, Calcària, Gel, Altres");
        String nomRoca = scan.nextLine();

        int idRoca = obtenirRocaID(nomRoca);
        while (idRoca == -1) {
            System.out.println("Aquest tipus de roca no exsisteix.");
            System.out.println("Introdueix el nou nom del tipus de roca");
            nomRoca = scan.nextLine();
            obtenirRocaID(nomRoca);
        }

        System.out.println("El tipus de roca és vàlid");

        //Demana àlies de l'Escalador (Retorna ID)
        System.out.println("\nIntrodueix l'àlies del Escalador");
        String nomEscalador = scan.nextLine();

        int idEscalador = obtenirEscaladorID(nomEscalador);
        while (idEscalador == -1) {
            System.out.println("Aquest escalador no exsisteix.");
            System.out.println("\nIntrodueix l'àlies del Escalador");
            nomEscalador = scan.nextLine();
            obtenirEscaladorID(nomEscalador);
        }

        System.out.println("El nom de l'escalador és vàlid");

        //Crida la funció que calcula automàticament el número de Via
        int seguentViaNum = obtenirViaNum(id_Sector);

        //Comproba que el nom de la Via tingui un format
        String nomVia;
        Pattern pattern = Pattern.compile("^Via(\\d{1,2})-([A-Z][a-zA-Z]*)$");

        while (true) {
            System.out.println("Introdueix el nou nom de la via (format: ViaX-Nom):");
            nomVia = scan.nextLine().trim();

            Matcher matcher = pattern.matcher(nomVia);
            if (!matcher.matches()) {
                System.out.println("El nou nom de la via no compleix el format correcte (ViaX-Nom).");
                continue;
            }

            //Comprova que al nom s'ha introduit correctament el número de la Via.
            int numeroVia = Integer.parseInt(matcher.group(1));
            if (numeroVia != seguentViaNum) {
                System.out.println("El número de la via ha de ser " + seguentViaNum);
                continue;
            }

            if (ViaNomExisteix(nomVia, id_Sector)) {
                System.out.println("Aquest nom de via ja existeix per aquest sector.");
                continue;
            }

            break;
        }

        //Orientació
        System.out.println("\nEscriu la nova orientació de la via (N,S,E,O,SO,SE,NO,NE");
        String orientacio = scan.nextLine();

        while (comprobarOrientacio(orientacio) == null) {
            System.out.println("Aquesta orientació es incorrecta.");
            System.out.println("Escriu la nova orientació de la via (N,S,E,O,SO,SE,NO,NE");
            orientacio = scan.nextLine();
        }
        System.out.println("orientacio afegida correctament.");
        String orientacioFormat = orientacio.toUpperCase();//Dona format a la orientació (Majuscula)

        //Estat de la via
        System.out.println("\nEscriu el nou estat de la via (Apta, Construcció o Tancada)");
        String estat = scan.nextLine();
        while (comprovarEstat(estat) == null) {
            System.out.println("Error: L'estat està mal introduït.");
            System.out.println("Escriu el nou estat de la via (Apta, Construcció o Tancada)");
            estat = scan.nextLine();
        }
        String estatModificat = estat.substring(0, 1).toUpperCase() + estat.substring(1).toLowerCase();//Dona el format a l'estat de la Via

        //Demana llargada de la via i crida a la funció per validar segons el tipus de la via.
        int llargadaVia = demanarLlargada(tipus, scan);

        //Creació Objecte per modificar una via
        Vies newVia = new Vies(
                0,
                id_Sector,
                ancoratgeId,
                idRoca,
                idEscalador,
                seguentViaNum,
                nomVia,
                orientacioFormat,
                estatModificat,
                tipus,
                llargadaVia
        );

        SQLiteViesDAO dao = new SQLiteViesDAO();
        dao.llegirTot();
        dao.actualitzar(newVia);
        System.out.println("La Via s'ha actualitzat correctament.");
    }

    /**
     * Mètode temporal per eliminar una via. Aquí s'hauria de passar una via real
     * o bé obtenir-la mitjançant ID, però actualment usa dades fixes.
     */
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

    /**
     * Mostra les dades d'una sola via.
     */
    public static void llistarUnaVia(){
        SQLiteViesDAO dao = new SQLiteViesDAO();
        dao.llegir();
    }

    /**
     * Mostra totes les vies existents a la base de dades.
     */
    public static void llistarTotesVies(){
        SQLiteViesDAO dao = new SQLiteViesDAO();
        dao.llegirTot();
    }

    /**
     * Retorna l'identificador d'un sector a partir del seu nom.
     * @param nomSector Nom del sector
     * @return ID del sector o -1 si no es troba
     */
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

    /**
     * Comprova si el tipus de via és vàlid (C, E, G).
     * @param tipus Tipus de via
     * @return Tipus si és vàlid o null si no ho és
     */
    public static String tipusVia(String tipus) {
        //Retorna null si el tipus está buit
        if (tipus == null || tipus.trim().isEmpty()) {
            return null;
        }

        tipus = tipus.substring(0, 1).toUpperCase();//Agafa només el primer caracter

        if (tipus.equals("C") || tipus.equals("E") || tipus.equals("G")) {
            return tipus;
        } else {
            return null;
        }
    }

    /**
     * Valida l'ancoratge segons el tipus de via i retorna l'ID si és vàlid.
     * @param scan Scanner per llegir dades
     * @param tipusVia Tipus de via
     * @return ID de l'ancoratge
     */
    public static int ancoratgeValid(Scanner scan, String tipusVia) {
        Map<String, List<String>> ancoratgesValids = new HashMap<>();//Crea un HashMap per guardar segons el tipus de via els seus ancoratges

        ancoratgesValids.put("G", Arrays.asList("friends", "tascons", "bagues", "pitons", "tricams", "bigbros"));
        ancoratgesValids.put("E", Arrays.asList("spits", "parabolts", "químics"));
        ancoratgesValids.put("C", Arrays.asList("friends", "tascons", "bagues", "pitons", "tricams", "bigbros", "spits", "parabolts", "químics"));

        tipusVia = tipusVia.toUpperCase();

        if (!ancoratgesValids.containsKey(tipusVia)) {
            System.out.println("Tipus de via no vàlid: " + tipusVia);
            return -1;
        }

        List<String> valids = ancoratgesValids.get(tipusVia);

        while (true) {
            System.out.println("Introdueix el tipus d'ancoratge: (friends, tascons, bagues, pitons, tricams, bigbros,spits,parabolts,químics)");
            String ancoratgeNom = scan.nextLine().trim().toLowerCase();

            int ancoratgeID = obtenirAncoratgeID(ancoratgeNom);
            if (ancoratgeID == -1) {
                System.out.println("L'ancoratge introduït no existeix");
                continue;
            }

            if (!valids.contains(ancoratgeNom)) {
                System.out.println("Aquest tipus d'ancoratge no és vàlid per a una via de tipus " + tipusVia);
                continue;
            }

            return ancoratgeID;
        }
    }

    /**
     * Retorna l'ID de l'ancoratge donat el nom.
     * @param nom Nom de l'ancoratge
     * @return ID de l'ancoratge o -1 si no existeix
     */
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

    /**
     * Retorna l'ID del tipus de roca donat el nom.
     * @param nom Nom de la roca
     * @return ID de la roca o -1 si no es troba
     */
    public static int obtenirRocaID(String nom) {
        String sql = "SELECT roca_id FROM tipus_roques WHERE nom = ?";
        try (Connection con = DBConnection.openCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("roca_id");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la roca per nom.");
        }
        return -1;
    }

    /**
     * Retorna l'ID de l'escalador a partir del seu àlies.
     * @param nom Àlies de l'escalador
     * @return ID de l'escalador o -1 si no es troba
     */
    public static int obtenirEscaladorID(String nom) {
        String sql = "SELECT escaldor_id FROM escaladors WHERE alies = ?";
        try (Connection con = DBConnection.openCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nom);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("escaldor_id");
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar l'Escalador per àlies.");
        }
        return -1;
    }

    /**
     * Obté el següent número disponible de via dins d'un sector.
     * @param sector_id ID del sector
     * @return número de via següent (1 si no hi ha vies)
     */
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

    /**
     * Comprova si ja existeix una via amb el mateix nom en un sector concret.
     * @param nomVia Nom de la via
     * @param sector_id ID del sector
     * @return true si ja existeix, false en cas contrari
     */
    public static boolean ViaNomExisteix(String nomVia, int sector_id) {
        String sql = "SELECT COUNT(*) FROM vies WHERE nom = ? AND sector_id = ?";
        try (Connection con = DBConnection.openCon();
             PreparedStatement stmt = con.prepareStatement(sql)) {

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

    /**
     * Comprova si l'orientació és vàlida (N, S, E, O, SE, SO, NE, NO).
     * @param orientacio Cadena amb l'orientació introduïda
     * @return orientació normalitzada si és vàlida, o null si no ho és
     */
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

    /**
     * Comprova si l'estat introduït és vàlid i el retorna normalitzat.
     * @param estat Estat introduït
     * @return Estat amb format correcte si és vàlid, o null si no ho és
     */
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

    /**
     * Demana la llargada de la via segons el tipus i comprova si és vàlida.
     * @param tipus Tipus de via (C, G o E)
     * @param scan Scanner per llegir l'entrada
     * @return llargada vàlida en metres
     */
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

    /**
     * Consulta per demanar a l'usuari una escola i mostrar les vies mes llargues.
     */
    public static void consulta11() {
        System.out.println("Escriu el nom de l'escola que vols comprovar les vies mes llargues");
        Scanner scan = new Scanner(System.in);
        String escolaNom = scan.nextLine();
        SQLiteViesDAO.sectorsMesViesDisp(escolaNom);
    }
}