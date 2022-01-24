package ch.hearc.example1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;

public class Transaction {

  private static  ThreadLocal<Connection> activeConnection = new ThreadLocal<>();

  public static Connection getActiveConnection() {
    if(activeConnection.get()==null) {
      throw new IllegalStateException("No active JDBC connection");
    }
    return activeConnection.get();
  }

  // Pour les tests uniquement...
  public static <T> T inTransaction(Connection connection, Function<Connection, T> f) {
    try(Connection conn = connection)  {
      activeConnection.set(conn);
      T result = f.apply(conn);
      activeConnection.get().commit();
      return result;
    } catch (SQLException e) {
      throw new IllegalStateException();
    } finally {
      activeConnection.remove();
    }
  }

  public static <T> T inTransaction(Function<Connection, T> f) {
    try(Connection connection = DriverManager.getConnection("jdbc:...."))  {
      activeConnection.set(connection);
      T result = f.apply(connection);
      activeConnection.get().commit();
      return result;
    } catch (SQLException e) {
      throw new IllegalStateException();
    } finally {
      activeConnection.remove();
    }
  }
}
