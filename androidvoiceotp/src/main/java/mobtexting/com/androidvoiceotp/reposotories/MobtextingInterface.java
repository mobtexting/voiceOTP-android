package mobtexting.com.androidvoiceotp.reposotories;

import mobtexting.com.androidvoiceotp.config.MobtextingConfig;
import mobtexting.com.androidvoiceotp.model.ServerResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MobtextingInterface {
    @FormUrlEncoded
    @POST(MobtextingConfig.MOBTEXTING_VOICE_OTP_URL)
    Call<ServerResponse > post(@Field("api_key") String api_key,
                               @Field("pilot_number") String pilotNumber,
                               @Field("caller_number") String callerNumber,
                               @Field("otp") String otp);

}
