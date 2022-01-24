package ch.hearc.example1;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class AllocataireService {

  private final AllocataireDAO allocataireDAO;
  private final FactureDAO factureDAO;

  public AllocataireService(AllocataireDAO allocataireDAO, FactureDAO factureDAO) {
    this.allocataireDAO = allocataireDAO;
    this.factureDAO = factureDAO;
  }

  public String generateFacturesInTransaction() {
    return Transaction.inTransaction(connection -> generateFactures());
  }

  public String generateFactures() {
    List<Allocataire> allocataires = allocataireDAO.findAll();

    // Génération d'un rapport sous forme de String
    return allocataires.stream()
        .map(a ->
            a.getNom() + " " + a.getPrenom() + " : Montant: "  + factureDAO.findFacturesForAllocataire(a.getId()).stream()
            .map(Facture::getMontant)
            .reduce(BigDecimal.ZERO, BigDecimal::add))
        .collect(Collectors.joining("\n"));


  }


}
