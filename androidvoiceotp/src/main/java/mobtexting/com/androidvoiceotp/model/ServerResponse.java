package mobtexting.com.androidvoiceotp.model;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private boolean status;
    @SerializedName("response_code")
    private int response_code;

    public ServerResponse(String message, boolean status, int response_code) {
        this.message = message;
        this.status = status;
        this.response_code = response_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }
}
