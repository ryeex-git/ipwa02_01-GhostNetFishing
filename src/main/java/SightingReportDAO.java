import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@Stateless
public class SightingReportDAO {

    @Inject
    private EntityManager em;

    @Transactional
    public void create(SightingReport sightingReport) {
        em.persist(sightingReport);
    }

    public SightingReport find(Long id) {
        return em.find(SightingReport.class, id);
    }

    public List<SightingReport> findAll() {
        return em.createQuery("SELECT s FROM SightingReport s", SightingReport.class).getResultList();
    }

    @Transactional
    public void update(SightingReport sightingReport) {
        em.merge(sightingReport);
    }

    @Transactional
    public void delete(Long id) {
        SightingReport sightingReport = find(id);
        if (sightingReport != null) {
            em.remove(sightingReport);
        }
    }
}
