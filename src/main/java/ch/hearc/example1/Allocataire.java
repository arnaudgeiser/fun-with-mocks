package ch.hearc.example1;

public class Allocataire {
  private final Long id;
  private final String nom;
  private final String prenom;

  public Allocataire(Long id, String nom, String prenom) {
    this.id = id;
    this.nom = nom;
    this.prenom = prenom;
  }

  public Long getId() {
    return id;
  }

  public String getNom() {
    return nom;
  }

  public String getPrenom() {
    return prenom;
  }
}
