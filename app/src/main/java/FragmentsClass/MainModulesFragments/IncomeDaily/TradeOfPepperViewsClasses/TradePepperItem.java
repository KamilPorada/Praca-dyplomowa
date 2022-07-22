package FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses;

public class TradePepperItem {
    private int IId;
    private final int IColor;
    private final String IDate;
    private final int IPepperClass;
    private final double IPrice;
    private final double IWeight;
    private final String ITotalSum;
    private final String IPlace;

    public TradePepperItem(int id, int color, String date, int pepperClass, double price,
                           double weight, String totalSum, String place)
    {
        IId=id;
        IColor=color;
        IDate=date;
        IPepperClass=pepperClass;
        IPrice=price;
        IWeight=weight;
        ITotalSum=totalSum;
        IPlace=place;
    }

    public int getIColor()
    {
        return IColor;
    }
    public String getIDate(){ return IDate; }
    public int getIPepperClass()
    {
        return IPepperClass;
    }
    public double getIPrice()
    {
        return IPrice;
    }
    public double getIweight()
    {
        return IWeight;
    }
    public String getITotalSum() { return ITotalSum; }
    public String getIPlace()
    {
        return IPlace;
    }
    public int getIId() { return IId; }
}
