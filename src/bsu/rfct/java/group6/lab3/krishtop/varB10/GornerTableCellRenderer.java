package bsu.rfct.java.group6.lab3.krishtop.varB10;

import javax.swing.*;
import java.awt.Component;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

public class GornerTableCellRenderer implements TableCellRenderer {
    private JPanel panel = new JPanel();
    private JLabel label = new JLabel();
    private String needle = null;
    private DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance();

    public GornerTableCellRenderer() {
        formatter.setMaximumFractionDigits(5);
        formatter.setGroupingUsed(false);
        DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols();
        dottedDouble.setDecimalSeparator('.');
        formatter.setDecimalFormatSymbols(dottedDouble);
        panel.add(label);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        String formattedDouble = formatter.format(value); // Форматируем значение
        label.setText(formattedDouble); // Устанавливаем текст метки


        if (!isSelected) { // Если ячейка не выделена
            if (col == 1 || col==0)  { // Работаем только со вторым столбцом
                double doubleValue;
                // Безопасное преобразование к double
                try {
                    doubleValue = Double.parseDouble(formattedDouble);
                } catch (NumberFormatException e) {
                    doubleValue = 0; // Или другое значение по умолчанию при ошибке
                }

                String[] parts = formattedDouble.split("\\.");
                if (parts.length == 2) {
                    String fractionalPart = parts[1];
                    int sum = 0;

                    for (char c : fractionalPart.toCharArray()) {
                        if (Character.isDigit(c)) { // Проверяем, что символ - цифра
                            sum += Character.getNumericValue(c);
                        }
                    }


                    if (sum % 10 == 0) {
                        panel.setBackground(Color.CYAN); // Голубой
                    } else {
                        panel.setBackground(Color.WHITE); // Белый
                    }
                } else {
                    panel.setBackground(Color.WHITE); // Белый, если нет дробной части
                }



            } else {
                panel.setBackground(Color.WHITE); // Белый для остальных столбцов
            }

            if (col == 1 && needle != null && needle.equals(formattedDouble)) { // Выделение красным
                panel.setBackground(Color.RED);
            }
        }
        else {
            panel.setBackground(table.getSelectionBackground()); // Цвет выделения таблицы

        }

        return panel;
    }

    public void setNeedle(String needle) {
        this.needle = needle;
    }
}
