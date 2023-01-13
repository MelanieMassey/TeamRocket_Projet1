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

}

/*
CLASSE ARBRE
*/
public class Tree {
    Node root;


    // Méthode ajout d'un noeud
    public Node addNode(Node current, Stagiaire data) {


        if (current == null) {
            System.out.println("Plus de noeud, on créé le noeud pour " + data.get_nom());
            return new Node(data);


        }

        try {
            System.out.println("Stagiaire " + data.get_nom() +
                    "\n********\nAdresse : " + data.get_adresse() +
                    "\nGauche : " + data.get_gauche() +
                    "\nDroite : " + data.get_droite() +
                    "\n");

            System.out.println("Current " + current.data.get_nom() +
                    "\n********\nAdresse : " + current.data.get_adresse() +
                    "\nGauche : " + current.data.get_gauche() +
                    "\nDroite : " + current.data.get_droite() +
                    "\n");



            System.out.println("Condition " + data.get_nom() + " < " + current.data.get_nom() + " ? => " + data.get_nom().compareTo(current.data.get_nom()));
            System.out.println("Condition " + data.get_nom() + " > " + current.data.get_nom() + " ? => " + data.get_nom().compareTo(current.data.get_nom()));
            if(data.get_nom().compareTo(current.data.get_nom()) <0 ){
                current.data.set_gauche(data.get_adresse());
                current.left = addNode(current.left, data);


                //TESTS
                System.out.println("Current modifie est : " + current.data.get_nom() +
                        "\n********\nAdresse : " + current.data.get_adresse() +
                        "\nGauche : " + current.data.get_gauche() +
                        "\nDroite : " + current.data.get_droite() +
                        "\n");

            } else if (data.get_nom().compareTo(current.data.get_nom()) >0 ) {
                current.data.set_droite(data.get_adresse());
                current.right = addNode(current.right, data);


                //TESTS
                System.out.println("Current modifie est : " + current.data.get_nom() +
                        "\n********\nAdresse : " + current.data.get_adresse() +
                        "\nGauche : " + current.data.get_gauche() +
                        "\nDroite : " + current.data.get_droite() +
                        "\n");
            } else {
                // La valeur existe déjà
                return current;
            }
            return current;


        }
        catch(NullPointerException e){
            System.out.println("NullPointerException caught");
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
            System.out.print("\n" + node.data);
            traverseInOrder(node.right);
        }
    }
}
