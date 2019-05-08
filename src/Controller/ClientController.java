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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ClientController {

    @FXML
    private Button btnTickets;

    @FXML
    private Button btnProds;

    @FXML
    private Button btnMyProds;

    @FXML
    private Button btnLogout;

    @FXML
    private Text id;

    @FXML
    private Label nameMSG;

    @FXML
    private ImageView imgId;

    private final Stage thisStage;
    private final LoginController loginController;

    FosUser connectedUserClient;

    public ClientController(LoginController loginController) throws IOException {
        this.loginController = loginController;
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Client.fxml"));
        loader.setController(this);
        thisStage.setScene(new Scene(loader.load()));
        thisStage.setTitle("Client Page");
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
        TicketsClientController tac = new TicketsClientController(this);
        tac.showStage();

    }

    private void goToListProduits(ActionEvent event) throws IOException {
        ProdsListController p = new ProdsListController();
        p.showStage();
    }

    private void goToMyProds(ActionEvent value) throws IOException {
        MesProduitsController mc = new MesProduitsController(this);
        mc.showStage();

    }

    @FXML
    public void initialize() {

        connectedUserClient = loginController.connectedUser;
        nameMSG.setText(loginController.connectedUser.getUsername());
        String i = loginController.connectedUser.getImageName();
        String imToDisplay = ((i == null) || i.equals("")) ? "0.png" : i;
        
        System.out.println("image name is ? "+imToDisplay);
        
        
        Image im = new Image("/images_users/" + imToDisplay);
        imgId.setImage(im);

        btnLogout.setOnAction(event -> {
            try {
                goToLogout(event);
            } catch (IOException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
//
//        
////        
        btnTickets.setOnAction(event -> {
            try {
                goToTickets(event);
            } catch (IOException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnProds.setOnAction(value -> {
            try {
                goToListProduits(value);
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnMyProds.setOnAction(value -> {
            try {
                goToMyProds(value);
            } catch (IOException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }

}
