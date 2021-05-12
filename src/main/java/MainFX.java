import controller.Controller;
import domain.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import repository.generic.*;
import repository.hibernate.*;
import service.Service;

import java.io.IOException;

public class MainFX extends Application {
    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("window.fxml"));

        Parent root=loader.load();
//        Parent root=loader.getRoot();

        Controller ctrl = loader.getController();
        ctrl.setServ(GetService());

        primaryStage.setTitle("Buggers");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private Service GetService(){
        AccountRepo accountRepo = new AccountHbmRepo();
        VerifierRepo verifierRepo = new VerifierHbmRepo();
        ProgrammerRepo programmerRepo = new ProgrammerHbmRepo();
        CodeRepo codeRepo = new CodeHbmRepo();
        BugRepo bugRepo = new BugHbmRepo();
        AnalyzeRepo analyzeRepo = new AnalyzeHbmRepo();
        return new Service(accountRepo,programmerRepo,verifierRepo,codeRepo,bugRepo,analyzeRepo);
    }
}
