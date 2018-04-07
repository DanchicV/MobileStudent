package com.istu.sisyuk.mobilestudent;

import android.app.Application;
import android.util.Log;

import com.istu.sisyuk.mobilestudent.data.api.ApiService;
import com.istu.sisyuk.mobilestudent.data.api.HeaderInterceptor;
import com.istu.sisyuk.mobilestudent.util.PreferenceHelper;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MobileStudentApplication extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = new AppComponent();
    }

    public static AppComponent getComponent() {
        return appComponent;
    }

    public class AppComponent {

        private ApiService apiService;
        private Retrofit retrofit;

        private PreferenceHelper preferenceHelper;

        private AppComponent() {
            preferenceHelper = new PreferenceHelper(MobileStudentApplication.this);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new HeaderInterceptor())
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            apiService = retrofit.create(ApiService.class);
        }

        public ApiService getApiService() {
            return apiService;
        }

        public PreferenceHelper getPreferenceHelper() {
            return preferenceHelper;
        }
    }
}
