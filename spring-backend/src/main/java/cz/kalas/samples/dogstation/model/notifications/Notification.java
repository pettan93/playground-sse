package cz.kalas.samples.dogstation.model.notifications;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Notification {

    private final Integer id;

    private final String message;

    private final NotifyType notifyType;

    public Notification(String message) {
        this(message, NotifyType.PLAIN_TEXT);
    }

    public Notification(String message, NotifyType notifyType) {
        this.id = LocalDateTime.now().getNano();
        this.message = message;
        this.notifyType = notifyType;
    }
}
