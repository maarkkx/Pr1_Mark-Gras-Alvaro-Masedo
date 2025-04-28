package controller;

import dao.DBConnection;
import dao.SQLite.SQLiteEscolesDAO;
import model.Escoles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class EscolesController {
    public static void crearEscola() {
        Connection con = DBConnection.openCon();
        Scanner scan = new Scanner(System.in);
        System.out.println("Escriu el nom de la escola: ");
        String nom = scan.nextLine();

        try {
            //Nom i comprovar
            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM escoles WHERE nom = ?");
            check.setString(1, nom);
            ResultSet rs2 = check.executeQuery();
            if (rs2.next() && rs2.getInt(1) > 0) {
                System.out.println("La escola ja existeix, prova amb un altre nom.");
            } else {
                try {
                    //Taula poblacions
                    PreparedStatement ps = con.prepareStatement("SELECT * FROM poblacions");
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        System.out.printf("Poblacio ID: %-5d Nom: %-9s\n",
                                rs.getInt("poblacio_id"),
                                rs.getString("nom")
                        );
                    }

                } catch (SQLException e) {
                    System.out.println(e);
                }

                //ID POB
                System.out.println("Escriu l' ID de la poblacio on esta la escola: ");
                int idPob = scan.nextInt();
                scan.nextLine();

                //APROX
                System.out.println("Escriu una breu descripcio sobre com arribar: ");
                String aprox = scan.nextLine();

                //QT VIES
                System.out.println("Escriu la quantitat de vies que te: ");
                int qtVies = scan.nextInt();
                scan.nextLine();

                //POPULARITAT
                String popu = "";
                System.out.println("Escull el numero de la popularitat: \n 1-Alta 2-Mitajana 3-Baixa");
                int sw1 = scan.nextInt();
                while (sw1 < 1 && sw1 > 3) {
                    System.out.println("Escriu un numero del 1 al 3: ");
                    sw1 = scan.nextInt();
                }
                scan.nextLine();
                switch (sw1) {
                    case 1:
                        popu = "Alta";
                        break;

                    case 2:
                        popu = "Mitjana";
                        break;

                    case 3:
                        popu = "Baixa";
                        break;
                }

                //RESTRICCIO
                System.out.println("Aquesta escola te restriccio? Si es que si escriu la data, si es NO, presiona ENTER");
                String rest = scan.nextLine();

                Escoles escola = new Escoles(1, idPob, nom, aprox, qtVies, popu, rest);
                SQLiteEscolesDAO dao = new SQLiteEscolesDAO();
                con.close();
                dao.crear(escola);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void actualitzarEscoles() {
            Connection con = DBConnection.openCon();
            Scanner scan = new Scanner(System.in);
            System.out.println("Escriu el nom de la escola: ");
            String nom = scan.nextLine();

            try {
                //Nom i comprovar
                PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM escoles WHERE nom = ?");
                check.setString(1, nom);
                ResultSet rs2 = check.executeQuery();
                if (rs2.next() && rs2.getInt(1) > 0) {
                    System.out.println("La escola ja existeix, prova amb un altre nom.");
                } else {
                    try {
                        //Taula poblacions
                        PreparedStatement ps = con.prepareStatement("SELECT * FROM poblacions");
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            System.out.printf("Poblacio ID: %-5d Nom: %-9s\n",
                                    rs.getInt("poblacio_id"),
                                    rs.getString("nom")
                            );
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                    //ID POB
                    System.out.println("Escriu l' ID de la poblacio on esta la escola: ");
                    int idPob = scan.nextInt();
                    scan.nextLine();

                    //APROX
                    System.out.println("Escriu una breu descripcio sobre com arribar: ");
                    String aprox = scan.nextLine();

                    //QT VIES
                    System.out.println("Escriu la quantitat de vies que te: ");
                    int qtVies = scan.nextInt();
                    scan.nextLine();

                    //POPULARITAT
                    String popu = "";
                    System.out.println("Escull el numero de la popularitat: \n 1-Alta 2-Mitajana 3-Baixa");
                    int sw1 = scan.nextInt();
                    while (sw1 < 1 && sw1 > 3) {
                        System.out.println("Escriu un numero del 1 al 3: ");
                        sw1 = scan.nextInt();
                    }
                    scan.nextLine();
                    switch (sw1) {
                        case 1:
                            popu = "Alta";
                            break;

                        case 2:
                            popu = "Mitjana";
                            break;

                        case 3:
                            popu = "Baixa";
                            break;
                    }

                    //RESTRICCIO
                    System.out.println("Aquesta escola te restriccio? Si es que si escriu la data, si es NO, presiona ENTER");
                    String rest = scan.nextLine();

                    Escoles escola = new Escoles(1, idPob, nom, aprox, qtVies, popu, rest);
                    SQLiteEscolesDAO dao = new SQLiteEscolesDAO();
                    dao.llegirTot();
                    con.close();
                    dao.actualitzar(escola);
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
    }

    public static void eliminarEscola() {
        SQLiteEscolesDAO dao = new SQLiteEscolesDAO();
        Escoles escola = new Escoles(1, 1, "def", "def", 21, "def", "def");
        dao.llegirTot();
        dao.eliminar(escola);
    }
}
