package FragmentsClass.MainModulesFragments.Operations.CatalogPesticidesClasses;

public class CatalogOfPesticideItem {
    private final int id;
    private final String nameOfPesticide;
    private final int grace;

    public CatalogOfPesticideItem(int id, String nameOfPesticide, int grace)
    {
        this.id=id;
        this.nameOfPesticide=nameOfPesticide;
        this.grace=grace;
    }
    public String getNameOfPesticide()
    {
        return nameOfPesticide;
    }

    public int getId() {
        return id;
    }

    public int getGrace() {
        return grace;
    }
}
