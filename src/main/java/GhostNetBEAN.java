import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Named("ghostNetBean")
@RequestScoped
public class GhostNetBEAN {

    @Inject
    private EntityManager em;

    @Inject
    private GhostNetDAO ghostNetDAO;

    @Inject
    private PersonDAO personDAO;

    @Inject
    private SightingReportDAO sightingReportDAO;

    private GhostNet ghostNet;
    private Person person;
    private SightingReport sightingReport;

    private List<GhostNet> ghostNets;
    private GhostNetStatus[] statuses = GhostNetStatus.values();

    @PostConstruct
    public void init() {
        ghostNet = new GhostNet();
        person = new Person();
        sightingReport = new SightingReport();
    }

    @Transactional
    public void createReport() {
        EntityTransaction txn = em.getTransaction();
        try {
            if (!txn.isActive()) {
                txn.begin();
            }
            if (!person.getFirstname().isEmpty() || !person.getLastname().isEmpty() || !person.getPhoneNumber().isEmpty()) {
                personDAO.create(person);
                sightingReport.setPersonId(person);
            }
            ghostNet.setStatus(statuses[0]);
            ghostNetDAO.create(ghostNet);

            sightingReport.setNetId(ghostNet);
            sightingReport.setReportTime(LocalDateTime.now());

            sightingReportDAO.create(sightingReport);
            txn.commit();
        } catch (Exception e) {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    public void create() {
        ghostNetDAO.create(ghostNet);
        ghostNet = new GhostNet();
        init();
    }

    public void update(GhostNet ghostNet) {
        ghostNetDAO.update(ghostNet);
        init();
    }

    public void delete(Long id) {
        ghostNetDAO.delete(id);
        init();
    }

    // Getter und Setter
    public GhostNet getGhostNet() {
        return ghostNet;
    }

    public void setGhostNet(GhostNet ghostNet) {
        this.ghostNet = ghostNet;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public SightingReport getSightingReport() {
        return sightingReport;
    }

    public void setSightingReport(SightingReport sightingReport) {
        this.sightingReport = sightingReport;
    }

    public Date getToday() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }
}
