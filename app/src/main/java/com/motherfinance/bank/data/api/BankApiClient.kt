package com.motherfinance.bank.data.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val APP_ID = "wnV24w-O8SJiHqk2DYzynz"
const val SECRET_KEY = "fq7emqsxGUdXvZ6ck2mcH6Tvf-GbUgZZlcB1UMKn7wb99ny"
const val BASE_URL = "https://dev.meemee.online/"
object BankApiClient {
  fun getClient() : BankInterface{
      val requestInterceptor = Interceptor{
          chain ->
          val url : HttpUrl = chain.request()
              .url()
              .newBuilder()
              .build()
          val request : Request = chain.request()
              .newBuilder()
              .header("app-id", APP_ID)
              .header("secret-key", SECRET_KEY)
              .url(url)
              .build()
          return@Interceptor chain.proceed(request)
      }

      val okHttpClient : OkHttpClient = OkHttpClient.Builder()
          .addInterceptor(requestInterceptor)
          .connectTimeout(60,TimeUnit.SECONDS)
          .build()
      return Retrofit.Builder()
          .client(okHttpClient)
          .baseUrl(BASE_URL)
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(BankInterface::class.java)
  }

}