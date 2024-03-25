package com.rudra.moengagenews.network

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

@OptIn(DelicateCoroutinesApi::class)
fun fetchDataFromApi(
        apiUrl: String,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL(apiUrl)
                val urlConnection = url.openConnection() as HttpURLConnection

                urlConnection.requestMethod = "GET"
                urlConnection.connectTimeout = 10000
                urlConnection.readTimeout = 10000

                val responseCode = urlConnection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStreamReader = InputStreamReader(urlConnection.inputStream)
                    val bufferedReader = BufferedReader(inputStreamReader)
                    val stringBuilder = StringBuilder()
                    var line: String? = null

                    while (bufferedReader.readLine().also { line = it } != null) {
                        stringBuilder.append(line)
                    }

                    bufferedReader.close()
                    inputStreamReader.close()

                    val result = stringBuilder.toString()
                    onSuccess(result)
                } else {
                    onError(Exception("Failed to fetch data. Response code: $responseCode"))
                }
            } catch (e: Exception) {
                onError(e)
            }
        }
    }