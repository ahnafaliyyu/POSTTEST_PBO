import java.util.ArrayList;
import java.util.Scanner;

//  INTERFACE (Minimal 2 Method) 
interface ManajemenBarang {
    void cekKetersediaan(); // Method 1
    String getKategori();   // Method 2
}

//ABSTRACT CLASS (Superclass) 
abstract class Sembako implements ManajemenBarang {
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

    // [ABSTRACT METHOD] - Wajib diimplementasikan subclass untuk menentukan satuan
    public abstract String getUnit();

    // Implementasi Method 1 dari Interface
    @Override
    public void cekKetersediaan() {
        if (jumlahStok > 10) {
            System.out.println("Status: Stok Aman");
        } else {
            System.out.println("Status: Stok Menipis (Segera Re-stock!)");
        }
    }

    public void tampilkanInfo() {
        System.out.print("Kode: " + kodeBarang + " | Nama: " + namaBarang +
                         " | Stok: " + jumlahStok + " | Harga: Rp" + hargaSatuan);
    }

    public double hitungNilaiStok() {
        return jumlahStok * hargaSatuan;
    }

    // Getter & Setter
    public String getKodeBarang() { return kodeBarang; }
    public String getNamaBarang() { return namaBarang; }
    public int getJumlahStok()    { return jumlahStok; }
    public double getHargaSatuan(){ return hargaSatuan; }

    public void setNamaBarang(String namaBarang)   { this.namaBarang = namaBarang; }
    public void setJumlahStok(int jumlahStok)      { this.jumlahStok = jumlahStok; }
    public void setHargaSatuan(double hargaSatuan) { this.hargaSatuan = hargaSatuan; }
}

//  SUBCLASS 1: CAIR 
class SembakoCair extends Sembako {
    private double volumeLiter;

    public SembakoCair(String kode, String nama, int stok, double harga, double volume) {
        super(kode, nama, stok, harga);
        this.volumeLiter = volume;
    }

    @Override
    public String getUnit() { return "Liter"; }

    @Override
    public String getKategori() { return "Cairan/Sembako Basah"; }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println(" | Jenis: Cair (" + volumeLiter + " " + getUnit() + ")");
    }

    @Override
    public double hitungNilaiStok() {
        return super.hitungNilaiStok() + (super.hitungNilaiStok() * 0.05); // +5% biaya penanganan
    }

    public double getVolumeLiter() { return volumeLiter; }
    public void setVolumeLiter(double vol) { this.volumeLiter = vol; }
}

//  SUBCLASS 2: PADAT 
class SembakoPadat extends Sembako {
    private double beratKg;

    public SembakoPadat(String kode, String nama, int stok, double harga, double berat) {
        super(kode, nama, stok, harga);
        this.beratKg = berat;
    }

    @Override
    public String getUnit() { return "Kg"; }

    @Override
    public String getKategori() { return "Barang Kering/Padat"; }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println(" | Jenis: Padat (" + beratKg + " " + getUnit() + ")");
    }

    @Override
    public double hitungNilaiStok() {
        return super.hitungNilaiStok() - (super.hitungNilaiStok() * 0.03); // -3% diskon
    }

    public double getBeratKg() { return beratKg; }
    public void setBeratKg(double beratKg) { this.beratKg = beratKg; }
}

