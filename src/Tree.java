import java.util.*;

/*
CLASSE NOEUD
Création d'un noeud à partir de données
*/
class Node {
    // DONNEES du Stagiaire => Test avec un string
    //String data;
    Stagiaire data;

    // Permettront de circuler dans l'arbre
    Node left;
    Node right;

    // ATTRIBUTS
    private int _left;
    private int _right;

    // CONSTRUCTEUR Pour attribuer les données au noeud
    public Node(Stagiaire data) {
        this.data = data;
    }

    // Constructeur 2
    public Node(int gauche, int droite){

        this._left = gauche;
        this._right = droite;
    }

    public void set_left(int left){
        this._left = left;
    }

    public void set_right(int right){
        this._right = right;
    }
}

/*
CLASSE ARBRE
*/
public class Tree {
    Node root;


    // Méthode ajout d'un noeud
    public Node addNode(Node current, Stagiaire data) {
        if (current == null) {

            return new Node(data);

        }

        if(data.get_nom().compareTo(current.data.get_nom()) < 0 ){
            current.left = addNode(current.left, data);
            current.set_left(data.get_adresse());
            System.out.println(current);
        } else if (data.get_nom().compareTo(current.data.get_nom()) > 0 ) {
            current.right = addNode(current.right, data);
            current.set_right(data.get_adresse());
        } else {
            // La valeur existe déjà
            return current;
        }
        return current;

    }

    // Démarre la récursivité depuis le noeud racine
    public void add(Stagiaire data) {
        root = addNode(root, data);
    }

    // Récupération du noeud de l'arbre
    public Node getRoot(){
        return root;
    }

    // Lecture de l'arbre par ordre alphabétique
    public void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(" " + node.data.get_nom());
            traverseInOrder(node.right);
        }
    }
}
