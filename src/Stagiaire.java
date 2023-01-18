import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.Vector;

public class Stagiaire {

    // Attributs
    private String _nom;
    private String _prenom;
    private String _departement;
    private String _annee;
    private String _promo;
    private String _adresse;
    private String _gauche;
    private String _droite;

    // Constructeur
    public Stagiaire( String nom, String prenom, String departement, String annee, String promo,  String adresse, String gauche, String droite) {
        this._nom = nom;
        this._prenom = prenom;
        this._departement = departement;
        this._annee = annee;
        this._promo = promo;
        this._adresse = adresse;
        this._gauche = gauche;
        this._droite = droite;
    }


    /*public Stagiaire(String nom, String prenom, String departement, String annee, String promo, int adresse, int gauche, int droite){
        this(nom, prenom, departement, annee, promo, adresse);
        this._gauche = gauche;
        this._droite = droite;
    }*/

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
    public void set_annee( String new_value ) { this._annee = new_value; }

    public String get_promo() {
        return this._promo ;
    }
    public void set_promo( String new_value ) {
        this._promo = new_value;
    }

    public String get_adresse(){
        return this._adresse;
    }
    public void set_adresse( String new_value ) {
        this._adresse = new_value;
    }

    public String get_gauche(){
        return this._gauche;
    }
    public void set_gauche( String new_value ) {
        this._gauche = new_value;
    }

    public String get_droite(){
        return this._droite;
    }
    public void set_droite( String new_value ) {
        this._droite = new_value;
    }


    // Méthodes
    public String toString()
    {
        return "Nom : " + _nom +
                "\nPrenom : " + _prenom +
                "\nDepartement : " + _departement +
                "\nAnnee : " + _annee +
                "\nPromo : " + _promo +
                "\nAdresse : " + _adresse +
                "\nGauche : " + _gauche +
                "\nDroite : " + _droite;
    }

    //public String toString()
    /*{
        return _nom +_prenom +_departement + _annee + _promo + _adresse + _gauche + _droite;
    }*/

    public void  afficherStagiaire() {
        System.out.println( "Nom : " + _nom +
                "\nPrenom : " + _prenom +
                "\nDepartement : " + _departement +
                "\nAnnee : " + _annee +
                "\nPromo : " + _promo +
                "\nAdresse : " + _adresse
        );
        /*"\nGauche : " + _gauche +
                "\nDroite : " + _droite)*/
    }

    public static ObservableList<Stagiaire> getStagiaireList(RandomAccessFile raf) throws IOException {

        List<Stagiaire> Stagiaires = new Vector<Stagiaire>();
        String chaine ="";
        String promo ="";
        String annee ="";
        String nom = "";
        String prenom = "";
        String dept = "";
        String p_gauche = "";
        String p_droite = "";
        int compteur= 1;
        long adresse = 0;

        try {
            do{
                raf.seek(adresse);
                for (int i = 0; i < 15; i++) {
                    promo += raf.readChar();
                }
                for (int i = 0; i < 10; i++) {
                    annee += raf.readChar();
                }
                for (int i = 0; i < 20; i++) {
                    nom += raf.readChar();
                }
                for (int i = 0; i < 20; i++) {
                    prenom += raf.readChar();
                }
                for (int i = 0; i < 5; i++) {
                    dept += raf.readChar();
                }
                for (int i = 0; i < 10; i++) {
                    p_gauche += raf.readChar();
                }
                for (int i = 0; i < 10; i++) {
                    p_droite += raf.readChar();
                }

                Stagiaire stg = new Stagiaire(nom, prenom, dept, annee, promo, String.valueOf(adresse), p_gauche, p_droite);
                System.out.println(stg.toString());
                Stagiaires.add(stg);

                adresse += 200;
                compteur ++;
            }while(compteur<3);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObservableList<Stagiaire> list = FXCollections.observableArrayList(Stagiaires);

        return list;
    }


}
