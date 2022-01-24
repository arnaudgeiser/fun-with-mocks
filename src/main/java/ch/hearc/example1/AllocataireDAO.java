package ch.hearc.example1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AllocataireDAO {

  public List<Allocataire> findAll() {
    Connection connection = Transaction.getActiveConnection();
    try(PreparedStatement stmt = connection.prepareStatement("SELECT * FROM ALLOCATAIRES");
        ResultSet rs =  stmt.executeQuery()) {
      List<Allocataire> allocataires = new ArrayList<>();
      while (rs.next()) {
        allocataires.add(new Allocataire(rs.getLong("ID"), rs.getString("NOM"), rs.getString("PRENOM")));
      }
      return allocataires;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}
