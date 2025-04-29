package controller;

import dao.SQLite.SQLiteEscaladorsDAO;
import model.Escaladors;

import java.util.InputMismatchException;
import java.util.Scanner;

public class EscaladorsController {

    public static void crearEscalador() {

        //Nom
        Scanner scan = new Scanner (System.in);
        System.out.println("Escriu el nom del escalador: (Maxim 35 caracters)");
        String nom = scan.nextLine();
        while (nom.length() > 35) {
            System.out.println("El nom supera la mida maxima, torna a intentar-ho");
            nom = scan.nextLine();
        }


        //alias
        System.out.println("Escriu un alias: (Maxim 25 caracters)");
        String alias = scan.nextLine();
        while (alias.length() > 25) {
            System.out.println("El alias supera la mida maxima, torna a intentar-ho:");
            alias = scan.nextLine();
        }

        //edat
        System.out.println("Escriu l'edat: ");
            int edad = scan.nextInt();
            scan.nextLine();


        //Nivell
        String nivell = "";
        System.out.println("Escull la dificultat maxima del escalador, primer un numero del 4 al 9: ");
        int niv = scan.nextInt();
        scan.nextLine();
        while (niv < 4 && niv > 9) {
            System.out.println("El nivell ha de ser un numero del 4 al 9, torna a intentar-ho: ");
            niv = scan.nextInt();
            scan.nextLine();
        }

        if (niv == 4) {
            nivell += 4;
            System.out.println("Dificultat 4 o 4+? (No escriguis res en cas de 4 o escriu nomes + en cas de 4+): ");
            nivell += scan.nextLine();
        } else if (niv == 5) {
            nivell += 5;
            System.out.println("Dificultat 5 o 5+? (No escriguis res en cas de 5 o escriu nomes + en cas de 5+): ");
            nivell += scan.nextLine();
        } else if (niv == 6) {
            nivell += 6;
            System.out.println("Dificultat 6a, 6b o 6c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 7) {
            nivell += 7;
            System.out.println("Dificultat 7a, 7b o 7c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 8) {
            nivell += 8;
            System.out.println("Dificultat 8a, 8b o 8c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 9) {
            nivell += 9;
            System.out.println("Dificultat 9a, 9b o 9c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        }

        //Nom via
        String via = "";
        System.out.println("Escriu el nom de la via que ha creat, - en cas de cap (Ex: Via30-<nom>)");
        via = scan.nextLine();

        //Estil
        String estil = "";
        System.out.println("Quin estil fa? (Escriu el numero): \n 1-Esportiva 2-Gel 3-Clàssica");
        int est = scan.nextInt();
        scan.nextLine();
        switch (est) {
            case 1:
                estil = "esportiva";
                break;

            case 2:
                estil = "gel";
                break;

            case 3:
                estil = "clàssica";
                break;
        }

        //Crear Obj
        Escaladors escalador = new Escaladors(1, nom, alias, edad, nivell, via, estil);

        SQLiteEscaladorsDAO dao = new SQLiteEscaladorsDAO();
        dao.crear(escalador);
    }

    public static void actualitzarEscalador() {

        //Nom
        Scanner scan = new Scanner (System.in);
        System.out.println("Escriu el nom del escalador: (Maxim 35 caracters)");
        String nom = scan.nextLine();
        while (nom.length() > 35) {
            System.out.println("El nom supera la mida maxima, torna a intentar-ho");
            nom = scan.nextLine();
        }


        //alias
        System.out.println("Escriu un alias: (Maxim 25 caracters)");
        String alias = scan.nextLine();
        while (alias.length() > 25) {
            System.out.println("El alias supera la mida maxima, torna a intentar-ho:");
            alias = scan.nextLine();
        }

        //edat
        System.out.println("Escriu l'edat: ");
        int edad = scan.nextInt();
        scan.nextLine();


        //Nivell
        String nivell = "";
        System.out.println("Escull la dificultat maxima del escalador, primer un numero del 4 al 9: ");
        int niv = scan.nextInt();
        scan.nextLine();
        while (niv < 4 && niv > 9) {
            System.out.println("El nivell ha de ser un numero del 4 al 9, torna a intentar-ho: ");
            niv = scan.nextInt();
            scan.nextLine();
        }

        if (niv == 4) {
            nivell += 4;
            System.out.println("Dificultat 4 o 4+? (No escriguis res en cas de 4 o escriu nomes + en cas de 4+): ");
            nivell += scan.nextLine();
        } else if (niv == 5) {
            nivell += 5;
            System.out.println("Dificultat 5 o 5+? (No escriguis res en cas de 5 o escriu nomes + en cas de 5+): ");
            nivell += scan.nextLine();
        } else if (niv == 6) {
            nivell += 6;
            System.out.println("Dificultat 6a, 6b o 6c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 7) {
            nivell += 7;
            System.out.println("Dificultat 7a, 7b o 7c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 8) {
            nivell += 8;
            System.out.println("Dificultat 8a, 8b o 8c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        } else if (niv == 9) {
            nivell += 9;
            System.out.println("Dificultat 9a, 9b o 9c? (Escriu nomes la lletra en minuscula)");
            nivell += scan.nextLine();

            System.out.println("Dificultat + o no? (No escriguis res si no es + o escriu nomes + si ho es");
            nivell += scan.nextLine();
        }

        //Nom via
        String via = "";
        System.out.println("Escriu el nom de la via que ha creat, - en cas de cap (Ex: Via30-<nom>)");
        via = scan.nextLine();

        //Estil
        String estil = "";
        System.out.println("Quin estil fa? (Escriu el numero): \n 1-Esportiva 2-Gel 3-Clàssica");
        int est = scan.nextInt();
        scan.nextLine();
        switch (est) {
            case 1:
                estil = "esportiva";
                break;

            case 2:
                estil = "gel";
                break;

            case 3:
                estil = "clàssica";
                break;
        }

        //Crear Obj
        Escaladors escalador = new Escaladors(1, nom, alias, edad, nivell, via, estil);

        SQLiteEscaladorsDAO dao = new SQLiteEscaladorsDAO();
        dao.llegirTot();
        dao.actualitzar(escalador);
    }

    public static void eliminarEscalador() {
        SQLiteEscaladorsDAO dao = new SQLiteEscaladorsDAO();
        dao.llegirTot();
        Escaladors escalador = new Escaladors(1, "def", "def", 0,"def", "def", "def");
        dao.eliminar(escalador);
    }
}
