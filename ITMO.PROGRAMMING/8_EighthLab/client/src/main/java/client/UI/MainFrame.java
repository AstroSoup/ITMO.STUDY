package client.UI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainFrame extends JFrame {

    private String username;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    // Sorting controls (above the table)
    private JComboBox<String> sortColumnCombo;
    private JButton sortToggleButton;
    private boolean ascending = true;

    // Filtering controls (above the table)
    private JComboBox<String> filterColumnCombo;
    private JTextField filterTextField;
    private JButton applyFilterButton;
    private JButton clearFilterButton;

    // The left commands panel itself (placeholder for future commands)
    private JPanel commandsPanel;

    // The “burger” button that toggles commandsPanel visibility
    private JToggleButton burgerButton;

    public MainFrame(String username) {
        super("Main Dashboard");
        this.username = username;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ==== TOP BAR: burger button + user label ====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Burger button (☰) on the left
        burgerButton = new JToggleButton("\u2630"); // Unicode “hamburger” icon
        burgerButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        burgerButton.setFocusable(false);
        topBar.add(burgerButton, BorderLayout.WEST);

        // “Logged in as: <username>” label in the center (left‐aligned)
        JLabel userLabel = new JLabel("Logged in as: " + username);
        userLabel.setFont(userLabel.getFont().deriveFont(Font.BOLD, 14f));
        topBar.add(userLabel, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);

        // ==== LEFT COMMANDS PANEL (initially hidden) ====
        commandsPanel = new JPanel();
        commandsPanel.setPreferredSize(new Dimension(250, 0));
        commandsPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Commands",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                )
        );
        commandsPanel.setLayout(new BoxLayout(commandsPanel, BoxLayout.Y_AXIS));

        // Placeholder for future commands
        commandsPanel.add(Box.createVerticalStrut(20));
        JButton placeholderBtn = new JButton("Future Command");
        placeholderBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        placeholderBtn.setEnabled(false);
        commandsPanel.add(placeholderBtn);

        commandsPanel.setVisible(false);
        add(commandsPanel, BorderLayout.WEST);

        // ==== CENTER REGION: single‐line control panel above the table ====
        JPanel centerWrapper = new JPanel(new BorderLayout(5, 5));

        // --- Control panel with centered FlowLayout ---
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        // SORT CONTROLS
        controlPanel.add(new JLabel("Sort by:"));
        sortColumnCombo = new JComboBox<>();
        controlPanel.add(sortColumnCombo);

        sortToggleButton = new JButton("Sort Asc");
        controlPanel.add(sortToggleButton);

        // Add spacing before filter controls
        controlPanel.add(Box.createHorizontalStrut(20));

        // FILTER CONTROLS
        controlPanel.add(new JLabel("Filter by:"));
        filterColumnCombo = new JComboBox<>();
        controlPanel.add(filterColumnCombo);

        filterTextField = new JTextField(10);
        controlPanel.add(filterTextField);

        applyFilterButton = new JButton("Apply Filter");
        controlPanel.add(applyFilterButton);

        clearFilterButton = new JButton("Clear Filter");
        controlPanel.add(clearFilterButton);

        centerWrapper.add(controlPanel, BorderLayout.NORTH);

        // --- The JTable itself ---
        String[] columnNames = {"ID", "Name", "Category", "Price"};
        Object[][] data = {
                {1, "Apple", "Fruit", 0.99},
                {2, "Banana", "Fruit", 0.59},
                {3, "Carrot", "Vegetable", 0.39},
                {4, "Detergent", "Household", 5.49},
                {5, "Eggs", "Dairy", 2.99},
        };

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        // Populate combo boxes with column names
        for (String col : columnNames) {
            sortColumnCombo.addItem(col);
            filterColumnCombo.addItem(col);
        }

        // Wire up sort toggle button
        sortToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int colIndex = sortColumnCombo.getSelectedIndex();
                if (colIndex < 0) return;

                if (ascending) {
                    sorter.setSortKeys(java.util.List.of(
                            new RowSorter.SortKey(colIndex, SortOrder.ASCENDING)
                    ));
                    sortToggleButton.setText("Sort Desc");
                } else {
                    sorter.setSortKeys(java.util.List.of(
                            new RowSorter.SortKey(colIndex, SortOrder.DESCENDING)
                    ));
                    sortToggleButton.setText("Sort Asc");
                }
                ascending = !ascending;
            }
        });

        // Wire up filter buttons
        applyFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int colIndex = filterColumnCombo.getSelectedIndex();
                String text = filterTextField.getText().trim();
                if (!text.isEmpty() && colIndex >= 0) {
                    try {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, colIndex));
                    } catch (java.util.regex.PatternSyntaxException ex) {
                        JOptionPane.showMessageDialog(
                                MainFrame.this,
                                "Invalid regex pattern: " + ex.getMessage(),
                                "Filter Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
        clearFilterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sorter.setRowFilter(null);
                filterTextField.setText("");
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        "Data Table",
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                )
        );
        centerWrapper.add(scrollPane, BorderLayout.CENTER);

        add(centerWrapper, BorderLayout.CENTER);

        // ==== Action Listener for burgerButton (toggle commandsPanel) ====
        burgerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean showing = commandsPanel.isVisible();
                commandsPanel.setVisible(!showing);
                MainFrame.this.revalidate();
            }
        });
    }

    /**
     * If you later want to populate the table from code, you can do:
     *   addRow(new Object[]{ val1, val2, val3, val4 });
     */
    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }
}