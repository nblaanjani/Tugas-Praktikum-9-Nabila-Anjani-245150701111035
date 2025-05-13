import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class InventoryService {
    private List<Product> products;
    private Path csvFile;

    public InventoryService(Path csvFile) {
        this.csvFile = csvFile;
        this.products = new ArrayList<>();
    }

    // Load produk dari file CSV
    public void loadFromCSV() throws IOException {
        if (Files.exists(csvFile)) {
            try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
                reader.lines().skip(1).forEach(line -> products.add(Product.fromCsvLine(line)));
            }
        }
    }

    // Simpan produk ke file CSV
    public void saveToCSV() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(csvFile)) {
            writer.write("id,name,category,price,quantity\n");
            for (Product product : products) {
                writer.write(product.toCsvLine() + "\n");
            }
        }
    }

    // Lihat semua produk
    public void readAll() {
        if (products.isEmpty()) {
            System.out.println("Tidak ada produk.");
        } else {
            System.out.println("ID | Nama Produk          | Kategori        | Harga      | Stok ");
            System.out.println("---------------------------------------------------------------");
            products.forEach(System.out::println);
        }
    }

    // Tambah produk
    public void create(Scanner scanner) {
        System.out.print("Masukkan nama produk: ");
        String name = scanner.nextLine();
        System.out.print("Masukkan kategori produk: ");
        String category = scanner.nextLine();
        System.out.print("Masukkan harga produk: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Masukkan jumlah produk: ");
        int quantity = Integer.parseInt(scanner.nextLine());
        
        int id = products.size() + 1;
        Product newProduct = new Product(id, name, category, price, quantity);
        products.add(newProduct);
        System.out.println("Produk berhasil ditambahkan.");
    }

    // Update kuantitas produk
    public void update(Scanner scanner) {
        System.out.print("Masukkan ID produk untuk diupdate: ");
        int id = Integer.parseInt(scanner.nextLine());
        Optional<Product> productOpt = products.stream().filter(p -> p.getId() == id).findFirst();

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            System.out.print("Masukkan kuantitas baru: ");
            int newQuantity = Integer.parseInt(scanner.nextLine());
            product.setQuantity(newQuantity);
            System.out.println("Stok produk berhasil diperbarui.");
        } else {
            System.out.println("Produk tidak ditemukan.");
        }
    }

    // Hapus produk
    public void delete(Scanner scanner) {
        System.out.print("Masukkan ID produk untuk dihapus: ");
        int id = Integer.parseInt(scanner.nextLine());
        products.removeIf(p -> p.getId() == id);
        System.out.println("Produk berhasil dihapus.");
    }

    // Cari produk berdasarkan nama
    public void searchByName(String keyword) {
        List<Product> results = products.stream()
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            System.out.println("Produk tidak ditemukan.");
        } else {
            System.out.println("ID | Nama Produk          | Kategori        | Harga      | Stok ");
            System.out.println("---------------------------------------------------------------");
            results.forEach(System.out::println);
        }
    }

    // Sortir produk berdasarkan kriteria (price/quantity)
    public void sort(String criteria) {
        Comparator<Product> comparator = "price".equalsIgnoreCase(criteria) ?
                Comparator.comparingDouble(Product::getPrice) :
                Comparator.comparingInt(Product::getQuantity);

        products.sort(comparator);
        readAll(); // Tampilkan hasil setelah sortir
    }

    // Filter produk berdasarkan rentang harga
    public void filterByPriceRange(double min, double max) {
        List<Product> filtered = products.stream()
                .filter(p -> p.getPrice() >= min && p.getPrice() <= max)
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("Tidak ada produk dalam rentang harga tersebut.");
        } else {
            System.out.println("ID | Nama Produk          | Kategori        | Harga      | Stok ");
            System.out.println("---------------------------------------------------------------");
            filtered.forEach(System.out::println);
        }
    }

    // Mendapatkan list produk
    public List<Product> getProducts() {
        return products;
    }
}
