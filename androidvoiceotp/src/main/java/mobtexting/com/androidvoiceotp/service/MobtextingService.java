package mobtexting.com.androidvoiceotp.service;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import java.lang.annotation.Annotation;

import mobtexting.com.androidvoiceotp.config.Error;
import mobtexting.com.androidvoiceotp.config.MobtextingConfig;
import mobtexting.com.androidvoiceotp.model.ServerResponse;
import mobtexting.com.androidvoiceotp.reposotories.MobtextingInterface;
import mobtexting.com.androidvoiceotp.reposotories.VerificationInterface;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MobtextingService {
    private static final String TAG="Mobtexting";
    private Retrofit retrofit;
    private Context context;
    private String api_key;


    public MobtextingService(Context context){
        this.context=context;
    }

    public void getVoiceOTP(String pilot_number, String otp, String caller_number, final VerificationInterface verificationInterface){
        //check api_key in application manifest file
        try{
            ApplicationInfo applicationInfo=context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle=applicationInfo.metaData;
            api_key=bundle.getString("mobtexting.api_key");
        }catch (Exception e){
            verificationInterface.onErrorResponse(new ServerResponse(Error.API_KEY_NOT_FOUND,false,Error.API_KEY_NOT_FOUND_CODE));
            return;
        }

        //check api_key should not be null and empty
        if(api_key!=null&&!api_key.equals("")){
            try {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
                httpClient.addInterceptor(logging);

                retrofit=new Retrofit.Builder()
                        .client(httpClient.build())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(MobtextingConfig.MOBTEXTING_BASE_URL)
                        .build();

                MobtextingInterface mobtextingInterface=retrofit.create(MobtextingInterface.class);

                Call<ServerResponse> call=mobtextingInterface.post(api_key,pilot_number,caller_number,otp);

                call.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getResponse_code()==200){
                                verificationInterface.onSuccesResponse(response.body());
                            }else{
                                verificationInterface.onErrorResponse(response.body());
                            }
                        }else{
                            try{
                                Converter<ResponseBody, ServerResponse> errorConverter = retrofit.responseBodyConverter(ServerResponse.class, new Annotation[0]);
                                ServerResponse error = errorConverter.convert(response.errorBody());
                                verificationInterface.onErrorResponse(error);
                            }catch (Exception e){
                                e.printStackTrace();
                                verificationInterface.onErrorResponse(new ServerResponse(Error.SERVER_ERROR,false,Error.SERVER_ERROR_CODE));
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {

                    }
                });

            }catch (Exception e){
                verificationInterface.onErrorResponse(new ServerResponse(Error.SERVER_ERROR,false,Error.SERVER_ERROR_CODE));
            }
        }else{
            verificationInterface.onErrorResponse(new ServerResponse(Error.API_KEY_FOUND_NULL_EMPTY,false,Error.API_KEY_NOT_FOUND_CODE));
        }

    }

}
