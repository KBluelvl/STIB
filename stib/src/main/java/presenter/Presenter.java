package presenter;

import model.dto.StationDto;
import model.exception.RepositoryException;
import model.util.Observer;
import view.StibView;
import model.Model;

import java.util.List;

public class Presenter implements Observer {
    private static Model model;
    private static StibView view;

    public Presenter(StibView view) {
        try {
            this.view = view;
            this.view.register(this);
            model = new Model();
        } catch (Exception e){
            System.out.println("Exception in main : "+e.getMessage());
        }
    }

    @Override
    public void update(StationDto origine, StationDto destination) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    List<String> names = model.search(origine, destination);
                    view.displayInformation(names);
                } catch (RepositoryException e){
                    System.out.println("search failed");
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
