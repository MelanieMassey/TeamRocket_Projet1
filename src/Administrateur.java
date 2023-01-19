import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class Administrateur {

    private String _nom;
    private String _prenom;
    private String _identifiant;
    private String _password;

    public ObservableList<Administrateur> listeAdmin;


    public Administrateur(String identifiant, String password, String nom, String prenom) {
        this._nom = nom;
        this._prenom = prenom;
        this._identifiant = identifiant;
        this._password = password;
    }

    //Méthodes d'accès aux variables d'instance/getters
    public String get_nom(){return this._nom; }
    public String get_prenom(){return this._prenom; }
    public String get_identifiant(){return this._identifiant;}
    public String get_password(){return this._password;}

    //Modifieurs/setters
    public void set_nom(String newNom){
        this._nom = newNom;
    }
    public void setNom(String newPrenom){
        this._prenom = newPrenom;
    }
    public void set_identifiant(String newIdentifiant){
        this._identifiant = newIdentifiant;
    }
    public void set_password(String newPassword){
        this._password =newPassword;
    }

    //Méthodes
    @Override
    public String toString(){
        return this._identifiant+this._password;
    }

   public static boolean checkList(Administrateur admin, ObservableList<Administrateur> listeAdminId) {
       for (Administrateur a : listeAdminId) {
           if (admin.get_identifiant().equals(admin.get_identifiant()) || admin.get_password().equals(admin.get_password()))  return true;
           }
    return false;
   }

}
