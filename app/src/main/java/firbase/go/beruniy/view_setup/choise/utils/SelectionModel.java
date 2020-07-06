package firbase.go.beruniy.view_setup.choise.utils;

public class SelectionModel {

    private int position;
    private boolean isChecked;


    public SelectionModel(int position, boolean isChecked) {
        this.position = position;
        this.isChecked = isChecked;
    }


    public int getPosition() {
        return position;
    }


    public SelectionModel setPosition(int position) {
        this.position = position;
        return this;
    }


    public boolean isChecked() {
        return isChecked;
    }


    public SelectionModel setChecked(boolean checked) {
        isChecked = checked;
        return this;
    }
}