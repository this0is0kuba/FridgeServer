package pl.edu.agh.FridgeServer.model;

public class SimpleMessage {

    private String info;

    public SimpleMessage() {}

    public SimpleMessage(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "SimpleMessage{" +
                "info='" + info + '\'' +
                '}';
    }
}
