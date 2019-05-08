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
import services.TicketService;

public class TicketAdminController {

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXTextField txtSujet;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXComboBox<String> cbStatus;

    @FXML
    private Label txtDateCreation;

    @FXML
    private Label txtEmeteur;

    @FXML
    private Label txtRecepteur;

    @FXML
    private Label imgNameLabel;

    @FXML
    private JFXTextField txtComment;

    @FXML
    private JFXButton btnComment;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private TableView<entities.Message> tableViewCom;

    @FXML
    private TableColumn<entities.Message, String> tComUsername;

    @FXML
    private TableColumn<entities.Message, String> tComContenu;

    @FXML
    private ImageView imgId;

    @FXML
    private JFXButton btnImage;
    
    
    private Stage thisStage;
    private TicketsAdminController ticketsAdminController;

    private entities.Ticket myTicket;
    TicketService ts = new TicketService();
    MessageService ms = new MessageService();

    //open ticket details with List Tickets  on double click
    public TicketAdminController(TicketsAdminController tac) throws IOException {
        // We received the first controller, now let's make it usable throughout this controller.
        this.ticketsAdminController = tac;
        // Create the new stage
        thisStage = new Stage();
        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TicketAdminView.fxml"));
            // Set this class as the controller
            loader.setController(this);
            // Load the scene
            thisStage.setScene(new Scene(loader.load()));
            // Setup the window/stage
            thisStage.setTitle("Ticket Details et messages");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showStage() {
        thisStage.show();
    }

    public void addComment() throws SQLException {
        System.out.println(txtComment.getText());

        entities.Message m = new Message(
                this.ticketsAdminController.connectedTicketsUser.getId().intValue(),
                this.ticketsAdminController.currentTicket.getId().intValue(),
                txtComment.getText()
        );

        ms.ajouterMessage(m);

        refreshList();

    }

    public void refreshList() throws SQLException {
        tableViewCom.getItems().clear();
        populateCommentsTable();
    }

    public void delete() {
        //from view
        Message selectedItem = tableViewCom.getSelectionModel().getSelectedItem();
        tableViewCom.getItems().remove(selectedItem);
        //from DB
        ms.supprMessage(selectedItem);
    }

    public void populateDetails() {
        //populate with details
        txtSujet.setText(ticketsAdminController.getCurrentTicket().getSujet());
        txtDescription.setText(ticketsAdminController.getCurrentTicket().getDescription());
        txtEmeteur.setText(ticketsAdminController.getCurrentTicket().getEmeteurEmail());
        txtRecepteur.setText(ticketsAdminController.getCurrentTicket().getRecepteurEmail());
        txtDateCreation.setText(ticketsAdminController.getCurrentTicket().getCreatedat().toString());

        if (ticketsAdminController.getCurrentTicket().getStatut().equals("ouvert")) {
            cbStatus.getSelectionModel().select(0);
        } else {
            cbStatus.getSelectionModel().select(1);
        };

        String imgName = ticketsAdminController.getCurrentTicket().getImageName();

        String imToDisplay = ((imgName == null) || imgName.equals("")) ? "0.png" : imgName;

        System.out.println(imgName);
//        System.exit(0);
        Image im = new Image("/images/" + imToDisplay);
        imgId.setImage(im);
    }

    public ObservableList<entities.Message> commentsOL = FXCollections.observableArrayList();

    public void populateCommentsTable() throws SQLException {

        tableViewCom.setPlaceholder(new Label("There are no existing comments for this ticket! "));

        MessageService cs = new MessageService();
        for (entities.Message m : ms.afficherMessagesByTicket(this.ticketsAdminController.currentTicket)) {
            commentsOL.add(m);
            System.out.println(m.getText());
        }

        tComContenu.setCellValueFactory(new PropertyValueFactory<>("text"));
        tComUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));

        tableViewCom.setItems(commentsOL);
    }
//    public ObservableList<entities.Ticket> ticketsOL = FXCollections.observableArrayList();

    public void showComment(ActionEvent event) {
        String newComment = txtComment.getText();
        System.out.println(newComment);
    }

    private void updateTicket() throws SQLException {

        String newSujet = txtSujet.getText();
        String newDescription = txtDescription.getText();
        String newStatut = cbStatus.getValue();
        String newImageName = imgNameLabel.getText();

        System.out.println("------" + newImageName);
        ts.modifTicket(this.ticketsAdminController.currentTicket, newSujet, newDescription, newStatut, newImageName);

        ticketsAdminController.refreshList();

        refreshImage();

        //alert updated or exit 
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Details Tickets");
        alert1.setContentText("Ticket Modifié avec succés");
        alert1.setHeaderText("Details Tickets");
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

        //getting the selected ticket from the list
        myTicket = ticketsAdminController.getCurrentTicket();

        ObservableList<String> statusOL = FXCollections.observableArrayList("ouvert", "fermé");
        cbStatus.setItems(statusOL);

        btnComment.setOnAction(event -> {
            try {
                addComment();
            } catch (SQLException ex) {
                Logger.getLogger(TicketAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        populateDetails();

        //save the changes 
        btnSave.setOnAction(value -> {
            try {
                updateTicket();
            } catch (SQLException ex) {
                Logger.getLogger(TicketAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnCancel.setOnAction(event -> {
            txtDescription.clear();
            txtSujet.clear();
        });
        btnDelete.setOnAction(value -> delete());

        btnImage.setOnAction(event -> {
            try {
                loadImage(event);
            } catch (IOException ex) {
                Logger.getLogger(TicketAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        populateCommentsTable();

    }

}
