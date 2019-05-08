package Controller;

import entities.FosUser;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class Societe1Controller  {

    @FXML
    private Label nameMSG;
     @FXML
    private Button bntLogout;
    FosUser user;
    
      // Holds this controller's Stage
    private Stage thisStage;

    // Will hold a reference to the first controller, allowing us to access the methods found there.
    private final LoginController loginController;
    
    public Societe1Controller(LoginController loginController) {
        this.loginController = loginController;
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Client.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Socété 1 Session ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.showAndWait();
    }
     public void goToLogout(MouseEvent event) throws IOException{
        LoginController c = new LoginController(this);
        c.showStage();
        this.thisStage.close();
    }
    
    @FXML
    private void initialize() {
        user=loginController.connectedUser;
        nameMSG.setText(loginController.connectedUser.getUsername());
         bntLogout.setOnMouseClicked(event->{
            try {
                goToLogout(event);
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

    
   
    
}
