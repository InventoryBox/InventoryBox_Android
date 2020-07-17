package com.example.inventorybox.etc

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun<ResponseType> Call<ResponseType>.errorIncludedEnqueue(
    onFail:(Throwable)->Unit,
    onSuccess:(ResponseType)->Unit,
    onError:(Response<ResponseType>)->Unit
){
    this.enqueue(object: Callback<ResponseType> {
        override fun onFailure(call: Call<ResponseType>, t: Throwable){
            onFail(t)
            Log.d("network", t.message)
        }

        override fun onResponse(call: Call<ResponseType>, response: Response<ResponseType>){
            response.body()?.let{
                onSuccess(it)
            }?:onError(response)
            Log.d("network", response.message())
            Log.d("network", response.code().toString())
        }
    })
}