package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.dto.StationDto;
import model.util.Observable;
import model.util.Observer;
import presenter.Presenter;

import java.util.ArrayList;
import java.util.List;

public class StibView extends Application implements Observer,Observable {
    private List<Observer> observers = new ArrayList();
    private StibController stibController;
    private Presenter presenter;

    @Override
    public void start(Stage stage) throws Exception {
        presenter = new Presenter(this);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../model/STIB_Plan.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stibController = fxmlLoader.getController();
        stibController.register(this);
        stage.setTitle("STIB - App");
        stage.getIcons().add(new Image("model/miniLogo.png"));
        stage.setScene(scene);
        stage.show();
    }

    public void displayInformation(List<String> names){
        stibController.displayInformation(names);
    }

    @Override
    public void update(StationDto origine, StationDto destination) {
        for (Observer obs: observers) {
            obs.update(origine,destination);
        }
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

}
