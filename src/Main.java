import java.io.*;
import java.text.Normalizer;


public class Main {

    public static final int PROMO = 15;        //nombre de caractères max alloués
    public static final int ANNEE = 10;
    public static final int PRENOM = 20;
    public static final int NOM = 20;
    public static final int DEPARTEMENT = 5;
    public static final int ADRESSE = 10;           //position du noeud
    public static final int LEFTCHILD = 10;         //pointeur noeud enfant gauche
    public static final int RIGHTCHILD = 10;        //pointeur noeud enfant droit

    //Allocation espace total par stagiaire (incluant informations et pointeurs)
    public static final int STAGIAIRELENGTH = (PROMO + ANNEE + PRENOM + NOM + DEPARTEMENT
            + ADRESSE+LEFTCHILD+RIGHTCHILD) * 2 ;

    public static void main(String[] args) {

        try{
            // Création d'un fichier bin vide
            RandomAccessFile raf;

            raf = new RandomAccessFile("listeStagiaires.bin", "rw");


            txtFileToBinFile("src/stagiaireTest.txt", raf);


            System.out.println("\n****Ajouter de nouvelles données:");
            appendToBinary(new Stagiaire(completer("Hernandez",NOM),
                    completer("Céline",PRENOM),
                    completer("59", DEPARTEMENT),
                    completer("2022", ANNEE),
                    completer("EQELLES",PROMO),
                    completer("1000", ADRESSE),
                    completer("50", LEFTCHILD),
                    completer("600", RIGHTCHILD)), raf);

            /*System.out.println("\n****Lecture du fichier binaire:");
            listeStagiaires(raf); //lecture du fichier bin et affichage de la liste de stagiaires*/

//            extractionDonneesStagiaire(raf, 0);
            rechercherStagiaireBin(raf, "fiore", NOM);

            //raf.close();
//            raf.seek(0);
//            System.out.println("\nLe pointeur est à l'endroit " + raf.getFilePointer());

            /*String motRecherche = "";
            for(int i=0 ; i<20 ; i++){
                motRecherche += raf.readChar();
            }
            System.out.println(motRecherche);*/

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    // METHODES

    public static void txtFileToBinFile(String PathTxtFile, RandomAccessFile raf) {

        //Déclaration variables (et initialisation)
        String ligne = "";
        String mot;
        String mot_promo="", mot_prenom="", mot_departement="", mot_nom="", mot_annee="", mot_adresse="";
        int adresse = 0;
        String gauche = "", droite = "";


        //Initialisation des compteurs pour les lignes du fichier et pour les stagiaires
        int linenumber = 0;
        int compteurStagiaires = 0;


        // Creation d'un nouvel arbre
        Tree arbre = new Tree();

        try {


            //Lecture du fichier text
            FileReader fichierOriginal = new FileReader(PathTxtFile);
            BufferedReader bf = new BufferedReader(fichierOriginal);


            /* Lecture du fichier txt ligne par ligne tant que la ligne n'est pas vide
               Récupération des informations sur chaque ligne Ecriture des données dans le fichier bin:
                    Ligne 0: Promo
                    Ligne 1: Année
                    Ligne 2: Nom
                    Ligne 3: Prénom
                    Ligne 4: Département
                    Ligne 5: réinitialisation du compteur de ligne pour collecter les informations pour le stagiaire suivant
                             + Création d'un objet de la classe Stagiaire pour stocker les infos relatives à chaque stagiaire
                             + Création d'un noeud (avec les données d'un stagiaire)
                             + incrémentation du compteur stagiaire

             */
            while ((ligne = bf.readLine()) != null && (linenumber <= 5)) {

                mot = "";
                String[] tokens = stripAccents(ligne.toLowerCase()).split("  ");   //met tout en minuscule et split la ligne en utilisant triple espace comme séparateur
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

                        // System.out.println("Adresse in = " + adresse);
                        String adresseToString = Integer.toString(adresse);
                        mot_adresse =  completer(adresseToString, ADRESSE);
                        gauche=completer(gauche, LEFTCHILD);
                        droite=completer(droite, RIGHTCHILD);

                        //Creation d'un objet stagiaire
                        Stagiaire stagiaire= new Stagiaire(mot_nom, mot_prenom, mot_departement, mot_annee, mot_promo, mot_adresse, gauche, droite);
                        //Ajout de l'objet dans l'arbre
                        arbre.addNode(stagiaire);

                        //Incrémentation du compteur stagiaire
                        compteurStagiaires += 1;
                        //Réinitialisation du compteur de ligne
                        linenumber = -1;
                        //Positionnement du pointeur pour l'écriture des données
                        adresse += STAGIAIRELENGTH;

                        break;
                    default:
                        break;
                }
                // Incrémentation du compteur de lignes
                linenumber += 1;

            }


            System.out.println("\n****Lecture arbre binaire créé (avant écriture dans le fichier bin):");
            arbre.traverseInOrder(arbre.root);

            //ecriture des données dans le fichier binaire
            arbre.traverseInOrderAndWrite(arbre.root, raf);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String stripAccents(String s)
    {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }

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

    public static void listeStagiaires(RandomAccessFile raf) {

        try {
            raf.seek(0);
            int compteurStagiaire = (int) raf.length()/STAGIAIRELENGTH ;
            int taille1 = NOM + PRENOM + DEPARTEMENT+ANNEE +PROMO + ADRESSE +LEFTCHILD+ RIGHTCHILD  ;

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

    public static void appendToBinary(Stagiaire stagiaire, RandomAccessFile raf){
        try  {
            raf.seek(raf.length());
            raf.writeChars(stagiaire.get_nom());           //write UTF ???
            raf.writeChars(stagiaire.get_prenom());
            raf.writeChars(stagiaire.get_departement());
            raf.writeChars(stagiaire.get_annee());
            raf.writeChars(stagiaire.get_promo());
            raf.writeChars(stagiaire.get_adresse());
            raf.writeChars(stagiaire.get_gauche());
            raf.writeChars(stagiaire.get_gauche());
            raf.seek(0);
            //raf.close();
            System.out.println("Données sauvegardées");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture des données");
            e.printStackTrace();
        }
    }

    public static void extractionDonneesStagiaire(RandomAccessFile raf, int position) throws IOException {
        System.out.println("Extraction données d'un stagiaire démarrée");

        String nomLu = "", prenomLu = "", departementLu = "", anneeLu = "", promoLu = "", adresseLu = "", gaucheLu = "", droiteLu = "";
        String nom = "", prenom = "", departement = "", annee = "", promo = "", adresse = "", gauche = "", droite = "";

        // Extraction du nom
        raf.seek(position); // Pointeur placé au début d'un stagiaire
        for(int i=0; i<NOM; i++){
            nomLu += raf.readChar();

        }
        nom = completer(nomLu, NOM);
        System.out.println(nom);

        // Extraction du prénom
        raf.seek((position+NOM)*2); // Pointeur déplacé sur premier caractère du prénom
        for(int i=0; i<PRENOM; i++){
            prenomLu += raf.readChar();
        }
        prenom = completer(prenomLu, PRENOM);
        System.out.println(prenom);

        // Extraction du département
        raf.seek((position+NOM+PRENOM)*2); // Pointeur déplacé sur département
        for(int i=0; i<DEPARTEMENT; i++){
            departementLu += raf.readChar();
        }
        departement = completer(departementLu, DEPARTEMENT);
        System.out.println(departement);

        // Extraction de l'année
        raf.seek((position+NOM+PRENOM+DEPARTEMENT)*2); // Pointeur déplacé sur année
        for(int i=0; i<ANNEE; i++){
            anneeLu += raf.readChar();
        }
        annee = completer(anneeLu, ANNEE);
        System.out.println(annee);

        // Extraction de la promo
        raf.seek((position+NOM+PRENOM+DEPARTEMENT+ANNEE)*2); // Pointeur déplacé sur promo
        for(int i=0; i<PROMO; i++){
            promoLu += raf.readChar();
        }
        promo = completer(promoLu, PROMO);
        System.out.println(promo);

        // Extraction de l'adresse
        raf.seek((position+NOM+PRENOM+DEPARTEMENT+ANNEE+PROMO)*2); // Pointeur déplacé sur adresse
        for(int i=0; i<ADRESSE; i++){
            adresseLu += raf.readChar();
        }
        adresse = completer(adresseLu, PROMO);
        System.out.println(adresse);

        // Extraction de gauche
        raf.seek((position+NOM+PRENOM+DEPARTEMENT+ANNEE+PROMO+ADRESSE)*2); // Pointeur déplacé sur gauche
        for(int i=0; i<LEFTCHILD; i++){
            gaucheLu += raf.readChar();
        }
        gauche = completer(gaucheLu, PROMO);
        System.out.println(gauche);

        // Extraction de droite
        raf.seek((position+NOM+PRENOM+DEPARTEMENT+ANNEE+PROMO+ADRESSE+LEFTCHILD)*2); // Pointeur déplacé sur droite
        for(int i=0; i<RIGHTCHILD; i++){
            droiteLu += raf.readChar();
        }
        droite = completer(droiteLu, PROMO);
        System.out.println(droite);

    }

    public static void rechercherStagiaireBin(RandomAccessFile raf, String motSearched, int dataSpace) throws IOException {
        System.out.println("recherche stagiaire activee");
        System.out.println("le mot recherché est : " + motSearched);

        // On veut pouvoir chercher un ou des stagaires par : nom, prénom, promo, année ou département

        // Recherche par nom
        // 1. Lecture du fichier bin de nom en nom (adresse à préciser)

        int position = 0;
//        String motLu = "";

        String motSearchedComplete = completer(motSearched, dataSpace);


        while(position < raf.length()){
            String mot = "";

            System.out.println("\nposition = " + position);
            raf.seek(position);
            System.out.println("\nLe pointeur est à l'endroit " + raf.getFilePointer());

            position += STAGIAIRELENGTH;
            System.out.println("future position = " + position);

            // Lecture du mot
            for(int i=0 ; i< dataSpace ; i++){
                mot += raf.readChar();
                System.out.println("\nLe mot lu est : " + mot);

                // 2.1. Comparaison du nom récupéré avec le nom recherché formaté
                if(mot.equals(motSearchedComplete)){
                    // 2.2. Si nom correspond, alors on garde toutes les infos du stagiaire
                    System.out.println("\nNom cherché =  nom stagiaire parcouru");
                    String nom = "", prenom = "", departement = "", annee = "", promo = "", adresse = "", gauche = "", droite = "";
                    extractionDonneesStagiaire(raf,position);
                    Stagiaire newStagiaire = new Stagiaire(nom, prenom, departement, annee, promo, adresse, gauche, droite );
                    System.out.println(newStagiaire.toString());

                }

                // 2.3. Si nom correspond pas, alors on passe au stagiaire suivant

            }
        }

        // 3. Affichage de la liste des stagiaires qui correspondent


    }

}




