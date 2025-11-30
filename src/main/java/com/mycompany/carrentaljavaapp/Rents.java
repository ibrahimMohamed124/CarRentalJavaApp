package com.mycompany.carrentaljavaapp;

import utils.UIHelper.AppTheme;
import utils.UIHelper.StyleUtils;
import utils.UIHelper.BaseFrame;
import utils.UIHelper.HintTextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
import java.awt.*;

public class Rents extends BaseFrame {

    private JTable rentsTable;
    // الحقول المطلوبة لصفحة الإيجارات
    private JTextField carRegNo, customerName, rentDate, returnDate, totalPaid, searchField;
    private JButton addBtn, editBtn, deleteBtn, clearBtn;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public Rents() {
        // استدعاء الباني الأب مع تحديد العنوان واسم الصفحة النشطة
        super("Rental Transactions", "Rentals");
        buildRentsPage();
    }

    private void buildRentsPage() {
        // --- 1. Header and Search Panel ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppTheme.BACKGROUND_LIGHT);

        JLabel title = new JLabel("Rental Transactions", SwingConstants.LEFT);
        title.setFont(AppTheme.HEADER_FONT);
        title.setForeground(AppTheme.SIDEBAR_BG_DARK);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        searchPanel.setBackground(AppTheme.BACKGROUND_LIGHT);

        // حقل البحث
        searchField = new HintTextField("Search by Registration No. or Customer...");
        StyleUtils.applyTextFieldStyle(searchField);
        searchField.setPreferredSize(new Dimension(300, 40));

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(AppTheme.LABEL_FONT);
        searchLabel.setForeground(AppTheme.SIDEBAR_BG_DARK);

        searchPanel.add(searchLabel);
        searchPanel.add(searchField);

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(searchPanel, BorderLayout.EAST);

        // --- 2. Table and Table Setup ---
        String[] columns = {"ID", "Car Reg. No.", "Customer Name", "Rent Date", "Return Date", "Total Paid"};
        tableModel = new DefaultTableModel(columns, 0);
        rentsTable = new JTable(tableModel);
        rentsTable.setRowHeight(35);
        rentsTable.setFont(AppTheme.FIELD_FONT);
        rentsTable.getTableHeader().setFont(AppTheme.BUTTON_FONT);
        rentsTable.getTableHeader().setReorderingAllowed(false);

        // محاذاة النص في الخلايا للوسط
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < rentsTable.getColumnCount(); i++) {
            rentsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(rentsTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(AppTheme.FIELD_BORDER_GRAY));

        // إعداد الفرز والبحث
        sorter = new TableRowSorter<>(tableModel);
        rentsTable.setRowSorter(sorter);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterTable(); }
            public void removeUpdate(DocumentEvent e) { filterTable(); }
            public void changedUpdate(DocumentEvent e) { filterTable(); }
        });

        // --- 3. Form Fields (Bottom Panel) ---
        // استخدام HintTextFields
        carRegNo = new HintTextField("Enter Car Registration No.");
        customerName = new HintTextField("Enter Customer Name");
        rentDate = new HintTextField("DD/MM/YYYY");
        returnDate = new HintTextField("DD/MM/YYYY");
        totalPaid = new HintTextField("Total Amount Paid ($)");

        // تطبيق نفس التنسيق من StyleUtils
        StyleUtils.applyTextFieldStyle(carRegNo);
        StyleUtils.applyTextFieldStyle(customerName);
        StyleUtils.applyTextFieldStyle(rentDate);
        StyleUtils.applyTextFieldStyle(returnDate);
        StyleUtils.applyTextFieldStyle(totalPaid);

        JPanel bottomPanel = new JPanel(new BorderLayout(20, 20));
        bottomPanel.setBackground(AppTheme.FIELD_BACKGROUND_WHITE);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AppTheme.FIELD_BORDER_GRAY),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // شبكة إدخال الحقول
        JPanel inputGrid = new JPanel(new GridLayout(2, 3, 25, 15)); // 5 حقول
        inputGrid.setBackground(AppTheme.FIELD_BACKGROUND_WHITE);

        inputGrid.add(StyleUtils.createLabelFieldPanel("Car Registration No.", carRegNo));
        inputGrid.add(StyleUtils.createLabelFieldPanel("Customer Name", customerName));
        inputGrid.add(StyleUtils.createLabelFieldPanel("Rent Date", rentDate));
        inputGrid.add(StyleUtils.createLabelFieldPanel("Return Date", returnDate));
        inputGrid.add(StyleUtils.createLabelFieldPanel("Total Paid ($)", totalPaid));
        inputGrid.add(Box.createGlue()); // حقل فارغ للمحاذاة

        bottomPanel.add(inputGrid, BorderLayout.CENTER);

        // --- 4. Action Buttons ---
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        buttonsPanel.setBackground(AppTheme.FIELD_BACKGROUND_WHITE);

        addBtn = StyleUtils.createActionStyledButton("Add Rental", AppTheme.PRIMARY_BLUE, AppTheme.PRIMARY_BLUE.darker());
        editBtn = StyleUtils.createActionStyledButton("Update", AppTheme.PRIMARY_BLUE, AppTheme.PRIMARY_BLUE.darker());
        deleteBtn = StyleUtils.createActionStyledButton("Delete", AppTheme.DANGER_RED, AppTheme.DANGER_RED.darker());
        clearBtn = StyleUtils.createActionStyledButton("Clear Form", AppTheme.FIELD_BORDER_GRAY.darker(), AppTheme.FIELD_BORDER_GRAY.darker().darker());

        buttonsPanel.add(clearBtn);
        buttonsPanel.add(deleteBtn);
        buttonsPanel.add(editBtn);
        buttonsPanel.add(addBtn);
        bottomPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // --- 5. Add Action Listeners ---
        addBtn.addActionListener(e -> addRent());
        editBtn.addActionListener(e -> editRent());
        deleteBtn.addActionListener(e -> deleteRent());
        clearBtn.addActionListener(e -> clearFields());

        // --- 6. Final Layout ---
        JPanel contentPanel = new JPanel(new BorderLayout(25, 25));
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPanel(contentPanel);
    }

    // --- Action Methods ---

    // دالة محاكاة لتوليد ID تلقائي
    private int generateRentID() {
        return tableModel.getRowCount() + 1;
    }

    private void addRent() {
        // التحقق من الحقول الأساسية
        if (carRegNo.getText().isEmpty() || customerName.getText().isEmpty() || rentDate.getText().isEmpty() || returnDate.getText().isEmpty() || totalPaid.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Please fill all rental details!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int rentID = generateRentID();
        // إضافة صف جديد للجدول
        tableModel.addRow(new Object[]{
                rentID,
                carRegNo.getText(),
                customerName.getText(),
                rentDate.getText(),
                returnDate.getText(),
                totalPaid.getText()
        });
        clearFields();
    }

    private void editRent() {
        int row = rentsTable.getSelectedRow();
        if (row == -1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Select a rental transaction to update!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // تحويل الفهرس المرئي إلى فهرس النموذج (ضروري عند وجود فرز)
        int modelRow = rentsTable.convertRowIndexToModel(row);

        // التحقق من الحقول الأساسية
        if (carRegNo.getText().isEmpty() || customerName.getText().isEmpty() || rentDate.getText().isEmpty() || returnDate.getText().isEmpty() || totalPaid.getText().isEmpty()) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Please fill all rental details!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // تحديث قيم الصف المختار (ID هو العمود 0، لا يتم تحديثه يدوياً)
        tableModel.setValueAt(carRegNo.getText(), modelRow, 1);
        tableModel.setValueAt(customerName.getText(), modelRow, 2);
        tableModel.setValueAt(rentDate.getText(), modelRow, 3);
        tableModel.setValueAt(returnDate.getText(), modelRow, 4);
        tableModel.setValueAt(totalPaid.getText(), modelRow, 5);
        clearFields();
    }

    private void deleteRent() {
        int row = rentsTable.getSelectedRow();
        if (row == -1) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(this, "Select a rental transaction to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int modelRow = rentsTable.convertRowIndexToModel(row);
        tableModel.removeRow(modelRow);
        clearFields();
    }

    private void clearFields() {
        carRegNo.setText("");
        customerName.setText("");
        rentDate.setText("");
        returnDate.setText("");
        totalPaid.setText("");
        rentsTable.clearSelection();
    }

    private void filterTable() {
        String query = searchField.getText().trim();
        // البحث بدون تمييز لحالة الأحرف في جميع الأعمدة
        // (?i) يتيح البحث بدون حساسية لحالة الأحرف
        sorter.setRowFilter(query.isEmpty() ? null : RowFilter.regexFilter("(?i)" + query));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Rents().setVisible(true));
    }
}