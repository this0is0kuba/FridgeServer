package pl.edu.agh.FridgeServer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name = "device_id_sequence",
            sequenceName = "device_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "device_id_sequence"
    )
    private int id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(
            mappedBy = "device",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
    )
    private List<History> history;

    public Device() {}

    public Device(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    public void addHistory(History theHistory) {

        if(theHistory == null)
            return;

        if(this.history == null)
            this.history = new ArrayList<>();

        this.history.add(theHistory);
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", startDate=" + startDate +
                '}';
    }
}
