import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.RandomAccessFile;

/*
CLASSE NOEUD
Création d'un noeud à partir de données
*/

class Node {
    // DONNEES du Stagiaire => Test avec un string
    //String data;
    Stagiaire data;

    // Permettront de circuler dans l'arbre
    Node leftChild;
    Node rightChild;

    // ATTRIBUTS
   /* private int _left;
    private int _right;*/

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
    //public Node addNode(Node current, Stagiaire data) {
    public void addNode(Stagiaire data) {
        Node newNode= new Node(data);
        //cas où le nouveau noeud est la racine
        if (root == null) {
           // return new Node(data);
            root= newNode;
        }
        else{
            //on démarre de la racine
            Node currentNode=root;
            //on crée un noeud parent
            Node parent;
            while(true){
                parent=currentNode;
                //compare la valeur des nouvelles ddonnées avec la valeur du noeud parent
                if (data.get_nom().compareTo(currentNode.data.get_nom()) <0){
                    //la valeur est inférieure: on focus sur le noeud enfant gauche
                    currentNode=currentNode.leftChild;                    //on se place à gauche du noeud parent
                    if(currentNode==null){                               //leftchild node n'a pas d'enfant:
                        parent.leftChild=newNode;                        //on attribue le noeud gauche au parent
                        parent.data.set_gauche(data.get_adresse());     //on attribue l'adresse au parent l'adresse du leftchild node
                        return;
                    }
                } else {                                                //la valeur est supérieure au noeud parent
                    currentNode=currentNode.rightChild;                 //on se place à droite du parent
                    if(currentNode==null){                              //rightChild node n'a pas d'enfants
                        parent.rightChild=newNode;                      //on attribue le noeud droit au parent
                        parent.data.set_droite(data.get_adresse());     //on attribue au parent l'adresse du noeud droit
                        return; //return None
                    }
                }
            }
        }

        /*try {
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
        return current;*/
    }

    // Démarre la récursivité depuis le noeud racine
    /*public void add(Stagiaire data) {
        root = addNode(root, data);
    }*/

    // Récupération du noeud de l'arbre
 /*   public Node getRoot(){
        return root;
    }*/

    // Lecture de l'arbre par ordre alphabétique
    public void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.leftChild);
            System.out.println("\n" + node.data);
            traverseInOrder(node.rightChild);

        }
    }

    public void traverseInOrderAndWrite (Node node, RandomAccessFile raf){

        if (node != null) {
            traverseInOrderAndWrite(node.leftChild,raf);
            try {
                raf.writeChars(node.data.get_nom()
                                +node.data.get_prenom()
                                +node.data.get_departement()
                                +node.data.get_annee()
                                +node.data.get_promo()
                                +node.data.get_adresse()
                                +node.data.get_gauche()
                                +node.data.get_droite());
                traverseInOrderAndWrite(node.rightChild,raf);
            }  catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void searchInTreeWriteInBin (Node node, String address,RandomAccessFile raf) {
        if (node != null) {
            if (node.data.get_adresse().equals(address)) {
                try {
                    raf.writeChars(node.data.get_nom()
                            + node.data.get_prenom()
                            + node.data.get_departement()
                            + node.data.get_annee()
                            + node.data.get_promo()
                            + node.data.get_adresse()
                            + node.data.get_gauche()
                            + node.data.get_droite());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            searchInTreeWriteInBin(node.leftChild, address,raf);
            searchInTreeWriteInBin(node.rightChild, address,raf);
        }

    }



    public void rechercherStagiaireNom(Node node, String nom){

//        System.out.println("Nom recherché = " + nom);
//        System.out.println("Nom du noeud exploré = " + nomNode);
//
        ObservableList<Node> result;
//
        if(node != null) {
            // Formattage sur nom du Node : suppression des espaces
            String nomNode = node.data.get_nom().replaceAll("\\s+","");

            if(nomNode.equals(nom)){
                result = createResultList(node);
                System.out.println("Nom du noeud ( " + nomNode + " ) = nom recherche ( " + nom + " )");
                rechercherStagiaireNom(node.leftChild, nom);
                rechercherStagiaireNom(node.rightChild, nom);
            } else {
                rechercherStagiaireNom(node.leftChild, nom);
                rechercherStagiaireNom(node.rightChild, nom);
            }
        }
    }

    // Méthode qui créé une ObservableList en fonction des résultats de la recherche lancée
    private ObservableList<Node> createResultList(Node node){
        ObservableList<Node> list = FXCollections.observableArrayList(node);
        return list;
    }

}

