package FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses;

public class RoundItem {
    private int IId;
    private String IHighgroves;
    private String ITime;

    public RoundItem(int id, String highgroves, String time) {
        this.IId = id;
        this.IHighgroves = highgroves;
        this.ITime = time;
    }

    public int getIId() {
        return IId;
    }

    public String getIHighgroves() {
        return IHighgroves;
    }

    public String getITime() {
        return ITime;
    }
}
