package view;

public class View {
    public static void menuPrincipal(){
        System.out.println("Que vols fer?\n");

        System.out.println("1. Modificar dades");
        System.out.println("2. Consultar dades ");
        System.out.println("0. Sortir");
    }

    public static void menuModificarDades(){
        System.out.println("Que vols fer?\n");

        System.out.println("1. Crear una nova entrada");
        System.out.println("2. Modificar una entrada");
        System.out.println("3. Eliminar una entrada");
        System.out.println("0. Sortir");
    }

    public static void crearDades(){
        System.out.println("De que vols afegir les dades?\n");

        System.out.println("1. Escoles");
        System.out.println("2. Sectors");
        System.out.println("3. Vies");
        System.out.println("4. Escaladors");
        System.out.println("0. Sortir");
    }

    public static void modificarDades(){
        System.out.println("De que vols modificar les dades?\n");

        System.out.println("1. Escoles");
        System.out.println("2. Sectors");
        System.out.println("3. Vies");
        System.out.println("4. Escaladors");
        System.out.println("0. Sortir");
    }

    public static void eliminarDades(){
        System.out.println("De que vols eliminar les dades?\n");

        System.out.println("1. Escoles");
        System.out.println("2. Sectors");
        System.out.println("3. Vies");
        System.out.println("4. Escaladors");
        System.out.println("0. Sortir");
    }

    public static void menuConsultarDades(){
        System.out.println("Que vols consultar?\n");

        System.out.println("1. Consultar una entrada especifica");
        System.out.println("2. Consultar totes les entrades");
        System.out.println("3. Consultar les vies d'una Escola que es trobaran disponibles");
        System.out.println("4. Cercar vies per dificultat en un rang específic (via, grau, sector, escola)");
        System.out.println("5. Cercar vies segons estat (Apte, Construcció, Tancada)");
        System.out.println("6. Consultar escoles amb restriccions actives actualment");
        System.out.println("7. Mostrar sectors amb més de X vies disponibles");
        System.out.println("8. Mostrar escaladors amb el mateix nivell màxim assolit");
        System.out.println("9. Mostrar les vies que han passat a \"Apte\" recentment");
        System.out.println("10. Mostrar les vies més llargues d’una escola determinada");
        System.out.println("0. Sortir");
    }

    public static void consultarUna(){
        System.out.println("De que vols consultar una entrada?\n");

        System.out.println("1. Escoles");
        System.out.println("2. Sectors");
        System.out.println("3. Vies");
        System.out.println("4. Escaladors");
        System.out.println("0. Sortir");
    }

    public static void consultarTotes(){
        System.out.println("De que vols consultar totes les dades?\n");

        System.out.println("1. Escoles");
        System.out.println("2. Sectors");
        System.out.println("3. Vies");
        System.out.println("4. Escaladors");
        System.out.println("0. Sortir");
    }
}
