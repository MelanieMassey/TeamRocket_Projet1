import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;
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
        //Label tableview
        Label labelTable = new Label("Liste des stagiaires");
        labelTable.setFont(new Font("Montserrat", 15));

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
        table.getColumns().addAll(nomCol, prenomCol, deptCol, promoCol, anneeCol);

        //CREATION DE LA ZONE EDITABLE
        TextField promotxt = new TextField();
        promotxt.setPromptText("Promo");
        promotxt.setPrefWidth(100);

        TextField anneetxt = new TextField();
        anneetxt.setPromptText("Année");
        anneetxt.setPrefWidth(100);

        TextField nomtxt = new TextField();
        nomtxt.setPromptText("Nom");
        nomtxt.setPrefWidth(200);

        TextField prenomtxt = new TextField();
        prenomtxt.setPromptText("Prénom");
        prenomtxt.setPrefWidth(200);

        TextField departementtxt = new TextField();
        departementtxt.setPromptText("Département");
        departementtxt.setPrefWidth(100);

        Button btnAjouter = new Button("Ajouter");
        Button btnSupprimer = new Button("Supprimer");

        //ACTION: AJOUTER UN STAGIAIRE
        btnAjouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                prenomtxt.clear();
                nomtxt.clear();
            }
        });

        //ACTION: SUPPRIMER UN STAGIAIRE
        btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                prenomtxt.clear();
                nomtxt.clear();
            }
        });

        //Création Hbox pour la zone éditable
        HBox zoneEditable = new HBox();
        zoneEditable.getChildren().addAll(promotxt, anneetxt, nomtxt,prenomtxt, departementtxt, btnAjouter,btnSupprimer);
        zoneEditable.setSpacing(10);
        zoneEditable.setSpacing(10.0);

        // Création du VBox Vboxtable (tableview+label)
        VBox vboxTable = new VBox();
        vboxTable.setSpacing(5);
        vboxTable.setPadding(new Insets(10, 10, 10, 10));
        vboxTable.getChildren().addAll(labelTable, table);

        /*
        // Creation du VBox totale (zoneEditable+vboxTable)
        VBox vboxtotale = new VBox();
        vboxtotale.getChildren().addAll(vboxTable, zoneEditable);
        // Set Spacing to 20 pixels
        vboxtotale.setSpacing(20);*/

        // Préparation du premier stage et affichage
        AnchorPane root = new AnchorPane();
        // Ajouter le HBox et le TextArea à la VBox
       // root.getChildren().addAll(vboxtable, vboxtotale);
        // Set Spacing to 10 pixels
        //root.setPadding(new Insets(10, 10, 10, 10));

        //PLACEMENT
        //Menubars
        root.setTopAnchor(menubars,0.0);
        root.setLeftAnchor(menubars,0.0);
        root.setRightAnchor(menubars,0.0);

        //vboxTable
        root.setTopAnchor(vboxTable,50.0);
        root.setLeftAnchor(vboxTable,100.0);
        root.setRightAnchor(vboxTable,100.0);

        //zoneEditable
        root.setBottomAnchor(zoneEditable,40.0);
        root.setLeftAnchor(zoneEditable,10.0);
        root.setRightAnchor(zoneEditable,10.0);

        root.getChildren().addAll(menubars, vboxTable,zoneEditable);

        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add("Front.css");
        primaryStage.setScene(scene);
        primaryStage.show();


        //AFFICHER TABLEVIEW
        ouvrirfichier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                RandomAccessFile raf;
                String txtFile = "src/stagiaireTest.txt";

                //initialisation du raf
                AnnuaireBack newAnnuaire = new AnnuaireBack(txtFile);
                newAnnuaire.creerAnnuaire();

                try {
                    //on créé le fichier binaire à partir du fichier txt
                    raf = newAnnuaire.getRaf();
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
