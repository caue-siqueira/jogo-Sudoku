import java.util.HashSet;
import java.util.Set;

public class SudokuCell {
    private Integer value;
    private boolean fixed;
    private Set<Integer> pencilMarks;

    public SudokuCell(Integer value, boolean fixed) {
        this.value = value;
        this.fixed = fixed;
        this.pencilMarks = new HashSet<>();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        if (!fixed) this.value = value;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void addPencilMark(int mark) {
        if (!fixed) this.pencilMarks.add(mark);
    }

    public void removePencilMark(int mark) {
        this.pencilMarks.remove(mark);
    }

    public void clearPencilMarks() {
        this.pencilMarks.clear();
    }

    public Set<Integer> getPencilMarks() {
        return pencilMarks;
    }

    public void clearValue() {
        if (!fixed) this.value = null;
    }

    @Override
    public String toString() {
        return (value != null) ? value.toString() : ".";
    }
}
