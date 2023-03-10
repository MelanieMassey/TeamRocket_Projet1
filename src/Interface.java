import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.security.auth.callback.Callback;
import javax.swing.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static javafx.beans.binding.Bindings.select;
import static javafx.scene.paint.Color.*;


public class Interface extends Application {

    // Déclaration variables
    ObservableList<Stagiaire> data; // utilisée pour créer et afficher formulaire et mise à jour de la liste

    @Override
    public void start(Stage primaryStage) {


        // Création des menus
        Menu fileMenu1 = new Menu("Connexion");
        Menu fileMenu2 = new Menu("Annuaire");
        Menu fileMenu3 = new Menu("Aide");

        // Création du menuItem pour la connexion
        MenuItem connexion = new MenuItem("Se connecter");
       MenuItem deconnexion = new MenuItem("Se déconnecter");
       deconnexion.setDisable(true);
       MenuItem modifId = new MenuItem("Modifier ses identifiants");
        Menu menuModifId = new Menu("Modifier ses paramètres de connnexion");
        menuModifId.setDisable(true);
        MenuItem changeId = new MenuItem("Identifiant");
        MenuItem changePswd = new MenuItem("Mot de passe");


        // Ajout des MenuItems au Menu Connexion
        fileMenu1.getItems().addAll(connexion, menuModifId, deconnexion);
        menuModifId.getItems().addAll(changeId,changePswd);

        // Création des MenuItems du Menu Annuaire
        MenuItem annuaire1 = new MenuItem("Exporter en PDF");
        MenuItem ouvrirAnnuaire = new MenuItem(("Ouvrir l'annuaire"));
        MenuItem ouvrirfichier = new MenuItem(("Importer un annuaire"));

        // Ajout des MenuItems au Menu Annuaire
        fileMenu2.getItems().addAll(ouvrirAnnuaire, ouvrirfichier, annuaire1);

        // Création des MenuItems du Menu Aide
        MenuItem aide1 = new CheckMenuItem("Document");
        MenuItem aide2 = new MenuItem("Montrer les icônes");


        // Ajout des MenuItems au Menu Aide
        fileMenu3.getItems().add(aide1);
        fileMenu3.getItems().add(aide2);

        // Préparation de la barre Menu pour affichage
        MenuBar leftBar = new MenuBar();
        leftBar.getMenus().addAll(fileMenu1, fileMenu2);
        MenuBar rightBar = new MenuBar();
        rightBar.getMenus().addAll(fileMenu3);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.SOMETIMES);
        HBox menubars = new HBox(leftBar, spacer, rightBar);
        menubars.getStyleClass().add("menu-bar");


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
                    scene.setFill(MINTCREAM);
                    secondstage.setTitle("Aide");
                    secondstage.setScene(scene);
                    secondstage.show();
                }
                catch (IOException ex){
                    System.out.println("Erreur à l'affichage du texte");
                }
            }
        });


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


        ///////MODIFICATION DES STAGIAIRES DANS LA TABLEVIEW



        //On ajoute les cinq colonnes à la table
        table.getColumns().addAll(nomCol, prenomCol, deptCol, promoCol, anneeCol);

        //CREATION DE LA ZONE EDITABLE
        TextField nomtxt = new TextField();
        nomtxt.setPromptText("Nom");
        nomtxt.setPrefWidth(200);

        TextField prenomtxt = new TextField();
        prenomtxt.setPromptText("Prénom");
        prenomtxt.setPrefWidth(200);

        TextField departementtxt = new TextField();
        departementtxt.setPromptText("Dépt");
        departementtxt.setPrefWidth(100);

        TextField promotxt = new TextField();
        promotxt.setPromptText("Promo");
        promotxt.setPrefWidth(100);

        TextField anneetxt = new TextField();
        anneetxt.setPromptText("Année");
        anneetxt.setPrefWidth(100);

        Button btnAjouter = new Button("Ajouter");
        Button btnSupprimer = new Button("Supprimer");

        // ACTION: sélectionner un stagiaire dans le tableau
        table.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Stagiaire>() {
                    @Override
                    public void changed(ObservableValue<? extends Stagiaire> observable, Stagiaire oldValue, Stagiaire newValue) {
                        Stagiaire oldStagiaire = oldValue == null ? null : oldValue;
                        Stagiaire newStagiaire = newValue == null ? null : newValue;
                        System.out.println(newStagiaire.get_nom());
                        nomtxt.setText(newStagiaire.get_nom());
                        prenomtxt.setText(newStagiaire.get_prenom());
                        departementtxt.setText(newStagiaire.get_departement());
                        promotxt.setText(newStagiaire.get_promo());
                        anneetxt.setText((newStagiaire.get_annee()));
                    }
                }
        );



        //Ajout choicebox pour la recherche
        Label rechercheLabel= new Label("Rechercher par:");
        ComboBox filtreRecherche= new ComboBox();
        filtreRecherche.getItems().addAll("Aucun filtre","Promotion","Année","Nom","Prénom", "Département");


        //Menu pour les mises à jour

      /*  Label modifierLabel= new Label("Mettre à jour:");
        ChoiceBox updateChoiceBox= new ChoiceBox();
        updateChoiceBox.getItems().addAll("","Modifier","Ajouter","Supprimer");*/

        Menu updateMenu = new Menu("Mise à jour");
        // Création du menuItem pour le menu update
        MenuItem modifier = new MenuItem("Modifier");
        MenuItem ajouter = new MenuItem("Ajouter");
        MenuItem supprimer = new MenuItem("Supprimer");
        ajouter.getStyleClass().add("item-ajouter");
        modifier.getStyleClass().add("item-modifier");
        supprimer.getStyleClass().add("item-supprimer");

        // Ajout des MenuItems au Menu Connexion
        updateMenu.getItems().addAll(ajouter,supprimer,modifier);
        modifier.setDisable(true);
        supprimer.setDisable(true);
        MenuBar updateBar = new MenuBar();
        updateBar.getMenus().add(updateMenu);
        updateBar.getStyleClass().add("menu-update");

        Button searchButton= new Button ("Search");
        
        /*
        CONTENEURS
        Création Hbox pour la zone éditable */
        HBox zoneEditable = new HBox();

        zoneEditable.getChildren().addAll(nomtxt,prenomtxt, departementtxt, promotxt, anneetxt, rechercheLabel, filtreRecherche);
        zoneEditable.setSpacing(10);
        zoneEditable.setSpacing(10.0);

        // Création du VBox Vboxtable (tableview+label)
        VBox vboxTable = new VBox();
        vboxTable.setSpacing(5);
        vboxTable.setPadding(new Insets(10, 10, 10, 10));
        vboxTable.getChildren().addAll(labelTable, table);


        // Creation HBox filtre+update
        HBox hBoxChoice = new HBox();
        hBoxChoice.getChildren().addAll(rechercheLabel, filtreRecherche,updateBar);
        hBoxChoice.setSpacing(20.0);

        // Préparation du premier stage et affichage
        AnchorPane root = new AnchorPane();


        //ANCRAGE CONTENEURS
        //Menubars
        root.setTopAnchor(menubars,0.0);
        root.setLeftAnchor(menubars,0.0);
        root.setRightAnchor(menubars,0.0);

        //vboxTable
        root.setTopAnchor(vboxTable,50.0);
        root.setBottomAnchor(vboxTable,80.0);
        root.setLeftAnchor(vboxTable,100.0);
        root.setRightAnchor(vboxTable,100.0);

        //zoneEditable
        root.setBottomAnchor(zoneEditable,100.0);
        root.setLeftAnchor(zoneEditable,200.0);
        root.setRightAnchor(zoneEditable,200.0);

        //updateBar
        root.setBottomAnchor(hBoxChoice,40.0);
        root.setRightAnchor(hBoxChoice,100.0);

        root.getChildren().addAll(menubars, vboxTable,zoneEditable, hBoxChoice);

        Scene scene = new Scene(root, 1000,700);
        scene.getStylesheets().add("Front.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        //GESTION EVENEMENTS
        //Action: ouvrir Fichier. Créer er afficher tableview
        ouvrirfichier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //On rempli la table avec la liste observable

                RandomAccessFile raf;
                String txtFile = "src/stagiaires.txt";

                //initialisation du raf
                AnnuaireBack newAnnuaire = new AnnuaireBack(txtFile);
                newAnnuaire.creerAnnuaire();

                try {
                    //on créé le fichier binaire à partir du fichier txt
                    raf = newAnnuaire.getRaf();

                    // On parcourt le .bin pour extraire les Stagiaires avec méthode .getStagiairesList(raf)
                    data = AnnuaireBack.getStagiairesList(raf);

                    // On envoie les données dans le TableView pour affichage sur l'application/interface
                    table.setItems(data);
                    raf.close();

                } catch (IOException e) {
                    System.out.println("erreur de chargement du fichier bin");
                    throw new RuntimeException(e);
                }

            }
        });

        ouvrirAnnuaire.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    RandomAccessFile raf = new RandomAccessFile("listeStagiaires.bin", "rw");

                    ObservableList<Stagiaire> data = AnnuaireBack.getStagiairesList(raf);


                    //On rempli la table avec la liste observable
                    table.setItems(data);
                    raf.close();
                } catch (FileNotFoundException e) {
                    System.out.println("je trouve pas le fichier");
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    System.out.println("je suis dans l'io");
                    throw new RuntimeException(e);
                }

            }
        });

        //Action rechercher par filtre
        filtreRecherche.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Stagiaire> result = FXCollections.observableArrayList();

                String selection = filtreRecherche.getValue().toString();


                try {
                    RandomAccessFile raf = new RandomAccessFile("listeStagiaires.bin", "rw");
                    ObservableList<Stagiaire> data = AnnuaireBack.getStagiairesList(raf);
                    System.out.println(selection);
                    String motSearched;


                    switch (selection) {
                        case "Promotion":
                            motSearched = promotxt.getText();
                            result = AnnuaireBack.rechercherStagiaireBin(raf, motSearched, "promo");
                           table.setItems(result);
                            break;
                        case "Année":
                            motSearched = anneetxt.getText();
                            result = AnnuaireBack.rechercherStagiaireBin(raf, motSearched, "annee");
                            table.setItems(result);
                            break;
                        case "Nom":
                            motSearched = nomtxt.getText();
                            result = AnnuaireBack.rechercherStagiaireBin(raf, motSearched, "nom");
                           table.setItems(result);

                            break;
                        case "Prénom":
                            motSearched = prenomtxt.getText();
                            result = AnnuaireBack.rechercherStagiaireBin(raf, motSearched, "prenom");
                           table.setItems(result);
                            break;
                        case "Département":
                            motSearched = departementtxt.getText();
                            result = AnnuaireBack.rechercherStagiaireBin(raf, motSearched, "departement");
                            table.setItems(result);
                            break;
                        case "Aucun filtre":
                            table.setItems(data);
                            break;
                        default:
                            break;
                    }
                    //table.setItems(result);
                } catch (IOException e) {
                    System.out.println("erreur de chargement du fichier bin");
                    throw new RuntimeException(e);
                }
                promotxt.clear();
                prenomtxt.clear();
                nomtxt.clear();
                anneetxt.clear();
                departementtxt.clear();


            }

        });


        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Stagiaire> result = FXCollections.observableArrayList();

                String promoSearched = promotxt.getText();
                String anneeSearched = anneetxt.getText();
                String nomSearched = nomtxt.getText();
                String prenomSearched = prenomtxt.getText();
                String dptSearched = departementtxt.getText();


               try {
                    RandomAccessFile raf = new RandomAccessFile("listeStagiaires.bin", "rw");
                   // ObservableList<Stagiaire> data = AnnuaireBack.getStagiairesList(raf);
                    result = AnnuaireBack.rechercherStagiaireBin(raf, promoSearched, "promo");
                    result = AnnuaireBack.rechercherStagiaireBin(raf, prenomSearched, "prenom");
                    result = AnnuaireBack.rechercherStagiaireBin(raf, nomSearched, "nom");
                    result = AnnuaireBack.rechercherStagiaireBin(raf, anneeSearched, "annee");
                    result = AnnuaireBack.rechercherStagiaireBin(raf, dptSearched, "departement");
                   table.setItems(result);


                    //table.setItems(result);
                } catch (IOException e) {
                    System.out.println("erreur de chargement du fichier bin");
                    throw new RuntimeException(e);
                }
                promotxt.clear();
                prenomtxt.clear();
                nomtxt.clear();
                anneetxt.clear();
                departementtxt.clear();
            }

        });

        //Action: ajouter un stagiaire
        ajouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                RandomAccessFile raf = null;
                try {
                    raf = new RandomAccessFile("listeStagiaires.bin", "rw");
                    ObservableList<Stagiaire> data = AnnuaireBack.getStagiairesList(raf);
                    // Récupération des valeurs des champs
                    String nom = nomtxt.getText();
                    String prenom = prenomtxt.getText();
                    String departement = departementtxt.getText();
                    String promo = promotxt.getText();
                    String annee = anneetxt.getText();

                    // Appel méthode addStagiaire
                    Stagiaire stagiaire = AnnuaireBack.addStagiaire(nom, prenom, departement, promo, annee);
                    data.add(stagiaire); // data déclaré ligne 38 pour pouvoir être utilisé dans plusieurs méthodes
                    table.setItems(data);

                    // Vider les champs
                    nomtxt.clear();
                    prenomtxt.clear();
                    departementtxt.clear();
                    promotxt.clear();
                    anneetxt.clear();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }




           }
        });


        // ACTION: sélectionner un stagiaire dans le tableau
        table.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Stagiaire>() {
                     @Override
                     public void changed(ObservableValue<? extends Stagiaire> observable, Stagiaire oldValue, Stagiaire newValue) {
                         Stagiaire oldStagiaire = oldValue == null ? null : oldValue;
                         Stagiaire newStagiaire = newValue == null ? null : newValue;
                         nomtxt.setText(newStagiaire.get_nom());
                         prenomtxt.setText(newStagiaire.get_prenom());
                         departementtxt.setText(newStagiaire.get_departement());
                         promotxt.setText(newStagiaire.get_promo());
                         anneetxt.setText((newStagiaire.get_annee()));
                     }
                 }
        );


        //ACTION: SUPPRIMER UN STAGIAIRE
        supprimer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                Stagiaire stagiaire = table.getSelectionModel().getSelectedItem();
                System.out.println("Le stagiaire séléectionné est : " + stagiaire);
                /*data.remove(stagiaire);
                table.setItems(data);*/

                // Suppression  du stagiaire dans le bin
                try {
                    AnnuaireBack.removeStagiaireBin(stagiaire);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }


                // Vider les champs
                nomtxt.clear();
                prenomtxt.clear();
                departementtxt.clear();
                promotxt.clear();
                anneetxt.clear();
            }
        });

        //Action : mise à jour stagiaire
        updateMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

            }
        });



        // Action connexion Admin+ Super-admin
        connexion.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //Création d'une boîte de dialogue pour la connexion Administrateur
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Connexion");
                dialog.getDialogPane().setBackground((new Background(new BackgroundFill(
                        LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY))));
                dialog.getDialogPane().setMinSize(200.0, 200.0);
                dialog.setHeaderText("Veuillez saisir vos identifiants");
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);


                //Création des champs de saisie
                TextField identifiant = new TextField();
                identifiant.setPromptText("Identifiant");
                Label idLabel = new Label("Identifiant:");
                Label pswLabel = new Label("Mot de passe:");
                PasswordField password = new PasswordField();
                password.setPromptText("Mot de passe");

                //création VBox
                VBox contenu = new VBox();
                contenu.setAlignment(Pos.CENTER_LEFT);
                contenu.setSpacing(20);
                contenu.getChildren().addAll(idLabel, identifiant,pswLabel, password);
                dialog.getDialogPane().setContent(contenu);
                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == ButtonType.OK) {
                        ObservableList<Administrateur> listeAdmin = FXCollections.observableArrayList();

                        //création liste identifiants pour la comparaison
                        listeAdmin = getAdminList();
                        System.out.println(listeAdmin);

                        //Création d'un objet avec password et identifiant
                        String pswd = password.getText();
                        String id = identifiant.getText();

                        Administrateur checkAdmin = new Administrateur(id,pswd,null,null);
                        System.out.println(checkAdmin);
                       // boolean check=listeAdminId.contains(checkAdmin);
                        boolean check=Administrateur.checkList(checkAdmin,listeAdmin );
                        System.out.println(check);

                        //vérification des champs
                        if (id.isEmpty() || pswd.isEmpty()) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setContentText("Merci de renseigner les deux champs");
                            alert.showAndWait();
                        } else if (check==false) {
                            System.out.println("not in the list");
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setHeaderText(null);
                            alert.setContentText("Identifiants incorrects");
                            alert.showAndWait();

                            //} else if (listeAdminId.contains(checkAdmin)==true) {
                        } else if (check==true) {
                            System.out.println("in the list");
                            modifier.setDisable(false);
                            supprimer.setDisable(false);
                            menuModifId.setDisable(false);
                            connexion.setDisable(true);
                            deconnexion.setDisable(false);

                            JOptionPane.showMessageDialog(null,"Connexion réussie");
                           // dialog.close();

                            //MODIF DU TABLEAU ACTIVEE
                            table.setEditable(true);
                            nomCol.setCellFactory(TextFieldTableCell.forTableColumn());
                            nomCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Stagiaire, String>>() {  // Colonne NOM
                                @Override
                                public void handle(TableColumn.CellEditEvent<Stagiaire, String> event) {

                                    Stagiaire stg = event.getRowValue();
                                    //on remplace le nom changé dans le stagiaire
                                    stg.set_nom(AnnuaireBack.completer(event.getNewValue(), AnnuaireBack.NOM));

                                    try {
                                        AnnuaireBack.updateBinary(table.getItems());
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            });
                            prenomCol.setCellFactory(TextFieldTableCell.forTableColumn());
                            prenomCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Stagiaire, String>>() {  // Colonne PRENOM
                                @Override
                                public void handle(TableColumn.CellEditEvent<Stagiaire, String> event) {

                                    Stagiaire stg = event.getRowValue();
                                    stg.set_prenom(AnnuaireBack.completer(event.getNewValue(), AnnuaireBack.PRENOM));

                                    try {
                                        AnnuaireBack.updateInBinary(stg);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                            deptCol.setCellFactory(TextFieldTableCell.forTableColumn());
                            deptCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Stagiaire, String>>() {  // colonne DEPARTEMENT
                                @Override
                                public void handle(TableColumn.CellEditEvent<Stagiaire, String> event) {

                                    Stagiaire stg = event.getRowValue();
                                    stg.set_departement(AnnuaireBack.completer(event.getNewValue(), AnnuaireBack.DEPARTEMENT));
                                    try {
                                        AnnuaireBack.updateInBinary(stg);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            });
                            promoCol.setCellFactory(TextFieldTableCell.forTableColumn());
                            promoCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Stagiaire, String>>() {  // colonne PROMO
                                @Override
                                public void handle(TableColumn.CellEditEvent<Stagiaire, String> event) {

                                    Stagiaire stg = event.getRowValue();
                                    stg.set_promo(AnnuaireBack.completer(event.getNewValue(), AnnuaireBack.PROMO));
                                    try {
                                        AnnuaireBack.updateInBinary(stg);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            });
                            anneeCol.setCellFactory(TextFieldTableCell.forTableColumn());
                            anneeCol.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Stagiaire, String>>() {  // colonne ANNEE
                                @Override
                                public void handle(TableColumn.CellEditEvent<Stagiaire, String> event) {

                                    Stagiaire stg = event.getRowValue();
                                    stg.set_annee(AnnuaireBack.completer(event.getNewValue(), AnnuaireBack.ANNEE));
                                    try {
                                        AnnuaireBack.updateInBinary(stg);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            });
                        }
                    }
                    return null;
                });

                Optional<String> result = dialog.showAndWait();
               /* if (result.isPresent()) {
                   System.out.println(result.get());
               }*/
            }
        });

        deconnexion.setOnAction(new EventHandler<ActionEvent>() {
                                  @Override
                                  public void handle(ActionEvent event) {
                                      connexion.setDisable(false);
                                      modifier.setDisable(true);
                                      supprimer.setDisable(true);
                                      menuModifId.setDisable(true);
                                      deconnexion.setDisable(true);


                                  }
                              });

    }

    //Methodes

    private ObservableList<Administrateur> getAdminList() {
        Administrateur admin1 = new Administrateur("MaJe", "password1","Machin", "Jean");
        Administrateur admin2 = new Administrateur("DuPa","password2","Dupond", "Paul");
        Administrateur admin3 = new Administrateur("DuMa","password3","Durand", "Martine");
        ObservableList<Administrateur> list = FXCollections.observableArrayList(admin1,admin2,admin3);
        return list;
    }
    public static void main(String[] args) {
        launch(args);

    }


}
