import java.util.ArrayList;
import java.util.Scanner;

class Sembako {
     String kodeBarang;
     String namaBarang;
     int jumlahStok;
     double hargaSatuan;

    public Sembako(String kodeBarang, String namaBarang, int jumlahStok, double hargaSatuan) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.jumlahStok = jumlahStok;
        this.hargaSatuan = hargaSatuan;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getJumlahStok() {
        return jumlahStok;
    }

    public void setJumlahStok(int jumlahStok) {
        this.jumlahStok = jumlahStok;
    }

    public double getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }
}

public class App {
    static ArrayList<Sembako> daftarSembako = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        boolean berjalan = true;

        while (berjalan) {
            System.out.println("\n--- SISTEM MANAJEMEN GUDANG SEMBAKO ---");
            System.out.println("1. Tambah Data Barang (Create)");
            System.out.println("2. Tampilkan Data Barang (Read)");
            System.out.println("3. Ubah Data Barang (Update)");
            System.out.println("4. Hapus Data Barang (Delete)");
            System.out.println("5. Keluar Program");
            System.out.print("Pilih menu (1-5): ");
            
            int pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1:
                    tambahData();
                    break;
                case 2:
                    tampilkanData();
                    break;
                case 3:
                    ubahData();
                    break;
                case 4:
                    hapusData();
                    break;
                case 5:
                    berjalan = false;
                    System.out.println("Terima kasih telah menggunakan program ini.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    static void tambahData() {
        System.out.println("\n-- Tambah Data Baru --");
        System.out.print("Masukkan Kode Barang: ");
        String kode = scanner.nextLine();
        System.out.print("Masukkan Nama Barang: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Jumlah Stok: ");
        int stok = scanner.nextInt();
        System.out.print("Masukkan Harga Satuan: ");
        double harga = scanner.nextDouble();
        scanner.nextLine();

        Sembako barangBaru = new Sembako(kode, nama, stok, harga);
        daftarSembako.add(barangBaru);
        System.out.println("Data berhasil ditambahkan.");
    }

    static void tampilkanData() {
        System.out.println("\n-- Daftar Barang di Gudang --");
        if (daftarSembako.isEmpty()) {
            System.out.println("Gudang masih kosong. Belum ada data.");
        } else {
            for (int i = 0; i < daftarSembako.size(); i++) {
                Sembako barang = daftarSembako.get(i);
                System.out.println((i + 1) + ". Kode: " + barang.getKodeBarang() + 
                                   " | Nama: " + barang.getNamaBarang() + 
                                   " | Stok: " + barang.getJumlahStok() + 
                                   " | Harga: Rp" + barang.getHargaSatuan());
            }
        }
    }

    static void ubahData() {
        System.out.println("\n-- Ubah Data Barang --");
        tampilkanData();
        
        if (!daftarSembako.isEmpty()) {
            System.out.print("Pilih nomor barang yang ingin diubah: ");
            int nomor = scanner.nextInt();
            scanner.nextLine();

            if (nomor > 0 && nomor <= daftarSembako.size()) {
                Sembako barang = daftarSembako.get(nomor - 1);
                
                System.out.println("Masukkan data baru (tekan Enter tanpa mengetik jika tidak ingin mengubah):");
                
                System.out.print("Nama Barang (" + barang.getNamaBarang() + "): ");
                String namaBaru = scanner.nextLine();
                if (!namaBaru.isEmpty()) {
                    barang.setNamaBarang(namaBaru);
                }

                System.out.print("Jumlah Stok (" + barang.getJumlahStok() + "): ");
                String stokBaruStr = scanner.nextLine();
                if (!stokBaruStr.isEmpty()) {
                    barang.setJumlahStok(Integer.parseInt(stokBaruStr));
                }

                System.out.print("Harga Satuan (" + barang.getHargaSatuan() + "): ");
                String hargaBaruStr = scanner.nextLine();
                if (!hargaBaruStr.isEmpty()) {
                    barang.setHargaSatuan(Double.parseDouble(hargaBaruStr));
                }

                System.out.println("Data berhasil diperbarui.");
            } else {
                System.out.println("Nomor barang tidak ditemukan.");
            }
        }
    }

    static void hapusData() {
        System.out.println("\n-- Hapus Data Barang --");
        tampilkanData();
        
        if (!daftarSembako.isEmpty()) {
            System.out.print("Pilih nomor barang yang ingin dihapus: ");
            int nomor = scanner.nextInt();
            scanner.nextLine();

            if (nomor > 0 && nomor <= daftarSembako.size()) {
                daftarSembako.remove(nomor - 1);
                System.out.println("Data berhasil dihapus.");
            } else {
                System.out.println("Nomor barang tidak ditemukan.");
            }
        }
    }
}