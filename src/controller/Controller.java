package controller;
import view.*;
import java.util.Scanner;

public class Controller {
    public static void menu() {
        Scanner scan = new Scanner(System.in);
        int num = -1;
        int num2;
        int num3;

        while(num != 0) {
            num2 = -1;
            //mostrar menu principal
            View.menuPrincipal();
            num = scan.nextInt();
            switch (num) {

                //cas mostrar menu per modificar dades
                case 1:
                    while(num2 != 0) {
                        num3 = -1;
                        View.menuModificarDades();
                        num2 = scan.nextInt();
                        switch (num2) {

                            //cas mostrar menu per crear dades
                            case 1:
                                while(num3 != 0) {
                                    View.crearDades();
                                    num3 = scan.nextInt();
                                    switch (num3) {
                                        case 1:
                                            //LLAMAR DESDE MODELO CREAR ESCOLA
                                            break;

                                        case 2:
                                            //LLAMAR DESDE MODELO CREAR SECTORS
                                            break;

                                        case 3:
                                            //LLAMAR DESDE MODELO CREAR VIES
                                            break;

                                        case 4:
                                            //LLAMAR DESDE MODELO CREAR ESCALADORS
                                            break;
                                    }
                                }
                                break;

                            //cas mostrar menu modificar una dada
                            case 2:
                                while(num3 != 0) {
                                    View.modificarDades();
                                    num3 = scan.nextInt();
                                    switch (num3) {
                                        case 1:
                                            //LLAMAR DESDE MODELO MODIFICAR ESCOLA
                                            break;

                                        case 2:
                                            //LLAMAR DESDE MODELO MODIFICAR SECTORS
                                            break;

                                        case 3:
                                            //LLAMAR DESDE MODELO MODIFICAR VIES
                                            break;

                                        case 4:
                                            //LLAMAR DESDE MODELO MODIFICAR ESCALADORS
                                            break;
                                    }
                                }
                                break;

                            //cas mostrar menu eliminar dades
                            case 3:
                                while(num3 != 0) {
                                    View.eliminarDades();
                                    num3 = scan.nextInt();
                                    switch (num3) {
                                        case 1:
                                            //LLAMAR DESDE MODELO ELIMINAR ESCOLA
                                            break;

                                        case 2:
                                            //LLAMAR DESDE MODELO ELIMINAR SECTORS
                                            break;

                                        case 3:
                                            //LLAMAR DESDE MODELO ELIMINAR VIES
                                            break;

                                        case 4:
                                            //LLAMAR DESDE MODELO ELIMINAR ESCALADORS
                                            break;
                                    }
                                }
                                break;
                        }
                    }
                    break;

                //cas mostrar menu per consultar dades
                case 2:
                    while(num2 != 0) {
                        num3 = -1;
                        View.menuConsultarDades();
                        num2 = scan.nextInt();
                        switch (num2) {
                            //
                            case 1:
                                while(num3 != 0) {
                                    View.consultarUna();
                                    num3 = scan.nextInt();
                                    switch(num3) {
                                        case 1:
                                            //PONER DESDE MODEL CONSULTAR UNA ESCOLA
                                            break;

                                        case 2:
                                            //PONER DESDE MODEL CONSULTAR UN SECTOR
                                            break;

                                        case 3:
                                            //PONER DESDE MODEL CONSULTAR UNA VIA
                                            break;

                                        case 4:
                                            //PONER DESDE MODEL CONSULTAR UN ESCALADOR
                                            break;
                                    }
                                }
                                break;

                            case 2:
                                while(num3 != 0) {
                                    View.consultarTotes();
                                    num3 = scan.nextInt();
                                    switch(num3) {
                                        case 1:
                                            //PONER DESDE MODEL CONSULTAR TOTES ESCOLES
                                            break;

                                        case 2:
                                            //PONER DESDE MODEL CONSULTAR TOTS SECTORS
                                            break;

                                        case 3:
                                            //PONER DESDE MODEL CONSULTAR TOTES VIES
                                            break;

                                        case 4:
                                            //PONER DESDE MODEL CONSULTAR TOTS ESCALADORS
                                            break;
                                    }
                                }
                                break;


                            //OPCIONS CONSULTAR
                            case 3:
                                break;

                            case 4:
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
                        }
                    }
                    break;
            }
        }

    }
}
