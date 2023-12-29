package pl.edu.agh.FridgeServer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
public class History {

    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name = "history_id_sequence",
            sequenceName = "history_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "history_id_sequence"
    )
    private int id;

    @Column(name = "temperature")
    @Min(-100)
    @Max(200)
    private double temp;

    @Column(name = "humidity")
    @Min(0)
    @Max(100)
    private double humidity;

    @Column(name = "open_door")
    @NotNull(message = "is required")
    private boolean openDoor;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "date")
    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "device_id")
    private Device device;

    public History() {}

    public History(double temp, double humidity, boolean openDoor) {
        this.temp = temp;
        this.humidity = humidity;
        this.openDoor = openDoor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public boolean isOpenDoor() {
        return openDoor;
    }

    public void setOpenDoor(boolean openDoor) {
        this.openDoor = openDoor;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", temp=" + temp +
                ", humidity=" + humidity +
                ", openDoor=" + openDoor +
                '}';
    }
}
