import java.util.*;

/*
CLASSE NOEUD
Création d'un noeud à partir de données
*/
class Node {
    // DONNEES du Stagiaire => Test avec un string
    //String data;
    Stagiaire data;

    // ATTRIBUTS
    Node left;
    Node right;

    // CONSTRUCTEUR Pour attribuer les données au noeud
    public Node(Stagiaire data) {
        this.data = data;
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
        } else if (data.get_nom().compareTo(current.data.get_nom()) > 0 ) {
            current.right = addNode(current.right, data);
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
