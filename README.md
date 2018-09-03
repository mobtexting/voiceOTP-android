# Mobtexting Voice OTP To Verify Number
_Easy to integrate android sdk to verify phone number from Mobtexting_
## Getting Started
### Gradle
**Step 1.** _Add the JitPack repository to your build file_
```java
allprojects {
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```
**Step 2.** Add the dependency
```java
dependencies {
  implementation 'com.github.mobtexting:voiceOTP-android:3638bf3f39'
}
```
**Step 3.** Add Internet Permisssion in Manifest
```xml
<uses-permission android:name="android.permission.INTERNET" />
```
#### Define _API KEY_ in Manifest file inside Application tag
```xml
<meta-data android:name="mobtexting.api_key" android:value="@string/mobtextingapikey" />
```
#### Usage (How to verify phone number using Voice OTP)
**Step 4.**
implement VerificationInterface in Activity or fragment and implement the methods.
```java
public class MainActivity extends AppCompatActivity implements View.OnClickListener,VerificationInterface{

    private EditText phonenumberEt;
    private Button btnOtpVoice;
    private MobtextingService mobtextingService;
    private String sixDigitOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phonenumberEt=(EditText)findViewById(R.id.phonenumberEt);
        btnOtpVoice=(Button)findViewById(R.id.btnOtpVoice);
        
        //craeate the instance of MobtextingService
        mobtextingService=new MobtextingService(getBaseContext());
        
        
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
    public void onSuccesResponse(ServerResponse serverResponse) {
        
        //check the response code with 200 and status==true
        if(serverResponse.isStatus()&&serverResponse.getResponse_code()==200){
        
        //pass the generated six digit OTP code to VerifyActivity
            Intent verifyActivityIntent=new Intent(getBaseContext(),VerificationActivity.class);
            verifyActivityIntent.putExtra("otp",sixDigitOTP);
            startActivity(verifyActivityIntent);
        
        }else{
            Log.d("TAG",serverResponse.getMessage()+"   "+serverResponse.getResponse_code());
        }
    }
    
    @Override
    public void onErrorResponse(ServerResponse serverResponse) {
        
    }
 }
 ```
 **Step 5.** 
 verify your generated six digit OTP with entered OTP by user
 ```java
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

        //get the six digit OTP from previous actvity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            otpFromActivity = bundle.getString("otp");
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVerify:
                //match with generated OTP with entered OTP
                String enteredOTP = editOtp.getText().toString().trim();
                if (editOtp != null) {
                    if (enteredOTP.equals(otpFromActivity)) {
                        Toast.makeText(getBaseContext(), "OTP Verification success", Toast.LENGTH_SHORT).show();
                    } else {
                        //enter OTP verification failed
                        Toast.makeText(getBaseContext(), "OTP verfiifcation failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Enter Your OTP first", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

 ```
