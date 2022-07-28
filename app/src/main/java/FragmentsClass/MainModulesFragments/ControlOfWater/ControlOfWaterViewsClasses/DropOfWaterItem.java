package FragmentsClass.MainModulesFragments.ControlOfWater.ControlOfWaterViewsClasses;

public class DropOfWaterItem {
    private int IId;
    private int IRound;
    private int IImage;

    public DropOfWaterItem(int id, int round, int image) {
        this.IId = id;
        this.IRound = round;
        this.IImage = image;
    }

    public int getIId() {
        return IId;
    }

    public int getIRound() {
        return IRound;
    }

    public int getIImage() {
        return IImage;
    }
}
