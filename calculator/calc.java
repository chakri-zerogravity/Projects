package calculator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class calc {
    private JFrame frame;
    private JTextField display;
    private double operand = 0;
    private String operator = "";
    private boolean startNewNumber = true;

    public calc() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(320, 420);
        frame.setResizable(false);

        display = new JTextField("0");
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setEditable(false);

        JButton clearBtn = new JButton("C");
        clearBtn.setFont(new Font("Arial", Font.BOLD, 18));
        clearBtn.addActionListener(e -> clear());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(display, BorderLayout.CENTER);
        topPanel.add(clearBtn, BorderLayout.EAST);

        JPanel buttons = new JPanel(new GridLayout(4, 4, 6, 6));
        String[] labels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String label : labels) {
            JButton btn = new JButton(label);
            btn.setFont(new Font("Arial", Font.PLAIN, 18));
            btn.addActionListener(new ButtonHandler());
            buttons.add(btn);
        }

        JPanel content = new JPanel();
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.setLayout(new BorderLayout(8, 8));
        content.add(topPanel, BorderLayout.NORTH);
        content.add(buttons, BorderLayout.CENTER);

        frame.setContentPane(content);
        frame.setLocationRelativeTo(null);
    }

    private void clear() {
        operand = 0;
        operator = "";
        startNewNumber = true;
        display.setText("0");
    }

    private void doOperator(String op) {
        double displayed = parseDisplay();
        if (!operator.isEmpty() && !startNewNumber) {
            operand = calculate(operand, displayed, operator);
            display.setText(formatDouble(operand));
        } else {
            operand = displayed;
        }
        operator = op;
        startNewNumber = true;
    }

    private void doEquals() {
        double displayed = parseDisplay();
        if (!operator.isEmpty()) {
            double result = calculate(operand, displayed, operator);
            display.setText(formatDouble(result));
            operator = "";
            operand = result;
            startNewNumber = true;
        }
    }

    private double parseDisplay() {
        try {
            return Double.parseDouble(display.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String formatDouble(double v) {
        if (v == (long) v) return String.format("%d", (long) v);
        return String.valueOf(v);
    }

    private double calculate(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/":
                if (b == 0) {
                    JOptionPane.showMessageDialog(frame, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                    return a;
                }
                return a / b;
            default: return b;
        }
    }

    private class ButtonHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();
            if ("0123456789".contains(cmd)) {
                if (startNewNumber) {
                    display.setText(cmd);
                    startNewNumber = false;
                } else {
                    display.setText(display.getText().equals("0") ? cmd : display.getText() + cmd);
                }
            } else if (".+-*/".contains(cmd)) {
                if (".+-*/".equals(cmd)) return; // safety
                if ("+".equals(cmd) || "-".equals(cmd) || "*".equals(cmd) || "/".equals(cmd)) {
                    doOperator(cmd);
                }
            } else if (".".equals(cmd)) {
                if (startNewNumber) {
                    display.setText("0.");
                    startNewNumber = false;
                } else if (!display.getText().contains(".")) {
                    display.setText(display.getText() + ".");
                }
            } else if ("=".equals(cmd)) {
                doEquals();
            }
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            calc app = new calc();
            app.show();
        });
    }
}
