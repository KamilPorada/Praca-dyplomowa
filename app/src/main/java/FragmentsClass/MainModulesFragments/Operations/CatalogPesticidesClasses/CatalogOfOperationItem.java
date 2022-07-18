package FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses;

public class CatalogOfOperationItem {
    private final int id;
    private final String date;
    private final String time;
    private final String status;
    private final String grace;
    private final String pesticide;
    private final String endOfGrace;
    private final int image;
    private final String describe;

    public CatalogOfOperationItem(int id, String date, String time, String status, String grace, String pesticide, String endOfGrace, int image, String describe) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.status = status;
        this.grace = grace;
        this.pesticide = pesticide;
        this.endOfGrace=endOfGrace;
        this.image = image;
        this. describe = describe;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() { return status; }

    public String getGrace() { return grace; }

    public String getPesticide() {
        return pesticide;
    }

    public String getEndOfGrace() { return endOfGrace; }

    public int getImage() {
        return image;
    }

    public String getDescribe() {
        return describe;
    }
}
