import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@Startup
@ApplicationScoped
public class PersonDAO {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void create(Person person) {
        em.persist(person);
    }

    public Person find(Long id) {
        return em.find(Person.class, id);
    }

    public List<Person> findAll() {
        return em.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    @Transactional
    public void update(Person person) {
        em.merge(person);
    }

    @Transactional
    public void delete(Long id) {
        Person person = find(id);
        if (person != null) {
            em.remove(person);
        }
    }
}
