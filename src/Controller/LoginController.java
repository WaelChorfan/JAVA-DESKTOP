package Controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import entities.FosUser;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import services.UserService;

public class LoginController {

    @FXML
    private AnchorPane tab;
    @FXML
    private Button btnConnect;
    @FXML
    private Button btnRegister;
    @FXML
    private Button btnClose;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField password;
    @FXML
    private AnchorPane RegisterPane;

    FosUser connectedUser;

    private final Stage thisStage;

    private AdminController adminController;

//    private ClientController clientController;
    private Societe1Controller societe1Controller;
    private ClientController clientController;

    public LoginController() {
        thisStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Login or Register");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //open login page from admin page  ==logout
    LoginController(AdminController a) throws IOException {
        this.adminController = a;
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
        loader.setController(this);
        thisStage.setScene(new Scene(loader.load()));
        thisStage.setTitle("You have logged out ");
    }

//    open login page from client page
    LoginController(ClientController c) throws IOException {
        this.clientController = c;
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
        loader.setController(this);
        thisStage.setScene(new Scene(loader.load()));
        thisStage.setTitle("You have logged out ");
    }

    //open login page from societe 1 page
    LoginController(Societe1Controller s) throws IOException {
        this.societe1Controller = s;
        thisStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Login.fxml"));
        loader.setController(this);
        thisStage.setScene(new Scene(loader.load()));
        thisStage.setTitle("You have logged out ");
    }

    public FosUser getConnectedUser() {
        return connectedUser;
    }

    @FXML
    void GoToRegister(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Register.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Register Now !");
        stage.setScene(new Scene(root1));
        stage.show();
//        this.hide(event);
    }

    public static String mail, pwd;

    @FXML
    public void Connect(ActionEvent event) throws IOException {
        try {
            if (validateInputs()) {
                UserService us = new UserService();
                mail = email.getText();
                pwd = password.getText();
                FosUser fu;

                if (us.checkUser(mail, pwd) != null) {
                    fu = us.checkUser(mail, pwd);
                    connectedUser = fu;
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Welcome");
                    alert.setContentText("Vous êtes connecté " + fu.getUsername());
                    this.hide(event);
                    goTo(fu);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText("Sorry");
                    alert.setContentText("Wrong username or password");

                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validateInputs() throws SQLException {

        if (email.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez saisir votre nom d'utilisateur");
            alert1.setHeaderText("Controle de saisie");
            alert1.show();
            return false;
        }
        if (password.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez saisir votre mot de passe");
            alert1.setHeaderText("Controle de saisie");
            alert1.show();
            return false;
        }
        return true;
    }

    public void goTo(FosUser u) throws IOException {

        System.out.println("---------" + u.getRole());

        switch (u.getRole().toString()) {
            case "ADMIN":
                GoToAdmin();
                break;
            case "CLIENT":
                GoToClient();
                break;
            case "SOCIETE1":
                GoToSociete1();
                break;
        }
    }

    void GoToAdmin() throws IOException {
        AdminController c = new AdminController(this);
        c.showStage();

    }

    void GoToClient() throws IOException {
//        this.thisStage.close();
        ClientController c = new ClientController(this);
        c.showStage();
    }

    void GoToSociete1() throws IOException {
//        this.thisStage.close();
        Societe1Controller c = new Societe1Controller(this);
        c.showStage();
    }

    //publish this stage
    public void showStage() {
        thisStage.show();
    }

    public void hide(ActionEvent event) {
        this.thisStage.close();
    }

    @FXML
    public void initialize() {

        btnConnect.setOnAction(event -> {
            try {
                Connect(event);

            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        btnRegister.setOnAction(event -> {
            try {
                GoToRegister(event);
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
