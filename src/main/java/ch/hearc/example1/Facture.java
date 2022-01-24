package ch.hearc.example1;

import java.math.BigDecimal;

public class Facture {

  private final Long id;
  private final BigDecimal montant;

  public Facture(Long id, BigDecimal montant) {
    this.id = id;
    this.montant = montant;
  }

  public BigDecimal getMontant() {
    return montant;
  }
}
