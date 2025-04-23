package dao.SQLite;
import model.CRUD;
import model.Vies;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SQLiteViesDAO implements CRUD<Vies> {
    private Connection openCon;

    public SQLiteViesDAO(Connection openCon){
        this.openCon = openCon;
    }
    @Override
    public void crear(Vies vies) {
        String sql = "INSERT INTO vies (sector_id, ancoratge_id, tipus_roca_id, escalador_id, via_num, nom, orientacio, estat, tipus, llargada) VALUES (?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = openCon)
    }

    @Override
    public void actualitzar(Vies vies) {

    }

    @Override
    public void eliminar(Vies vies) {

    }

    @Override
    public void llegir() {

    }
}
