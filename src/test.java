import java.lang.reflect.Array;

public class test {

    public static void main(String[] args) {
        String sentence="e      ";
        String[] tokens = sentence.split("  ");   //split la ligne en utilisant triple espace comme séparateur
        String token ="";
        token+= tokens[0];
        System.out.println(token);
        String sentence2="e";
        String[] tokens2 = sentence2.split("  ");   //split la ligne en utilisant triple espace comme séparateur
        String token2 ="";
        token2+= tokens2[0];
        System.out.println(token2);
        System.out.println(token.equals(token2));
        //int compare= token2.compareTo(token);
       // System.out.println(compare);
        String[][] myArray = new String[4][];










    }
}
