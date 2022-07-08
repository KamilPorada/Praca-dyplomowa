package FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses;

public class OutgoingsItem {
    private final int IOutgoingImage;
    private final String IOutgoingCategory;
    private final String IOutgoingDescribe;
    private final double IOutgoingPrice;
    private final String IOutgoingDate;
    private final String IOutgoingPasswordKey;

    public OutgoingsItem(int outgoingImage, String outgoingCategory, String outgoingDescribe,
                         double outgoingPrice, String outgoingDate, String outgoingPasswordKey)
    {
        IOutgoingImage=outgoingImage;
        IOutgoingCategory=outgoingCategory;
        IOutgoingDescribe=outgoingDescribe;
        IOutgoingPrice=outgoingPrice;
        IOutgoingDate=outgoingDate;
        IOutgoingPasswordKey=outgoingPasswordKey;
    }

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
    public String getIOutgoingPasswordKey(){return IOutgoingPasswordKey;}

}
