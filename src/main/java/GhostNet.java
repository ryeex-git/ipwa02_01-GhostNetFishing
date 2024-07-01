import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class GhostNet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double lat;
    private double lng;

    private int size;
    @Enumerated(EnumType.STRING)
    private GhostNetStatus status;

    // Getter und Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public GhostNetStatus  getStatus() {
        return status;
    }

    public void setStatus(GhostNetStatus  status) {
        this.status = status;
    }

    public GhostNet() {}

    public GhostNet(double lat, double lng, int size, GhostNetStatus status) {
        this.lat = lat;
        this.lng = lng;
        this.size = size;
        this.status = status;
    }
}

