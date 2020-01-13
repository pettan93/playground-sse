package cz.kalas.samples.dogstation.events;

import cz.kalas.samples.dogstation.model.DogStationState;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
@EqualsAndHashCode(callSuper = true)
public class StateChangeEvent extends ApplicationEvent {

    private DogStationState dogStationState;

    public StateChangeEvent(Object source, DogStationState dogStationState) {
        super(source);
        this.dogStationState = dogStationState;
    }
}
