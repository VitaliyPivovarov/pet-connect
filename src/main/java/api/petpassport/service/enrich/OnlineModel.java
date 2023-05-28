package api.petpassport.service.enrich;

import java.util.UUID;

public interface OnlineModel {

    UUID getId();

    boolean isOnline();

    void setOnline(boolean value);
}
