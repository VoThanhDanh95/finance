package vn.assignment.finance.network;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.assignment.finance.api.ApiService;

/**
 * Created by Ray on 3/6/17.
 */

public class ApiHelper {
    public static final String BASE_URL = "http://api.fixer.io/";
    private static ApiHelper instance;

    private Cache cache;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private ApiService apiService;

    public static ApiHelper getInstance() {
        if (instance == null) {
            instance = new ApiHelper();
        }
        return instance;
    }

    public Cache getCache() {
        if (cache == null) {
            cache = new Cache(new File("cache"), 1024L * 1024L * 100L);
        }
        return cache;
    }

    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder().addInterceptor(logging)
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            if (!TextUtils.isEmpty("")) {
                                Request newRequest = request.newBuilder().addHeader("Http-Auth-Token",
                                        "").build();
                                return chain.proceed(newRequest);
                            }
                            return chain.proceed(request);
                        }
                    })
                    .cache(getCache()).build();
        }
        return okHttpClient;
    }

    public Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return retrofit;
    }


    public ApiService getApiService() {
        if (apiService == null) {
            apiService = getRetrofit().create(ApiService.class);
        }
        return apiService;
    }
}
