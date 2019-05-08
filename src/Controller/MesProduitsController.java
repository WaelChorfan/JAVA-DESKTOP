package Controller;

import api.SimpleMail;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import entities.FosUser;
import entities.Produit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.ProduitService;
import services.UserService;

public class MesProduitsController {

    @FXML
    private Label titleText;
    @FXML
    private TableView<entities.Produit> tableViewProds;

    @FXML
    private TableColumn<entities.Produit, String> nomId;

    @FXML
    private TableColumn<entities.Produit, Double> prixId;

    @FXML
    private TableColumn<entities.Produit, String> descriptionId;

    @FXML
    private TableColumn<entities.Produit, String> emailId;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnDelete;

    @FXML
    private JFXTextField nomToFocus;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtPrix;

    @FXML
    private JFXComboBox<String> cbViewTypes;

    @FXML
    private JFXComboBox<String> cbViewCategories;

    @FXML
    private Button btnLoadImg;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnReset;

    @FXML
    private Label imgNameLabel;

    @FXML
    private ImageView imgId;

    private Stage thisStage;

    private ClientController clientController;

    public Produit currentProduit;

    ProduitService ps = new ProduitService();
    UserService us = new UserService();

    FosUser connectedProduitsUser;

    public MesProduitsController() throws IOException {
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MesProduits.fxml"));
            // Set this class as the controller
            loader.setController(this);
            // Load the scene
            thisStage.setScene(new Scene(loader.load()));
            // Setup the window/stage
            thisStage.setTitle("Details produit");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public MesProduitsController(ClientController ac) throws IOException {
        // We received the first controller, now let's make it usable throughout this controller.
        System.out.println("Controller.mesProduitsController.<init>()********");
        this.clientController = ac;
        // Create the new stage
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/MesProduits.fxml"));
            // Set this class as the controller
            loader.setController(this);
            // Load the scene
            thisStage.setScene(new Scene(loader.load()));
            // Setup the window/stage
            thisStage.setTitle("Details produit");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showStage() {
        thisStage.showAndWait();
    }

    public entities.Produit getCurrentProduit() {
        return this.currentProduit;
    }

    public ObservableList<entities.Produit> prodsOL = FXCollections.observableArrayList();

    public void populateProduitsTable() throws SQLException {

        cbViewTypes.valueProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue ov, String oldV, String newV) {

                tableViewProds.getItems().clear();
                System.out.println(ov);
                System.out.println();

                ps.afficherProduitsParTypeEtUser(ov.getValue().toString(), connectedProduitsUser).forEach(p -> {
                    prodsOL.add(p);
                });

                nomId.setCellValueFactory(new PropertyValueFactory<>("nom"));
                prixId.setCellValueFactory(new PropertyValueFactory<>("prix"));
                descriptionId.setCellValueFactory(new PropertyValueFactory<>("description"));
                emailId.setCellValueFactory(new PropertyValueFactory<>("userEmail"));

                tableViewProds.setItems(prodsOL);

            }
        ;

        }

 );
        
        
             cbViewCategories.valueProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue ov, String oldV, String newV) {

                tableViewProds.getItems().clear();
                System.out.println(ov);
                System.out.println();

                ps.afficherProduitsParCategorieEtUser(ov.getValue().toString(), connectedProduitsUser).forEach(p -> {
                    prodsOL.add(p);
                });

                nomId.setCellValueFactory(new PropertyValueFactory<>("nom"));
                prixId.setCellValueFactory(new PropertyValueFactory<>("prix"));
                descriptionId.setCellValueFactory(new PropertyValueFactory<>("description"));
                emailId.setCellValueFactory(new PropertyValueFactory<>("userEmail"));

                tableViewProds.setItems(prodsOL);

            }
        ;
    }

    );
        


    }
    
    public void populateTypesCatsLists() throws SQLException {

        ObservableList<String> typesOL = FXCollections.observableArrayList("Type1", "Type2");
        cbViewTypes.setItems(typesOL);

        ObservableList<String> catOL = FXCollections.observableArrayList("Categorie1", "Categorie2");
        cbViewCategories.setItems(catOL);
    }

    @FXML
    public void getDetails(MouseEvent event) throws IOException, SQLException {
        System.out.println("Controller.MesProduitsController.getDetails()");
        //change image
        if (event.getClickCount() == 2) //Checking double click
        {

            String imgName = tableViewProds.getSelectionModel().getSelectedItem().getImageName();
            
            String imToDisplay = ((imgName == null) || imgName.equals("")) ? "0.png" : imgName;
//            
        System.out.println("------------------"+imToDisplay);

            Image im = new Image("/images_prod/" + imToDisplay);
            imgId.setImage(im);

        }
    }

    public void delete() {
        //from view
        Produit selectedItem = tableViewProds.getSelectionModel().getSelectedItem();
        tableViewProds.getItems().remove(selectedItem);
        //from DB
        ps.supprProduit(selectedItem);
    }

    public void add() {
        nomToFocus.requestFocus();
    }

    Produit nt;

    public void save() throws SQLException, Exception {

        System.out.println("adding a new product ****/");
        FosUser user = clientController.connectedUserClient;

        String type = cbViewTypes.getValue();
        String categorie = cbViewCategories.getValue();
        String nom = nomToFocus.getText();
        String description = txtDescription.getText();
        Double prix = new Double(txtPrix.getText());
        nt = new Produit();
        nt.setDescription(description);
        nt.setNom(nom);
        nt.setPrix(prix);
        nt.setCategorie(categorie);
        nt.setType(type);
        nt.setUserId(user);
        nt.setImageName(imgNameLabel.getText());

//add produit to db 
        ps.ajouterProduit(nt);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("New produit was added !");
        alert.setContentText("produit added successfully from ");

        refreshList();

    }

    ///*
    public void refreshList() throws SQLException {
        tableViewProds.getItems().clear();
        populateProduitsTable();
    }

    public void clear() {
        nomToFocus.clear();
        txtDescription.clear();
        imgNameLabel.setText(" ---------- ");

    }

    private static void copyFileUsingChannel(File source, File dest) throws IOException {
        FileChannel sourceChannel = null;
        FileChannel destChannel = null;
        try {
            sourceChannel = new FileInputStream(source).getChannel();
            destChannel = new FileOutputStream(dest).getChannel();
            destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        } finally {
            sourceChannel.close();
            destChannel.close();
        }
    }

    public void loadImage(ActionEvent event) throws IOException {
        
        
        
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Sélectionnez une image pour le produit");
        fileChoser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.bmp", "*.jpeg", "*.gif")
        );
        ;


        File file = fileChoser.showOpenDialog(theStage);
        if (file != null) {
            imgNameLabel.setText(file.getName());

            Image im = new Image("file:///" + file.toPath().toString());

            String p = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\NetBeansProjects\\PIjava\\src\\images_prod\\" + imgNameLabel.getText();

         

            File file2 = new File(p);
            copyFileUsingChannel(file, file2);

        }
    }

    @FXML
    public void initialize() throws SQLException {
        connectedProduitsUser = clientController.connectedUserClient;

//remplissage de la liste des produits
        try {
            populateProduitsTable();
        } catch (SQLException ex) {
            Logger.getLogger(MesProduitsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        populateTypesCatsLists();

        tableViewProds.setOnMouseClicked(event -> {
            try {
                getDetails(event);
            } catch (IOException ex) {
                Logger.getLogger(MesProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(MesProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //delete
        btnDelete.setOnAction(value -> delete());
        btnAdd.setOnAction(value -> add());

        btnSave.setOnAction(value -> {
            try {
                save();
            } catch (SQLException ex) {
                Logger.getLogger(MesProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(MesProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //clear fiels
        btnReset.setOnAction(value -> clear());

        //fill image 
        Image im = new Image("/images_prod/" + "0.png");
        imgId.setImage(im);

        //load image 
        btnLoadImg.setOnAction(event -> {
            try {
                loadImage(event);
            } catch (IOException ex) {
                Logger.getLogger(MesProduitsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

}
