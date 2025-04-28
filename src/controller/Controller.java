package controller;
import dao.SQLite.SQLiteEscaladorsDAO;
import view.*;
import java.util.Scanner;

public class Controller {
    public static void menu() {
        int num = -1;
        Scanner scan = new Scanner(System.in);
        while (num != 0) {
            View.menuPrincipal();
            num = scan.nextInt();
            switch (num) {
                case 1:
                    //ESCOLAS
                    int num1 = -1;
                    while (num1 != 0) {
                        View.menuEscoles();
                        num1 = scan.nextInt();
                        switch (num1) {
                            case 1:
                                break;

                            case 2:
                                break;

                            case 3:
                                break;

                            case 4:
                                break;

                            case 5:
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
                        switch (num2) {
                            case 1:
                                break;

                            case 2:
                                break;

                            case 3:
                                break;

                            case 4:
                                break;

                            case 5:
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
                        switch (num3) {
                            case 1:
                                break;

                            case 2:
                                break;

                            case 3:
                                break;

                            case 4:
                                break;

                            case 5:
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
                    break;

                case 6:
                    break;

                case 7:
                    break;

                case 8:
                    break;

                case 9:
                    break;

                case 10:
                    break;

                case 11:
                    break;

                case 12:
                    break;
            }
        }
    }
}
