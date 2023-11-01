package com.example.android_buoi9_btvn;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {
    @GET("products?limit=0")
    Call<ProductsResponse> getProducts();
}
