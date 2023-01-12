import java.io.*;


public class Main {

    public static final int PROMO = 15;        //nombre de caractères max alloués
    public static final int ANNEE = 10;
    public static final int PRENOM = 20;
    public static final int NOM = 20;
    public static final int DEPARTEMENT = 2;

    //Allocation espace total saisie infos stagiaire
    public static final int STAGIAIRELENGTH = ((PROMO + ANNEE + PRENOM + NOM + DEPARTEMENT) * 2);


    public static void main(String[] args) {

        String ligne = "";
        String mot;
        int compteurStagiaires = 0;                     //compteur stagiaires
        int linenumber = 0;                               //compteur ligne


        RandomAccessFile raf;
        try {
            raf = new RandomAccessFile("listeStagiaires.bin", "rw");        //création fichier bin vide

            FileReader fichierOriginal = new FileReader("src/stagiaires.txt");
            BufferedReader bf = new BufferedReader(fichierOriginal);
            //LineNumberReader lnr = new LineNumberReader(bf);
            //int lineNumber = lnr.getLineNumber();

            /* Lecture du fichier txt ligne par ligne tant que la ligne n'est pas vide
               Récupération des informations sur chaque ligne Ecriture des données dans le fichier bin:
                    Ligne 0: Promo
                    Ligne 1: Année
                    Ligne 2: Nom
                    Ligne 3: Prénom
                    Ligne 4: Département
                    Ligne 5: réinitialisation du compteur de ligne pour recommencer les informations pour le stagiaire suivant
                             + incrémentation du compteur stagiaire

             */

            while ((ligne = bf.readLine()) != null && (linenumber <= 5)) {

                mot = "";
                String[] tokens = ligne.split("  ");   //split la ligne en utilisant triple espace comme séparateur
                mot += tokens[0];                            //récupération du premier token (mot)
                switch (linenumber) {
                    case 0:
                        mot = completer(mot, PROMO);
                        raf.writeChars(mot);
                        break;
                    case 1:
                        mot = completer(mot, ANNEE);
                        raf.writeChars(mot);
                        break;
                    case 2:
                        mot = completer(mot, NOM);
                        raf.writeChars(mot);
                        break;
                    case 3:
                        mot = completer(mot, PRENOM);
                        raf.writeChars(mot);
                        break;
                    case 4:
                        mot = completer(mot, DEPARTEMENT);
                        raf.writeChars(mot);
                        break;
                    case 5:
                        // lnr.setLineNumber(0);
                        linenumber = -1;
                        compteurStagiaires += 1;
                        break;
                    default:
                        break;
                }
                linenumber += 1;                                   //incrémentation du compteur de ligne

            }

            listeStagiaires(compteurStagiaires, raf);             //lecture du fichier bin et affichage de la liste de stagiaires

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
