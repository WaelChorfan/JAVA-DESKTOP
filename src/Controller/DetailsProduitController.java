package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import entities.Message;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.MessageService;
import services.ProduitService;

public class DetailsProduitController {

    
    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXTextField txtNom;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtPrix;

    @FXML
    private Label txtDate;

    @FXML
    private ImageView imgId;

    @FXML
    private JFXButton btnImage;

    @FXML
    private Label imgNameLabel;
    
    
    private Stage thisStage;
    private MesProduitsController mesProduitsController;

    private entities.Produit myProduit;
    ProduitService ts = new ProduitService();
    MessageService ms = new MessageService();

    //open Produit details with List Produits  on double click
    public DetailsProduitController(MesProduitsController tac) throws IOException {
        // We received the first controller, now let's make it usable throughout this controller.
        this.mesProduitsController = tac;
        // Create the new stage
        thisStage = new Stage();
        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/ProduitAdminView.fxml"));
            // Set this class as the controller
            loader.setController(this);
            // Load the scene
            thisStage.setScene(new Scene(loader.load()));
            // Setup the window/stage
            thisStage.setTitle("Produit Details et messages");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showStage() {
        thisStage.show();
    }

 

  

    public void populateDetails() {
        //populate with details
        txtNom.setText(mesProduitsController.getCurrentProduit().getNom());
        txtDescription.setText(mesProduitsController.getCurrentProduit().getDescription());
        txtPrix.setText(mesProduitsController.getCurrentProduit().getPrix().toString());
        txtDate.setText(mesProduitsController.getCurrentProduit().getDate().toString());

        String imgName = mesProduitsController.getCurrentProduit().getImageName();

        String imToDisplay = ((imgName == null) || imgName.equals("")) ? "0.png" : imgName;

        System.out.println(imgName);
//        System.exit(0);
        Image im = new Image("/images/" + imToDisplay);
        imgId.setImage(im);
    }

    public ObservableList<entities.Message> commentsOL = FXCollections.observableArrayList();



    
    private void updateProduit() throws SQLException {

        
        System.out.println("fdlkhqsdlkfhqsldkfh");
        String newNom = txtNom.getText();
        String newDescription = txtDescription.getText();
        Double newPrix = Double.parseDouble( txtPrix.getText().toString());
        String newImageName = imgNameLabel.getText();

        System.out.println("------" + newImageName);
        ts.modifProduit(this.mesProduitsController.currentProduit, newNom, newDescription, newPrix, newImageName);

        mesProduitsController.refreshList();

        refreshImage();

        //alert updated or exit 
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Details Produits");
        alert1.setContentText("Produit Modifié avec succés");
        alert1.setHeaderText("Details Produits");
        alert1.show();

//        thisStage.close();
//        
    }

    public void loadImage(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Sélectionnez une autre image ");
        fileChoser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.bmp", "*.jpeg", "*.gif")
        );

        File file = fileChoser.showOpenDialog(theStage);
        if (file != null) {

            Image im = new Image("file:///" + file.toPath().toString());

            imgNameLabel.setText(file.getName());
            String p = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\NetBeansProjects\\PIjava\\src\\images\\" + imgNameLabel.getText();


            File file2 = new File(p);
            
            System.out.println("file"+file.getPath());
            System.out.println("file2"+file2.getPath());
            System.out.println("----cccc---------");
            
            copyFileUsingChannel(file, file2);

            
//            Image im2 = new Image("file:///" + file2.toPath().toString());
//
//            //change image 
//            imgId.setImage(im2);

        }
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

    public void refreshImage() {
        Image im = new Image("/images/" + imgNameLabel.getText());
        imgId.setImage(im);
    }

    @FXML
    public void initialize() throws SQLException {

        //getting the selected Produit from the list
        myProduit = mesProduitsController.getCurrentProduit();


        populateDetails();

        //save the changes 
        btnSave.setOnAction(value -> {
            try {
                updateProduit();
            } catch (SQLException ex) {
                Logger.getLogger(DetailsProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnCancel.setOnAction(event -> {
            txtDescription.clear();
            txtNom.clear();
            txtPrix.clear();
        });

        btnImage.setOnAction(event -> {
            try {
                loadImage(event);
            } catch (IOException ex) {
                Logger.getLogger(DetailsProduitController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });


    }

}
