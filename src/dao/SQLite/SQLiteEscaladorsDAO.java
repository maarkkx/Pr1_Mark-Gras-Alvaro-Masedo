package dao.SQLite;

import dao.DBConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.Scanner;

public class SQLiteEscaladorsDAO implements CRUD<Escaladors> {

    /**
     * Funcio per crear un registre en la base de dades de escalada
     * @param escalador objecte d'on treure els parametres per la comanda sql
     */
    @Override
    public void crear(Escaladors escalador) {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO escaladors(nom, alies, edad, nivell, via_nom, estil) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setString(1, escalador.getNom());
            ps.setString(2, escalador.getAlies());
            ps.setInt(3, escalador.getEdat());
            ps.setString(4, escalador.getNivell());
            ps.setString(5, escalador.getNomVia());
            ps.setString(6, escalador.getEstil());

            ps.executeUpdate();
            System.out.println("S'ha creat correctament");
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
            e.printStackTrace();
        }

    }

    /**
     * Funcio per actualitzar un registre de la base de dades de escalada
     * @param escalador Agafa els parametres del objecte i els modifica al registre indicat.
     */
    @Override
    public void actualitzar(Escaladors escalador) {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE escaladors SET nom = ?, alies = ?, edad = ?, nivell = ?, estil = ? WHERE alies = ?");
            Scanner scan = new Scanner(System.in);
            System.out.println("Escriu el alies del escalador que vols actualitzar: ");
            String alies = scan.nextLine();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM escaladors WHERE alies = ?");
            check.setString(1, alies);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                ps.setString(1, escalador.getNom());
                ps.setString(2, escalador.getAlies());
                ps.setInt(3, escalador.getEdat());
                ps.setString(4, escalador.getNivell());
                ps.setString(5, escalador.getEstil());
                ps.setString(6, alies);
                ps.executeUpdate();
                System.out.println("S'ha actualitzat correctament");
            } else {
                System.out.println("El escalador no existeix, prova amb un altre alies.");
            }
            con.close();
            ps.close();
            check.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Funcio que amb el alies del escalador elimina el registre
     * @param escalador -
     */
    @Override
    public void eliminar(Escaladors escalador) {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM escaladors WHERE alies = ?");
            Scanner scan = new Scanner(System.in);
            System.out.println("Escriu el alies del escalador que vols eliminar: ");
            String alies = scan.nextLine();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM escaladors WHERE alies = ?");
            check.setString(1, alies);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                ps.setString(1, alies);
                ps.executeUpdate();
                System.out.println("S'ha eliminat correctament");
            } else {
                System.out.println("El escalador no existeix, prova amb un altre alies.");
            }
            con.close();
            ps.close();
            check.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    /**
     * Funcio per buscar un escalador en concret pasant-li el alies
     */
    @Override
    public void llegir() {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM escaladors WHERE alies = ?");
            Scanner scan = new Scanner(System.in);
            System.out.println("Escriu el alies del escalador que vols veure: ");
            String alies = scan.nextLine();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM escaladors WHERE alies = ?");
            check.setString(1, alies);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                ps.setString(1, alies);
                ResultSet rs2 = ps.executeQuery();
                System.out.printf("ID: %-3d Nom: %-20s alies: %-15s Edat: %-3d Nivell: %-4s Nom via: %-25s Estil: %s\n",
                        rs2.getInt("escaldor_id"),
                        rs2.getString("nom"),
                        rs2.getString("alies"),
                        rs2.getInt("edad"),
                        rs2.getString("nivell"),
                        rs2.getString("via_nom"),
                        rs2.getString("estil")
                );
            } else {
                System.out.println("El escalador no existeix, prova amb un altre alies.");
            }
            con.close();
            ps.close();
            check.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Funcio per llegir tota la taula de escaladors
     */
    public void llegirTot() {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM escaladors");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.printf("ID: %-3d Nom: %-40s alies: %-15s Edat: %-3d Nivell: %-4s Nom via: %-25s Estil: %s\n",
                        rs.getInt("escaldor_id"),
                        rs.getString("nom"),
                        rs.getString("alies"),
                        rs.getInt("edad"),
                        rs.getString("nivell"),
                        rs.getString("via_nom"),
                        rs.getString("estil")
                );
            }
            con.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
