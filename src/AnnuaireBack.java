import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import static java.lang.Integer.parseInt;

public class AnnuaireBack {

    public static final int PROMO = 15;        //nombre de caractères max alloués
    public static final int ANNEE = 10;
    public static final int PRENOM = 20;
    public static final int NOM = 20;
    public static final int DEPARTEMENT = 5;
    public static final int ADRESSE = 10; //position du noeud
    public static final int LEFTCHILD = 10; //pointeur noeud enfant gauche
    public static final int RIGHTCHILD = 10; //pointeur noeud enfant droit

    //Allocation espace total par stagiaire (incluant informations et pointeurs)
    public static final int STAGIAIRELENGTH = (PROMO + ANNEE + PRENOM + NOM + DEPARTEMENT
            + ADRESSE+LEFTCHILD+RIGHTCHILD) * 2 ;
    private static RandomAccessFile raf;
    private static String txtFile ="";
    public static Tree arbre; // Déclaration ici pour utilisation dans plusieurs méthodes

    public AnnuaireBack(String path){
        this.txtFile = path;
        this.raf = null;
    }

    // ACCESSEURS
    public static RandomAccessFile getRaf() {
        return raf;
    }
    public static void setRaf(RandomAccessFile raf) {
        AnnuaireBack.raf = raf;
    }

    public static String getTxtFile() {
        return txtFile;
    }
    public static void setTxtFile(String txtFile) {
        AnnuaireBack.txtFile = txtFile;

    }

