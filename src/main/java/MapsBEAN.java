import jakarta.annotation.PostConstruct;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Named("mapsBean")
@ViewScoped
public class MapsBEAN implements Serializable {


    private GhostNetStatus ghostNetStatus;
    @Inject
    private EntityManager em;
    @Inject
    private GhostNetDAO ghostNetDAO;
    @Inject
    private PersonDAO personDAO;
    @Inject
    private SightingReportDAO sightingReportDAO;
    private MapModel mapModel = new DefaultMapModel();
    private Marker marker;
    private boolean successfulSalvageReport;
    private boolean plannedSalvageReport;
    private boolean reSightedReport;
    private boolean missingReport;
    private Person person = new Person();

    @PostConstruct
    public void initializeMap() {
        List<GhostNet> ghostNets = ghostNetDAO.findAll();
        for (GhostNet ghostNet : ghostNets) {
            LatLng cord = new LatLng(ghostNet.getLat(), ghostNet.getLng());
            String icon = getMarkerIcon(ghostNet.getStatus());
            Marker marker = new Marker(cord, ghostNet.getStatus().toString(), String.valueOf(ghostNet.getId()), icon);
            mapModel.addOverlay(marker);
        }
    }

    @Transactional
    public void updateReportAndPerson(GhostNetStatus status, Long ghostNetId) {
        EntityTransaction txn = em.getTransaction();
        try {
            if (!txn.isActive()) {
                txn.begin();
            }
            SightingReport sightingReport = sightingReportDAO.findByGhostNetId(ghostNetId);
            if (!person.getFirstname().isEmpty() || !person.getLastname().isEmpty() || !person.getPhoneNumber().isEmpty()) {
                personDAO.create(person);
                sightingReport.setPersonId(person);
            }
            GhostNet ghostNet = ghostNetDAO.find(ghostNetId);
            ghostNet.setStatus(status);
            sightingReport.setReportTime(LocalDateTime.now());

            sightingReportDAO.create(sightingReport);
            txn.commit();
        } catch (Exception e) {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    private String getMarkerIcon(GhostNetStatus status) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String contextPath = externalContext.getRequestContextPath();
        switch (status) {
            case LOST:
                return contextPath + "/resources/images/lost_marker.png";
            case REPORTED:
                return contextPath + "/resources/images/reported_marker.png";
            case SALVAGED:
                return contextPath + "/resources/images/salvaged_marker.png";
            case SALVAGE_PENDING:
                return contextPath + "/resources/images/salvaged_pending_marker.png";
            default:
                return contextPath + "/resources/images/reported_marker.png";
        }
    }

    public String returnStatusName(String status) {
        switch (status) {
            case "LOST":
                return "Verschollen";
            case "REPORTED":
                return "Gemeldet";
            case "SALVAGED":
                return "Geborgen";
            case "SALVAGE_PENDING":
                return "Bergung bevorstehend";
            default:
                return "Unbekannt";
        }
    }

    public MapModel getMapModel() {
        return mapModel;
    }

    public Marker getMarker() {
        return marker;
    }

    public void onMarkerSelect(OverlaySelectEvent event) {
        marker = (Marker) event.getOverlay();
    }

    public String searchGhostNet(String ghostNetId) {
        GhostNet ghostNet = ghostNetDAO.find(Long.parseLong(ghostNetId));
        SightingReport sightingReport = sightingReportDAO.find(ghostNet.getId());
        return "Netz-Nummer: " + ghostNet.getId() + ", Durchmesser Meter: " + ghostNet.getSize() + ", Zuletzt gesichtet: " + sightingReport.getReportTime();
    }

    public long getGhostNetStatusOutOfString(String ghostNetId) {
        GhostNet ghostNet = ghostNetDAO.find(Long.parseLong(ghostNetId));
        return ghostNet.getStatus().ordinal();
    }

    public void setReportStatus(GhostNetStatus status) {
        successfulSalvageReport = status == GhostNetStatus.SALVAGED;
        plannedSalvageReport = status == GhostNetStatus.SALVAGE_PENDING;
        reSightedReport = status == GhostNetStatus.REPORTED;
        missingReport = status == GhostNetStatus.LOST;
    }

    public boolean isSuccessfulSalvageReport() {
        return successfulSalvageReport;
    }

    public boolean isPlannedSalvageReport() {
        return plannedSalvageReport;
    }

    public boolean isReSightedReport() {
        return reSightedReport;
    }

    public boolean isMissingReport() {
        return missingReport;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public GhostNetStatus getGhostNetStatusByName(String name) {
        return GhostNetStatus.valueOf(name);
    }

    public void setGhostNetStatus(GhostNetStatus ghostNetStatus) {
        this.ghostNetStatus = ghostNetStatus;
    }
}
