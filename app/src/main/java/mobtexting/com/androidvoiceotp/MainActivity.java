package mobtexting.com.androidvoiceotp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText phonenumberEt;
    private Button btnOtpVoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phonenumberEt=(EditText)findViewById(R.id.phonenumberEt);
        btnOtpVoice=(Button)findViewById(R.id.btnOtpVoice);


    }
}
