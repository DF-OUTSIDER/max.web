package common;

import java.io.Serializable;

public class JsonResponse<T> implements Serializable {
    private Boolean success;
    private T data;

    public JsonResponse(Boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
