package mobtexting.com.androidvoiceotp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VerificationActivity extends AppCompatActivity implements View.OnClickListener {
    private String otpFromActivity;
    private EditText editOtp;
    private Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);


        editOtp = (EditText) findViewById(R.id.editOtp);
        btnVerify = (Button) findViewById(R.id.btnVerify);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            otpFromActivity = bundle.getString("otp");
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVerify:
                String enteredOTP = editOtp.getText().toString().trim();
                if (editOtp != null) {
                    if (enteredOTP.equals(otpFromActivity)) {
                        Toast.makeText(getBaseContext(), "OTP Verification success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), "OTP verfiifcation failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Enter Your OTP first", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
