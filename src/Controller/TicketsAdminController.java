package Controller;

import api.SimpleMail;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import entities.FosUser;
import entities.Ticket;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import services.TicketService;
import services.UserService;

public class TicketsAdminController {

    @FXML
    private TableView<entities.Ticket> tableViewTickets;
    @FXML
    private TableColumn<entities.Ticket, String> sujetId;
    @FXML
    private TableColumn<entities.Ticket, String> statutId;
    @FXML
    private TableColumn<entities.Ticket, String> dateId;
    @FXML
    private TableColumn<entities.Ticket, String> descriptionId;
    @FXML
    private TableColumn<entities.Ticket, String> emEmail;

    @FXML
    private TableColumn<entities.Ticket, String> recEmail;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnDelete;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnReset;

    @FXML
    private JFXTextField sujetToFocus;

    @FXML
    private JFXComboBox<String> listViewRecepteurs;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private Button btnLoadImg;

    @FXML
    private Label imgNameLabel;

    private Stage thisStage;
    private AdminController adminController;

    public Ticket currentTicket;

    TicketService ts = new TicketService();
    UserService us = new UserService();

    FosUser connectedTicketsUser;

    public TicketsAdminController(AdminController ac) throws IOException {
        // We received the first controller, now let's make it usable throughout this controller.
        System.out.println("Controller.TicketsAdminController.<init>()********");
        this.adminController = ac;
        // Create the new stage
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TicketsAdminView.fxml"));
            // Set this class as the controller
            loader.setController(this);
            // Load the scene
            thisStage.setScene(new Scene(loader.load()));
            // Setup the window/stage
            thisStage.setTitle("Details ticket");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public TicketsAdminController() {

        thisStage = new Stage();
        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/TicketsAdminView.fxml"));
            // Set this class as the controller
            loader.setController(this);
            // Load the scene
            thisStage.setScene(new Scene(loader.load()));
            // Setup the window/stage
            thisStage.setTitle("Tickets Admin View");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    public entities.Ticket getCurrentTicket() {
        return this.currentTicket;
    }
    public ObservableList<entities.Ticket> ticketsOL = FXCollections.observableArrayList();

    public void populateTicketsAdminTable() throws SQLException {

        TicketService ts = new TicketService();
        for (entities.Ticket t : ts.afficherTicketsWithUsernames()) {
            ticketsOL.add(t);
//            System.out.println(t.getDescription());
        }
        sujetId.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        statutId.setCellValueFactory(new PropertyValueFactory<>("statut"));
        descriptionId.setCellValueFactory(new PropertyValueFactory<>("description"));
        dateId.setCellValueFactory(new PropertyValueFactory<>("createdat"));
        emEmail.setCellValueFactory(new PropertyValueFactory<>("emeteurEmail"));
        recEmail.setCellValueFactory(new PropertyValueFactory<>("recepteurEmail"));

        tableViewTickets.setItems(ticketsOL);
    }

//    public ObservableList<entities.FosUser> recepteursOL = FXCollections.observableArrayList();
    public ObservableList<String> emailsRecepteursOL = FXCollections.observableArrayList();

    public void populateRecepteursListView() throws SQLException {
//get Users and show emails
        for (entities.FosUser fu : us.afficherUsers()) {
//            recepteursOL.add(fu);
            emailsRecepteursOL.add(fu.getEmail());
            System.out.println(fu.getUsername());
        }

        listViewRecepteurs.setItems(emailsRecepteursOL);
    }

    @FXML
    public void getDetails(MouseEvent event) throws IOException {

        if (event.getClickCount() == 2) //Checking double click
        {
            this.currentTicket = ts.getTicketById(tableViewTickets.getSelectionModel().getSelectedItem().getId());
            TicketAdminController tac = new TicketAdminController(this);

            tac.showStage();

        }
    }

    public void delete() {
        //from view
        Ticket selectedItem = tableViewTickets.getSelectionModel().getSelectedItem();
        tableViewTickets.getItems().remove(selectedItem);
        //from DB
        ts.supprTicket(selectedItem);
    }

    public void add() {
        sujetToFocus.requestFocus();
    }

    Ticket nt;

    public void save() throws SQLException, Exception {

        System.out.println("Controller.TicketsAdminController.save()");
        //create a new ticket
        FosUser emetteur = adminController.connectedUserAdmin;
        System.out.println(emetteur.getEmail());

        FosUser recepteur = us.getUserByEmail(listViewRecepteurs.getValue());
        String status = "ouvert";//nouveau ticket !
        String sujet = sujetToFocus.getText();
        String description = txtDescription.getText();

        nt = new Ticket();
        nt.setDescription(description);
        nt.setStatut(status);
        nt.setSujet(sujet);
        nt.setEmeteurId(emetteur);
        nt.setRecepteurId(recepteur);
        nt.setImageName(imgNameLabel.getText());

        System.out.println("----------" + nt.getImageName());
        //add ticket to db 
        ts.ajouterTicket(nt);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("New ticket was added !");
        alert.setContentText("ticket sent successfully from " + emetteur.getEmail() + "to " + recepteur.getEmail());

        refreshList();

//        SimpleMail sm = new SimpleMail(nt, emetteur);
//        sm.send();
    }

    ///*
    public void refreshList() throws SQLException {
        tableViewTickets.getItems().clear();
        populateTicketsAdminTable();
    }

    public void clear() {
        sujetToFocus.clear();
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
        fileChoser.setTitle("Sélectionnez une image pour le ticket");
        fileChoser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.bmp", "*.jpeg", "*.gif")
        );
        ;
//          

        File file = fileChoser.showOpenDialog(theStage);
        if (file != null) {
            
            Image im = new Image("file:///" + file.toPath().toString());
            
            
            String p="C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\NetBeansProjects\\PIjava\\src\\images\\"+imgNameLabel.getText();
            
            imgNameLabel.setText(file.getName());
            
            
            File file2  = new File(p);
            copyFileUsingChannel(file, file2);
            

        }
    }

    @FXML
    public void initialize() throws SQLException {
        connectedTicketsUser = adminController.connectedUserAdmin;
        //remplissage de la liste des tickets
        try {
            populateTicketsAdminTable();
        } catch (SQLException ex) {
            Logger.getLogger(TicketsAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //remplissage des recepteurs
        populateRecepteursListView();

        tableViewTickets.setOnMouseClicked(event -> {
            try {
                getDetails(event);
            } catch (IOException ex) {
                Logger.getLogger(TicketsAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //delete
        btnDelete.setOnAction(value -> delete());
        btnAdd.setOnAction(value -> add());

        btnSave.setOnAction(value -> {
            try {
                save();
            } catch (SQLException ex) {
                Logger.getLogger(TicketsAdminController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TicketsAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //clear fiels
        btnReset.setOnAction(value -> clear());

        //load image 
        btnLoadImg.setOnAction(event -> {
            try {
                loadImage(event);
            } catch (IOException ex) {
                Logger.getLogger(TicketsAdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

}
