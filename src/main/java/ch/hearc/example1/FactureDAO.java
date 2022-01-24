package ch.hearc.example1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FactureDAO {

  public List<Facture> findFacturesForAllocataire(Long allocataireId) {
    Connection connection = Transaction.getActiveConnection();
    try(PreparedStatement stmt = connection.prepareStatement("SELECT * FROM FACTURES WHERE FK_ALLOCATAIRES=?")) {
      stmt.setLong(1, allocataireId);
      try(ResultSet rs =  stmt.executeQuery()) {
        List<Facture> factures = new ArrayList<>();
        while (rs.next()) {
          factures.add(new Facture(rs.getLong("ID"), rs.getBigDecimal("MONTANT")));
        }
        return factures;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
