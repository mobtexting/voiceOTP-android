package mobtexting.com.androidvoiceotp.config;

public class Error {
    //string error status
    public static String NO_INTERNET="CHECK YOUR INTERNET CONNECTIVITY";
    public static String API_KEY_NOT_FOUND="Dear developer. Don't forget to configure <meta-data android:name=\"mobtexting.api_key\" android:value=\"testValue\"/> in your AndroidManifest.xml file.";
    public static String PILOT_NUMBER="PILOT NUMBER NOT FOUND";
    public static String SERVER_ERROR="SOMETHING WENT WRONG!";
    public static String API_KEY_FOUND_NULL_EMPTY="API KEY FOUND INSIDE MANIFEST BUT SHOULD NOT BE NULL OR EMPTY";

    //int error code
    public static int NO_INTERNET_CODE=500;
    public static int API_KEY_NOT_FOUND_CODE=401;
    public static int PILOT_NUMBER_CODE=402;
    public static int SERVER_ERROR_CODE=500;
}
