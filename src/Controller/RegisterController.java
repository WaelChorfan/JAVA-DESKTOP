package Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import entities.FosUser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import services.UserService;

public class RegisterController implements Initializable {

    @FXML
    private AnchorPane registrate;

    @FXML
    private Button selct;

    @FXML
    private ImageView imajout1;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private JFXTextField txtCemail;

    @FXML
    private JFXTextField txtPwd;

    @FXML
    private ComboBox<String> cbRoles;
    ObservableList<String> rolesOL = FXCollections.observableArrayList("CLIENT", "SOCIETE1", "SOCIETE2");

    @FXML
    private CheckBox termsBox;

    @FXML
    private Button btnConnect;

    @FXML
    private Button btnConnect1;
    
    @FXML
    private JFXButton btnImage;

    @FXML
    private ImageView imgId;

  

    @FXML
    private Label imgNameLabel;
    
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
        fileChoser.setTitle("Sélectionnez un avatar ");
        fileChoser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.bmp", "*.jpeg", "*.gif")
        );
        ;
//          

        File file = fileChoser.showOpenDialog(theStage);
        if (file != null) {
            
            Image im = new Image("file:///" + file.toPath().toString());
            
            
            imgNameLabel.setText(file.getName());
            String p="C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\NetBeansProjects\\PIjava\\src\\images_users\\"+imgNameLabel.getText();
            
            
            
            File file2  = new File(p);
            
            System.out.println("1"+file);
            System.out.println("2"+file2);
            copyFileUsingChannel(file, file2);
            

        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cbRoles.setItems(rolesOL);
        
            btnImage.setOnAction(event -> {
            try {
                loadImage(event);
            } catch (IOException ex) {
                Logger.getLogger(RegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    void createAccount(MouseEvent event) {
        if (validateInputs()) {
            FosUser user = new FosUser(
                    txtUsername.getText(),
                    txtEmail.getText(),
                    txtPwd.getText(),
                    cbRoles.getValue(),
                    imgNameLabel.getText()
            );
        
            UserService us = new UserService();
            
            us.ajouterUser(user);
            

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(user.getUsername() + "  a été enregistré avec succés");
            alert.show();

        }
    }

    @FXML
    void resetForm(MouseEvent event) {
        System.out.println("Controller.RegisterController.resetForm()");
        txtUsername.setText("");
        txtEmail.setText("");
        txtPwd.setText("");
        txtEmail.setText("");
        txtCemail.setText("");
    }

    private boolean validateInputs() {
        if ((txtUsername.getText().isEmpty()) || (txtEmail.getText().isEmpty())
                || (txtCemail.getText().isEmpty()) || (txtPwd.getText().isEmpty())
                || (!termsBox.isSelected())) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veillez remplir tout les champs");
            alert1.setHeaderText(null);
            alert1.show();
            return false;

        } else if (!(validate(txtEmail.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veillez vérifier votre email");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }
        return true;
    }

    //email validation
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}
