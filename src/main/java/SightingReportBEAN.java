import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;

@Named
@RequestScoped
public class SightingReportBEAN {

    @Inject
    private SightingReportDAO sightingReportDAO;

    private SightingReport sightingReport = new SightingReport();
    private List<SightingReport> sightingReports;

    @PostConstruct
    public void init() {
        sightingReports = sightingReportDAO.findAll();
    }

    public void create() {
        sightingReportDAO.create(sightingReport);
        sightingReport = new SightingReport();
        init();
    }

    public void update(SightingReport sightingReport) {
        sightingReportDAO.update(sightingReport);
        init();
    }

    public void delete(Long id) {
        sightingReportDAO.delete(id);
        init();
    }

    // Getter und Setter
    public SightingReport getSightingReport() {
        return sightingReport;
    }

    public void setSightingReport(SightingReport sightingReport) {
        this.sightingReport = sightingReport;
    }

    public List<SightingReport> getSightingReports() {
        return sightingReports;
    }

    public void setSightingReports(List<SightingReport> sightingReports) {
        this.sightingReports = sightingReports;
    }
}
