package xyz.cupscoffee.coffeefiles.api;

import java.util.Date;
import java.util.HashMap;

public interface File {
    String getName();
    Date getCreatedDate();
    Date getLastModifiedDate();
    long getSize();
    HashMap<String, Object> getMeta();
}
