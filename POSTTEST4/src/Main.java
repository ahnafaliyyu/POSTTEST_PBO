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

    // [OVERRIDE BASE] - Metode dasar yang akan di-override subclass
    public void tampilkanInfo() {
        System.out.print("Kode: " + kodeBarang + " | Nama: " + namaBarang +
                         " | Stok: " + jumlahStok + " | Harga: Rp" + hargaSatuan);
    }

    // [OVERRIDE BASE] - Hitung nilai total stok; tiap subclass bisa punya perhitungan beda
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

class SembakoCair extends Sembako {
    private double volumeLiter;

    public SembakoCair(String kode, String nama, int stok, double harga, double volume) {
        super(kode, nama, stok, harga);
        this.volumeLiter = volume;
    }

    public double getVolumeLiter()              { return volumeLiter; }
    public void setVolumeLiter(double vol)      { this.volumeLiter = vol; }

    // [OVERRIDE 1] - Tampilkan info + volume liter khusus barang cair
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println(" | Jenis: Cair (" + volumeLiter + " Liter)");
    }

    // [OVERRIDE 2] - Barang cair dikenakan biaya penanganan 5% (butuh wadah khusus)
    @Override
    public double hitungNilaiStok() {
        double nilaiDasar = super.hitungNilaiStok();
        double biayaPenanganan = nilaiDasar * 0.05;
        return nilaiDasar + biayaPenanganan;
    }
}

class SembakoPadat extends Sembako {
    private double beratKg;

    public SembakoPadat(String kode, String nama, int stok, double harga, double berat) {
        super(kode, nama, stok, harga);
        this.beratKg = berat;
    }

    public double getBeratKg()             { return beratKg; }
    public void setBeratKg(double beratKg) { this.beratKg = beratKg; }

    // [OVERRIDE 1] - Tampilkan info + berat kg khusus barang padat
    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.println(" | Jenis: Padat (" + beratKg + " Kg)");
    }

    // [OVERRIDE 2] - Barang padat mendapat diskon penyimpanan 3% (lebih mudah ditumpuk)
    @Override
    public double hitungNilaiStok() {
        double nilaiDasar = super.hitungNilaiStok();
        double diskon = nilaiDasar * 0.03;
        return nilaiDasar - diskon;
    }
}

public class Main {
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
            System.out.println("6. Cari Barang (by Kode)");
            System.out.println("7. Cari Barang (by Nama)");
            System.out.println("8. Tampilkan Nilai Stok");
            System.out.println("9. Keluar");
            System.out.print("Pilih menu: ");

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
        }
    }

    static void tambahBarang(boolean isCair) {
        System.out.print("Kode: "); String kode = scanner.nextLine();
        System.out.print("Nama: "); String nama = scanner.nextLine();
        System.out.print("Stok: "); int stok = scanner.nextInt();
        System.out.print("Harga: "); double harga = scanner.nextDouble();

        if (isCair) {
            System.out.print("Volume (Liter): "); double vol = scanner.nextDouble();
            scanner.nextLine();
            tambahBarang(new SembakoCair(kode, nama, stok, harga, vol)); // memanggil overload 2
        } else {
            System.out.print("Berat (Kg): "); double berat = scanner.nextDouble();
            scanner.nextLine();
            tambahBarang(new SembakoPadat(kode, nama, stok, harga, berat)); // memanggil overload 2
        }
    }


    static void tambahBarang(Sembako barang) {
        // Validasi: cek apakah kode sudah ada
        for (Sembako b : daftarSembako) {
            if (b.getKodeBarang().equalsIgnoreCase(barang.getKodeBarang())) {
                System.out.println("Gagal! Kode barang '" + barang.getKodeBarang() + "' sudah ada.");
                return;
            }
        }
        daftarSembako.add(barang);
        System.out.println("Barang '" + barang.getNamaBarang() + "' berhasil ditambahkan!");
    }

    static void tampilkanData() {
        System.out.println("\n-- DAFTAR BARANG GUDANG --");
        if (daftarSembako.isEmpty()) {
            System.out.println("Gudang Kosong.");
        } else {
            for (int i = 0; i < daftarSembako.size(); i++) {
                System.out.print((i + 1) + ". ");
                daftarSembako.get(i).tampilkanInfo(); // POLYMORPHISM: memanggil tampilkanInfo() sesuai tipe objek
            }
        }
    }

    static Sembako cariBarang(String kode) {
        for (Sembako b : daftarSembako) {
            if (b.getKodeBarang().equalsIgnoreCase(kode)) {
                return b;
            }
        }
        return null;
    }

    static ArrayList<Sembako> cariBarang(String nama, boolean byNama) {
        ArrayList<Sembako> hasil = new ArrayList<>();
        for (Sembako b : daftarSembako) {
            if (b.getNamaBarang().toLowerCase().contains(nama.toLowerCase())) {
                hasil.add(b);
            }
        }
        return hasil;
    }

    static void cariBarangByKode() {
        System.out.print("Masukkan kode barang: ");
        String kode = scanner.nextLine();
        Sembako hasil = cariBarang(kode); // memanggil overload by kode
        if (hasil != null) {
            System.out.print("Ditemukan -> ");
            hasil.tampilkanInfo();
        } else {
            System.out.println("Barang dengan kode '" + kode + "' tidak ditemukan.");
        }
    }

    static void cariBarangByNama() {
        System.out.print("Masukkan kata kunci nama: ");
        String nama = scanner.nextLine();
        ArrayList<Sembako> hasil = cariBarang(nama, true); // memanggil overload by nama
        if (!hasil.isEmpty()) {
            System.out.println("Ditemukan " + hasil.size() + " barang:");
            for (int i = 0; i < hasil.size(); i++) {
                System.out.print((i + 1) + ". ");
                hasil.get(i).tampilkanInfo();
            }
        } else {
            System.out.println("Tidak ada barang dengan nama mengandung '" + nama + "'.");
        }
    }

    static void tampilkanNilaiStok() {
        System.out.println("\n-- NILAI STOK GUDANG --");
        if (daftarSembako.isEmpty()) {
            System.out.println("Gudang Kosong.");
            return;
        }
        double totalNilai = 0;
        for (Sembako b : daftarSembako) {
            // POLYMORPHISM: hitungNilaiStok() dipanggil sesuai tipe nyata objek
            // -> SembakoCair: nilai + 5% biaya penanganan
            // -> SembakoPadat: nilai - 3% diskon penyimpanan
            double nilai = b.hitungNilaiStok();
            System.out.printf("  %-20s : Rp %.2f%n", b.getNamaBarang(), nilai);
            totalNilai += nilai;
        }
        System.out.println("  --------------------------------");
        System.out.printf("  Total Nilai Stok    : Rp", totalNilai);
        System.out.println("  (Cair: +5% biaya penanganan | Padat: -3% diskon penyimpanan)");
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
            if (!namaBaru.isEmpty()) barang.setNamaBarang(namaBaru);

            System.out.print("Stok Baru (" + barang.getJumlahStok() + "): ");
            barang.setJumlahStok(scanner.nextInt());

            System.out.print("Harga Baru (" + barang.getHargaSatuan() + "): ");
            barang.setHargaSatuan(scanner.nextDouble());

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
            scanner.nextLine();
            if (no > 0 && no <= daftarSembako.size()) {
                System.out.println("Barang '" + daftarSembako.get(no - 1).getNamaBarang() + "' berhasil dihapus.");
                daftarSembako.remove(no - 1);
            } else {
                System.out.println("Nomor tidak valid.");
            }
        }
    }
}