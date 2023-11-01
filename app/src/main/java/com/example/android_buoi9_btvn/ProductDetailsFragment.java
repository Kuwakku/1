package com.example.android_buoi9_btvn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private List<String> mListPhotos;
    private TextView tvTitle, tvPrice;
    private ProductService productServices;
    private List<Product> mListProducts;
    private ImageView imgBack;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }

    public static ProductDetailsFragment newInstance(String param1, String param2) {
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData();
        initView(view);
    }

    private void initView(View view) {
        tvTitle = view.findViewById(R.id.tvTitle);
        tvPrice = view.findViewById(R.id.tvPrice);
        imgBack = view.findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(ProductDetailsFragment.this);
                navController.navigate(R.id.homeFragment);
            }
        });

    }

    private void initData() {
        String productID = getArguments().getString("id");

        if (productID != null) {
            productServices.getProducts().enqueue(new Callback<ProductsResponse>() {
                @Override
                public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                    ProductsResponse productsResponse = response.body();
                    mListProducts = new ArrayList<>();
                    mListProducts = productsResponse.getProducts();
                    Product product = getProductById(mListProducts, Integer.parseInt(productID));
                    mListPhotos = new ArrayList<>();
                    mListPhotos = product.getImages();
                    tvTitle.setText(product.getTitle());
                    tvPrice.setText("$" + product.getPrice());
                }

                @Override
                public void onFailure(Call<ProductsResponse> call, Throwable t) {

                }
            });
        }
    }

    private Product getProductById(List<Product> productList, int productID) {
        return productList.stream()
                .filter(product -> product.getId() == productID)
                .findFirst()
                .orElse(null);
    }
}