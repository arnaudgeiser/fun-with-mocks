package ch.hearc.example1;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AllocataireServiceTest {

  @Test
  @DisplayName("Mock de la transaction JDBC")
  public void test1() throws SQLException {
    // Pas de mocks sur les services et les DAOs
    var allocataireService = new AllocataireService(new  AllocataireDAO(), new FactureDAO());

    // Comment mocker la transaction JDBC?
    // Pas trop possible dans le design actuel, car la gestion ouverture/fermeture de la
    // transaction est faite dans la méthode Transaction#inTransaction

    // On peut toutefois rajouter une méthode Transaction#inTransaction qui reçoit en
    // paramètre la méthode à utiliser....

    Connection connection = Mockito.mock(Connection.class);
    // String facture = Transaction.inTransaction(connection, (__ -> allocataireService.generateFactures()));

    // Il faut maintenant trouver un moyen d'instrumenter la connection JDBC, la gestion des PreparedStatement, des ResultSet etc...
    PreparedStatement stmt = Mockito.mock(PreparedStatement.class);
    Mockito.when(connection.prepareStatement("SELECT * FROM ALLOCATAIRES")).thenReturn(stmt);
    Mockito.when(stmt.executeQuery()).thenReturn(new ResultSetStub(List.of(Map.of("NOM", "Geiser", "PRENOM", "Arnaud", "ID", 1L))));

    // et pour les factures aussi
    PreparedStatement stmt2 = Mockito.mock(PreparedStatement.class);
    Mockito.when(connection.prepareStatement("SELECT * FROM FACTURES WHERE FK_ALLOCATAIRES=?")).thenReturn(stmt2);
    Mockito.when(stmt2.executeQuery()).thenReturn(new ResultSetStub(List.of(Map.of("ID", 1L, "MONTANT", new BigDecimal(200)), Map.of("ID", 2L, "MONTANT", new BigDecimal(100)))));

    // Mais du coup, comment retourner des valeurs différentes si il y a plusieurs allocataires?....
    // Mince, ça vaudrait dire qu'un mock ne sera pas suffisant pour les PreparedStatement, il faudra
    // créer notre propre classe pour gérer l'état lors du PreparedStatement#setParameter

    String facture = Transaction.inTransaction(connection, (__ -> allocataireService.generateFactures()));

    Assertions.assertEquals("Geiser Arnaud : Montant: 300", facture);
  }


}