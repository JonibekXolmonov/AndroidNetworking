package com.example.androidnetworking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.androidnetworking.adapter.PostAdapter
import com.example.androidnetworking.model.Poster
import com.example.androidnetworking.model.PosterResp
import com.example.androidnetworking.networking.retrofit.RetrofitHttp
import com.example.androidnetworking.networking.volley.VolleyHandler
import com.example.androidnetworking.networking.volley.VolleyHttp
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var poster: Poster
    private lateinit var rvPost: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private lateinit var btnAdd: FloatingActionButton
    private lateinit var posts: ArrayList<Poster>
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        progressBar = findViewById(R.id.progressBar)
        rvPost = findViewById(R.id.rvPost)
        poster = Poster(1, 1, "PDP", "Online")

        apiPosterList()
        //controlVolley()
        //controlRetrofit()
    }

    private fun apiPosterList() {
        progressBar.visibility = View.VISIBLE

        VolleyHttp.get(VolleyHttp.API_LIST_POST, VolleyHttp.paramsEmpty(), object : VolleyHandler {
            override fun onSuccess(response: String?) {
                val postArray = Gson().fromJson(response, Array<Poster>::class.java)
                posts = ArrayList()
                posts.clear()
                posts.addAll(postArray)

                progressBar.visibility = View.GONE

                refreshAdapter(posts)
            }

            override fun onError(error: String?) {
                Logger.e("@@@@Error", error.toString())
                progressBar.visibility = View.GONE
            }

        })
    }

    fun refreshAdapter(posts: ArrayList<Poster>) {
        postAdapter = PostAdapter(posts)

        postAdapter.itemClick = { post ->
            dialogPoster(post)
        }

        rvPost.adapter = postAdapter
    }

    private fun dialogPoster(post: Poster) {
        AlertDialog.Builder(this)
            .setTitle("Delete post")
            .setMessage("Are you sure to delete this post")
            .setPositiveButton(
                android.R.string.yes
            ) { _, _ ->
                apiPosterDelete(post)
            }
            .setNegativeButton(android.R.string.no, null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    private fun apiPosterDelete(post: Poster) {

        progressBar.visibility = View.VISIBLE

        VolleyHttp.del(VolleyHttp.API_DELETE_POST + post.id, object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Logger.d("@@@Delete", response!!)
                apiPosterList()
                progressBar.visibility = View.GONE
            }

            override fun onError(error: String?) {
                Logger.e("@@@Delete", error!!)
                progressBar.visibility = View.GONE
            }

        })
    }

    private fun controlRetrofit() {
        // getPosts()

        //createPost()

        //updatePost()

        //deletePost()

        singlePost()
    }

    //retrofit
    private fun getPosts() {
        RetrofitHttp.posterService.listPost().enqueue(object : Callback<ArrayList<PosterResp>> {
            override fun onResponse(
                call: Call<ArrayList<PosterResp>>,
                response: Response<ArrayList<PosterResp>>
            ) {
                Logger.d("@@@", response.body().toString())
            }

            override fun onFailure(call: Call<ArrayList<PosterResp>>, t: Throwable) {
                Logger.e("@@@", t.message.toString())
            }

        })
    }

    private fun createPost() {
        RetrofitHttp.posterService.createPost(poster).enqueue(object : Callback<PosterResp> {
            override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                Logger.d("@@@", response.body().toString())
            }

            override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                Logger.e("@@@", t.message.toString())
            }
        })
    }

    private fun updatePost() {
        RetrofitHttp.posterService.updatePost(poster.id, poster)
            .enqueue(object : Callback<PosterResp> {
                override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                    Logger.d("@@@", response.body().toString())
                }

                override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                    Logger.e("@@@", t.message.toString())
                }
            })
    }

    private fun deletePost() {
        RetrofitHttp.posterService.deletePost(poster.id).enqueue(object : Callback<PosterResp> {
            override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                Logger.d("@@@", response.body().toString())
            }

            override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                Logger.e("@@@", t.message.toString())
            }

        })
    }

    private fun singlePost() {
        RetrofitHttp.posterService.singlePost(poster.id).enqueue(object : Callback<PosterResp> {
            override fun onResponse(call: Call<PosterResp>, response: Response<PosterResp>) {
                Logger.d("@@@", response.body().toString())
            }

            override fun onFailure(call: Call<PosterResp>, t: Throwable) {
                Logger.e("@@@", t.message.toString())
            }

        })
    }


    //volley
    private fun controlVolley() {
//        getPosters()
//
//        addPoster()
//
//        putPoster()

        deletePoster()
    }

    private fun deletePoster() {
        VolleyHttp.del(VolleyHttp.API_DELETE_POST + poster.id, object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Logger.d("@@@", response!!)
            }

            override fun onError(error: String?) {
                Logger.e("@@@", error!!)
            }

        })
    }

    private fun putPoster() {
        VolleyHttp.put(
            VolleyHttp.API_UPDATE_POST + poster.id,
            VolleyHttp.paramsUpdate(poster),
            object : VolleyHandler {
                override fun onSuccess(response: String?) {
                    Logger.d("@@@", response!!)
                }

                override fun onError(error: String?) {
                    Logger.e("@@@", error!!)
                }

            })
    }

    private fun addPoster() {

        VolleyHttp.post(
            VolleyHttp.API_CREATE_POST,
            VolleyHttp.paramsCreate(poster),
            object : VolleyHandler {
                override fun onSuccess(response: String?) {
                    Logger.d("@@@", response!!)
                }

                override fun onError(error: String?) {
                    Logger.e("@@@", error!!)
                }

            })
    }

    private fun getPosters() {
        VolleyHttp.get(VolleyHttp.API_LIST_POST, VolleyHttp.paramsEmpty(), object : VolleyHandler {
            override fun onSuccess(response: String?) {
                Logger.d("@@@", response!!)
            }

            override fun onError(error: String?) {
                Logger.e("@@@", error!!)
            }

        })
    }
}