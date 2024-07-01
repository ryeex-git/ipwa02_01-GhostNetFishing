import jakarta.persistence.*;
import jakarta.validation.constraints.Past;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class SightingReport  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "ghostnet_ghostnet_id", nullable = false)
    private GhostNet netId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_person_id")
    private Person personId;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime reportTime;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime sightingTime;

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GhostNet getNetId() {
        return netId;
    }

    public void setNetId(GhostNet netId) {
        this.netId = netId;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public LocalDateTime getReportTime() {
        return reportTime;
    }

    public void setReportTime(LocalDateTime reportTime) {
        this.reportTime = reportTime;
    }

    public LocalDateTime getSightingTime() {
        return sightingTime;
    }

    public void setSightingTime(LocalDateTime sightingTime) {
        this.sightingTime = sightingTime;
    }

    public SightingReport() {}

    public SightingReport(GhostNet netId, Person personId, LocalDateTime reportTime, LocalDateTime sightingTime) {
        this.netId = netId;
        this.personId = personId;
        this.reportTime = reportTime;
        this.sightingTime = sightingTime;
    }
}
