package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    String BASE_URL = "https://fakestoreapi.com";
    String REGISTER_BASE_URL = "https://reqres.in";
    @GET("/post/{id}")
    Call<Post> getPostById(@Path(value = "id") int id);

    @GET("/search?")
    Call<List<UniversityModel>> getUniversity(@Query("count") String country);

    @GET("/products")
    Call<List<ProductModel>> getAllProduct();

    @GET("/products/{id}")
    Call<ProductModel> getProductId(@Path("id") int id);
    //auth
    @FormUrlEncoded
    @POST("/api/login")
    Call<AuthModel> postAuth(@Field("email") String email,@Field("password") String password);

    @GET("/api/login")
    Call<AuthModel> getAuth();

    @FormUrlEncoded
    @POST("/api/register")
    Call<AuthModel> postResister(@Field("email") String email,@Field("password") String password);

    @POST("/carts")
    Call<CartModel> postCarts(@Body CartModel cartModel);

    @GET("/users/{id}")
    Call<UserModel> getUseById(@Path("id") int id);

    @GET("/products/categories")
    Call<List<String>> getProductCategory();

    @PUT("/user/{id}")
    Call<UserModel> updateUser(@Path("id") int id);

}
