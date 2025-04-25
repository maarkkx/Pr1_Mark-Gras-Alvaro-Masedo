package dao.SQLite;

import dao.DBConnection;
import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.*;
import java.util.Scanner;

public class SQLiteEscaladorsDAO implements CRUD<Escaladors> {

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

            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
            e.printStackTrace();
        }

    }


    @Override
    public void actualitzar(Escaladors escalador) {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE escaladors SET nom = ?, alies = ?, edat = ?, nivell = ?, estil = ? WHERE alias = ?");
            Scanner scan = new Scanner(System.in);
            System.out.println("Escriu el alias del escalador que vols actualitzar: ");
            String alias = scan.nextLine();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM escaladors WHERE alies = ?");
            check.setString(1, alias);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                ps.setString(1, escalador.getNom());
                ps.setString(2, escalador.getAlies());
                ps.setInt(3, escalador.getEdat());
                ps.setString(4, escalador.getNivell());
                ps.setString(5, escalador.getEstil());
                ps.setString(6, alias);
                ps.executeUpdate();
            } else {
                System.out.println("El escalador no existeix, prova amb un altre alias.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void eliminar(Escaladors escalador) {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM escaladors WHERE alies = ?");
            Scanner scan = new Scanner(System.in);
            System.out.println("Escriu el alias del escalador que vols actualitzar: ");
            String alias = scan.nextLine();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM escaladors WHERE alies = ?");
            check.setString(1, alias);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                ps.setString(1, alias);
                ps.executeUpdate();
            } else {
                System.out.println("El escalador no existeix, prova amb un altre alias.");
            }


        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    @Override
    public void llegir() {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM escaladors WHERE alias = ?");
            Scanner scan = new Scanner(System.in);
            System.out.println("Escriu el alias del escalador que vols actualitzar: ");
            String alias = scan.nextLine();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM escaladors WHERE alies = ?");
            check.setString(1, alias);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                ps.setString(1, alias);
                ResultSet rs2 = ps.executeQuery();
                System.out.println("Escalador trobat:");
                System.out.println("Nom: " + rs2.getString("nom"));
                System.out.println("Àlies: " + rs2.getString("alies"));
                System.out.println("Edat: " + rs2.getInt("edat"));
                System.out.println("Nivell: " + rs2.getString("nivell"));
                System.out.println("Nom via: " + rs2.getString("nom_via"));
                System.out.println("Estil: " + rs2.getString("estil"));
            } else {
                System.out.println("El escalador no existeix, prova amb un altre alias.");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void llegirTot() {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM escaladors");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("escalador_id") + " Nom: " + rs.getString("nom") + " alias: " + rs.getString("alias") + " Edat: " + rs.getInt("edad") + " Nivell: " + rs.getString("nivell") + " Nom via: " + rs.getString("via_nom") + " Estil: " + rs.getString("estil"));
            }
        } catch (SQLException e) {

        }
    }
}
