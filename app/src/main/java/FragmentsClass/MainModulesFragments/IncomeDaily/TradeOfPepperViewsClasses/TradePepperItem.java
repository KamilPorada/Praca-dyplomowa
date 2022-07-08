package FragmentsClass.MainModulesFragments.IncomeDaily.TradeOfPepperViewsClasses;

public class TradePepperItem {
    private final int IPepperImage;
    private final String IDate;
    private final String IPepperClass;
    private final double IPrice;
    private final double IWeight;
    private final String ITotalSum;
    private final String IPlace;
    private final String IDataPassword;

    public TradePepperItem(int pepperImage, String date, String pepperClass, double price,
                           double weight, String totalSum, String place, String dataPassword)
    {
        IPepperImage=pepperImage;
        IDate=date;
        IPepperClass=pepperClass;
        IPrice=price;
        IWeight=weight;
        ITotalSum=totalSum;
        IPlace=place;
        IDataPassword=dataPassword;
    }

    public int getIPepperImage()
    {
        return IPepperImage;
    }
    public String getIDate(){ return IDate; }
    public String getIPepperClass()
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
    public String getIDataPassword()
    {
        return IDataPassword;
    }

}
