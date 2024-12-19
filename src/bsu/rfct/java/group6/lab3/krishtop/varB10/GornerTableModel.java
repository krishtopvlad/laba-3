package bsu.rfct.java.group6.lab3.krishtop.varB10;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class GornerTableModel extends AbstractTableModel {

    private Double[] coefficients;
    private Double from;
    private Double to;
    private Double step;

    public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) {
        this.from = from;
        this.to = to;
        this.step = step;
        this.coefficients = coefficients;
    }

    public Double getFrom() {
        return from;
    }

    public Double getTo() {
        return to;
    }

    public Double getStep() {
        return step;
    }

    @Override
    public int getColumnCount() {
        return 3; // Два столбца: X и значение многочлена
    }

    @Override
    public int getRowCount() {
        return (int) (Math.ceil((to - from) / step)) + 1;
    }

    @Override
    public Object getValueAt(int row, int col) {
        double x = from + step * row;

        if (col == 0) {
            return x; // Значение X
        } else if (col == 1) {
            double result = 0.0;
            for (int i = 0; i < coefficients.length; i++) {
                result += coefficients[i] * Math.pow(x, coefficients.length - 1 - i);
            }
            return result;  // Значение многочлена
        } else if (col == 2) { // Ограниченная симметрия
            String valueStr = String.valueOf(getValueAt(row, 1)); // Значение многочлена как строка

            // Проверка на NaN и Infinity
            if (valueStr.equals("NaN") || valueStr.equals("Infinity") || valueStr.equals("-Infinity")) {
                return false;
            }

            String[] parts = valueStr.split("\\.");
            if (parts.length == 2) {
                String integerPart = parts[0];
                String fractionalPart = parts[1];
                if (integerPart.equals(reverseString(fractionalPart))) {
                    return true;
                }
            }
            return false;
        }

        return null; // Или другое значение по умолчанию, если col > 2
    }

    public static String reverseString(String str) {
        return new StringBuilder(str).reverse().toString();
    }

    @Override
    public String getColumnName(int col) {
        switch (col) {
            case 0:
                return "Значение X";
            case 1:
                return "Значение многочлена";
            case 2:
                return "Ограниченная симметрия";
            default:
                return "";
        }
    }

    public Class<?> getColumnClass(int col) {
        if (col == 2) {
            return Boolean.class; // Третий столбец имеет тип Boolean
        } else {
            return Double.class;
        }
    }
}

