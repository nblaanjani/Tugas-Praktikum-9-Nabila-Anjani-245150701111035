import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String category;
    private double price;
    private int quantity;

    public Product(int id, String name, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Metode untuk konversi ke format CSV
    public String toCsvLine() {
        return id + "," + name + "," + category + "," + price + "," + quantity;
    }

    // Metode untuk membuat objek Product dari baris CSV
    public static Product fromCsvLine(String csvLine) {
        String[] tokens = csvLine.split(",");
        int id = Integer.parseInt(tokens[0]);
        String name = tokens[1];
        String category = tokens[2];
        double price = Double.parseDouble(tokens[3]);
        int quantity = Integer.parseInt(tokens[4]);
        return new Product(id, name, category, price, quantity);
    }

    @Override
    public String toString() {
        return String.format("%d | %-20s | %-15s | %-10.2f | %-5d", id, name, category, price, quantity);
    }
}