//  MAIN CLASS 
public class App {
    static ArrayList<Sembako> daftarSembako = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean berjalan = true;
        while (berjalan) {
            System.out.println("\n SISTEM MANAJEMEN GUDANG (ABSTRACT & INTERFACE) ");
            System.out.println("1. Tambah Barang Cair");
            System.out.println("2. Tambah Barang Padat");
            System.out.println("3. Tampilkan Semua Barang (Read)");
            System.out.println("4. Ubah Data Barang (Update)");
            System.out.println("5. Hapus Data (Delete)");
            System.out.println("6. Cari Barang (by Kode)");
            System.out.println("7. Cari Barang (by Nama)");
            System.out.println("8. Tampilkan Nilai Stok (Polymorphism)");
            System.out.println("9. Keluar");
            System.out.print("Pilih menu: ");

            try {
                int pilihan = scanner.nextInt();
                scanner.nextLine();

                switch (pilihan) {
                    case 1: tambahBarang(true);  break;
                    case 2: tambahBarang(false); break;
                    case 3: tampilkanData();     break;
                    case 4: ubahData();          break;
                    case 5: hapusData();         break;
                    case 6: cariBarangByKode();  break;
                    case 7: cariBarangByNama();  break;
                    case 8: tampilkanNilaiStok();break;
                    case 9:
                        berjalan = false;
                        System.out.println("Program Selesai.");
                        break;
                    default: System.out.println("Pilihan salah!");
                }
            } catch (Exception e) {
                System.out.println("Input harus berupa angka!");
                scanner.nextLine();
            }
        }
    }

    //  LOGIKA CRUD (TETAP DIPERTAHANKAN) 

    static void tambahBarang(boolean isCair) {
        System.out.print("Kode: "); String kode = scanner.nextLine();
        System.out.print("Nama: "); String nama = scanner.nextLine();
        System.out.print("Stok: "); int stok = scanner.nextInt();
        System.out.print("Harga: "); double harga = scanner.nextDouble();

        if (isCair) {
            System.out.print("Volume (Liter): "); double vol = scanner.nextDouble();
            scanner.nextLine();
            prosesTambah(new SembakoCair(kode, nama, stok, harga, vol));
        } else {
            System.out.print("Berat (Kg): "); double berat = scanner.nextDouble();
            scanner.nextLine();
            prosesTambah(new SembakoPadat(kode, nama, stok, harga, berat));
        }
    }

    static void prosesTambah(Sembako barang) {
        for (Sembako b : daftarSembako) {
            if (b.getKodeBarang().equalsIgnoreCase(barang.getKodeBarang())) {
                System.out.println("Gagal! Kode '" + barang.getKodeBarang() + "' sudah ada.");
                return;
            }
        }
        daftarSembako.add(barang);
        System.out.println("Berhasil menambahkan ke kategori: " + barang.getKategori());
    }

    static void tampilkanData() {
        System.out.println("\n-- DAFTAR BARANG GUDANG --");
        if (daftarSembako.isEmpty()) {
            System.out.println("Gudang Kosong.");
        } else {
            for (int i = 0; i < daftarSembako.size(); i++) {
                Sembako b = daftarSembako.get(i);
                System.out.print((i + 1) + ". ");
                b.tampilkanInfo();
                b.cekKetersediaan(); // Memanggil method interface
            }
        }
    }

    static void ubahData() {
        tampilkanData();
        if (daftarSembako.isEmpty()) return;
        System.out.print("\nPilih nomor barang: ");
        int no = scanner.nextInt(); scanner.nextLine();

        if (no > 0 && no <= daftarSembako.size()) {
            Sembako b = daftarSembako.get(no - 1);
            System.out.print("Nama Baru: "); String n = scanner.nextLine();
            if (!n.isEmpty()) b.setNamaBarang(n);
            System.out.print("Stok Baru: "); b.setJumlahStok(scanner.nextInt());
            System.out.print("Harga Baru: "); b.setHargaSatuan(scanner.nextDouble());

            if (b instanceof SembakoCair) {
                System.out.print("Volume Baru (L): "); ((SembakoCair)b).setVolumeLiter(scanner.nextDouble());
            } else {
                System.out.print("Berat Baru (Kg): "); ((SembakoPadat)b).setBeratKg(scanner.nextDouble());
            }
            System.out.println("Data diperbarui.");
        }
    }

    static void hapusData() {
        tampilkanData();
        if (daftarSembako.isEmpty()) return;
        System.out.print("\nNomor yang dihapus: ");
        int no = scanner.nextInt();
        if (no > 0 && no <= daftarSembako.size()) {
            daftarSembako.remove(no - 1);
            System.out.println("Data terhapus.");
        }
    }

    static void cariBarangByKode() {
        System.out.print("Kode: "); String k = scanner.nextLine();
        for (Sembako b : daftarSembako) {
            if (b.getKodeBarang().equalsIgnoreCase(k)) {
                b.tampilkanInfo(); return;
            }
        }
        System.out.println("Tidak ditemukan.");
    }

    static void cariBarangByNama() {
        System.out.print("Nama: "); String n = scanner.nextLine();
        for (Sembako b : daftarSembako) {
            if (b.getNamaBarang().toLowerCase().contains(n.toLowerCase())) b.tampilkanInfo();
        }
    }

    static void tampilkanNilaiStok() {
        double total = 0;
        for (Sembako b : daftarSembako) {
            double v = b.hitungNilaiStok();
            System.out.println(b.getNamaBarang() + " [" + b.getKategori() + "] : Rp " + v);
            total += v;
        }
        System.out.println("Total Nilai: Rp " + total);
    }
}