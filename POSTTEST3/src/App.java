import java.util.ArrayList;
import java.util.Scanner;

class Sembako {
    private String kodeBarang;
    private String namaBarang;
    private int jumlahStok;
    private double hargaSatuan;

    public Sembako(String kodeBarang, String namaBarang, int jumlahStok, double hargaSatuan) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.jumlahStok = jumlahStok;
        this.hargaSatuan = hargaSatuan;
    }

    public void tampilkanInfo() {
        System.out.print("Kode: " + kodeBarang + " | Nama: " + namaBarang + 
                         " | Stok: " + jumlahStok + " | Harga: Rp" + hargaSatuan);
    }

    // Getter & Setter
    public String getKodeBarang() { return kodeBarang; }
    public String getNamaBarang() { return namaBarang; }
    public int getJumlahStok() { return jumlahStok; }
    public double getHargaSatuan() { return hargaSatuan; }

    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }
    public void setJumlahStok(int jumlahStok) { this.jumlahStok = jumlahStok; }
    public void setHargaSatuan(double hargaSatuan) { this.hargaSatuan = hargaSatuan; }
}

class SembakoCair extends Sembako {
    private double volumeLiter;

    public SembakoCair(String kode, String nama, int stok, double harga, double volume) {
        super(kode, nama, stok, harga);
        this.volumeLiter = volume;
    }

    public double getVolumeLiter() { return volumeLiter; }
    public void setVolumeLiter(double volumeLiter) { this.volumeLiter = volumeLiter; }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println(" | Jenis: Cair (" + volumeLiter + " Liter)");
    }
}

class SembakoPadat extends Sembako {
    private double beratKg;

    public SembakoPadat(String kode, String nama, int stok, double harga, double berat) {
        super(kode, nama, stok, harga);
        this.beratKg = berat;
    }

    public double getBeratKg() { return beratKg; }
    public void setBeratKg(double beratKg) { this.beratKg = beratKg; }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println(" | Jenis: Padat (" + beratKg + " Kg)");
    }
}

public class App {
    static ArrayList<Sembako> daftarSembako = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n--- SISTEM MANAJEMEN GUDANG SEMBAKO ---");
            System.out.println("1. Tambah Barang Cair");
            System.out.println("2. Tambah Barang Padat");
            System.out.println("3. Tampilkan Semua Barang");
            System.out.println("4. Ubah Data Barang (Edit)");
            System.out.println("5. Hapus Data");
            System.out.println("6. Keluar");
            System.out.print("Pilih menu: ");
            
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1: tambahBarang(true); break;
                case 2: tambahBarang(false); break;
                case 3: tampilkanData(); break;
                case 4: ubahData(); break;
                case 5: hapusData(); break;
                case 6: 
                    berjalan = false;
                    System.out.println("Program Selesai.");
                    break;
                default: System.out.println("Pilihan salah!");
            }
        }
    }

    static void tambahBarang(boolean isCair) {
        System.out.print("Kode: "); String kode = scanner.nextLine();
        System.out.print("Nama: "); String nama = scanner.nextLine();
        System.out.print("Stok: "); int stok = scanner.nextInt();
        System.out.print("Harga: "); double harga = scanner.nextDouble();
        
        if (isCair) {
            System.out.print("Volume (Liter): "); double vol = scanner.nextDouble();
            daftarSembako.add(new SembakoCair(kode, nama, stok, harga, vol));
        } else {
            System.out.print("Berat (Kg): "); double berat = scanner.nextDouble();
            daftarSembako.add(new SembakoPadat(kode, nama, stok, harga, berat));
        }
        System.out.println("Barang berhasil ditambahkan!");
    }

    static void tampilkanData() {
        System.out.println("\n-- DAFTAR BARANG GUDANG --");
        if (daftarSembako.isEmpty()) {
            System.out.println("Gudang Kosong.");
        } else {
            for (int i = 0; i < daftarSembako.size(); i++) {
                System.out.print((i + 1) + ". ");
                daftarSembako.get(i).tampilkanInfo();
            }
        }
    }

    static void ubahData() {
        tampilkanData();
        if (daftarSembako.isEmpty()) return;

        System.out.print("\nPilih nomor barang yang ingin diubah: ");
        int no = scanner.nextInt();
        scanner.nextLine();

        if (no > 0 && no <= daftarSembako.size()) {
            Sembako barang = daftarSembako.get(no - 1);
            
            System.out.print("Nama Baru (" + barang.getNamaBarang() + "): ");
            String namaBaru = scanner.nextLine();
            if(!namaBaru.isEmpty()) barang.setNamaBarang(namaBaru);

            System.out.print("Stok Baru (" + barang.getJumlahStok() + "): ");
            barang.setJumlahStok(scanner.nextInt());

            System.out.print("Harga Baru (" + barang.getHargaSatuan() + "): ");
            barang.setHargaSatuan(scanner.nextDouble());
            
            // Logika spesifik menggunakan instanceof (Cek tipe Subclass)
            if (barang instanceof SembakoCair) {
                SembakoCair bc = (SembakoCair) barang;
                System.out.print("Volume Baru (" + bc.getVolumeLiter() + " L): ");
                bc.setVolumeLiter(scanner.nextDouble());
            } else if (barang instanceof SembakoPadat) {
                SembakoPadat bp = (SembakoPadat) barang;
                System.out.print("Berat Baru (" + bp.getBeratKg() + " Kg): ");
                bp.setBeratKg(scanner.nextDouble());
            }

            System.out.println("Data berhasil diperbarui!");
        } else {
            System.out.println("Nomor tidak valid.");
        }
    }

    static void hapusData() {
        tampilkanData();
        if (!daftarSembako.isEmpty()) {
            System.out.print("\nNomor yang dihapus: ");
            int no = scanner.nextInt();
            if (no > 0 && no <= daftarSembako.size()) {
                daftarSembako.remove(no - 1);
                System.out.println("Data berhasil dihapus.");
            }
        }
    }
}