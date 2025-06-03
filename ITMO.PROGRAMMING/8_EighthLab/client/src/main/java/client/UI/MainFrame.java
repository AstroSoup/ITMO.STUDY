package client.UI;

import client.Main;
import client.utility.ClientCommandHandler;
import client.utility.RequestSerializer;
import shared.command.LongPoll;
import shared.entity.Color;
import shared.entity.Ticket;
import shared.entity.TicketType;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.util.Vector;

public class MainFrame extends JFrame {

    private String username;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    // Sorting controls (above the table)
    private JComboBox<String> sortColumnCombo;
    private JButton sortToggleButton;
    private boolean ascending = true;

    private JComboBox<String> filterColumnCombo;
    private JTextField filterTextField;
    private JButton applyFilterButton;
    private JButton clearFilterButton;
    private JButton addTicketButton;

    private JPanel commandsPanel;

    private JToggleButton burgerButton;

    public MainFrame(String username) {

        super("Main Dashboard");
        this.username = username;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        burgerButton = new JToggleButton("\u2630");
        burgerButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        burgerButton.setFocusable(false);
        topBar.add(burgerButton, BorderLayout.WEST);

        JLabel userLabel = new JLabel("Logged in as: " + username);
        userLabel.setFont(userLabel.getFont().deriveFont(Font.BOLD, 14f));
        topBar.add(userLabel, BorderLayout.CENTER);

        add(topBar, BorderLayout.NORTH);

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


        //TODO: add actual commands here
        commandsPanel.add(Box.createVerticalStrut(20));
        JButton placeholderBtn = new JButton("Future Command");
        placeholderBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        placeholderBtn.setEnabled(false);
        commandsPanel.add(placeholderBtn);

        commandsPanel.setVisible(false);
        add(commandsPanel, BorderLayout.WEST);


        JPanel centerWrapper = new JPanel(new BorderLayout(5, 5));


        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

//        controlPanel.add(new JLabel("Sort by:"));
//        sortColumnCombo = new JComboBox<>();
//        controlPanel.add(sortColumnCombo);
//
//        sortToggleButton = new JButton("Sort Asc");
//        controlPanel.add(sortToggleButton);
//
//        // Add spacing before filter controls
//        controlPanel.add(Box.createHorizontalStrut(20));

        controlPanel.add(new JLabel("Filter by:"));
        filterColumnCombo = new JComboBox<>();
        controlPanel.add(filterColumnCombo);

        filterTextField = new JTextField(10);
        controlPanel.add(filterTextField);

        applyFilterButton = new JButton("Apply Filter");
        controlPanel.add(applyFilterButton);

        clearFilterButton = new JButton("Clear Filter");
        controlPanel.add(clearFilterButton);
        addTicketButton = new JButton("Add new ticket");
        controlPanel.add(addTicketButton); // TODO: add logic for adding ticket
        centerWrapper.add(controlPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID",
                "Name",
                "Price",
                "Discount",
                "Type",
                "Coordinate X",
                "Coordinate Y",
                "Passport ID",
                "Eye color",
                "Location X",
                "Location Y",
                "Location Z",
                "Creation date",
                "Creator"};
        Object[][] data = {
                {1, "Amogus", 100, 24, TicketType.VIP, 20, 30, "123321", Color.RED, 1,2,3, LocalDate.now(), "someguy"},
                {2, "Baka", 200, 52, TicketType.USUAL, 20, 30, "7777777", Color.GREEN, 3,2,1, LocalDate.now(), "someguy"},
                {3, "Lulich", 400, 3, TicketType.CHEAP, 20, 30, "4444444", Color.ORANGE, 1,2,2, LocalDate.now(), "someguy4"},
                {4, "Pupa", 1000, 55, TicketType.BUDGETARY, 20, 30, "1333333", Color.RED, 1,2,4, LocalDate.now(), "someguy2"},
                {5, "Lupa", 5, 11, null, 20, 30, "aaaaaaa21", Color.RED, 1,2,5, LocalDate.now(), "test"},
        }; // TODO: actually populate table with tickets on creation

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;  // all cells un-editable
            }
        };
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        for (String col : columnNames) {
            //sortColumnCombo.addItem(col);
            filterColumnCombo.addItem(col);
        }


//        sortToggleButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int colIndex = sortColumnCombo.getSelectedIndex();
//                if (colIndex < 0) return;
//
//                if (ascending) {
//                    sorter.setSortKeys(java.util.List.of(
//                            new RowSorter.SortKey(colIndex, SortOrder.ASCENDING)
//                    ));
//                    sortToggleButton.setText("Sort Desc");
//                } else {
//                    sorter.setSortKeys(java.util.List.of(
//                            new RowSorter.SortKey(colIndex, SortOrder.DESCENDING)
//                    ));
//                    sortToggleButton.setText("Sort Asc");
//                }
//                ascending = !ascending;
//            }
//        });

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

        burgerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean showing = commandsPanel.isVisible();
                commandsPanel.setVisible(!showing);
                MainFrame.this.revalidate();
            }
        });
    }


    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void setTable(Vector<Ticket> v) {
        tableModel = new DefaultTableModel();
        String[] columnNames = {"ID",
                "Name",
                "Price",
                "Discount",
                "Type",
                "Coordinate X",
                "Coordinate Y",
                "Passport ID",
                "Eye color",
                "Location X",
                "Location Y",
                "Location Z",
                "Creation date",
                "Creator"};
        tableModel.setColumnIdentifiers(columnNames);
        for (Ticket ticket : v) {
            tableModel.addRow(new Object[]{ticket.getId(),
                    ticket.getName(),
                    ticket.getPrice(),
                    ticket.getDiscount(),
                    ticket.getType(),
                    ticket.getCoordinates().getX(),
                    ticket.getCoordinates().getY(),
                    ticket.getPerson().getPassportID(),
                    ticket.getPerson().getEyeColor(),
                    ticket.getPerson().getLocation().getX(),
                    ticket.getPerson().getLocation().getY(),
                    ticket.getPerson().getLocation().getZ(),
                    ticket.getCreationDate(),
                    ticket.getCreator()});
        }
        table.setModel(tableModel);
    }

}
