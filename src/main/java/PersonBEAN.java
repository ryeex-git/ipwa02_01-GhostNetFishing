import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class PersonBEAN {

    @Inject
    private PersonDAO personDAO;

    private Person person;
    private List<Person> personList;

    @PostConstruct
    public void init() {
        person = new Person();
        personList = personDAO.findAll();
    }

    public void createPerson() {
        personDAO.create(person);
        personList = personDAO.findAll();
        person = new Person(); // Reset the form
    }

    public void updatePerson() {
        personDAO.update(person);
        personList = personDAO.findAll();
    }

    public void deletePerson(Long id) {
        personDAO.delete(id);
        personList = personDAO.findAll();
    }

    // Getter und Setter
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }
}
