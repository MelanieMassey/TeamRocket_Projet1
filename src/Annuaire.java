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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

import static javafx.application.Application.launch;

public class Annuaire extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Création des menus
        Menu fileMenu1 = new Menu("Connexion");
        Menu fileMenu2 = new Menu("Annuaire");
        Menu fileMenu3 = new Menu("Aide");

        // Création des MenuItems au Menu Annuaire
        MenuItem annuaire1 = new MenuItem("Exporter en PDF");

        MenuItem ouvrirfichier = new MenuItem(("ouvrir annuaire"));

        // Ajout des MenuItems au Menu Annuaire
        fileMenu2.getItems().addAll(ouvrirfichier, annuaire1);

        // Création des MenuItems au Menu Aide
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
                    Stage secondstage = new Stage();
                    InputStream inputStream = new FileInputStream("src\\Bla.txt");
                    Scanner sc = new Scanner(inputStream);
                    StringBuffer sb = new StringBuffer();
                    while (sc.hasNext()) {
                        sb.append(" " + sc.nextLine() + " ");
                    }
                    String str = sb.toString();
                    //Creating a text object
                    Text text = new Text();
                    //Setting the properties of text
                    text.setText(str);
                    text.setWrappingWidth(580);
                    text.setX(10.0);
                    text.setY(25.0);
                    //Setting the stage
                    Group root = new Group(text);
                    Scene scene = new Scene(root, 595, 300);
                    scene.getStylesheets().add(getClass().getResource("Front.css").toExternalForm());
                    secondstage.setTitle("Displaying Text");
                    secondstage.setScene(scene);
                    secondstage.show();
                }
                catch (FileNotFoundException ex){
                    System.out.println("Hello error");
                }
            }
        });

        MenuBar leftBar = new MenuBar();
        leftBar.getMenus().addAll(fileMenu1, fileMenu2);
        MenuBar rightBar = new MenuBar();
        rightBar.getMenus().addAll(fileMenu3);
        Region spacer = new Region();

        HBox.setHgrow(spacer, Priority.SOMETIMES);
        HBox menubars = new HBox(leftBar, spacer, rightBar);
        menubars.getStyleClass().add("menu-bar");
        BorderPane root = new BorderPane();
        root.setTop(menubars);


        TableView<Stagiaire> table = new TableView<Stagiaire>();
        table.setEditable(true);
        Label label = new Label("Liste des stagiaires");
        label.setFont(new Font("Arial", 20));

        //Création des cinq colonnes
        TableColumn<Stagiaire, Integer> promoCol =
                new TableColumn<Stagiaire, Integer>("Promo");
        promoCol.setMinWidth(100);
        //specifier un "cell factory" pour cette colonne.
        promoCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, Integer>("promo"));

        TableColumn<Stagiaire, String> anneeCol =
                new TableColumn<Stagiaire, String>("Année");
        anneeCol.setMinWidth(100);
        //specifier un "cell factory" pour cette colonne.
        anneeCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("annee"));

        TableColumn<Stagiaire, String> nomCol =
                new TableColumn<Stagiaire, String>("Nom");
        nomCol.setMinWidth(100);
        //specifier un "cell factory" pour cette colonne.
        nomCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("nom"));

        TableColumn<Stagiaire, String> prenomCol =
                new TableColumn<Stagiaire, String>("Prénom");
        prenomCol.setMinWidth(100);
        //specifier un "cell factory" pour cette colonne.
        prenomCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("prenom"));

        TableColumn<Stagiaire, String> deptCol = new TableColumn<Stagiaire, String>("Département");
        nomCol.setMinWidth(100);
        //specifier un "cell factory" pour cette colonne.
        deptCol.setCellValueFactory(
                new PropertyValueFactory<Stagiaire, String>("dept"));

        //On ajoute les cinq colonnes à la table
        table.getColumns().addAll(promoCol, anneeCol, nomCol, prenomCol, deptCol);




        Scene scene = new Scene(root, 600, 600);
        scene.getStylesheets().add(getClass().getResource("Front.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

        ouvrirfichier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //On rempli la table avec la liste observable
                RandomAccessFile raf = null;
                try {
                    raf = new RandomAccessFile("src/listeStagiaires.bin", "rw");
                    ObservableList<Stagiaire> data = Stagiaire.getStagiaireList(raf);
                    table.setItems(data);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
