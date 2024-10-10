package model.util;

import model.dto.StationDto;

/**
 * Represents an observer interface.
 * @author Florian Essomba
 */
public interface Observer {

    /**
     * Update the observer.
     */
    void update(StationDto origine, StationDto destination);
}
