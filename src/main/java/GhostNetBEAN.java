import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class GhostNetBEAN {

    @Inject
    private GhostnetDAO ghostNetDAO;

    private GhostNet ghostNet = new GhostNet();
    private List<GhostNet> ghostNets;
    private GhostNetStatus[] statuses = GhostNetStatus.values();

    @PostConstruct
    public void init() {
        ghostNets = ghostNetDAO.findAll();
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

    public List<GhostNet> getGhostNets() {
        return ghostNets;
    }

    public void setGhostNets(List<GhostNet> ghostNets) {
        this.ghostNets = ghostNets;
    }

    public GhostNetStatus[] getStatuses() {
        return statuses;
    }

    public void setStatuses(GhostNetStatus[] statuses) {
        this.statuses = statuses;
    }
}
