package security;

import model.Operation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UrlCache implements Serializable {
    private Map<String, List<String>> urlCaches = new HashMap<>();

    public UrlCache(List<Operation> operations) {
        for (Operation op : operations) {
            urlCaches.put(String.format("/%s/%s/", op.getController(), op.getAction()),
                    op.getRoles().stream().map(t -> t.getName()).collect(Collectors.toList()));
        }
    }

    public Map<String, List<String>> getUrlCaches() {
        return urlCaches;
    }
}
