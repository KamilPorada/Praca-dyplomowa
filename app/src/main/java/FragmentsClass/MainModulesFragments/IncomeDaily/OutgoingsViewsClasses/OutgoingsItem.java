package FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses;

public class OutgoingsItem {
    private int IOutgoingImage;
    private String IOutgoingCategory;
    private String IOutgoingDescribe;
    private double IOutgoingPrice;
    private String IOutgoingDate;
    private String IOutgoingPasswordKey;

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
