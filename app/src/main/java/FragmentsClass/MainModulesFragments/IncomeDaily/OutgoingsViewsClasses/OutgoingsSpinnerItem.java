package FragmentsClass.MainModulesFragments.IncomeDaily.OutgoingsViewsClasses;

public class OutgoingsSpinnerItem {
    private final String mText;
    private final int mImage;

    public OutgoingsSpinnerItem(String text, int image) {
        mText = text;
        mImage = image;
    }

    public String getText() {
        return mText;
    }

    public int getImage() {
        return mImage;
    }
}
