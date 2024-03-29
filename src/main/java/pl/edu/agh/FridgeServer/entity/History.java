package pl.edu.agh.FridgeServer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

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
    private Double temp;

    @Column(name = "closed_door")
    private Boolean closedDoor;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "date")
    private LocalDateTime date;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "device_id")
    private Device device;

    public History() {}

    public History(Double temp, Boolean closedDoor, LocalDateTime date) {
        this.temp = temp;
        this.closedDoor = closedDoor;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Boolean getClosedDoor() {
        return closedDoor;
    }

    public void setClosedDoor(Boolean closedDoor) {
        this.closedDoor = closedDoor;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
                ", closedDoor=" + closedDoor +
                ", date=" + date +
                '}';
    }
}
