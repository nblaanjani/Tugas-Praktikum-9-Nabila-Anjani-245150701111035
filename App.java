import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Path dataDir = Paths.get("data");
        Path csvFile = dataDir.resolve("products.csv");
        InventoryService service = new InventoryService(csvFile);

        // 1) Inisialisasi folder data
        if (!Files.exists(dataDir)) {
            Files.createDirectories(dataDir);
            System.out.println("Folder 'data/' berhasil dibuat.");
        }

        // 2) Load data dari CSV
        try {
            service.loadFromCSV();
            System.out.println("Data produk berhasil dimuat.");
        } catch (IOException e) {
            System.out.println("Gagal memuat data: " + e.getMessage());
        }

        // 3) Jalankan menu interaktif
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("\n=== INVENTORY MANAGER TOKO ===");
            System.out.println("1. Lihat semua produk");
            System.out.println("2. Tambah produk");
            System.out.println("3. Perbarui stok produk");
            System.out.println("4. Hapus produk");
            System.out.println("5. Cari produk berdasarkan nama");
            System.out.println("6. Sortir produk");
            System.out.println("7. Filter produk berdasarkan harga");
            System.out.println("8. Keluar");
            System.out.print("Pilih menu: ");

            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1" -> service.readAll();
                case "2" -> service.create(scanner);
                case "3" -> service.update(scanner);
                case "4" -> service.delete(scanner);
                case "5" -> {
                    System.out.print("Masukkan nama produk yang ingin dicari: ");
                    String keyword = scanner.nextLine();
                    service.searchByName(keyword);
                }
                case "6" -> {
                    System.out.print("Pilih kriteria sortir (price/quantity): ");
                    String criteria = scanner.nextLine();
                    service.sort(criteria);
                }
                case "7" -> {
                    System.out.print("Masukkan harga minimum: ");
                    double min = Double.parseDouble(scanner.nextLine());
                    System.out.print("Masukkan harga maksimum: ");
                    double max = Double.parseDouble(scanner.nextLine());
                    service.filterByPriceRange(min, max);
                }
                case "8" -> {
                    running = false;
                    System.out.println("Keluar dari aplikasi...");
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }

        // 4) Simpan data kembali ke CSV sebelum keluar
        try {
            service.saveToCSV();
            System.out.println("Data berhasil disimpan ke CSV.");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data: " + e.getMessage());
        }

        scanner.close();
    }
}
