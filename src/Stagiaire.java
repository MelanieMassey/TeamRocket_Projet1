public class Stagiaire {

    // Attributs
    private String _nom;
    private String _prenom;
    private String _departement;
    private String _annee;
    private String _promo;

    // Constructeur
    public Stagiaire( String nom, String prenom, String departement, String annee, String promo ) {
        this._nom = nom;
        this._prenom = prenom;
        this._departement = departement;
        this._annee = annee;
        this._promo = promo;
    }

    // Accesseurs
    public String get_nom() {
        return this._nom ;
    }
    public void set_nom( String new_value ) {
        this._nom = new_value;
    }

    public String get_prenom() {
        return this._prenom ;
    }
    public void set_prenom( String new_value ) {
        this._prenom = new_value;
    }

    public String get_departement() {
        return this._departement ;
    }
    public void set_departement( String new_value ) {
        this._departement = new_value;
    }

    public String get_annee() {
        return this._annee ;
    }
    public void set_annee( String new_value ) {
        this._annee = new_value;
    }

    public String get_promo() {
        return this._promo ;
    }
    public void set_promo( String new_value ) {
        this._promo = new_value;
    }

    // Méthodes
    public String toString()
    {
        return "Nom : " + _nom +
                "\nPrenom : " + _prenom +
                "\nDepartement : " + _departement +
                "\nAnnee : " + _annee +
                "\nPromo : " + _promo;
    }

    public void  afficherStagiaire()
    {
        System.out.println( "Nom : " + _nom +
                "\nPrenom : " + _prenom +
                "\nDepartement : " + _departement +
                "\nAnnee : " + _annee +
                "\nPromo : " + _promo);
    }

}
