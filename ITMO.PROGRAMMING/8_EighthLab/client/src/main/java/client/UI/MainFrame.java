package client.UI;

import client.clientsideCommand.ExecuteScript;
import client.clientsideCommand.Help;
import client.clientsideCommand.LocalCommand;
import client.utility.ClientCommandHandler;
import client.utility.Localization;
import client.utility.TextUI;
import shared.command.*;
import shared.entity.*;
import shared.entity.Color;
import shared.exceptions.ConnectionLostException;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Vector;

public class MainFrame extends JFrame {

    private final String username;
    private final ClientCommandHandler cch;


    private JLabel userLabel;
    private JButton languageButton;
    private JPanel commandsPanel;

    private JLabel filterLabel;
    private JComboBox<String> filterColumnCombo;
    private JTextField filterTextField;
    private JButton applyFilterButton;
    private JButton clearFilterButton;
    private JButton addTicketButton;
    private JButton removeTicketButton;
    private JButton updateTicketButton;
    private JButton addIfMaxButton;
    private JButton averageOfPriceButton;
    private JButton clearButton;
    private JButton infoButton;
    private JButton insertButton;
    private JButton printFieldAscendingTypeButton;
    private JButton printFieldDescendingTypeButton;
    private JButton executeScriptButton;
    private JButton helpButton;


    private JScrollPane tableScrollPane;
    private TicketAnimatedPanel animatedPanel;

    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    private JToggleButton burgerButton;

    private JPopupMenu languageMenu;

    private final List<Ticket> currentTickets = new ArrayList<>();

    public MainFrame(String username, ClientCommandHandler cch) {
        super();
        this.username = username;
        this.cch = cch;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        burgerButton = new JToggleButton("\u2630");
        burgerButton.setFont(new Font("Dialog", Font.PLAIN, 18));
        burgerButton.setFocusable(false);
        topBar.add(burgerButton, BorderLayout.WEST);
        burgerButton.addActionListener(e -> {
            boolean showing = commandsPanel.isVisible();
            commandsPanel.setVisible(!showing);
            MainFrame.this.revalidate();
        });

        userLabel = new JLabel();
        userLabel.setFont(userLabel.getFont().deriveFont(Font.BOLD, 14f));
        topBar.add(userLabel, BorderLayout.CENTER);

        languageButton = new JButton();
        languageButton.setFocusable(false);
        topBar.add(languageButton, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        commandsPanel = new JPanel();
        commandsPanel.setPreferredSize(new Dimension(250, 0));
        commandsPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        Localization.get("commands.title"),
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                )
        );
        commandsPanel.setLayout(new BoxLayout(commandsPanel, BoxLayout.Y_AXIS));
        commandsPanel.add(Box.createVerticalStrut(20));


