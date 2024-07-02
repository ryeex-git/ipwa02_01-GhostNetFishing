import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import java.io.Serializable;
import java.util.List;

@Named("mapsBean")
@ViewScoped
public class MapsBEAN implements Serializable {

    @Inject
    private EntityManager em;

    @Inject
    private GhostNetDAO ghostNetDAO;

    @Inject
    private PersonDAO personDAO;

    @Inject
    private SightingReportDAO sightingReportDAO;

    private MapModel mapModel;
    private Marker marker;

    @PostConstruct
    public void initializeMap() {
        mapModel = new DefaultMapModel();
        List<GhostNet> ghostNets = ghostNetDAO.findAll();
        for (GhostNet ghostNet : ghostNets) {
            LatLng coord = new LatLng(ghostNet.getLat(), ghostNet.getLng());
            String icon = getMarkerIcon(ghostNet.getStatus());
            mapModel.addOverlay(new Marker(coord, "Geisternetz ID: " + ghostNet.getId() + ", Status: " + ghostNet.getStatus(), null, icon));
        }
    }

    private String getMarkerIcon(GhostNetStatus status) {
        switch (status) {
            case LOST:
                return "http://maps.google.com/mapfiles/ms/icons/red-dot.png";
            case REPORTED:
                return "http://maps.google.com/mapfiles/ms/icons/blue-dot.png";
            case SALVAGED:
                return "http://maps.google.com/mapfiles/ms/icons/green-dot.png";
            case SALVAGE_PENDING:
                return "http://maps.google.com/mapfiles/ms/icons/yellow-dot.png";
            default:
                return "http://maps.google.com/mapfiles/ms/icons/red-dot.png";
        }
    }
}
