package model.util;


/**
 * Represents an observable interface.
 * @author Florian Essomba
 */
public interface Observable {

    /**
     * Register an observer.
     * @param observer a given observer.
     */
    void register(Observer observer);

}
