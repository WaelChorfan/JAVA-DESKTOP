package Controller;

import entities.FosUser;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.UserService;

public class UsersAdminController {

  @FXML
    private TableView<entities.FosUser> tableViewFosUsers;

    @FXML
    private TableColumn<entities.FosUser, String> userNameId;

    @FXML
    private TableColumn<entities.FosUser, String> emailId;

    @FXML
    private TableColumn<entities.FosUser, String> roleId;

    @FXML
    private Button btnDelete;



    


    private Stage thisStage;
    private AdminController adminController;

    public FosUser currentFosUser;

    
    UserService us = new UserService();


    public UsersAdminController(AdminController ac) throws IOException {
        // We received the first controller, now let's make it usable throughout this controller.
        this.adminController = ac;
        // Create the new stage
        thisStage = new Stage();

        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UsersAdminView.fxml"));
            // Set this class as the controller
            loader.setController(this);
            // Load the scene
            thisStage.setScene(new Scene(loader.load()));
            // Setup the window/stage
            thisStage.setTitle("Users");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public UsersAdminController() {

        thisStage = new Stage();
        // Load the FXML file
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/UsersAdminView.fxml"));
            // Set this class as the controller
            loader.setController(this);
            // Load the scene
            thisStage.setScene(new Scene(loader.load()));
            // Setup the window/stage
            thisStage.setTitle("Users -Admin View");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }

    public entities.FosUser getCurrentFosUser() {
        return this.currentFosUser;
    }
    public ObservableList<entities.FosUser> usersOL = FXCollections.observableArrayList();

    public void populateFosUsersAdminTable() throws SQLException {

        
     
        
        UserService ts = new UserService();
        for (entities.FosUser t : ts.afficherUsers()) {
            usersOL.add(t);
//            System.out.println(t.getDescription());
        }
        userNameId.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailId.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleId.setCellValueFactory(new PropertyValueFactory<>("role"));

        tableViewFosUsers.setItems(usersOL);
    }

//    public ObservableList<entities.FosFosUser> recepteursOL = FXCollections.observableArrayList();
    public ObservableList<String> emailsRecepteursOL = FXCollections.observableArrayList();

    


    public void delete() {
        //from view
        FosUser selectedItem = tableViewFosUsers.getSelectionModel().getSelectedItem();
        tableViewFosUsers.getItems().remove(selectedItem);
        //from DB
        us.supprUser(selectedItem);
    }



    

    public void refreshList() throws SQLException {
        tableViewFosUsers.getItems().clear();
        populateFosUsersAdminTable();
    }

   


    @FXML
    public void initialize() throws SQLException {
        //remplissage de la liste des users
        try {
            populateFosUsersAdminTable();
        } catch (SQLException ex) {
            Logger.getLogger(UsersAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }



        //delete
        btnDelete.setOnAction(value -> delete());


    }

}
