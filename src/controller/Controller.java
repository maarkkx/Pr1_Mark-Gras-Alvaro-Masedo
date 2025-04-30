package controller;
import dao.SQLite.SQLiteEscaladorsDAO;
import dao.SQLite.SQLiteEscolesDAO;
import dao.SQLite.SQLiteSectorsDAO;
import dao.SQLite.SQLiteViesDAO;
import view.*;
import java.util.Scanner;

public class Controller {
    public static void menu() {
        int num = -1;
        Scanner scan = new Scanner(System.in);
        while (num != 0) {
            View.menuPrincipal();
            num = scan.nextInt();
            scan.nextLine();
            switch (num) {
                case 1:
                    //ESCOLAS
                    int num1 = -1;
                    while (num1 != 0) {
                        View.menuEscoles();
                        num1 = scan.nextInt();
                        scan.nextLine();
                        SQLiteEscolesDAO dao = new SQLiteEscolesDAO();
                        switch (num1) {
                            case 1:
                                EscolesController.crearEscola();
                                break;

                            case 2:
                                EscolesController.actualitzarEscoles();
                                break;

                            case 3:
                                EscolesController.eliminarEscola();
                                break;

                            case 4:
                                dao.llegir();
                                break;

                            case 5:
                                dao.llegirTot();
                                break;
                        }
                    }
                    break;

                case 2:
                    //SECTORS
                    int num2 = -1;
                    while (num2 != 0) {
                        View.menuSectors();
                        num2 = scan.nextInt();
                        scan.nextLine();
                        switch (num2) {
                            case 1:
                                SectorsController.afegirSector();
                                break;

                            case 2:
                                SectorsController.actualitzarSector();
                                break;

                            case 3:
                                SectorsController.eliminarSector();
                                break;

                            case 4:
                                SectorsController.llistarUn();
                                break;

                            case 5:
                                SectorsController.llistarTot();
                                break;
                        }
                    }
                    break;

                case 3:
                    //VIES
                    int num3 = -1;
                    while (num3 != 0) {
                        View.menuVies();
                        num3 = scan.nextInt();
                        scan.nextLine();
                        switch (num3) {
                            case 1:
                                ViesController.afegirVia();
                                break;

                            case 2:
                                ViesController.actualitzarVia();
                                break;

                            case 3:
                                ViesController.eliminarVia();
                                break;

                            case 4:
                                ViesController.llistarUnaVia();
                                break;

                            case 5:
                                ViesController.llistarTotesVies();
                                break;
                        }
                    }
                    break;

                case 4:
                    //ESCALADORS
                    int num4 = -1;
                    while (num4 != 0) {
                        SQLiteEscaladorsDAO dao = new SQLiteEscaladorsDAO();
                        View.menuEscaladors();
                        num4 = scan.nextInt();
                        scan.nextLine();
                        switch (num4) {
                            case 1:
                                EscaladorsController.crearEscalador();
                                break;

                            case 2:
                                EscaladorsController.actualitzarEscalador();
                                break;

                            case 3:
                                EscaladorsController.eliminarEscalador();
                                break;

                            case 4:
                                dao.llegir();
                                break;

                            case 5:
                                dao.llegirTot();
                                break;
                        }
                    }
                    break;

                case 5:
                    //Vies per escola
                    SQLiteViesDAO dao = new SQLiteViesDAO();
                    dao.totesViesEscola();
                    break;

                case 6:
                    //Vies per rang de dificultat
                    SQLiteViesDAO dao2 = new SQLiteViesDAO();
                    dao2.viesRangDificultat();
                    break;

                case 7:
                    //Vies per estat
                    SQLiteViesDAO dao3 = new SQLiteViesDAO();
                    dao3.estatVies();
                    break;

                case 8:
                    //Escoles Restriccions
                    SQLiteEscolesDAO dao4 = new SQLiteEscolesDAO();
                    dao4.escolesRestriccio();
                    break;

                case 9:
                    break;

                case 10:
                    break;

                case 11:
                    break;
            }
        }
    }
}
