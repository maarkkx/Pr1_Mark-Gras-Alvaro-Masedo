package dao.SQLite;

import dao.DBConnection;
import model.CRUD;
import model.Escoles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SQLiteEscolesDAO implements CRUD <Escoles> {
    @Override
    public void crear(Escoles escola) {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO escoles(poblacio_id, nom, aproximacio, vies_qt, popularitat, restriccio) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, escola.getIdpob());
            ps.setString(2, escola.getNom());
            ps.setString(3, escola.getAproximacio());
            ps.setInt(4, escola.getNumVies());
            ps.setString(5, escola.getPopularitat());
            ps.setString(6, escola.getRestriccio());

            ps.executeUpdate();
            System.out.println("S'ha creat correctament");
            con.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al afegir dades a la base de dades");
            e.printStackTrace();
        }
    }

    @Override
    public void actualitzar(Escoles escola) {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE escoles SET poblacio_id = ?, nom = ?, aproximacio = ?, vies_qt = ?, popularitat = ?, restriccio = ? WHERE nom = ?");
            Scanner scan = new Scanner(System.in);
            System.out.println("Escriu el nom de la escola que vols actualitzar: ");
            String nom = scan.nextLine();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM escoles WHERE nom = ?");
            check.setString(1, nom);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                ps.setInt(1, escola.getIdpob());
                ps.setString(2, escola.getNom());
                ps.setString(3, escola.getAproximacio());
                ps.setInt(4, escola.getNumVies());
                ps.setString(5, escola.getPopularitat());
                ps.setString(5, escola.getRestriccio());
                ps.setString(7, nom);
                ps.executeUpdate();
                System.out.println("S'ha actualitzat correctament");
            } else {
                System.out.println("La escola no existeix, prova amb un altre nom.");
            }
            con.close();
            ps.close();
            check.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void eliminar(Escoles escola) {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM escoles WHERE nom = ?");
            Scanner scan = new Scanner(System.in);
            System.out.println("Escriu el nom de la escola que vols eliminar: ");
            String nom = scan.nextLine();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM escoles WHERE nom = ?");
            check.setString(1, nom);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                ps.setString(1, nom);
                ps.executeUpdate();
                System.out.println("S'ha eliminat correctament");
            } else {
                System.out.println("La escola no existeix, prova amb un altre nom.");
            }

            con.close();
            ps.close();
            check.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void llegir() {
        Connection con = DBConnection.openCon();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM escoles WHERE nom = ?");
            Scanner scan = new Scanner(System.in);
            System.out.println("Escriu el nom de la escola que vols llegir: ");
            String nom = scan.nextLine();

            PreparedStatement check = con.prepareStatement("SELECT COUNT(*) FROM escoles WHERE nom = ?");
            check.setString(1, nom);
            ResultSet rs = check.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                ps.setString(1, nom);
                ResultSet rs2 = ps.executeQuery();
                System.out.printf("Escola ID: %-5d Poblacio ID: %-5d Nom: %s Aproximacio: %s Vies Qt: %-5d Popularitat: %-10s Restriccio: %-10s\n",
                        rs2.getInt("escola_id"),
                        rs2.getInt("poblacio_id"),
                        rs2.getString("nom"),
                        rs2.getString("aproximacio"),
                        rs2.getInt("vies_qt"),
                        rs2.getString("popularitat"),
                        rs2.getString("restriccio")
                );
            } else {
                System.out.println("La escola no existeix, prova amb un altre nom.");
            }
            con.close();
            ps.close();
            check.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

        public void llegirTot() {
            Connection con = DBConnection.openCon();
            try {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM escoles");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.printf("Escola ID: %-5d Poblacio ID: %-5d Nom: %-30s Aproximacio: %-40s Vies Qt: %-5d Popularitat: %-10s Restriccio: %-10s\n",
                            rs.getInt("escola_id"),
                            rs.getInt("poblacio_id"),
                            rs.getString("nom"),
                            rs.getString("aproximacio"),
                            rs.getInt("vies_qt"),
                            rs.getString("popularitat"),
                            rs.getString("restriccio")
                    );
                }
                con.close();
                ps.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        public void escolesRestriccio() {
        Connection con = DBConnection.openCon();
            try {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM escoles WHERE restriccio IS NOT NULL OR restriccio != \"\" OR restriccio != \" \";");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.printf("Escola ID: %-5d Poblacio ID: %-5d Nom: %-30s Aproximacio: %-40s Vies Qt: %-5d Popularitat: %-10s Restriccio: %-10s\n",
                            rs.getInt("escola_id"),
                            rs.getInt("poblacio_id"),
                            rs.getString("nom"),
                            rs.getString("aproximacio"),
                            rs.getInt("vies_qt"),
                            rs.getString("popularitat"),
                            rs.getString("restriccio")
                    );
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

