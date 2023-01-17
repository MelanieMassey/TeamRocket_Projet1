import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;



public class Interface extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création des menus
        Menu fileMenu1 = new Menu("Connexion");
        Menu fileMenu2 = new Menu("Annuaire");
        Menu fileMenu3 = new Menu("Aide");

        // Création des MenuItems du Menu Annuaire
        MenuItem annuaire1 = new MenuItem("Exporter en PDF");

        MenuItem ouvrirfichier = new MenuItem(("Ouvrir annuaire"));

        // Ajout des MenuItems au Menu Annuaire
        fileMenu2.getItems().addAll(ouvrirfichier, annuaire1);

        // Création des MenuItems du Menu Aide
        MenuItem aide1 = new MenuItem("Document");
        MenuItem aide2 = new MenuItem("Montrer les icônes");

        // Ajout des MenuItems au Menu Aide
        fileMenu3.getItems().add(aide1);
        fileMenu3.getItems().add(aide2);

        // Création de l'event aide1
        aide1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // Récupération et mise en forme du texte provenant du fichier Document.txt
                    FileReader input = new FileReader("src\\Documentation.txt");
                    Scanner sc = new Scanner(input);
                    StringBuffer sb = new StringBuffer();
                    while (sc.hasNext()) {
                        sb.append(sc.nextLine() + "\n");
                    }
                    String str = sb.toString();

                    // Création et formatage du texte
                    Text text = new Text();
                    text.setText(str);
                    text.setWrappingWidth(580);
                    text.setX(10.0);
                    text.setY(25.0);

                    // Préparation du second stage pour le texte "Aide"
                    Stage secondstage = new Stage();
                    Group grille = new Group(text);
                    Scene scene = new Scene(grille, 595, 300);
                    grille.getStyleClass().add("grille");
                    scene.getStylesheets().add(getClass().getResource("SecondStage.css").toExternalForm());
                    scene.setFill(Color.MINTCREAM);
                    secondstage.setTitle("Aide");
                    secondstage.setScene(scene);
                    secondstage.show();
                }
                catch (IOException ex){
                    System.out.println("Erreur à l'affichage du texte");
                }
            }
        });

        // Préparation de la barre Menu pour affichage
        MenuBar leftBar = new MenuBar();
        leftBar.getMenus().addAll(fileMenu1, fileMenu2);
        MenuBar rightBar = new MenuBar();
        rightBar.getMenus().addAll(fileMenu3);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.SOMETIMES);
        HBox menubars = new HBox(leftBar, spacer, rightBar);
        menubars.getStyleClass().add("menu-bar");




        TableView<Stagiaire> table = new TableView<Stagiaire>();
        table.setEditable(true);
        Label label = new Label("Liste des stagiaires");
        label.setFont(new Font("Arial", 20));

        //Création des cinq colonnes
        TableColumn<Stagiaire, String> promoCol = new TableColumn<Stagiaire, String>("Promo");
        promoCol.setMinWidth(100);
        //specifier un "cell factory" pour cette colonne.
        promoCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("_promo"));

        TableColumn<Stagiaire, String> anneeCol =
                new TableColumn<Stagiaire, String>("Année");
        anneeCol.setMinWidth(100);
        //specifier un "cell factory" pour cette colonne.
        anneeCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("_annee"));

        TableColumn<Stagiaire, String> nomCol =
                new TableColumn<Stagiaire, String>("Nom");
        nomCol.setMinWidth(100);
        //specifier un "cell factory" pour cette colonne.
        nomCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("_nom"));

        TableColumn<Stagiaire, String> prenomCol =
                new TableColumn<Stagiaire, String>("Prénom");
        prenomCol.setMinWidth(100);
        //specifier un "cell factory" pour cette colonne.
        prenomCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("_prenom"));

        TableColumn<Stagiaire, String> deptCol = new TableColumn<Stagiaire, String>("Département");
        nomCol.setMinWidth(100);
        //specifier un "cell factory" pour cette colonne.
        deptCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("_departement"));

        //On ajoute les cinq colonnes à la table
        table.getColumns().addAll(promoCol, anneeCol, nomCol, prenomCol, deptCol);

        // Préparation du premier stage et affichage
        BorderPane root = new BorderPane();
        root.setTop(menubars);
        root.setCenter(table);


        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add("Front.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        ouvrirfichier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //On rempli la table avec la liste observable

                RandomAccessFile raf;
                String txtFile = "src/stagiaireTest.txt";

                AnnuaireBack newAnnuaire = new AnnuaireBack(txtFile);
                newAnnuaire.creerAnnuaire();

                try {
                    // On récupère le fichier .bin écrit en instanciant newAnnuaire
                    raf = newAnnuaire.getRaf();
                    // On parcourt le .bin pour extraire les Stagiaires avec méthode .getStagiairesList(raf)
                    ObservableList<Stagiaire> data = AnnuaireBack.getStagiairesList(raf);
                    // On envoie les données dans le TableView pour affichage sur l'application/interface
                    table.setItems(data);
                } catch (IOException e) {
                    System.out.println("error chargement fichier bin");
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public static void main(String[] args) {
        launch(args);

    }
}
