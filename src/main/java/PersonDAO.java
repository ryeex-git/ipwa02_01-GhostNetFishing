import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@Stateless
public class PersonDAO implements Serializable {

    @Inject
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
