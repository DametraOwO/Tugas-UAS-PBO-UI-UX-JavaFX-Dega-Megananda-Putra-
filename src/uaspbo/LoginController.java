/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uaspbo;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;
/**
 *
 * @author DametraOwO
 */
public class LoginController implements Initializable{
    @FXML
    private ImageView logo_pass;

    @FXML
    private ImageView logo_password;

    @FXML
    private ImageView logo_user;

    @FXML
    private ImageView logo_username;

    @FXML
    private ImageView sigin_dokter;

    @FXML
    private Button signUp_btn;

    @FXML
    private ImageView signUp_dokter;

    @FXML
    private TextField signUp_email;

    @FXML
    private AnchorPane signUp_form;

    @FXML
    private Hyperlink signUp_login;

    @FXML
    private ImageView signUp_logo;

    @FXML
    private PasswordField signUp_password;

    @FXML
    private TextField signUp_username;

    @FXML
    private Hyperlink signin_createAccount;

    @FXML
    private AnchorPane signin_form;

    @FXML
    private Button signin_loginBtn;

    @FXML
    private ImageView signin_logo;

    @FXML
    private PasswordField signin_password;

    @FXML
    private TextField signin_username;
    
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;

    public boolean email() {
        String email = signUp_email.getText();
        if (EmailValidator.getInstance().isValid(email)) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Email Tidak Tepat");
            alert.showAndWait();
            return false;
        }
    }
    
    @FXML
    void signin(ActionEvent event) {
    String sql = "SELECT * FROM admin WHERE username = ? and password = ?";
    connect = database.connectDb();
    try {
        prepare = connect.prepareStatement(sql);
        prepare.setString(1, signin_username.getText());
        prepare.setString(2, signin_password.getText());
        result = prepare.executeQuery();
        Alert alert;
        if (signin_username.getText().isEmpty() || signin_password.getText().isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setContentText("Isi Teks Field Yang Kosong");
            alert.showAndWait();
        } else {
            if (result.next()) {
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Berhasil Login");
                alert.showAndWait();
                signin_loginBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Salah Username/Password");
                alert.showAndWait();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @FXML
    void signup(ActionEvent event) {
    String sql = "INSERT INTO admin (email, username, password) VALUES (?,?,?)";
    connect = database.connectDb();
    try {
        prepare = connect.prepareStatement(sql);
        if (!email()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Email Tidak Tepat");
            alert.showAndWait();
            return;
        }
        String password = signUp_password.getText();
        if (password.length() < 5) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Password tidak boleh kurang dari 5 karakter");
            alert.showAndWait();
            return;
        }
        prepare.setString(1, signUp_email.getText());
        prepare.setString(2, signUp_username.getText());
        prepare.setString(3, password);
        Alert alert;
        if (signUp_username.getText().isEmpty() || password.isEmpty()) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Isi Teks Field Yang Kosong");
            alert.showAndWait();
        } else {
            prepare.execute();
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Akun Baru Dibuat");
            alert.showAndWait();
            signUp_email.setText("");
            signUp_username.setText("");
            signUp_password.setText("");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
    void switchForm(ActionEvent event) {
        if(event.getSource()==signin_createAccount){
            signin_form.setVisible(false);
            signUp_form.setVisible(true);
        } else if(event.getSource()==signUp_login){
            signin_form.setVisible(true);
            signUp_form.setVisible(false);
        }
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {  
    }   
}