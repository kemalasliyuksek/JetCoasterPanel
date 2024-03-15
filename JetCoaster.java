import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class JetCoaster {

    // Jetcoaster'a her seferinde binebilecek maksimum kişi sayısı
    private static final int MAX_KISI_SAYISI = 4;

    // Trene aynı anda binebilecek maksimum kişi sayısı
    private static final int MAX_TREN_KAPASITESI = 4;

    // Ziyaretçi kuyruğu
    private static LinkedList<Ziyaretci> kuyruk = new LinkedList<>();

    // Kullanıcıdan veri alma
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Sonsuz döngü programın sürekli çalışmasını sağlar
        while (true) {
            // Menü gösterilir
            menuGoster();

            // Kullanıcıdan seçim alınır
            System.out.print("Seçiminiz : ");
            int secim = scanner.nextInt();
            System.out.println("---------------------------------\n");

            // Seçime göre işlem yapılır
            switch (secim) {
                case 1:
                    tekTekMusteriEkle();
                    break;
                case 2:
                    topluMusteriEkle();
                    break;
                case 3:
                    treneBinenleriGoster();
                    break;
                case 4:
                    siradaBekleyenleriGoster();
                    break;
                case 5:
                    treniCalistir();
                    break;
                case 6:
                    treniDurdur();
                    break;
                case 7:
                    programdanCik();
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        }
    }

    private static void menuGoster() {
        System.out.println("\n---------------------------------");
        System.out.println("--- JetCoaster Yönetim Paneli ---");
        System.out.println("---------------------------------");
        System.out.println("1 - Tek Tek Müşteri Ekle");
        System.out.println("2 - Toplu Müşteri Ekle");
        System.out.println("3 - Trene Binenleri Göster");
        System.out.println("4 - Sırada Bekleyenleri Göster");
        System.out.println("5 - Treni Çalıştır");
        System.out.println("6 - Treni Durdur");
        System.out.println("7 - Programdan Çık");
        System.out.println("---------------------------------");
    }

    private static void tekTekMusteriEkle() {
        // Kullanıcıdan müşterinin ismi alınır
        System.out.println("Müşterinin ismini girin!");
        System.out.print("Giriş : ");
        String isim = scanner.next();

        // Yeni bir ziyaretçi nesnesi oluşturulur ve kuyruğa eklenir
        kuyruk.add(new Ziyaretci(isim));

        // Eklenen müşteriye dair bilgilendirme mesajı gösterilir
        System.out.println(isim + " kuyruğa eklendi!");
    }

    private static void topluMusteriEkle() {
        // Kullanıcıdan eklenecek müşteri sayısı alınır
        System.out.println("Kaç müşteri eklemek istiyorsunuz?");
        System.out.print("Giriş Sayısı : ");
        int eklemeSayisi = scanner.nextInt();

        // Belirtilen sayıda müşteri tek tek eklenir
        for (int i = 0; i < eklemeSayisi; i++) {
            System.out.print((i + 1) + ". müşterinin ismini giriniz : ");
            String isim = scanner.next();
            kuyruk.add(new Ziyaretci(isim));
        }

        // Eklenen müşterilere dair bilgilendirme mesajı gösterilir
        System.out.println(eklemeSayisi + " müşteri kuyruğa eklendi!");
    }

    // Trene binenler için ayrı bir kuyruk
    private static Queue<Ziyaretci> binenlerKuyrugu = new LinkedList<>();

    public static void treneBinenleriGoster() {
        // Binenler kuyruğu boş değilse
        if (!binenlerKuyrugu.isEmpty()) {
            // Geçici bir kuyruk oluşturulur
            Queue<Ziyaretci> tempKuyruk = new LinkedList<>();

            // Binenler kuyruğundaki tüm ziyaretçiler geçici kuyruğa aktarılır
            while (!binenlerKuyrugu.isEmpty()) {
                Ziyaretci musteri = binenlerKuyrugu.poll();
                System.out.println(" - " + musteri.getIsim() + " trende!");
                tempKuyruk.add(musteri);
            }

            // Binenler kuyruğu, geçici kuyruktan geri doldurulur
            while (!tempKuyruk.isEmpty()) {
                binenlerKuyrugu.add(tempKuyruk.poll());
            }
        } else {
            // Binenler kuyruğu boşsa mesaj gösterilir
            System.out.println("Tren boş!");
        }
    }

    private static void siradaBekleyenleriGoster() {
        // Kuyruk boş değilse
        if (!kuyruk.isEmpty()) {
            // Sıra numarası değişkeni tanımlanır
            int siraNumarasi = 1;

            // Kuyruktaki tüm ziyaretçiler sıra numarasıyla birlikte yazdırılır
            System.out.println("Sırada bekleyenler:");
            for (Ziyaretci musteri : kuyruk) {
                System.out.println(" - " + siraNumarasi + ". " + musteri.getIsim());
                ++siraNumarasi;
            }
        } else {
            // Kuyruk boşsa mesaj gösterilir
            System.out.println("Kuyruk boş!");
        }
    }

    public static void treniCalistir() {
        // Kuyrukta en az tren kapasitesi kadar kişi varsa
        if (kuyruk.size() >= MAX_TREN_KAPASITESI) {
            // Tren çalıştırıldığının mesajı gösterilir
            System.out.println("Tren çalıştırıldı!");

            // Tren kapasitesi kadar kişi trene bindirilir
            for (int i = 0; i < MAX_TREN_KAPASITESI; i++) {
                Ziyaretci musteri = kuyruk.removeFirst();
                binenlerKuyrugu.add(musteri);
                musteri.setBindiMi(true);
                System.out.println(" - " + musteri.getIsim() + " trene bindi!");
            }
        } else {
            // Tren çalıştırmak için yeterli kişi olmadığının mesajı gösterilir
            System.out.println("Tren çalıştırmak için " + MAX_TREN_KAPASITESI + " kişiye ihtiyaç var!");
        }
    }

    public static void treniDurdur() {
        // Binenler kuyruğu boş değilse
        if (!binenlerKuyrugu.isEmpty()) {
            // Tren durdurulduğunun mesajı gösterilir
            System.out.println("Tren durduruldu!");

            // Binenler kuyruğu boşaltılır
            while (!binenlerKuyrugu.isEmpty()) {
                binenlerKuyrugu.poll();
            }
        } else {
            // Binenler kuyruğu boşsa mesaj gösterilir
            System.out.println("Tren boş!");
        }
    }

    private static void programdanCik() {
        // Programdan çıkılacağının mesajı gösterilir
        System.out.println("Programdan çıkılıyor!");
        // Program sonlandırılır
        System.exit(0);
    }
}

