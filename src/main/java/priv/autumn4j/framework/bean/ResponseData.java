package priv.autumn4j.framework.bean;

/**
 * 返回数据对象
 */
public class ResponseData {

    /**
     * 数据模型
     */
    private Object object;

    public ResponseData(Object object) {
        this.object = object;
    }

    public Object getModel() {
        return object;
    }
}
