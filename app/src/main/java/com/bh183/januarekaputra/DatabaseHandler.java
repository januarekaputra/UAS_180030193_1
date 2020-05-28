package com.bh183.januarekaputra;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_film";
    private final static String TABLE_FILM = "tb_film";
    private final static String KEY_ID_FILM = "ID_Film";
    private final static String KEY_JUDUL = "Judul_Film";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Poster";
    private final static String KEY_GENRE = "Genre_Film";
    private final static String KEY_SUTRADARA = "Sutradara_Film";
    private final static String KEY_PEMERAN = "Pemeran";
    private final static String KEY_SINOPSIS = "Sinopsis_Film";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Context context;

    public DatabaseHandler(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_FILM = "CREATE TABLE " + TABLE_FILM
                + "(" + KEY_ID_FILM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_JUDUL + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_GENRE + " TEXT, "
                + KEY_SUTRADARA + " TEXT, " + KEY_PEMERAN + " TEXT, "
                + KEY_SINOPSIS + " TEXT);";

        db.execSQL(CREATE_TABLE_FILM);
        inisialisasiFilmAwal(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_FILM;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahFilm(Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_PEMERAN, dataFilm.getPemeran());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());

        db.insert(TABLE_FILM, null, cv);
        db.close();
    }

    public void tambahFilm(Film dataFilm, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format(dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_PEMERAN, dataFilm.getPemeran());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());

        db.insert(TABLE_FILM, null, cv);
    }

    public void editFilm(Film dataFilm){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_JUDUL, dataFilm.getJudul());
        cv.put(KEY_TGL, sdFormat.format( dataFilm.getTanggal()));
        cv.put(KEY_GAMBAR, dataFilm.getGambar());
        cv.put(KEY_GENRE, dataFilm.getGenre());
        cv.put(KEY_SUTRADARA, dataFilm.getSutradara());
        cv.put(KEY_PEMERAN, dataFilm.getPemeran());
        cv.put(KEY_SINOPSIS, dataFilm.getSinopsis());

        db.update(TABLE_FILM, cv, KEY_ID_FILM + "=?", new String[]{String.valueOf(dataFilm.getIdFilm())});
        db.close();
    }

    public void hapusFilm(int idFilm){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_FILM, KEY_ID_FILM + "=?", new String[]{String.valueOf(idFilm)});
        db.close();
    }

    public ArrayList<Film> getAllFilm(){
        ArrayList<Film> dataFilm = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_FILM;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr = db.rawQuery(query, null);
        if(csr.moveToFirst()){
            do{
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er){
                    er.printStackTrace();
                }

                Film tempFilm = new Film(
                        csr.getInt(0),
                        csr.getString(1),
                        tempDate,
                        csr.getString(3),
                        csr.getString(4),
                        csr.getString(5),
                        csr.getString(6),
                        csr.getString(7)
                );

                dataFilm.add(tempFilm);
            } while (csr.moveToNext());
        }
        return dataFilm;
    }

    private String storeImageFile(int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inisialisasiFilmAwal(SQLiteDatabase db){
        int idFilm = 0;
        Date tempDate = new Date();
        Date tempDate1 = new Date();

        try {
            tempDate = sdFormat.parse("14/03/1972");
        } catch (ParseException er){
            er.printStackTrace();
        }

        Film film1 = new Film(
                idFilm,
                "The Godfather",
                tempDate,
                storeImageFile(R.drawable.godfather),
                "Kejahatan/Fiksi kriminal",
                "Francis Ford Coppola",
                "Marlon Brando, Al Pacino, James Caan",
                "Pada hari pernikahan putrinya, Connie Corleone dengan pria bernama Carlo Rizzi. Vito Corleone selaku The Godfather atau Don Keluarga Corleone tidak bisa menolak apapun permintaan atau kunjungan tamu. Salah satu tamu bernama Bonaserra yang meminta Vito untuk mengadili para pria tidak bertanggung jawab yang melecehkan dan menganiyaya anaknya. Di lain waktu pun anak laki laki Vito, Michael yang merupakan angkatan laut datang membawa kekasihnya Kay Adams. Beberapa waktu setelah itu, datang Godson Vito yang seorang artis terkenal bernama Johnny Fontane, dia meminta Vito untuk melindungi perannya di film impiannya. Beberapa waktu setelah pernikahan, Consiglieri, pengacara terkenal sekaligus anak angkat Vito, Tom Hagen pergi ke L.A untuk melindungi peran Johnny Fontane dari kepala studio yang kasar, Jack Woltz. Jack Woltz tidak merespon dan malah mengusir Tom. Namun keesokan harinya Woltz terbangun dengan melihat kuda kesayangannya terpenggal di tempat tidurnya, menganggap bahwa Tom tidak main-main, Jack pun menyetujui peran Johnny Fontane.\n" +
                        "Beberapa waktu sebelum natal 1945, Vito menerima tawaran dari Virgil Sollozo yang seorang bandar narkoba dari keluarga Tattaglia. Namun tawaran itu ditolak karena itu berhubungan dengan hal narkotika yang dapat merusak koneksi politik Vito. Menganggap bahwa Sollozo tidak akan berhenti sampai tawarannya diterima, Vito mengirim serdadu kepercayaannya Luca Brasi untuk menjadi mata-mata di keluarga Tattaglia. Namun sebelum Luca berhasil masuk, Luca terbunuh oleh Sollozo dan Bruno Tattaglia. Di waktu yang berbeda anak buah Sollozo berhasil menembak Vito hingga cedera serius namun tidak tewas. Selama Vito dirawat, anak pertama Vito, Sonny pun memimpin. Di bawah pimpinan Sonny, Keluarga Corleone semakin dekat dengan ambang kehancuran. Di tengah perseteruan antara Sonny dengan Tom, Michael pun mempunyai ide untuk membunuh Sollozo dan pengawalnya yang merupakan kapten kepolisian yang bernama Marc McCluskey di sebuah pertemuan, dan rencananya pun berhasil."
        );

        tambahFilm(film1, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("05/01/1998");
        } catch (ParseException er){
            er.printStackTrace();
        }

        Film film2 = new Film(
                idFilm,
                "Titanic",
                tempDate,
                storeImageFile(R.drawable.titanic),
                "Roman/Drama",
                "James Cameron",
                "Leonardo DiCaprio, Kate Winslet",
                "Pada tahun 1996, seorang pemburu harta karun bernama Brock Lovett beserta timnya menjelajahi bangkai kapal RMS Titanic untuk mencari sebuah kalung berlian berharga yang diyakini terkubur di dasar laut bersama bangkai kapal tersebut. Sebuah peti ditemukan dan dibawa segera ke permukaan untuk dibuka. Sayangnya, peti itu tidak berisi harta karun berharga tetapi hanya beberapa lembaran kertas yang sudah hancur karena air laut. Salah satunya adalah sebuah lukisan seorang wanita telanjang bertanggal 14 April 1912 dan bertanda tangan \"JD\". Lukisan itu menggambarkan seorang perempuan telanjang yang bersandar di sebuah kursi. Di lehernya terdapat sebuah kalung berlian yang mereka cari: \"Heart of the Ocean — Jantung Samudera.\n" +
                        "Rose Dawson Calvert, seorang perempuan tua berusia 101 tahun, menonton berita di CNN tentang penemuan lukisan tersebut. Dia menghubungi Brock Lovett, menanyakan soal Heart of the Ocean dan meyakinkan Lovett kalau perempuan yang ada dalam lukisan itu adalah dirinya. Lovett tertarik dan selanjutnya, Rose ditemani cucunya, Lizzy Calvert, terbang ke lokasi penemuan lukisan tersebut lalu menceritakan lebih lanjut tentang pengalamannya di atas kapal Titanic.\n" +
                        "Pada bulan April 1912, Rose (saat itu bernama Rose DeWitt Bukater) yang masih berusia 17 tahun menaiki RMS Titanic sebagai penumpang kelas satu bersama ibunya, Ruth DeWitt Bukater, dan tunangannya, Caledon Nathan Hockley (Cal), seorang pengusaha sukses di bidang industri. Rose tidak mencintai Cal, tetapi ibunya memaksanya untuk menikahinya karena masalah keuangan dan kehormatan keluarga. Di saat yang sama, seorang laki-laki bernama Jack Dawson memenangkan tiket kelas tiga dalam permainan poker dan ikut serta dalam pelayaran perdana Titanic dari Southampton menuju New York."
        );

        tambahFilm(film2, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("30/04/2008");
        } catch (ParseException er){
            er.printStackTrace();
        }

        Film film3 = new Film(
                idFilm,
                "Iron Man",
                tempDate,
                storeImageFile(R.drawable.ironman),
                "Laga/Fiksi ilmiah",
                "Jon Favreau",
                "Robert Downey Jr., Terrence Howard, Gwyneth Paltrow",
                "Tony Stark (Robert Downey Jr.) adalah pemimpin dari Stark Industries, sebuah perusahaan kontraktor militer utama yang ia warisi dari mendiang ayahnya. Stark adalah seorang jenius inventif dan ajaib, namun dia juga playboy. Setelah diwawancarai oleh Christine Everhart, ia membuatnya terpesona ke seks malam itu. Keesokan paginya asisten Stark, Virginia \"Pepper\" Potts (Gwyneth Paltrow), mengirimnya pulang.\n" +
                        "Sementara rekan lama ayahnya, Obadiah Stane (Jeff Bridges), menjaga operasional sehari-hari, Stark terbang ke Afghanistan yang dilanda perang bersama teman dan penghubung militernya, letnan kolonel James Rhodes (Terrence Howard), untuk demonstrasi senjata baru Stark, rudal \"Jericho\". Setelah itu, konvoi militer Stark diserang dan dia terluka kritis.\n" +
                        "Stark menemukan dirinya sebagai tawanan kelompok teroris Afghanistan yang dikenal sebagai Sepuluh Cincin. Sebuah elektromagnet telah ditanam di dadanya oleh Dr. Yinsen untuk menjaga pecahan peluru mendekat ke hatinya dan membunuhnya. Pemimpin Sepuluh Cincin, Raza, menawarkan kebebasan untuk Stark dengan membangun rudal Jericho untuk kelompoknya. Yinsen kemudian menegaskan kecurigaan Stark bahwa Raza tidak akan menepati janjinya."
        );

        tambahFilm(film3, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("04/04/2012");
        } catch (ParseException er){
            er.printStackTrace();
        }

        Film film4 = new Film(
                idFilm,
                "The Avengers",
                tempDate,
                storeImageFile(R.drawable.avanger),
                "Laga/Fiksi ilmiah",
                "Joss Whedon",
                "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth, Scarlett Johansson, Tom Hiddleston, Samuel L. Jackson",
                "Loki (Tom Hiddleston) bertemu dengan Other, pemimpin pasukan luar angkasa yang dikenal sebagai Chitauri. Dalam pertukaran untuk mengambil Tesseract, sumber energi yang sangat kuat, Other menjanjikan Loki tentara Chitauri yang dapat ia pimpin untuk menundukkan Bumi. Nick Fury (Samuel L. Jackson), direktur badan pengawas S.H.I.E.L.D, dan letnan Agen Maria Hill (Cobie Smulders) tiba di sebuah fasilitas penelitian terpencil selama evakuasi, di mana fisikawan Dr. Erik Selvig (Stellan Skarsgård) memimpin sebuah tim peneliti untuk melakukan percobaan pada Tesseract. Agen Phil Coulson (Clark Gregg) menjelaskan bahwa Tesseract telah memancar bentuk energi yang tidak biasa. Tesseract tiba-tiba aktif dan membuka portal, memungkinkan Loki untuk mencapai Bumi.\n" +
                        "Loki mengambil Tesseract dan menggunakan tongkatnya untuk memperbudak Selvig dan beberapa agen, termasuk Clint Barton/Hawkeye (Jeremy Renner), untuk membantunya. Dalam menanggapi serangan itu, Fury mengaktifkan kembali Avengers Initiative. Agen Natasha Romanoff (Scarlett Johansson) dikirim ke Calcutta untuk merekrut Dr. Bruce Banner (Mark Ruffalo) untuk melacak Tesseract melalui emisi radiasi gammanya. Coulson mengunjungi Tony Stark (Robert Downey, Jr.) untuk meninjau penelitian Selvig, dan Fury mendekati Steve Rogers/Captain America (Chris Evans) dengan tugas untuk mengambil Tesseract. Sementara Barton mencuri iridium yang diperlukan untuk menstabilkan daya Tesseract ini, Loki membuat kekacauan di Stuttgart yang mengarah ke konfrontasi dengan Rogers, Stark, dan Romanoff yang berakhir dengan penyerahan Loki.\n" +
                        "Sementara Loki sedang diantar ke S.H.I.E.L.D, Thor (Chris Hemsworth), saudara angkatnya, datang dan membebaskan dirinya berharap untuk meyakinkan dia untuk meninggalkan rencananya dan kembali ke Asgard. Setelah konfrontasi dengan Stark dan Rogers, Thor setuju untuk mengambil Loki ke kapal induk terbang S.H.I.E.L.D., Helicarrier. Ada Loki dipenjarakan sementara Banner dan Stark mencari Tesseract tersebut."
        );

        tambahFilm(film4, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("13/06/2013");
        } catch (ParseException er){
            er.printStackTrace();
        }

        Film film5 = new Film(
                idFilm,
                "Man of Steel",
                tempDate,
                storeImageFile(R.drawable.superman),
                "Laga/Petualangan",
                "Zack Snyder",
                "Henry Cavill, Amy Adams, Russell Crowe",
                "Planet Krypton menghadapi kehancuran dalam waktu dekat karena intinya tidak stabil, dan dewan yang berkuasa berada di bawah ancaman pemberontak Jenderal Zod dan para pengikutnya. Ilmuwan Jor-El dan istrinya Lara-El meluncurkan anak mereka yang baru lahir, Kal-El pada pesawat ruang angkasa ke bumi, menanamkan sel-selnya dengan codex genetik untuk melestarikan ras Krypton.\n" +
                        "Setelah pembunuhan Zod tehadap Jor-El, ia dan pengikutnya ditangkap dan dibuang ke Zona Bayangan. Namun, Krypton meledak beberapa waktu kemudian dan membebaskan mereka. Bayi Kal-El diangkat sebagai anak angkat Jonathan dan Martha Kent, dan dia diberi nama Clark Kent. Fisiologi Krypton Clark memberikan dia kemampuan super di Bumi, yang awalnya menyebabkan dia kebingungan, tetapi dia secara bertahap belajar untuk memanfaatkan kekuatannya untuk membantu orang lain. \n" +
                        "Jonathan mengungkapkan ke Clark remaja bahwa ia adalah alien dan menyarankan dia untuk tidak menggunakan kekuatannya secara terbuka, karena takut bahwa masyarakat akan menolaknya. Setelah kematian Jonathan, seorang Clark dewasa menghabiskan beberapa tahun hidup secara nomaden, mengerjakan pekerjaan yang berbeda di bawah nama palsu. Suatu ketika ia menyusup ke tempat penemuan ilmiah pesawat luar angkasa Kryptonian di Arktik. \n" +
                        "Clark memasuki kapal asing tersebut, dan memungkinkan dia untuk berkomunikasi dengan kesadaran Jor-El yang diawetkan dalam bentuk hologram. Lois Lane, seorang wartawan dari Daily Planet yang diutus untuk menulis cerita tentang penemuan ini, menyelinap di dalam kapal saat mengikuti Clark dan diselamatkan olehnya ketika dia terluka. Atasan Lois, Perry White menolak ceritanya tentang \"manusia super\" penyelamat, jadi kemudian Lois menelusuri Clark kembali."
        );

        tambahFilm(film5, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("21/06/2019");
        } catch (ParseException er){
            er.printStackTrace();
        }

        Film film6 = new Film(
                idFilm,
                "Parasite",
                tempDate,
                storeImageFile(R.drawable.parasite),
                "Cerita seru/Komedi",
                "Bong Joon-ho",
                "Song Kang-ho, Lee Sun-kyun, Cho Yeo-jeong, Choi Woo-shik, Park So-dam",
                "Keluarga Kim, terdiri dari sang ayah Kim Ki-taek (Song Kang-ho), istrinya Park Chung-suk (Jang Hye-jin), putranya Kim Ki-woo (Choi Woo-shik), dan putrinya Kim Ki-jeong (Park So-Dam) tinggal di sebuah banjiha, apartemen semi-bawah tanah yang kecil dan kumuh. Pekerjaan harian mereka adalah melipat kotak piza, dengan penghasilan yang sangat kecil dan kesulitan untuk memenuhi kebutuhan sehari-hari. Suatu hari, teman Ki-woo, Min-hyuk (Park Seo-joon) mengunjungi keluarga Kim dan memberikan \"batu keberuntungan\" (). \n" +
                        "Min-hyuk berencana menuntut ilmu ke luar negeri, sehingga menyarankan Ki-woo mengambil alih pekerjaannya sebagai guru les privat bahasa Inggris untuk Park Da-hye (Jung Ji-so), anak perempuan keluarga kaya Park Dong-ik (Lee Sun-kyun) dan istrinya Choi Yeon-gyo (Jo Yeo-jeong) sekaligus kakak dari Park Da-song (Jung Hyun-joon). Ki-woo bersedia menerima tawaran kawannya. Perlahan-lahan, keluarga Kim berusaha agar satu per satu anggota keluarga mereka dapat bekerja di keluarga Park, dengan saling merekomendasikan satu sama lain dan berbohong sebagai penyedia jasa profesional yang saling tidak kenal.\n" +
                        "Ki-woo menjadi guru les dan diam-diam memulai hubungan romantis dengan Da-hye. Ketika Yeon-gyo berniat mencarikan guru dan terapis seni untuk Da-song, Ki-woo memanfaatkan kesempatan ini dengan menyarankan seorang \"profesional\" bernama Jessica yang berasal dari Chicago, Illinois, Amerika Serikat, yang ternyata justru Ki-jeong saudarinya sendiri. Ki-jeong kemudian memfitnah supir keluarga Park dengan meletakkan celana dalamnya di dalam mobil milik keluarga Park. Dong-ik mengusir supir itu dan menggantinya dengan seorang mantan supir valet yang ternyata Ki-taek.\n" +
                        "Dan untuk terakhir kalinya, Ki-taek berkomplot dengan kedua anaknya untuk menarik sang ibu, Chung-suk menjadi asisten rumah tangga, dengan mencoba menakut-nakuti bahwa pembantu mereka saat ini, Mun-gwang (Lee Jung-eun), mengidap tuberkulosis dengan memanfaatkan alergi persik yang dideritanya."
        );

        tambahFilm(film6, db);
        idFilm++;

        try {
            tempDate = sdFormat.parse("18/01/2020");
        } catch (ParseException er){
            er.printStackTrace();
        }

        Film film7 = new Film(
                idFilm,
                "1917",
                tempDate,
                storeImageFile(R.drawable.perang),
                "Perang/Drama",
                "Sam Mendes",
                "George MacKay, Dean-Charles Chapman, Mark Strong",
                "Pada puncak Perang Dunia I selama musim semi tahun 1917 di Prancis utara, dua tentara muda Britania Raya, Schofield (George MacKay) dan Blake (Dean-Charles Chapman), diberikan misi yang tampaknya mustahil untuk menyampaikan pesan berisi peringatan akan terjadi penyergapan dalam salah satu pertempuran segera setelah pasukan Jerman mundur ke Garis Hindenburg dalam Operasi Alberich.\n" +
                        "Mereka berdua bergegas melawan waktu, melintasi wilayah musuh untuk menyampaikan peringatan dan menjaga satu batalion Britania Raya yang terdiri dari 1.600 tentara, termasuk saudara Blake sendiri, agar tidak masuk ke dalam jebakan maut. Mereka berdua harus memberikan segalanya untuk menyelesaikan misi mereka dengan selamat dari perang untuk mengakhiri semua perang."
        );

        tambahFilm(film7, db);
    }

}
