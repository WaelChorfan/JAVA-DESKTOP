package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import entities.Commentaire;
import entities.Ticket;
import java.io.File;
import java.io.IOException;
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
import services.CommentairesService;
import services.TicketService;

public class TicketClientController {

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
    private TableView<entities.Commentaire> tableViewCom;

    @FXML
    private TableColumn<entities.Commentaire, String> tComUsername;

    @FXML
    private TableColumn<entities.Commentaire, String> tComContenu;

    @FXML
    private ImageView imgId;

    @FXML
    private JFXButton btnImage;

    private Stage thisStage;
    private TicketsClientController ticketsClientController;

    private entities.Ticket myTicket;
    TicketService ts = new TicketService();
    CommentairesService cs = new CommentairesService();

    //open ticket details with List Tickets  on double click
    public TicketClientController(TicketsClientController tac) throws IOException {
        // We received the first controller, now let's make it usable throughout this controller.
        this.ticketsClientController = tac;
        // Create the new stage
        thisStage = new Stage();
        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TicketClientView.fxml"));
            // Set this class as the controller
            loader.setController(this);
            // Load the scene
            thisStage.setScene(new Scene(loader.load()));
            // Setup the window/stage
            thisStage.setTitle("Ticket Details et Commentaires");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showStage() {
        thisStage.show();
    }

    public void addComment() throws SQLException {
        System.out.println(txtComment.getText());

        entities.Commentaire c = new Commentaire(
                this.ticketsClientController.connectedTicketsUser.getId().intValue(),
                this.ticketsClientController.currentTicket.getId().intValue(),
                txtComment.getText()
        );

        cs.ajouterCommentaire(c);

        refreshList();

    }

    public void refreshList() throws SQLException {
        tableViewCom.getItems().clear();
        populateCommentsTable();
    }

    public void delete() {
        //from view
        Commentaire selectedItem = tableViewCom.getSelectionModel().getSelectedItem();
        tableViewCom.getItems().remove(selectedItem);
        //from DB
        cs.supprCommentaire(selectedItem);
    }

    public void populateDetails() {
        //populate with details
        txtSujet.setText(ticketsClientController.getCurrentTicket().getSujet());
        txtDescription.setText(ticketsClientController.getCurrentTicket().getDescription());
        txtEmeteur.setText(ticketsClientController.getCurrentTicket().getEmeteurEmail());
        txtRecepteur.setText(ticketsClientController.getCurrentTicket().getRecepteurEmail());
        txtDateCreation.setText(ticketsClientController.getCurrentTicket().getCreatedat().toString());

        if (ticketsClientController.getCurrentTicket().getStatut().equals("ouvert")) {
            cbStatus.getSelectionModel().select(0);
        } else {
            cbStatus.getSelectionModel().select(1);
        };

        String imgName = ticketsClientController.getCurrentTicket().getImageName();

        
        String imToDisplay =((imgName==null)||imgName.equals(""))?"0.png":imgName;
        
        Image im = new Image("/images/" + imToDisplay);
        imgId.setImage(im);
    }

    public ObservableList<entities.Commentaire> commentsOL = FXCollections.observableArrayList();

    public void populateCommentsTable() throws SQLException {
tableViewCom.setPlaceholder(new Label("There are no existing comments for this ticket! "));
        CommentairesService cs = new CommentairesService();
        for (entities.Commentaire c : cs.afficherCommentairesByTicket(this.ticketsClientController.currentTicket)) {
            commentsOL.add(c);
            System.out.println(c.getContenu());
        }

        tComContenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        tComUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

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
        
        System.out.println("------"+newImageName);
        ts.modifTicket(this.ticketsClientController.currentTicket, newSujet, newDescription, newStatut,newImageName);

        ticketsClientController.refreshList();
        
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

    
    
    
    public void loadImage(ActionEvent event) {
        Node source = (Node) event.getSource();
        Window theStage = source.getScene().getWindow();
        FileChooser fileChoser = new FileChooser();
        fileChoser.setTitle("Sélectionnez une autre image ");
        fileChoser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.bmp", "*.jpeg", "*.gif")
        );

        File Initial_Path = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\NetBeansProjects\\PIjava\\src\\images");
        fileChoser.setInitialDirectory(Initial_Path);
        File file = fileChoser.showOpenDialog(theStage);
        if (file != null) {
            Image im = new Image("file:///" + file.toPath().toString());

            imgNameLabel.setText(file.getName());
            
            //change image 
            imgId.setImage(im);

        }
    }
    
    public void  refreshImage(){
        Image im = new Image("/images/" + imgNameLabel.getText());
        imgId.setImage(im);
    }

    @FXML
    public void initialize() throws SQLException {

        //getting the selected ticket from the list
        myTicket = ticketsClientController.getCurrentTicket();

        ObservableList<String> statusOL = FXCollections.observableArrayList("ouvert", "fermé");
        cbStatus.setItems(statusOL);

        btnComment.setOnAction(event -> {
            try {
                addComment();
            } catch (SQLException ex) {
                Logger.getLogger(TicketClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        populateDetails();

        //save the changes 
        btnSave.setOnAction(value -> {
            try {
                updateTicket();
            } catch (SQLException ex) {
                Logger.getLogger(TicketClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnCancel.setOnAction(event -> {
            txtDescription.clear();
            txtSujet.clear();
        });
        btnDelete.setOnAction(value -> delete());

        btnImage.setOnAction(event -> loadImage(event));

        tableViewCom.refresh();
        populateCommentsTable();

    }

}
