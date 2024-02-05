package uaspbo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.apache.commons.validator.routines.EmailValidator;

public class Controller implements Initializable {
    
    @FXML
    private Button hapustbl;
    
    @FXML
    private Button tambahtbl;
    
    @FXML
    private Button edittbl;
    
    @FXML
    private VBox pnItems = null;
    
    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnOverview;

    @FXML
    private Button btnSignout;

    @FXML
    private ComboBox<String> comboantr;

    @FXML
    private RadioButton gndrL;

    @FXML
    private RadioButton gndrP;

    @FXML
    private TextField keluhanantr;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private TableView<DataAntrian> tabelantr;

    @FXML
    private TableColumn<DataAntrian, String> colLayanan;
    
    @FXML
    private TableColumn<DataAntrian, String> colKeluhan;
    
    @FXML
    private TableColumn<DataAntrian, String> colJenisKelamin;
    
    @FXML
    private TableColumn<DataAntrian, String> colUmur;
    
    @FXML
    private Button tmbhakun;
    
    @FXML
    private Button hpsakun;
    
    @FXML
    private Button editakun;
    
    @FXML
    private TableView<UserData> tblakun;

    @FXML
    private TableColumn<UserData, String> tblemail;

    @FXML
    private TableColumn<UserData, String> tblusername;

    @FXML
    private TableColumn<UserData, String> tblpass;

    @FXML
    private TextField txtemail;
    
    @FXML
    private TextField txtusername;

    @FXML
    private TextField txtpassword;

    @FXML
    private TextField umur;

    @FXML
    private Label username;
    
    public class UserData {
    private String email;
    private String username;
    private String password;

