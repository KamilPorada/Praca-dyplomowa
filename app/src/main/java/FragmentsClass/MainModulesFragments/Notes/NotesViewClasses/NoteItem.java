package FragmentsClass.MainModulesFragments.Notes.NotesViewClasses;

public class NoteItem {
    private final int IId;
    private final String ITitle;
    private final String IDate;
    private final String IDescribe;
    private final int IImage;

    public NoteItem(int id, String title, String date, String describe, int image) {
        this.IId = id;
        this.ITitle = title;
        this.IDate = date;
        this.IDescribe = describe;
        this.IImage = image;
    }

    public int getIId() {
        return IId;
    }

    public String getITitle() {
        return ITitle;
    }

    public String getIDate() {
        return IDate;
    }

    public String getIDescribe() {
        return IDescribe;
    }

    public int getIImage() {
        return IImage;
    }
}

