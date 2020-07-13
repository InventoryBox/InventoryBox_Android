package com.example.inventorybox.etc

import android.util.Log
import io.reactivex.internal.util.HalfSerializer.onError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <ResponseType> Call<ResponseType>.ExchangeEnqueue(
    onFail:()-> Unit,
    onSuccess:(ResponseType)->Unit,
    onError:()->Unit={}
){
    this.enqueue(object: Callback<ResponseType> {
        override fun onFailure(call: Call<ResponseType>, t: Throwable) {
            onFail()
            Log.d("network", t.message)
        }

        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>) {
            response.body()?.let{
                onSuccess(it)

            }?:onError()
            Log.d("c_enqueue", response.message())
            Log.d("c_enqueue", response.code().toString())
        }
    })

}