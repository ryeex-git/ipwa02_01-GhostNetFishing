import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

public class SightingReportDAO {
    @PersistenceContext
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
