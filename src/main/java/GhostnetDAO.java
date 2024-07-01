import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Startup
@ApplicationScoped
public class GhostnetDAO {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void create(GhostNet ghostNet) {
        em.persist(ghostNet);
    }

    public GhostNet find(Long id) {
        return em.find(GhostNet.class, id);
    }

    public List<GhostNet> findAll() {
        return em.createQuery("SELECT g FROM GhostNet g", GhostNet.class).getResultList();
    }

    @Transactional
    public void update(GhostNet ghostNet) {
        em.merge(ghostNet);
    }

    @Transactional
    public void delete(Long id) {
        GhostNet ghostNet = find(id);
        if (ghostNet != null) {
            em.remove(ghostNet);
        }
    }
}