    // METHODES
    public void creerAnnuaire() {

        try{

            this.raf = new RandomAccessFile("listeStagiaires.bin", "rw");
            txtFileToBinFile(this.txtFile, this.raf);

            //System.out.println("\n****Lecture du fichier binaire:");
            //listeStagiaires(raf); //lecture du fichier bin et affichage de la liste de stagiaires


//            extractionDonneesStagiaire(raf, 0);
            rechercherStagiaireBin(raf, "2008", "annee");

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
        arbre = new Tree();

        try {
            //Lecture du fichier text
            FileReader fichierOriginal = new FileReader(PathTxtFile);
            BufferedReader bf = new BufferedReader(fichierOriginal);

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


            //Ecriture des données dans le fichier binaire

            for (int j = 0; j <= compteurStagiaires; j++) {
                int key=STAGIAIRELENGTH * j;
                raf.seek(key);
                String keyString = completer(Integer.toString(key), ADRESSE);
                arbre.searchInTreeWriteInBin(arbre.root, keyString,raf);
            }

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
            int taille1 = NOM + PRENOM + DEPARTEMENT + ANNEE + PROMO + ADRESSE + LEFTCHILD+ RIGHTCHILD  ;

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

    public static void updateInBinary(Stagiaire stagiaire) throws IOException{

        String adresse = stagiaire.get_adresse().replaceAll("\\s+", "");
        System.out.println(adresse);
        int position = Integer.valueOf(adresse);
        System.out.println(position);
        try  {
            RandomAccessFile raf = new RandomAccessFile("listeStagiaires.bin", "rw");
            raf.seek(position);
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
            System.out.println("Données modifiées");
        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture des données");
            e.printStackTrace();
        }
    }


    public static Stagiaire extractionDonneesStagiaire(RandomAccessFile raf, int position) {
        System.out.println("Extraction données d'un stagiaire démarrée");

        String nomLu = "", prenomLu = "", departementLu = "", anneeLu = "", promoLu = "", adresseLu = "", gaucheLu = "", droiteLu = "";
        String nom = "", prenom = "", departement = "", annee = "", promo = "", adresse = "", gauche = "", droite = "";

        int pointeur = position;

        try {
            // Extraction du nom
            raf.seek(pointeur); // Pointeur placé au début d'un stagiaire
            for(int i=0; i<NOM; i++){
                nomLu += raf.readChar();
            }
            nom = completer(nomLu, NOM);
            pointeur += NOM*2;

            // Extraction du prénom
            raf.seek(pointeur); // Pointeur déplacé sur premier caractère du prénom
            for(int i=0; i<PRENOM; i++){
                prenomLu += raf.readChar();
            }
            prenom = completer(prenomLu, PRENOM);
            pointeur += PRENOM*2;

            // Extraction du département
            raf.seek(pointeur); // Pointeur déplacé sur département
            for(int i=0; i<DEPARTEMENT; i++){
                departementLu += raf.readChar();
            }
            departement = completer(departementLu, DEPARTEMENT);
            pointeur += DEPARTEMENT*2;

            // Extraction de l'année
            raf.seek(pointeur); // Pointeur déplacé sur année
            for(int i=0; i<ANNEE; i++){
                anneeLu += raf.readChar();
            }
            annee = completer(anneeLu, ANNEE);
            pointeur += ANNEE*2;

            // Extraction de la promo
            raf.seek(pointeur); // Pointeur déplacé sur promo
            for(int i=0; i<PROMO; i++){
                promoLu += raf.readChar();
            }
            promo = completer(promoLu, PROMO);
            pointeur += PROMO*2;

            // Extraction de l'adresse
            raf.seek(pointeur); // Pointeur déplacé sur adresse
            for(int i=0; i<ADRESSE; i++){
                adresseLu += raf.readChar();
            }
            adresse = completer(adresseLu, PROMO);
            pointeur += ADRESSE*2;

            // Extraction de gauche
            raf.seek(pointeur); // Pointeur déplacé sur gauche
            for(int i=0; i<LEFTCHILD; i++){
                gaucheLu += raf.readChar();
            }
            gauche = completer(gaucheLu, PROMO);
            pointeur += LEFTCHILD*2;

            // Extraction de droite
            raf.seek(pointeur); // Pointeur déplacé sur droite
            for(int i=0; i<RIGHTCHILD; i++){
                droiteLu += raf.readChar();
            }
            droite = completer(droiteLu, PROMO);



        } catch (Exception e){
            System.out.println("IOFEXCEPTION");
        }
        return new Stagiaire(nom, prenom, departement, annee, promo, adresse, gauche, droite);
    }

    public static ObservableList<Stagiaire> rechercherStagiaireBin(RandomAccessFile raf, String motSearched, String nomDonnee) throws IOException {
        // COMPARATOR POUR TRI PAR ORDRE ALPHABETIQUE
        Comparator<Stagiaire> comparator = Comparator.comparing(Stagiaire::get_nom);

        System.out.println("*** Début recherche ***");
        System.out.println("\n--- le mot recherché est : " + motSearched + " ---");

        int dataSpace = 0;
        int position = 0;
        int positionExtraction = 0;
        List<Stagiaire> stagiaires = new Vector<Stagiaire>(); // pour stocker les stagiaires correspondants à la recherche

        // Détermine la valeur de dataSPace en fonction du type de donnée recherchée (nom, prénom...)
        switch (nomDonnee){
            case "nom":
                dataSpace = NOM;
                position = 0;
                positionExtraction = 0;
                break;
            case "prenom":
                dataSpace = PRENOM;
                position = NOM*2;
                positionExtraction = NOM*2;
                break;
            case "departement":
                dataSpace = DEPARTEMENT;
                position = (NOM+PRENOM)*2;
                positionExtraction = (NOM+PRENOM)*2;
                break;
            case "annee":
                dataSpace = ANNEE;
                position = (NOM+PRENOM+DEPARTEMENT)*2;
                positionExtraction = (NOM+PRENOM+DEPARTEMENT)*2;
                break;
            case "promo":
                dataSpace = PROMO;
                position = (NOM+PRENOM+DEPARTEMENT+ANNEE)*2;
                positionExtraction = (NOM+PRENOM+DEPARTEMENT+ANNEE)*2;
                break;
        }

        System.out.println("=> dataSpace = " + nomDonnee + " = " + dataSpace);
        System.out.println("=> position = " + position);

        // 1. Lecture du fichier bin de donnée en même type de donnée (adresse à préciser)

//        String motLu = "";

        // On ajoute les espaces au "mot recherché" pour le formater comme les mots qui seront lus
        String motSearchedComplete = completer(motSearched, dataSpace);

        // Recherche dans le fichier .BIN
        while(position < raf.length()){
            System.out.println("\n--- Début boucle ---");

            String mot = "";

            System.out.println("position = " + position);
            raf.seek(position);
            System.out.println("Le pointeur est à l'endroit " + raf.getFilePointer());

            // Lecture du mot
            for(int i=0 ; i< dataSpace ; i++){
                mot += raf.readChar();
            }
            System.out.println("Le mot lu est : " + mot);

            // 2.1. Comparaison du mot récupéré avec le mot recherché formatté
            if(mot.equals(motSearchedComplete)){
                // 2.2. Si mot correspond, alors on instancie un Stagiaire
                System.out.println("=> Mot cherché =  mot parcouru");
                System.out.println("=> Position pour extraction = " + (position-positionExtraction));

                Stagiaire stagiaire = extractionDonneesStagiaire(raf, position-positionExtraction);

                // 2.3. Et on le stock dans le liste "stagiaires"
                stagiaires.add(stagiaire);

                System.out.println("~ Données stagiaire extrait ~");
                System.out.println(stagiaire.toString());

            } else {
                System.out.println("=> Mot cherché !=  mot parcouru");
            }

            // 2.3. Si nom correspond pas, alors on passe au stagiaire suivant
            position += STAGIAIRELENGTH;
            System.out.println("future position = " + position);
            System.out.println("--- Fin boucle ---");
        }

        System.out.println("*** Fin recherche ***");
        // 3. Affichage de la liste des stagiaires qui correspondent via une ObservableList "list"
        ObservableList<Stagiaire> list = FXCollections.observableArrayList(stagiaires);
        list.sort(comparator); // Utilisation du comparator pour trier par ordre alpha
        return list;
    }

    public static ObservableList<Stagiaire> getStagiairesList(RandomAccessFile raf) throws IOException {
        // COMPARATOR POUR TRI PAR ORDRE ALPHABETIQUE
//        Comparator<Stagiaire> comparator = Comparator.comparing(Stagiaire::get_nom);

      System.out.println("getStagiairesList démarrée");
        System.out.println(raf.length());
        List<Stagiaire> stagiaires = new Vector<Stagiaire>();

        int position = 0;

        while(position < raf.length()){
            System.out.println("***Nouveau tour***");
            System.out.println("position = " + position);
            raf.seek(position);
            Stagiaire stagiaire = extractionDonneesStagiaire(raf, position);
            System.out.println(stagiaire.toString());
            stagiaires.add(stagiaire);
            position += STAGIAIRELENGTH;
//            System.out.println(" future position = " + position);
        }

        ObservableList<Stagiaire> list = FXCollections.observableArrayList(stagiaires);
//        list.sort(comparator); // Utilisation du comparator pour trier par ordre alpha
        return list;
    }


    public static Stagiaire addStagiaire(String nom, String prenom, String departement, String promo, String annee){

        try {
            String adresse = String.valueOf(raf.length());
            //System.out.println(adresse);
            String nomFormatted = completer(nom, NOM);
            String prenomFormatted = completer(prenom, PRENOM);
            String departementFormatted = completer(departement, DEPARTEMENT);
            String promoFormatted = completer(promo, PROMO);
            String anneeFormatted = completer(annee, ANNEE);
            String gauche = "";
            String droite = "";

            Stagiaire stagiaire = new Stagiaire(nomFormatted, prenomFormatted, departementFormatted, promoFormatted, anneeFormatted, adresse, gauche, droite);
            Node noeud = new Node(stagiaire);
            arbre.addNode(stagiaire);
            arbre.searchInTreeWriteInBin(noeud, adresse, raf);
            return stagiaire;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //RETIRER DANS L'ARBRE SI NECESSAIRE
        //arbre.addNode(stagiaire);
    }

    public static void removeStagiaireBin(Stagiaire stagiaire) throws IOException {


        System.out.println("*** removeStagiaireBin commencee ***");
        String adresse = stagiaire.get_adresse().replaceAll("\\s+", ""); // supprime les espaces sinon NumberFormatException
        int pointeur = parseInt(adresse);
        String nomLu = "";

        // On place le pointeur à l'adresse du stagiaire dans le .bin et on remplace ses données par des *
        System.out.println("=> le pointeur est à " + raf.getFilePointer());

        raf.seek(pointeur);
        for(int i=0; i<NOM; i++){
            nomLu += raf.readChar();
        }
        System.out.println("=> Vérification nom à l'adresse " + raf.getFilePointer() + " => " + nomLu);

        raf.seek(pointeur);
        System.out.println("=> Réécriture ");
        for(int i = 0 ; i < NOM ; i++){
            raf.writeChars("*");
        }

        raf.seek(pointeur);
        System.out.println("=> le pointeur est à " + raf.getFilePointer());
        nomLu = "";
        for(int i=0; i<NOM; i++){
            nomLu += raf.readChar();
        }
        System.out.println("Vérification de ce qui est écrit maintenant à l'adresse " + raf.getFilePointer() + " => " + nomLu);

        // On créé un raf temporaire pour y réécrire le fichier bin sans les "*"
        RandomAccessFile rafTmp = new RandomAccessFile("listeStagiaires.bin.tmp", "rw");
        System.out.println("Création de rafTmp" + rafTmp);

        raf.seek(0);
        for(int i=0 ; i < raf.length() ; i++){
            char charValue = raf.readChar();
            System.out.println("char charValue = " + charValue);
            String charVal = String.valueOf(charValue);
            System.out.println("String charVal = " + charVal);
            if(charVal.compareTo("*") == 0){
                System.out.println("ON N'ECRIT PAS");
            } else {
                rafTmp.writeChars(charVal);
                System.out.println("Ecriture du char dans le fichier Tmp");
            }
        }
        System.out.println("Début suppression et renommage");




        // Suppression du fichier .bin
        File rafFile = new File("listeStagiaires.bin");
        rafFile.delete();
        System.out.println("raf initial supprimé");

        // Renommage du fichier .bin.tmp
        File rafFileTmp = new File("listeStagiaires.bin.tmp");
        rafFileTmp.renameTo(rafFile);
        System.out.println("raf tmp renommé");

        System.out.println("*** fin de la méthode ***");

    }

}
