package FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses;

public class OutgoingsItem {
    private final int IId;
    private final int IOutgoingImage;
    private final String IOutgoingCategory;
    private final String IOutgoingDescribe;
    private final double IOutgoingPrice;
    private final String IOutgoingDate;

    public OutgoingsItem(int id, int outgoingImage, String outgoingCategory, String outgoingDescribe,
                         double outgoingPrice, String outgoingDate)
    {
        IId=id;
        IOutgoingImage=outgoingImage;
        IOutgoingCategory=outgoingCategory;
        IOutgoingDescribe=outgoingDescribe;
        IOutgoingPrice=outgoingPrice;
        IOutgoingDate=outgoingDate;
    }

    public int getIId() { return IId; }
    public int getIOutgoingImage()
    {
        return IOutgoingImage;
    }
    public String getIOutgoingCategory(){ return IOutgoingCategory; }
    public String getIOutgoingDescribe()
    {
        return IOutgoingDescribe;
    }
    public double getIOutgoingPrice()
    {
        return IOutgoingPrice;
    }
    public String getIOutgoingDate(){return IOutgoingDate;}
}
