package Controller;

import entities.FosUser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AdminController {

   
   @FXML
    private Button btnHome;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnProds;

    @FXML
    private Button btnTickets;

    @FXML
    private Button btnLogout;

    @FXML
    private Label nameMSG;
  
    
    private final Stage thisStage;
    private final LoginController loginController;
    
    FosUser connectedUserAdmin;
    
   

    public AdminController(LoginController loginController) throws IOException {
        this.loginController = loginController;
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Admin.fxml"));
        loader.setController(this);
        thisStage.setScene(new Scene(loader.load()));
        thisStage.setTitle("Admin Page");
    }
    
    public void showStage() {
        thisStage.show();
    }

    public void goToLogout(ActionEvent event) throws IOException {
        LoginController c = new LoginController(this);
        c.showStage();
        thisStage.hide();
    }

    private void goToTickets(ActionEvent event) throws IOException {
//        thisStage.hide();
        TicketsAdminController tac = new TicketsAdminController(this);
        tac.showStage();
    }
    
    private void goToUsers(ActionEvent event) throws IOException {
//        thisStage.hide();
        UsersAdminController uac = new UsersAdminController(this);
        uac.showStage();
    }
    
    
     private void goToListProduits(ActionEvent event) throws IOException {
        ProdsListController p = new ProdsListController();
        p.showStage();
    }

    @FXML
    public void initialize() {
        
        connectedUserAdmin=loginController.connectedUser;
        nameMSG.setText(loginController.connectedUser.getUsername());
        
        
        btnLogout.setOnAction(event -> {
            try {
                goToLogout(event);
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        
        btnTickets.setOnAction(event -> {
            try {
                goToTickets(event);
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
          btnUsers.setOnAction(event -> {
            try {
                goToUsers(event);
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
          
          
          btnProds.setOnAction(value->{
            try {
                goToListProduits(value);
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
          });

    }

}
