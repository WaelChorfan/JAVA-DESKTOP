package Controller;

import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.sql.SQLException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ProduitService;
import services.UserService;

public class ProdsListController {

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
    private JFXComboBox<String> cbViewTypes;

    @FXML
    private JFXComboBox<String> cbViewCategories;

    @FXML
    private Label imgNameLabel;

    @FXML
    private ImageView imgId;

    private Stage thisStage;

    ProduitService ps = new ProduitService();
    UserService us = new UserService();

    public ProdsListController() {

        thisStage = new Stage();
        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ListeProduits.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("ListeProduitsView");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    public ObservableList<entities.Produit> prodsOL = FXCollections.observableArrayList();

    public void populateProduitsTable() throws SQLException {

        cbViewTypes.valueProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue ov, String oldV, String newV) {

                tableViewProds.getItems().clear();
                System.out.println(ov);
                System.out.println();

                ps.afficherProduitsParType(ov.getValue().toString()).forEach(p -> {
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

                ps.afficherProduitsParCategorie(ov.getValue().toString()).forEach(p -> {
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

//ProduitService ps = new ProduitService();
    private void changeImage(MouseEvent event) {
//        Image im = new Image("/images/" + "0.png");
//        imgId.setImage(im);

        if (event.getClickCount() == 2) //Checking double click
        {
            String imn = tableViewProds.getSelectionModel().getSelectedItem().getImageName();
            Image im = new Image("/images_prod/" + imn);
            imgId.setImage(im);

        }

    }

    @FXML
    public void initialize() throws SQLException {

        populateProduitsTable();

        //remplissage des recepteurs
        populateTypesCatsLists();

        //fill image 
        Image im = new Image("/images_prod/" + "0.png");
        imgId.setImage(im);

        tableViewProds.setOnMouseClicked(event -> {
            changeImage(event);
        });

        //
        cbViewTypes.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
//          System.out.println(ov);
//            System.out.println(t);
//            System.out.println(t1);
//            System.out.println("current"+t1);

            }

        });

    }

}
