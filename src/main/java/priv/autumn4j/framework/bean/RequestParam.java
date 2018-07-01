package priv.autumn4j.framework.bean;

import java.util.Map;

public class RequestParam {

    private Map<String, Object> paramMap;

    public RequestParam(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public Long getLong(String name) {
        return (Long) paramMap.get(name);
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
