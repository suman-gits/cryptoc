package com.example.crptoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.crptoc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var dataset: ArrayList<model>
    private lateinit var rvAdapter:CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataset= ArrayList<model>()
        apiData
        rvAdapter= CustomAdapter(dataset)
        binding.rv.layoutManager=LinearLayoutManager(this)
        binding.rv.adapter=rvAdapter


        binding.search.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               val filterList = dataset.filter {
                   it.name.contains(s.toString(),ignoreCase = true)
               }
                rvAdapter.updateList(filterList)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })









    }


    val apiData:Unit
        get() {
            val queue = Volley.newRequestQueue(this)
            val url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest"

            val jsonObjectRequest:JsonObjectRequest = object:JsonObjectRequest(
                Method.GET, url, null,
                Response.Listener { response ->
                    binding.progressBar.isVisible=false
                    try {
                        val dataArray=response.getJSONArray("data")
                        for(i in 0 until dataArray.length()){
                            val dataObject=dataArray.getJSONObject(i)
                            val symbol = dataObject.getString("symbol")
                            val name = dataObject.getString("name")
                            val quote = dataObject.getJSONObject("quote")
                            val usd =quote.getJSONObject("USD")
                            val price = String.format("$"+"%.2f",usd.getDouble("price"))
                            dataset.add(model(symbol, price, name))




                        }
                        rvAdapter.notifyDataSetChanged()


                    }catch (e:Exception){
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()

                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            )
            {

                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["X-CMC_PRO_API_KEY"] = "091ac98f-d589-450f-826f-ffc84aa3246a"
                    // Add any additional headers if needed
                    return headers
                }
            }

            queue.add(jsonObjectRequest)

        }

}