    public UserData(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

    
    public class DataAntrian {
    private String layanan;
    private String keluhan;
    private String jenisKelamin;
    private String umur;

    public DataAntrian(String layanan, String keluhan, String jenisKelamin, String umur) {
        this.layanan = layanan;
        this.keluhan = keluhan;
        this.jenisKelamin = jenisKelamin;
        this.umur = umur;
    }

    public String getLayanan() {
        return layanan;
    }

    public void setLayanan(String layanan) {
        this.layanan = layanan;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }
}
    
    @FXML
    void loadUserData(ActionEvent event) {
        try {
            Connection connection = database.connectDb();
            String query = "SELECT * FROM admin";
            ResultSet resultSet = connection.createStatement().executeQuery(query);

            tblakun.getItems().clear();

            while (resultSet.next()) {
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");

                tblakun.getItems().add(new UserData(email, username, password));
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void tambahAkun(ActionEvent event) {
        try {
            Connection connection = database.connectDb();
            String email = txtemail.getText();
            String username = txtusername.getText();
            String password = txtpassword.getText();
            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                showAlert("Isi Teks Field Yang Kosong");
                return;
            }
            if (!email()) {
                return;
            }
            if (password.length() < 5) {
                showAlert("Password tidak boleh kurang dari 5 karakter");
                return;
            }
            String insertQuery = "INSERT INTO admin (email, username, password) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);
                preparedStatement.executeUpdate();
            }
            tblakun.getItems().add(new UserData(email, username, password));
            txtemail.clear();
            txtusername.clear();
            txtpassword.clear();
            connection.close();
            showInfo("Akun Baru Dibuat");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void showInfo(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean email() {
        String email = txtemail.getText();
        if (EmailValidator.getInstance().isValid(email)) {
            return true;
        } else {
            showAlert("Email Tidak Tepat");
            return false;
        }
    }

    @FXML
    void hapusAkun(ActionEvent event) {
        int selectedIndex = tblakun.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                Connection connection = database.connectDb();
                UserData selectedUser = tblakun.getItems().get(selectedIndex);
                String deleteQuery = "DELETE FROM admin WHERE email = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                    preparedStatement.setString(1, selectedUser.getEmail());
                    preparedStatement.executeUpdate();
                }
                tblakun.getItems().remove(selectedIndex);
                connection.close();
                showInfo("Akun Telah Dihapus");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void editAkun(ActionEvent event) {
        int selectedIndex = tblakun.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                Connection connection = database.connectDb();

                UserData selectedUser = tblakun.getItems().get(selectedIndex);

                String updateQuery = "UPDATE admin SET email = ?, username = ?, password = ? WHERE email = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setString(1, txtemail.getText());
                    preparedStatement.setString(2, txtusername.getText());
                    preparedStatement.setString(3, txtpassword.getText());
                    preparedStatement.setString(4, selectedUser.getEmail());
                    preparedStatement.executeUpdate();
                }

                selectedUser.setEmail(txtemail.getText());
                selectedUser.setUsername(txtusername.getText());
                selectedUser.setPassword(txtpassword.getText());

                tblakun.refresh();

                txtemail.clear();
                txtusername.clear();
                txtpassword.clear();

                connection.close();
                showInfo("Akun Berhasil Dirubah");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    

    
    @FXML
    void displaySelectedUser() {
        int selectedIndex = tblakun.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            UserData selectedUser = tblakun.getItems().get(selectedIndex);

            txtemail.setText(selectedUser.getEmail());
            txtusername.setText(selectedUser.getUsername());
            txtpassword.setText(selectedUser.getPassword());
        }
    }
    
    @FXML
    void tambahTabel(ActionEvent event) {
        String layanan = comboantr.getValue();
        String keluhan = keluhanantr.getText();
        String jenisKelamin = gndrL.isSelected() ? "Laki-Laki" : "Perempuan";
        String umurPasien = umur.getText();
        tabelantr.getItems().add(new DataAntrian(layanan, keluhan, jenisKelamin, umurPasien));
        comboantr.setValue(null);
        keluhanantr.clear();
        gndrL.setSelected(false);
        gndrP.setSelected(false);
        umur.clear();
    }

    @FXML
    void hapusTabel(ActionEvent event) {
        int selectedIndex = tabelantr.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tabelantr.getItems().remove(selectedIndex);
        }
    }

    @FXML
    void editTabel(ActionEvent event) {
    String layanan = comboantr.getValue();
    String keluhan = keluhanantr.getText();
    String jenisKelamin = gndrL.isSelected() ? "Laki-Laki" : "Perempuan";
    String umurPasien = umur.getText();
    DataAntrian selectedRow = tabelantr.getSelectionModel().getSelectedItem();
    if (selectedRow != null) {
        selectedRow.setLayanan(layanan);
        selectedRow.setKeluhan(keluhan);
        selectedRow.setJenisKelamin(jenisKelamin);
        selectedRow.setUmur(umurPasien);
        tabelantr.refresh();
        comboantr.setValue(null);
        keluhanantr.clear();
        gndrL.setSelected(false);
        gndrP.setSelected(false);
        umur.clear();
    }
}
    
    @FXML
    void signout(ActionEvent event) {
        try{
            btnSignout.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (Exception e) {
        e.printStackTrace();
    }
    }
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tabelantr.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            comboantr.setValue(newSelection.getLayanan());
            keluhanantr.setText(newSelection.getKeluhan());
            if (newSelection.getJenisKelamin().equals("Laki-Laki")) {
                gndrL.setSelected(true);
                gndrP.setSelected(false);
            } else {
                gndrL.setSelected(false);
                gndrP.setSelected(true);
            }
            umur.setText(newSelection.getUmur());
        }
    });
        tblakun.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            displaySelectedUser();
        }
    });
        
        tblemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        tblusername.setCellValueFactory(new PropertyValueFactory<>("username"));
        tblpass.setCellValueFactory(new PropertyValueFactory<>("password"));
        loadUserData(null);
        
        colLayanan.setCellValueFactory(new PropertyValueFactory<>("layanan"));
        colKeluhan.setCellValueFactory(new PropertyValueFactory<>("keluhan"));
        colJenisKelamin.setCellValueFactory(new PropertyValueFactory<>("jenisKelamin"));
        colUmur.setCellValueFactory(new PropertyValueFactory<>("umur"));

        colJenisKelamin.setCellFactory(TextFieldTableCell.forTableColumn());
        
        ToggleGroup genderToggleGroup = new ToggleGroup();
        gndrL.setToggleGroup(genderToggleGroup);
        gndrP.setToggleGroup(genderToggleGroup);
        
        ArrayList<String> layananList = new ArrayList<>();
        layananList.add("Pemeriksaan Umum");
        layananList.add("Imunisasi");
        layananList.add("Anak");
        layananList.add("Kesehatan Haji");
        layananList.add("Laboratorium");
        layananList.add("Papilus");
        layananList.add("Lansia");
        layananList.add("KIR KPPS");
        layananList.add("Gigi Dan Mulut");
        layananList.add("Kesehatan Mental");

        comboantr.getItems().addAll(layananList);
    }

    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.setStyle("-fx-background-color: #FFFFFF");
            pnlOverview.toFront();
        }
        if (actionEvent.getSource()==btnOrders)
        {
            pnlOrders.toFront();
        }
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.setStyle("-fx-background-color: #FFFFFF");
            pnlCustomer.toFront();
        }
    }
}