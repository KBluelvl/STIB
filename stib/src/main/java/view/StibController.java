package view;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.dto.FavoriDto;
import model.repository.FavoriRepository;
import model.util.Observable;
import model.util.Observer;
import config.ConfigManager;
import javafx.fxml.FXML;
import javafx.util.StringConverter;
import model.dto.StationDto;
import model.repository.StationRepository;
import org.controlsfx.control.SearchableComboBox;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StibController implements Observable {
    @FXML
    private SearchableComboBox<StationDto> origineSCB;
    @FXML
    private SearchableComboBox<StationDto> destionationSCB;
    @FXML
    private Button button;
    @FXML
    private TableColumn<StationDto,String> stationTC;
    @FXML
    private TableColumn<StationDto,String> lignesTC;
    @FXML
    private TableView<StationDto> tableMain;
    private List<Observer> observers = new ArrayList();
    private Map<String, StationDto> map;
    @FXML
    private RadioButton frenchRB;
    @FXML
    private RadioButton dutchRB;
    @FXML
    private MenuItem closeMI;
    @FXML
    private MenuItem saveMI;
    @FXML
    private MenuItem loadMI;
    @FXML
    private MenuItem deleteMI;

    public void initialize() {
        addItemsInSearchableComboBox();
        initOrigineSCB("french");
        initDestionationSCB("french");
        initButton();
        initStationTC();
        initLignesTC();
        ToggleGroup toggleGroup = new ToggleGroup();
        frenchRB.selectedProperty().set(true);
        initDutchRB(toggleGroup);
        initFrenchRB(toggleGroup);
        initCloseMI();
        initSaveMI();
        initLoadMI();
        initDeleteMI();
    }

    public void initButton(){
        button.setOnMouseClicked(mouseEvent -> {
            notifyObservers();
        });
    }

    private void addItemsInSearchableComboBox(){
        map = new HashMap<>();
        try {
            ConfigManager.getInstance().load();
            StationRepository stationRepository = new StationRepository();
            map = stationRepository.getAll();
            for (Map.Entry e: map.entrySet()) {
                StationDto dto = (StationDto) e.getValue();
                origineSCB.getItems().add(dto);
                destionationSCB.getItems().add(dto);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void initOrigineSCB(String language){
        origineSCB.setConverter(new StringConverter<StationDto>() {
            @Override
            public String toString(StationDto dto) {
                if (dto != null) {
                    if(language.equals("french")) {
                        return dto.getNom();
                    } else if (language.equals("dutch")) {
                        return dto.getNomNl();
                    } else{
                        return ""+dto.getKey();
                    }
                } else {
                    return "";
                }

            }

            @Override
            public StationDto fromString(String nom) {
                return null;
            }
        });

    }

    private void initDestionationSCB(String language){
        destionationSCB.setConverter(new StringConverter<StationDto>() {
            @Override
            public String toString(StationDto dto) {
                if (dto != null) {
                    if(language.equals("french")) {
                        return dto.getNom();
                    } else if (language.equals("dutch")) {
                        return dto.getNomNl();
                    } else{
                        return ""+dto.getKey();
                    }
                } else {
                    return "";
                }
            }

            @Override
            public StationDto fromString(String nom) {
                return null;
            }
        });
    }

    private void initStationTC(){
        stationTC.setCellValueFactory(new PropertyValueFactory<>("Nom"));
    }

    private void initLignesTC(){
        lignesTC.setCellValueFactory(new PropertyValueFactory<>("Line"));
    }

    public void displayInformation(List<String> names){
        tableMain.getItems().clear();
        for (String s: names) {
            StationDto stationDto = map.get(s);
            tableMain.getItems().add(stationDto);
        }
    }

    private void initFrenchRB(ToggleGroup toggleGroup){
        frenchRB.setToggleGroup(toggleGroup);
        frenchRB.onMouseClickedProperty().set(mouseEvent -> {
            initOrigineSCB("french");
            initDestionationSCB("french");
            stationTC.setCellValueFactory(new PropertyValueFactory<>("Nom"));
        });
    }

    private void initDutchRB(ToggleGroup toggleGroup){
        dutchRB.setToggleGroup(toggleGroup);
        dutchRB.onMouseClickedProperty().set(mouseEvent -> {
            initOrigineSCB("dutch");
            initDestionationSCB("dutch");
            stationTC.setCellValueFactory(new PropertyValueFactory<>("NomNl"));
        });
    }

    private void initCloseMI() {
        closeMI.setOnAction(event -> System.exit(0));
    }

    private void initSaveMI() {
        saveMI.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        saveMI.setOnAction(event -> {
            if(origineSCB.getValue() != null && destionationSCB.getValue() != null) {
                askFavoriName();
            }
        });
    }

    private void initLoadMI(){
        loadMI.setOnAction(event -> {
            Button buttonOK = new Button("ok");
            Stage stage = new Stage();
            ComboBox<FavoriDto> favsCB = new ComboBox<>();
            chooseFavori(buttonOK, favsCB, stage);
            buttonOK.setOnAction(buttonClickEvent -> {
                FavoriDto favoriDto = favsCB.getValue();
                origineSCB.valueProperty().set(map.get(favoriDto.getOrigineName()));
                destionationSCB.valueProperty().set(map.get(favoriDto.getDestinationName()));
                stage.close();
            });
            stage.show();
        });
    }

    private void initDeleteMI() {
        deleteMI.setOnAction(event -> {
            Button buttonOK = new Button("ok");
            Stage stage = new Stage();
            ComboBox<FavoriDto> favsCB = new ComboBox<>();
            chooseFavori(buttonOK, favsCB, stage);
            buttonOK.setOnAction(event2 -> {
                FavoriDto favoriDto = favsCB.getValue();
                FavoriRepository favoriRepository = new FavoriRepository();
                favoriRepository.remove(favoriDto);
                stage.close();
            });
            stage.show();
        });
    }

    private void askFavoriName(){
        VBox root = new VBox();
        Label label = new Label("Nom du favori:");
        TextField textField = new TextField();
        Button buttonOK = new Button("ok");
        root.getChildren().add(label);
        root.getChildren().add(textField);
        root.getChildren().add(buttonOK);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("STIB - Favori");
        stage.getIcons().add(new Image("model/miniLogo.png"));
        stage.setScene(scene);
        stage.show();
        buttonOK.setOnAction(event1 -> {
            if(!textField.getText().isBlank()){
                FavoriDto favoriDto = new FavoriDto(textField.getText(),
                        origineSCB.getValue(), destionationSCB.getValue());
                FavoriRepository favoriRepository = new FavoriRepository();
                favoriRepository.add(favoriDto);
                stage.close();
            }
        });
    }

    private void chooseFavori(Button buttonOK, ComboBox<FavoriDto> favsCB,Stage stage) {
        VBox root = new VBox();
        Label titre = new Label("Choisisez un favori:");
        favsCB.setConverter(new StringConverter<FavoriDto>() {
            @Override
            public String toString(FavoriDto favoriDto) {
                if(favoriDto != null) {
                    return favoriDto.getNom();
                } return "";
            }

            @Override
            public FavoriDto fromString(String s) {
                return null;
            }
        });
        root.getChildren().add(titre);
        root.getChildren().add(favsCB);
        root.getChildren().add(buttonOK);
        FavoriRepository favoriRepository = new FavoriRepository();
        try {
            Map<String,FavoriDto> favoriDtoMap = favoriRepository.getAll();
            for (Map.Entry<String,FavoriDto> e : favoriDtoMap.entrySet()) {
                favsCB.getItems().add(e.getValue());
            }
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer obs: observers) {
            obs.update(origineSCB.getValue(),destionationSCB.getValue());
        }
    }
}
