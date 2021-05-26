package controller;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;

public class Controller {
    private Service serv;
    private User logged;

    @FXML
    Button loginButton, addCodeButton, addBugButton, updateBugButton, removeBugButton;
    @FXML
    TabPane mainPane;
    @FXML
    Tab loginTab, programmerTab, reviewerTab;

    public void setServ(Service serv) {
        this.serv = serv;
    }

    public void setLogged(User logged) {
        this.logged = logged;
    }


    @FXML
    TextField titleCBox,descCBox;
    @FXML
    Spinner<Integer> linesCBox,hoursCBox;
    @FXML
    TextField nameVBox,descVBox;

    @FXML
    public void initialize() {
        switchTabs(0);
        loginButton.setOnAction(e -> {
            try {
                handleLogin();
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING, ex.toString());
                alert.show();
            }
        });
        addCodeButton.setOnAction(e -> {
            try{
                this.serv.RegisterCode(titleCBox.getText(),descCBox.getText(), linesCBox.getValue(), hoursCBox.getValue(), (Programmer) this.logged);
                new Alert(Alert.AlertType.INFORMATION,"Code registered successfully!").show();
                fillProgrammerBugTable();
                clearSelectionsAndFields();
            }catch (Exception ex){
                new Alert(Alert.AlertType.WARNING,ex.toString()).show();
            }
        });
        addBugButton.setOnAction(e ->{
            try{
                this.serv.RegisterBug(nameVBox.getText(),descVBox.getText(),vCodeTable.getSelectionModel().getSelectedItem(),(Verifier)this.logged);
                new Alert(Alert.AlertType.INFORMATION,"Bug registered successfully!").show();
                fillVerifierBugTable();
                clearSelectionsAndFields();
            }catch (NullPointerException n){
                new Alert(Alert.AlertType.WARNING,"Select a bug to proceed registering!").show();
            }catch (Exception ex){
                new Alert(Alert.AlertType.WARNING,ex.toString()).show();
            }
        });
        updateBugButton.setOnAction(e ->{
            try{
                this.serv.UpdateBugStatus(vBugTable.getSelectionModel().getSelectedItem());
                new Alert(Alert.AlertType.INFORMATION,"Bug status update successfully!").show();
                fillVerifierBugTable();
                clearSelectionsAndFields();
            }catch (NullPointerException n){
              new Alert(Alert.AlertType.WARNING,"Select a bug to proceed updating!").show();
            } catch (Exception ex){
                new Alert(Alert.AlertType.WARNING,ex.toString()).show();
            }
        });
        removeBugButton.setOnAction(e ->{
            try{
                this.serv.RemoveBug(vBugTable.getSelectionModel().getSelectedItem());
                new Alert(Alert.AlertType.INFORMATION,"Bug removed forever!").show();
                fillVerifierBugTable();
                clearSelectionsAndFields();
            }catch (NullPointerException n){
                new Alert(Alert.AlertType.WARNING,"Select a bug to proceed removing!").show();
            }catch (Exception ex){
                new Alert(Alert.AlertType.WARNING,ex.toString()).show();
            }
        });
    }

    private void clearSelectionsAndFields(){
        titleCBox.clear();
        descCBox.clear();
        linesCBox.getValueFactory().setValue(100);
        hoursCBox.getValueFactory().setValue(0);
        nameVBox.clear();
        descVBox.clear();
        vBugTable.getSelectionModel().clearSelection();
        vCodeTable.getSelectionModel().clearSelection();
    }

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label progNameLBL,verifNameLBL;

    private void handleLogin() throws Exception {
        String username = usernameField.getText().strip();
        String password = passwordField.getText().strip();
        this.setLogged(this.serv.GetLogger(username, password));
        if (this.logged != null)
            if (this.logged instanceof Programmer) {
                new Alert(Alert.AlertType.INFORMATION,"Welcome, programmer!").show();
                fillProgrammerBugTable();
                switchTabs(1);
            } else {
                new Alert(Alert.AlertType.INFORMATION,"Welcome, reviewer!").show();
                fillVerifierCodeTable();
                fillVerifierBugTable();
                switchTabs(2);
            }
    }

    public void switchTabs(Integer tabIndex) {
        SingleSelectionModel<Tab> selectionModel = mainPane.getSelectionModel();
        switch (tabIndex) {
            case 0 -> {
                loginTab.setDisable(false);
                programmerTab.setDisable(true);
                reviewerTab.setDisable(true);
            }
            case 1 -> {
                loginTab.setDisable(true);
                programmerTab.setDisable(false);
                reviewerTab.setDisable(true);
                progNameLBL.setText("Welcome back, " + this.logged.getFullname() + "!");
            }
            case 2 -> {
                loginTab.setDisable(true);
                programmerTab.setDisable(true);
                reviewerTab.setDisable(false);
                verifNameLBL.setText("Welcome back, " + this.logged.getFullname() + "!");
            }
        }
        selectionModel.select(tabIndex);
    }

    @FXML
    TableView<Bug> pBugsTable;
    @FXML
    TableColumn<Bug,Long> pColID;
    @FXML
    TableColumn<Bug,String> pColName,pColDesc;
    @FXML
    TableColumn<Bug,Boolean> pColStatus;
    @FXML
    TableColumn<Bug,Integer> pColProp;

    ObservableList<Bug> pBugList = FXCollections.observableArrayList();

    public void fillProgrammerBugTable(){
        Programmer programmer = (Programmer)this.logged;

        pColDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        pColID.setCellValueFactory(new PropertyValueFactory<>("id"));
        pColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        pColProp.setCellValueFactory(new PropertyValueFactory<>("resolvingPercent"));
        pColStatus.setCellValueFactory(new PropertyValueFactory<>("isResolved"));

        pBugList.setAll(this.serv.GetProgrammerBugs(programmer.getId()));
        pBugsTable.setItems(pBugList);
    }

    @FXML
    TableView<Code> vCodeTable;
    @FXML
    TableColumn<Code,String> vColTitle,vColDescC;
    @FXML
    TableColumn<Code,Integer> vColLines,vColHours;

    ObservableList<Code> vCodeList = FXCollections.observableArrayList();

    public void fillVerifierCodeTable(){
        vColTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        vColDescC.setCellValueFactory(new PropertyValueFactory<>("description"));
        vColLines.setCellValueFactory(new PropertyValueFactory<>("noLines"));
        vColHours.setCellValueFactory(new PropertyValueFactory<>("hoursWorked"));

        vCodeList.clear();
        this.serv.ViewAllCodes().forEach(vCodeList::add);
        vCodeTable.setItems(vCodeList);
    }

    @FXML
    TableView<Bug> vBugTable;
    @FXML
    TableColumn<Bug,Long> vColID;
    @FXML
    TableColumn<Bug,String> vColName,vColDescB;
    @FXML
    TableColumn<Bug,Boolean> vColStatus;
    @FXML
    TableColumn<Bug,Integer> vColProp;

    ObservableList<Bug> vBugList = FXCollections.observableArrayList();

    public void fillVerifierBugTable(){
        Verifier verifier = (Verifier)this.logged;

        vColDescB.setCellValueFactory(new PropertyValueFactory<>("description"));
        vColID.setCellValueFactory(new PropertyValueFactory<>("id"));
        vColName.setCellValueFactory(new PropertyValueFactory<>("name"));
        vColProp.setCellValueFactory(new PropertyValueFactory<>("resolvingPercent"));
        vColStatus.setCellValueFactory(new PropertyValueFactory<>("isResolved"));

        vBugList.setAll(this.serv.GetVerifierRegisteredBugs(verifier.getId()));
        vBugTable.setItems(vBugList);

    }
}