        addIfMaxButton = new JButton("AddIfMax");
        addFullWidthButton(commandsPanel, addIfMaxButton);
        addIfMaxButton.addActionListener(e -> showAddTicketDialog(true));
        averageOfPriceButton = new JButton("AverageOfPrice");
        averageOfPriceButton.addActionListener(e -> {
            try {
                cch.invoke(new AverageOfPrice());
                JOptionPane.showMessageDialog(
                        this,
                        cch.getFeedback(),
                        "AverageOfPrice",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (ConnectionLostException ex) {
                ex.printStackTrace();
            }
        });

        addFullWidthButton(commandsPanel, averageOfPriceButton);

        clearButton = new JButton("Clear");

        clearButton.addActionListener(e -> {
            try {

                int confirmed = JOptionPane.showConfirmDialog(this, "Вы уверены?", "Confirmation",
                        JOptionPane.OK_CANCEL_OPTION);

                if (confirmed == JOptionPane.OK_OPTION) {
                    cch.invoke(new Clear());
                    JOptionPane.showMessageDialog(
                            this,
                            cch.getFeedback(),
                            "Clear",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (ConnectionLostException ex) {
                ex.printStackTrace();
            }
        });
        addFullWidthButton(commandsPanel, clearButton);

        infoButton = new JButton("Info");
        addFullWidthButton(commandsPanel, infoButton);

        infoButton.addActionListener(e -> {
            try {
                cch.invoke(new Info());
                JOptionPane.showMessageDialog(
                        this,
                        cch.getFeedback(),
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (ConnectionLostException ex) {
                ex.printStackTrace();
            }
        });

        insertButton = new JButton("Insert");
        addFullWidthButton(commandsPanel, insertButton);
        insertButton.addActionListener(e -> showAddTicketDialog(false));
        printFieldAscendingTypeButton = new JButton("PrintFieldAscendingType");
        addFullWidthButton(commandsPanel, printFieldAscendingTypeButton);
        printFieldAscendingTypeButton.addActionListener(e -> {
            try {
                cch.invoke(new PrintFieldAscendingType());
                JOptionPane.showMessageDialog(
                        this,
                        cch.getFeedback(),
                        "PrintFieldAscendingType",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (ConnectionLostException ex) {
                ex.printStackTrace();
            }
        });

        printFieldDescendingTypeButton = new JButton("PrintFieldDescendingType");
        addFullWidthButton(commandsPanel, printFieldDescendingTypeButton);
        printFieldDescendingTypeButton.addActionListener(e -> {
            try {
                cch.invoke(new PrintFieldDescendingType());
                JOptionPane.showMessageDialog(
                        this,
                        cch.getFeedback(),
                        "PrintFieldDescendingType",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (ConnectionLostException ex) {
                ex.printStackTrace();
            }
        });

        executeScriptButton = new JButton("ExecuteScript");
        addFullWidthButton(commandsPanel, executeScriptButton);
        executeScriptButton.addActionListener(e -> {

            String path = JOptionPane.showInputDialog(this, "Путь до файла скрипта:", "Script Input", JOptionPane.INFORMATION_MESSAGE);

            cch.invoke((LocalCommand) new ExecuteScript(path, new TextUI(), cch));
            JOptionPane.showMessageDialog(
                    this,
                    cch.getFeedback(),
                    "AverageOfPrice",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
        helpButton = new JButton("Help");
        addFullWidthButton(commandsPanel, helpButton);
        helpButton.addActionListener(e -> {
            cch.invoke((LocalCommand) new Help(cch));
            JOptionPane.showMessageDialog(this, cch.getFeedback(), "Help", JOptionPane.INFORMATION_MESSAGE);
        });

        commandsPanel.add(helpButton);
        commandsPanel.setVisible(false);
        add(commandsPanel, BorderLayout.WEST);

        JPanel centerWrapper = new JPanel(new BorderLayout(5, 5));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        filterLabel = new JLabel();
        controlPanel.add(filterLabel);

        filterColumnCombo = new JComboBox<>();
        controlPanel.add(filterColumnCombo);

        filterTextField = new JTextField(10);
        controlPanel.add(filterTextField);

        applyFilterButton = new JButton();
        controlPanel.add(applyFilterButton);

        clearFilterButton = new JButton();
        controlPanel.add(clearFilterButton);

        addTicketButton = new JButton();
        removeTicketButton = new JButton();
        updateTicketButton = new JButton();

        controlPanel.add(addTicketButton);
        controlPanel.add(removeTicketButton);
        controlPanel.add(updateTicketButton);

        centerWrapper.add(controlPanel, BorderLayout.NORTH);


        String[] columnNames = new String[]{
                Localization.get("col.id"),
                Localization.get("col.name"),
                Localization.get("col.price"),
                Localization.get("col.discount"),
                Localization.get("col.type"),
                Localization.get("col.coord_x"),
                Localization.get("col.coord_y"),
                Localization.get("col.passport"),
                Localization.get("col.eye_color"),
                Localization.get("col.loc_x"),
                Localization.get("col.loc_y"),
                Localization.get("col.loc_z"),
                Localization.get("col.creation_date"),
                Localization.get("col.creator")
        };
        Object[][] data = {};

        tableModel = new DefaultTableModel(data, columnNames);

        table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        for (String col : columnNames) {
            filterColumnCombo.addItem(col);
        }

        applyFilterButton.addActionListener(e -> {
            int colIndex = filterColumnCombo.getSelectedIndex();
            String text = filterTextField.getText().trim();
            if (!text.isEmpty() && colIndex >= 0) {
                try {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, colIndex));
                } catch (java.util.regex.PatternSyntaxException ex) {
                    JOptionPane.showMessageDialog(
                            MainFrame.this,
                            Localization.get("message.error") + ": " + ex.getMessage(),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        clearFilterButton.addActionListener(e -> {
            sorter.setRowFilter(null);
            filterTextField.setText("");
        });

        tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        Localization.get("table.border"),
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                )
        );
        centerWrapper.add(tableScrollPane, BorderLayout.CENTER);

        animatedPanel = new TicketAnimatedPanel(currentTickets);
        animatedPanel.setPreferredSize(new Dimension(0, 250));
        animatedPanel.setBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createEtchedBorder(),
                        Localization.get("animated.border"),
                        TitledBorder.CENTER,
                        TitledBorder.TOP
                )
        );
        centerWrapper.add(animatedPanel, BorderLayout.SOUTH);

        add(centerWrapper, BorderLayout.CENTER);

        addTicketButton.addActionListener(e -> showAddTicketDialog(false));
        removeTicketButton.addActionListener(e -> showRemoveTicketDialog());
        updateTicketButton.addActionListener(e -> showUpdateTicketDialog());

        languageMenu = new JPopupMenu();
        for (Localization.Language lang : Localization.Language.values()) {
            JMenuItem item = new JMenuItem(lang.getFlagEmoji() + "  " + lang.getDisplayName());
            item.addActionListener(ev -> {
                Localization.setLanguage(lang);
                updateTexts();
            });
            languageMenu.add(item);
        }
        languageButton.addActionListener(e -> {
            languageMenu.show(languageButton, 0, languageButton.getHeight());
        });

        updateTexts();
    }

    private void updateTexts() {
        setTitle(Localization.get("frame.title"));

        userLabel.setText(Localization.format("label.logged_in_as", username));
        String[] columnNames = new String[]{
                Localization.get("col.id"),
                Localization.get("col.name"),
                Localization.get("col.price"),
                Localization.get("col.discount"),
                Localization.get("col.type"),
                Localization.get("col.coord_x"),
                Localization.get("col.coord_y"),
                Localization.get("col.passport"),
                Localization.get("col.eye_color"),
                Localization.get("col.loc_x"),
                Localization.get("col.loc_y"),
                Localization.get("col.loc_z"),
                Localization.get("col.creation_date"),
                Localization.get("col.creator")
        };
        tableModel.setColumnIdentifiers(columnNames);

        TitledBorder cmdBorder = (TitledBorder) commandsPanel.getBorder();
        cmdBorder.setTitle(Localization.get("commands.title"));

        filterLabel.setText(Localization.get("label.filter_by"));
        applyFilterButton.setText(Localization.get("apply_filter"));
        clearFilterButton.setText(Localization.get("clear_filter"));
        addTicketButton.setText(Localization.get("add_ticket"));
        removeTicketButton.setText(Localization.get("remove_ticket"));
        updateTicketButton.setText(Localization.get("update_ticket"));

        TitledBorder tableBorder = (TitledBorder) tableScrollPane.getBorder();
        tableBorder.setTitle(Localization.get("table.border"));

        TitledBorder animBorder = (TitledBorder) animatedPanel.getBorder();
        animBorder.setTitle(Localization.get("animated.border"));

        Localization.Language lang = Localization.getLanguage();
        languageButton.setText(lang.getFlagEmoji());

        SwingUtilities.updateComponentTreeUI(this);
    }

    public void setTable(Vector<Ticket> v) {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            for (Ticket ticket : v) {
                Object[] rowData = new Object[]{
                        ticket.getId(),
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
                        ticket.getCreator()
                };
                tableModel.addRow(rowData);
            }
            tableModel.fireTableDataChanged();
            currentTickets.clear();
            currentTickets.addAll(v);
            animatedPanel.resetAndStartAnimation();
        });
    }

    private void addFullWidthButton(JPanel panel, JButton button) {
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(button);

    }

    private void showRemoveTicketDialog() {
        String input = JOptionPane.showInputDialog(
                this,
                Localization.get("dialog.remove.prompt"),
                Localization.get("dialog.remove.title"),
                JOptionPane.QUESTION_MESSAGE
        );
        if (input != null) {
            input = input.trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        Localization.get("message.id_empty"),
                        Localization.get("message.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            try {
                int idToRemove = Integer.parseInt(input);
                try {
                    cch.invoke(new RemoveById(idToRemove));
                } catch (ConnectionLostException e) {
                    e.printStackTrace();
                }

                String feedback = cch.getFeedback();
                if (feedback.equals(Localization.get("message.not_creator"))) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.not_creator"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                } else if (feedback.equals(Localization.get("message.success.remove"))) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.success.remove"),
                            Localization.get("dialog.remove.title"),
                            JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.not_in_collection"),
                            Localization.get("message.not_in_collection"),
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        Localization.get("message.invalid_id_format"),
                        Localization.get("message.error"),
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void showAddTicketDialog(boolean isMax) {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));

        panel.add(new JLabel(Localization.get("label.name")));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel(Localization.get("label.price")));
        JTextField priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel(Localization.get("label.discount")));
        JTextField discountField = new JTextField();
        panel.add(discountField);

        panel.add(new JLabel(Localization.get("label.type")));
        DefaultComboBoxModel<TicketType> typeModel = new DefaultComboBoxModel<>();
        typeModel.addElement(null);
        for (TicketType tt : TicketType.values()) {
            typeModel.addElement(tt);
        }
        JComboBox<TicketType> typeCombo = new JComboBox<>(typeModel);
        typeCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText(Localization.get("text.none"));
                } else {
                    setText(value.toString());
                }
                return this;
            }
        });
        panel.add(typeCombo);

        panel.add(new JLabel(Localization.get("label.coord_x")));
        JTextField coordXField = new JTextField();
        panel.add(coordXField);

        panel.add(new JLabel(Localization.get("label.coord_y")));
        JTextField coordYField = new JTextField();
        panel.add(coordYField);

        panel.add(new JLabel(Localization.get("label.passport")));
        JTextField passportField = new JTextField();
        panel.add(passportField);

        panel.add(new JLabel(Localization.get("label.eye_color")));
        JComboBox<Color> eyeColorCombo = new JComboBox<>(Color.values());
        panel.add(eyeColorCombo);

        panel.add(new JLabel(Localization.get("label.loc_x")));
        JTextField locXField = new JTextField();
        panel.add(locXField);

        panel.add(new JLabel(Localization.get("label.loc_y")));
        JTextField locYField = new JTextField();
        panel.add(locYField);

        panel.add(new JLabel(Localization.get("label.loc_z")));
        JTextField locZField = new JTextField();
        panel.add(locZField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                Localization.get("dialog.add.title"),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String priceText = priceField.getText().trim();
            String discountText = discountField.getText().trim();
            TicketType type = (TicketType) typeCombo.getSelectedItem();
            String coordXText = coordXField.getText().trim();
            String coordYText = coordYField.getText().trim();
            String passportID = passportField.getText().trim();
            Color eyeColor = (Color) eyeColorCombo.getSelectedItem();
            String locXText = locXField.getText().trim();
            String locYText = locYField.getText().trim();
            String locZText = locZField.getText().trim();

            if (name.isEmpty() || priceText.isEmpty() || discountText.isEmpty() ||
                    coordXText.isEmpty() || coordYText.isEmpty() ||
                    passportID.isEmpty() || locXText.isEmpty() ||
                    locYText.isEmpty() || locZText.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        Localization.get("message.all_fields"),
                        Localization.get("message.error"),
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            try {
                long price = Long.parseLong(priceText);
                float discount = Float.parseFloat(discountText);
                float coordX = Float.parseFloat(coordXText);
                double coordY = Double.parseDouble(coordYText);
                long locX = Long.parseLong(locXText);
                Long locY = Long.parseLong(locYText);
                long locZ = Long.parseLong(locZText);

                if (!Ticket.validateName(name)) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_name"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (!Ticket.validatePrice(price)) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_price"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (!Ticket.validateDiscount(discount)) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_discount"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (!Coordinates.validateX(coordX)) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_coord_x"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (!Coordinates.validateY(coordY)) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_coord_y"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (!Person.validatePassportID(passportID)) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_passport"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                try {
                    if (!isMax) {
                        cch.invoke(new Add(new Ticket(
                                name,
                                price,
                                discount,
                                type,
                                new Coordinates(coordX, coordY),
                                new Person(passportID, eyeColor, new Location(locX, locY, locZ))
                        )));
                    } else {
                        cch.invoke(new AddIfMax(new Ticket(
                                name,
                                price,
                                discount,
                                type,
                                new Coordinates(coordX, coordY),
                                new Person(passportID, eyeColor, new Location(locX, locY, locZ))
                        )));
                    }
                } catch (ConnectionLostException e) {
                    e.printStackTrace();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        Localization.get("message.numeric_invalid"),
                        Localization.get("message.error"),
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void showUpdateTicketDialog() {
        String idInput = JOptionPane.showInputDialog(
                this,
                Localization.get("dialog.update.prompt.id"),
                Localization.get("dialog.update.step1"),
                JOptionPane.QUESTION_MESSAGE
        );
        if (idInput == null) return;

        idInput = idInput.trim();
        if (idInput.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    Localization.get("message.id_empty"),
                    Localization.get("message.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        int idToUpdate;
        try {
            idToUpdate = Integer.parseInt(idInput);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    Localization.get("message.invalid_id_format"),
                    Localization.get("message.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        try {
            cch.invoke(new CheckID(idToUpdate));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!cch.getFeedback().equals("1")) {
            JOptionPane.showMessageDialog(
                    this,
                    Localization.get("dialog.update.id_not_found"),
                    Localization.get("message.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.add(new JLabel(Localization.get("label.name")));
        JTextField nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel(Localization.get("label.price")));
        JTextField priceField = new JTextField();
        panel.add(priceField);

        panel.add(new JLabel(Localization.get("label.discount")));
        JTextField discountField = new JTextField();
        panel.add(discountField);

        panel.add(new JLabel(Localization.get("label.type")));
        DefaultComboBoxModel<TicketType> typeModel = new DefaultComboBoxModel<>();
        typeModel.addElement(null);
        for (TicketType tt : TicketType.values()) {
            typeModel.addElement(tt);
        }
        JComboBox<TicketType> typeCombo = new JComboBox<>(typeModel);
        typeCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value == null) {
                    setText(Localization.get("text.none"));
                } else {
                    setText(value.toString());
                }
                return this;
            }
        });
        panel.add(typeCombo);

        panel.add(new JLabel(Localization.get("label.coord_x")));
        JTextField coordXField = new JTextField();
        panel.add(coordXField);

        panel.add(new JLabel(Localization.get("label.coord_y")));
        JTextField coordYField = new JTextField();
        panel.add(coordYField);

        panel.add(new JLabel(Localization.get("label.passport")));
        JTextField passportField = new JTextField();
        panel.add(passportField);

        panel.add(new JLabel(Localization.get("label.eye_color")));
        JComboBox<Color> eyeColorCombo = new JComboBox<>(Color.values());
        panel.add(eyeColorCombo);

        panel.add(new JLabel(Localization.get("label.loc_x")));
        JTextField locXField = new JTextField();
        panel.add(locXField);

        panel.add(new JLabel(Localization.get("label.loc_y")));
        JTextField locYField = new JTextField();
        panel.add(locYField);

        panel.add(new JLabel(Localization.get("label.loc_z")));
        JTextField locZField = new JTextField();
        panel.add(locZField);

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                Localization.format("dialog.update.title", idToUpdate),
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim().isEmpty() ? "-" : nameField.getText().trim();
            String priceText = priceField.getText().trim().isEmpty() ? "-" : priceField.getText().trim();
            String discountText = discountField.getText().trim().isEmpty() ? "-" : discountField.getText().trim();
            TicketType type = (TicketType) typeCombo.getSelectedItem();
            String coordXText = coordXField.getText().trim().isEmpty() ? "-" : coordXField.getText().trim();
            String coordYText = coordYField.getText().trim().isEmpty() ? "-" : coordYField.getText().trim();
            String passportID = passportField.getText().trim().isEmpty() ? "-" : passportField.getText().trim();
            Color eyeColor = (Color) eyeColorCombo.getSelectedItem();
            String locXText = locXField.getText().trim().isEmpty() ? "-" : locXField.getText().trim();
            String locYText = locYField.getText().trim().isEmpty() ? "-" : locYField.getText().trim();
            String locZText = locZField.getText().trim().isEmpty() ? "-" : locZField.getText().trim();

            try {
                if (!(name.equals("-") || Ticket.validateName(name))) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_name"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (!(priceText.equals("-") || Ticket.validatePrice(Long.parseLong(priceText)))) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_price"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (!(discountText.equals("-") || Ticket.validateDiscount(Float.parseFloat(discountText)))) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_discount"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (!(coordXText.equals("-") || Coordinates.validateX(Float.parseFloat(coordXText)))) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_coord_x"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (!(coordYText.equals("-") || Coordinates.validateY(Double.parseDouble(coordYText)))) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_coord_y"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (!(passportID.equals("-") || Person.validatePassportID(passportID))) {
                    JOptionPane.showMessageDialog(
                            this,
                            Localization.get("message.invalid_passport"),
                            Localization.get("message.error"),
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                String typeText = (type == null) ? null : type.toString();
                cch.invoke(new Update(
                        idToUpdate,
                        new StringifiedTicket(
                                name,
                                priceText,
                                discountText,
                                typeText,
                                new StringifiedCoordinates(coordXText, coordYText),
                                new StringifiedPerson(
                                        passportID,
                                        (eyeColor == null ? null : eyeColor.toString()),
                                        new StringifiedLocation(locXText, locYText, locZText)
                                )
                        )
                ));

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        this,
                        Localization.get("message.numeric_invalid"),
                        Localization.get("message.error"),
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (ConnectionLostException e) {
                e.printStackTrace();
            }
        }
    }

    private class TicketAnimatedPanel extends JPanel {
        private final List<Ticket> allTickets;
        private final List<AnimatedTicket> animatedList;
        private int nextTicketIndex = 0;

        private float minX, maxX, minY, maxY;
        private static final int MARGIN = 40;
        private static final int PANEL_UPDATE_MS = 40;
        private static final int ADD_INTERVAL_MS = 500;

        private int timeSinceLastAdd = 0;
        private Timer timer;

        public TicketAnimatedPanel(List<Ticket> tickets) {
            this.allTickets = tickets;
            this.animatedList = new ArrayList<>();
            setBackground(java.awt.Color.WHITE);

            minX = minY = -1;
            maxX = maxY = 1;

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int mx = e.getX();
                    int my = e.getY();
                    for (int i = animatedList.size() - 1; i >= 0; i--) {
                        AnimatedTicket at = animatedList.get(i);
                        int px = at.getPx();
                        int py = at.getPy();
                        int radius = at.getCurrentRadius();
                        int dx = mx - px;
                        int dy = my - py;
                        if (dx * dx + dy * dy <= radius * radius) {
                            Ticket t = at.getTicket();
                            StringBuilder info = new StringBuilder();
                            info.append(Localization.get("info.id")).append(t.getId()).append("\n");
                            info.append(Localization.get("info.name")).append(t.getName()).append("\n");
                            info.append(Localization.get("info.price")).append(t.getPrice()).append("\n");
                            info.append(Localization.get("info.discount")).append(t.getDiscount()).append("\n");
                            info.append(Localization.get("info.type")).append(t.getType()).append("\n");
                            info.append(Localization.get("info.coord_x"))
                                    .append(t.getCoordinates().getX()).append("\n");
                            info.append(Localization.get("info.coord_y"))
                                    .append(t.getCoordinates().getY()).append("\n");
                            info.append(Localization.get("info.passport"))
                                    .append(t.getPerson().getPassportID()).append("\n");
                            info.append(Localization.get("info.eye_color"))
                                    .append(t.getPerson().getEyeColor()).append("\n");
                            info.append(Localization.get("info.loc_x"))
                                    .append(t.getPerson().getLocation().getX()).append("\n");
                            info.append(Localization.get("info.loc_y"))
                                    .append(t.getPerson().getLocation().getY()).append("\n");
                            info.append(Localization.get("info.loc_z"))
                                    .append(t.getPerson().getLocation().getZ()).append("\n");
                            info.append(Localization.get("info.creation_date"))
                                    .append(t.getCreationDate()).append("\n");
                            info.append(Localization.get("info.creator"))
                                    .append(t.getCreator()).append("\n");
                            JOptionPane.showMessageDialog(
                                    TicketAnimatedPanel.this,
                                    info.toString(),
                                    Localization.get("table.border"),
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            break;
                        }
                    }
                }
            });

            timer = new Timer(PANEL_UPDATE_MS, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timeSinceLastAdd += PANEL_UPDATE_MS;
                    if (timeSinceLastAdd >= ADD_INTERVAL_MS && nextTicketIndex < allTickets.size()) {
                        addNextTicket();
                        timeSinceLastAdd = 0;
                    }

                    boolean needsRepaint = false;
                    for (AnimatedTicket at : animatedList) {
                        if (!at.isDone()) {
                            at.update();
                            needsRepaint = true;
                        }
                    }

                    if (needsRepaint || nextTicketIndex < allTickets.size()) {
                        repaint();
                    } else {
                        timer.stop();
                    }
                }
            });
        }

        public void resetAndStartAnimation() {
            computeDataBounds();
            animatedList.clear();
            nextTicketIndex = 0;
            timeSinceLastAdd = ADD_INTERVAL_MS;

            if (timer.isRunning()) {
                timer.stop();
            }
            timer.start();
        }

        private void computeDataBounds() {
            if (allTickets.isEmpty()) {
                minX = minY = -1;
                maxX = maxY = 1;
                return;
            }
            minX = Float.MAX_VALUE;
            maxX = -Float.MAX_VALUE;
            minY = Float.MAX_VALUE;
            maxY = -Float.MAX_VALUE;
            for (Ticket t : allTickets) {
                float x = t.getCoordinates().getX();
                float y = t.getCoordinates().getY().floatValue();
                minX = Math.min(minX, x);
                maxX = Math.max(maxX, x);
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
            }
            if (minX == maxX) {
                minX -= 1;
                maxX += 1;
            }
            if (minY == maxY) {
                minY -= 1;
                maxY += 1;
            }
        }

        private void addNextTicket() {
            Ticket t = allTickets.get(nextTicketIndex++);
            int w = getWidth() - 2 * MARGIN;
            int h = getHeight() - 2 * MARGIN;

            float dataX = t.getCoordinates().getX();
            float dataY = t.getCoordinates().getY().floatValue();
            int px = MARGIN + Math.round((dataX - minX) / (maxX - minX) * w);
            int py = MARGIN + Math.round((maxY - dataY) / (maxY - minY) * h);

            double maxPrice = allTickets.stream().mapToDouble(Ticket::getPrice).max().orElse(t.getPrice());
            double minPrice = allTickets.stream().mapToDouble(Ticket::getPrice).min().orElse(t.getPrice());
            int finalRadius = 8;
            if (maxPrice > minPrice) {
                finalRadius = 8 + (int) (
                        (t.getPrice() - minPrice) / (maxPrice - minPrice) * (24 - 8)
                );
            }
            animatedList.add(new AnimatedTicket(t, px, py, finalRadius));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (allTickets.isEmpty()) return;

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            int panelWidth = getWidth();
            int panelHeight = getHeight();

            g2.setColor(java.awt.Color.LIGHT_GRAY);
            g2.drawLine(MARGIN, panelHeight - MARGIN, panelWidth - MARGIN, panelHeight - MARGIN);
            g2.drawLine(MARGIN, MARGIN, MARGIN, panelHeight - MARGIN);

            for (AnimatedTicket at : animatedList) {
                Ticket t = at.getTicket();
                int px = at.getPx();
                int py = at.getPy();
                int radius = at.getCurrentRadius();
                if (radius <= 0) continue;

                String creator = t.getCreator();
                java.awt.Color fillColor = getColorFromString(creator);

                g2.setColor(fillColor);
                g2.fillOval(px - radius, py - radius, radius * 2, radius * 2);

                if (at.isDone()) {
                    g2.setColor(java.awt.Color.BLACK);
                } else {
                    g2.setColor(fillColor.darker());
                }
                g2.drawOval(px - radius, py - radius, radius * 2, radius * 2);

                if (at.isDone()) {
                    g2.setColor(java.awt.Color.BLACK);
                    g2.setFont(g2.getFont().deriveFont(10f));
                    g2.drawString(String.valueOf(t.getId()), px + radius + 2, py - radius - 2);
                }
            }

            drawLegend(g2, MARGIN, MARGIN / 2);
            g2.dispose();
        }

        private void drawLegend(Graphics2D g2, int x, int y) {
            int boxSize = 12;
            int spacing = 6;
            Font originalFont = g2.getFont();
            g2.setFont(originalFont.deriveFont(12f));

            Set<String> creators = new LinkedHashSet<>();
            for (Ticket t : allTickets) {
                if (t.getCreator() != null) {
                    creators.add(t.getCreator());
                }
            }

            int idx = 0;
            for (String creator : creators) {
                java.awt.Color color = getColorFromString(creator);
                int rowY = y + idx * (boxSize + spacing);
                g2.setColor(color);
                g2.fillRect(x, rowY, boxSize, boxSize);
                g2.setColor(java.awt.Color.BLACK);
                g2.drawString(creator, x + boxSize + spacing, rowY + boxSize);
                idx++;
            }

            g2.setFont(originalFont);
        }

        private java.awt.Color getColorFromString(String s) {
            if (s == null) {
                return java.awt.Color.GRAY;
            }
            int hash = s.hashCode();
            int r = (hash & 0xFF0000) >> 16;
            int g = (hash & 0x00FF00) >> 8;
            int b = (hash & 0x0000FF);
            r = (r < 0) ? 0 : (r > 255 ? 255 : r);
            g = (g < 0) ? 0 : (g > 255 ? 255 : g);
            b = (b < 0) ? 0 : (b > 255 ? 255 : b);
            return new java.awt.Color(r, g, b);
        }

        private class AnimatedTicket {
            private final Ticket ticket;
            private final int px, py;
            private final int finalRadius;
            private int currentRadius = 0;
            private final int growthStep;
            private boolean done = false;

            public AnimatedTicket(Ticket t, int px, int py, int finalRadius) {
                this.ticket = t;
                this.px = px;
                this.py = py;
                this.finalRadius = finalRadius;
                this.growthStep = Math.max(1, finalRadius / 8);
            }

            public Ticket getTicket() {
                return ticket;
            }

            public int getPx() {
                return px;
            }

            public int getPy() {
                return py;
            }

            public int getCurrentRadius() {
                return currentRadius;
            }

            public boolean isDone() {
                return done;
            }

            public void update() {
                if (done) return;
                currentRadius += growthStep;
                if (currentRadius >= finalRadius) {
                    currentRadius = finalRadius;
                    done = true;
                }
            }
        }
    }
}
