import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {
    // Override isCellEditable to always return false, making all cells non-editable
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;  // Prevent editing in all cells
    }
}