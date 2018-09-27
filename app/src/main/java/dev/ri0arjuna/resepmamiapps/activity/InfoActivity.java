package dev.ri0arjuna.resepmamiapps.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.marcoscg.licenser.Library;
import com.marcoscg.licenser.License;
import com.marcoscg.licenser.LicenserDialog;
import com.shashank.sony.fancyaboutpagelib.FancyAboutPage;

import dev.ri0arjuna.resepmamiapps.R;

public class InfoActivity extends AppCompatActivity {

    private LicenserDialog licenserDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        licenserDialog = new LicenserDialog(this)
                .setTitle("Licenses")
                .setCancelable(true)
                .setCustomNoticeTitle("Notice for files:")
                .setLibrary(new Library("Apache 2.0 License",
                        "https://www.apache.org/licenses/LICENSE-2.0",
                        License.APACHE))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        aboutPage();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_license, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int v = item.getItemId();

        if (v == R.id.menu_license) {
            licenserDialog.show();
            Toast.makeText(this, "clicked apache license", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void aboutPage() {
        FancyAboutPage fancyAboutPage = findViewById(R.id.fancyaboutpage);

        fancyAboutPage.setCover(R.drawable.logo_resepmami);
        fancyAboutPage.setName("Rio Arjuna");
        fancyAboutPage.setDescription("Android Developer | Certified Menjadi Android Developer Expert (MADE) by Dicoding");
        fancyAboutPage.setAppIcon(R.drawable.logo_resepmami);
        fancyAboutPage.setAppName("Resep Mami");
        fancyAboutPage.setVersionNameAsAppSubTitle("1.0.0");
        fancyAboutPage.setAppDescription("Resep Mami adalah kumpulan masakan lezat yang bisa kamu buat di rumah bersama dengan keluarga tercinta. " +
                "Dengan masak dirumah ditemani Resep Mami, keluarga menjadi mempunyai waktu yang berkualitas dengan keluarga sambil bercerita. " +
                "Nikmati waktu berkualitas dengan makanan lezat setiap hari dari Resep Mami. Mami memang hebat!");
        fancyAboutPage.addEmailLink("ri0arjuna@programmer.net");
        fancyAboutPage.addFacebookLink("https://www.facebook.com/sayarj11");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in/ri0arjuna/");
        fancyAboutPage.addGitHubLink("https://github.com/rarj/");
    }
}
