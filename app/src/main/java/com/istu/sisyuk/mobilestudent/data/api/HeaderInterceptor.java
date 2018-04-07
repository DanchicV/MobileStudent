package com.istu.sisyuk.mobilestudent.data.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalResponse = chain.request();
        Response response = chain.proceed(originalResponse.newBuilder()
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .build());
        return response;
    }
}
