package mobtexting.com.androidvoiceotp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import mobtexting.com.androidvoiceotp.model.ServerResponse;
import mobtexting.com.androidvoiceotp.reposotories.VerificationInterface;
import mobtexting.com.androidvoiceotp.service.MobtextingService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,VerificationInterface{
    private EditText phonenumberEt;
    private Button btnOtpVoice;
    private MobtextingService mobtextingService;
    private String sixDigitOTP;

    private String TAG=MainActivity.this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phonenumberEt=(EditText)findViewById(R.id.phonenumberEt);
        btnOtpVoice=(Button)findViewById(R.id.btnOtpVoice);

        mobtextingService=new MobtextingService(getBaseContext());


        btnOtpVoice.setOnClickListener(this);
    }

    /**
     * generate Six Digit OTP
     * @return
     */
    public int generateSixDigitRandomNumber(){
        Random random=new Random();
        int sixDigit=100000+ random.nextInt(800000);
        return sixDigit;
    }

    @Override
    public void onClick(View view) {
        switch (btnOtpVoice.getId()){
            case R.id.btnOtpVoice:
                if(phonenumberEt.getText().toString().equals("")){
                    Toast.makeText(getBaseContext(), "Enter Mobile number", Toast.LENGTH_SHORT).show();
                }else {
                    sixDigitOTP = String.valueOf(generateSixDigitRandomNumber());
                    mobtextingService.getVoiceOTP("7250705072", sixDigitOTP, "7488792140", MainActivity.this);
                }
            break;
        }
    }

    @Override
    public void onSuccesResponse(ServerResponse serverResponse) {
        Log.d("TAG",serverResponse.getMessage()+"  "+serverResponse.getResponse_code()+"  "+serverResponse.isStatus());
        if(serverResponse.isStatus()&&serverResponse.getResponse_code()==200){
            Intent verifyActivityIntent=new Intent(getBaseContext(),VerificationActivity.class);
            verifyActivityIntent.putExtra("otp",sixDigitOTP);
            startActivity(verifyActivityIntent);
        }else{
            Log.d("TAG",serverResponse.getMessage()+"   "+serverResponse.getResponse_code());
        }
    }

    @Override
    public void onErrorResponse(ServerResponse serverResponse) {
        Log.d("TAG",serverResponse.getMessage()+"  "+serverResponse.getResponse_code()+"  "+serverResponse.isStatus());
    }
}
