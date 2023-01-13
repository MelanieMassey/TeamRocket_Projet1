import java.io.*;


public class Main {

    public static final int PROMO = 15;        //nombre de caractères max alloués
    public static final int ANNEE = 10;
    public static final int PRENOM = 20;
    public static final int NOM = 20;
    public static final int DEPARTEMENT = 2;

    public static final int ADRESSE = 10;
    public static final int GAUCHE = 10;
    public static final int DROITE = 10;

    //Allocation espace total saisie infos stagiaire
    public static final int STAGIAIRELENGTH = ((PROMO + ANNEE + PRENOM + NOM + DEPARTEMENT) * 2 + 3); // +3 = espace pris par les 3 int Adresse, Gauche et Droite


    public static void main(String[] args) {

        Tree arbreTest = new Tree();

        /*Stagiaire Celine = new Stagiaire("Hernandez", "Celine", 59, 2022, "CAPEQELLES");
        Stagiaire Aline = new Stagiaire("Lacaze", "Aline", 31, 2022, "CAPEQELLES");
        Stagiaire Lydia = new Stagiaire("Lachenaud", "Lydia", 35, 2022, "CAPEQELLES");
        Stagiaire Melanie = new Stagiaire("Massey", "Melanie", 31, 2022, "CAPEQELLES");

        arbreTest.add(Celine);
        arbreTest.add(Aline);
        arbreTest.add(Lydia);
        arbreTest.add(Melanie);*/



        String ligne = "";
        String mot;
        String mot_promo="", mot_prenom="", mot_departement="", mot_nom="", mot_annee="";
        int adresse = 0;
        String gauche = "", droite = "";


        //Initialisation des compteurs pour les lignes du fichier et pour les stagiaires
        int linenumber = 0;
        int compteurStagiaires = 0;



        RandomAccessFile raf;
        try {
            // Création d'un fichier bin vide
            raf = new RandomAccessFile("listeStagiaires.bin", "rw");

            //Lecture du fichier text
            FileReader fichierOriginal = new FileReader("src/stagiaires.txt");
            BufferedReader bf = new BufferedReader(fichierOriginal);


            /* Lecture du fichier txt ligne par ligne tant que la ligne n'est pas vide
               Récupération des informations sur chaque ligne Ecriture des données dans le fichier bin:
                    Ligne 0: Promo
                    Ligne 1: Année
                    Ligne 2: Nom
                    Ligne 3: Prénom
                    Ligne 4: Département
                    Ligne 5: réinitialisation du compteur de ligne pour collecter les informations pour le stagiaire suivant
                             + Création d'un objet de la clsse stagiaire pour stocker les infos
                             + Création d'un noeud (avec ces données)
                             + incrémentation du compteur stagiaire

             */
            while ((ligne = bf.readLine()) != null && (linenumber <= 5)) {

                mot = "";
                String[] tokens = ligne.split("  ");   //split la ligne en utilisant triple espace comme séparateur
                mot += tokens[0];                            //récupération du premier token (mot)
                switch (linenumber) {
                    case 0:
                        mot_promo = mot;
                        mot_promo= completer(mot_promo, PROMO);

                        break;
                    case 1:
                        mot_annee =mot;
                        mot_annee = completer(mot_annee, ANNEE);

                        break;
                    case 2:
                        mot_nom=mot;
                        mot_nom = completer(mot_nom, NOM);

                        break;
                    case 3:
                        mot_prenom=mot;
                        mot_prenom = completer(mot_prenom, PRENOM);

                        break;
                    case 4:
                        mot_departement=mot;
                        mot_departement = completer(mot_departement, DEPARTEMENT);

                        break;
                    case 5:
                        // lnr.setLineNumber(0);

                        linenumber = -1;

                        String adresseToString = Integer.toString(adresse);

                        Stagiaire stagiaire= new Stagiaire(mot_nom, mot_prenom, mot_departement, mot_annee, mot_promo, adresseToString, gauche, droite);
                        arbreTest.add(stagiaire); // Ajout du stagiaire dans l'arbre


                        compteurStagiaires += 1;
                        adresse += STAGIAIRELENGTH;




                        // Ecriture dans le fichier binaire
                        /*raf.writeChars(mot_nom);
                        raf.writeChars(mot_prenom);
                        raf.writeChars(mot_departement);
                        raf.writeChars(mot_annee);
                        raf.writeChars(mot_promo);
                        raf.writeInt(adresse);*/

                        //stagiaire.afficherStagiaire();

                        break;
                    default:
                        break;
                }
                // Incrémentation du compteur de lignes
                linenumber += 1;

            }
            //Tri alphabétique
            Node root = arbreTest.getRoot();
            //System.out.println(root.data);
//            arbreTest.traverseInOrder(root);
            //listeStagiaires(compteurStagiaires, raf); //lecture du fichier bin et affichage de la liste de stagiaires

//            raf.seek(0);
//            System.out.println("\nLe pointeur est à l'endroit " + raf.getFilePointer());

            /*String motRecherche = "";
            for(int i=0 ; i<20 ; i++){
                motRecherche += raf.readChar();
            }
            System.out.println(motRecherche);*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // METHODES

    public static String completer(String mot, int taille) {

        int nbEspace = taille - mot.length();
        for (int i = 0; i < nbEspace; i++) {
            mot += " ";
        }
        return mot;
    }

    public static String lectureStagiaire(int longueurChaine, RandomAccessFile raf) {

        String chaine = "";
        try {
            for (int i = 0; i < longueurChaine; i++) {
                chaine += raf.readChar();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return chaine;
    }

    public static void listeStagiaires(int compteurStagiaire, RandomAccessFile raf) {

        try {
            raf.seek(0);
            int taille1 = PROMO + ANNEE + NOM + PRENOM + DEPARTEMENT;

            for (int j = 0; j <= compteurStagiaire; j++) {
                String chaine = "";
                String chaine1 = lectureStagiaire(taille1, raf);
                chaine = chaine + chaine1 + "	";
                System.out.println(chaine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
