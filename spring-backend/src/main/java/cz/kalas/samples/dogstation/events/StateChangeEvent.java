package cz.kalas.samples.dogstation.events;

import cz.kalas.samples.dogstation.dog.DogStationState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

@Data
@EqualsAndHashCode(callSuper = true)
public class StateChangeEvent extends ApplicationEvent {

    private DogStationState dogStationState;

    public StateChangeEvent(Object source, DogStationState dogStationState) {
        super(source);
        this.dogStationState = dogStationState;
    }
}
