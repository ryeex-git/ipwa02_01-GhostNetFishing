import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import java.io.Serializable;

@Named
@ViewScoped
public class Maps implements Serializable {

    @PostConstruct
    public void initializeMap() {
        // TODO add here the code to generate markers
    }
}
