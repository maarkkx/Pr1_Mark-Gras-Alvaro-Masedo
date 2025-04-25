package view;

public class View {
    public static void menuPrincipal(){
        System.out.println("Que vols tractar?\n");

        System.out.println("1. Escoles");
        System.out.println("2. Sectors");
        System.out.println("3. Vies");
        System.out.println("4. Escaladors");
        System.out.println("5. Consultar les vies d'una Escola que es trobaran disponibles");
        System.out.println("6. Cercar vies per dificultat en un rang específic (via, grau, sector, escola)");
        System.out.println("7. Cercar vies segons estat (Apte, Construcció, Tancada)");
        System.out.println("8. Consultar escoles amb restriccions actives actualment");
        System.out.println("9. Mostrar sectors amb més de X vies disponibles");
        System.out.println("10. Mostrar escaladors amb el mateix nivell màxim assolit");
        System.out.println("11. Mostrar les vies que han passat a \"Apte\" recentment");
        System.out.println("12. Mostrar les vies més llargues d’una escola determinada");
        System.out.println("0. Sortir");
    }

    public static void menuTaula(){
        System.out.println("Que vols fer?\n");

        System.out.println("1. Crear una nova entrada");
        System.out.println("2. Modificar una entrada");
        System.out.println("3. Eliminar una entrada");
        System.out.println("4. LListar una entrada");
        System.out.println("5. Llistar tota la taula");
        System.out.println("0. Sortir");
    }
}
