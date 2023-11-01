package com.example.android_buoi9_btvn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements IClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView rvProduct, rvCategory;
    private ProductAdapter mProductAdapter, mCategoryAdapter;
    private List<Product> mListProducts;
    private ProductService productServices;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        callApiGetProducts();
    }

    private void callApiGetProducts() {
        productServices = RetrofitClient.create(ProductService.class);
        productServices.getProducts().enqueue(new Callback<ProductsResponse>() {
            @Override
            public void onResponse(Call<ProductsResponse> call, Response<ProductsResponse> response) {
                ProductsResponse productsResponse = response.body();
                mListProducts = new ArrayList<>(productsResponse.getProducts());
                mProductAdapter = new ProductAdapter(mListProducts, HomeFragment.this);
                rvProduct.setAdapter(mProductAdapter);
                getProductsByCategory(mListProducts);
            }

            @Override
            public void onFailure(Call<ProductsResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getStackTrace());
            }
        });
    }

    private void getProductsByCategory(List<Product> mListProducts) {
        List<Product> products = mListProducts.stream()
                .filter(product -> product.getCategory().equals("smartphones"))
                .collect(Collectors.toList());
        mCategoryAdapter = new ProductAdapter(products, HomeFragment.this);
        rvCategory.setAdapter(mCategoryAdapter);
    }

    private void initView(View view) {
        rvProduct = view.findViewById(R.id.rvProduct);
        rvCategory = view.findViewById(R.id.rvCategory);
    }

    @Override
    public void onItemClick(int prodctID) {
        Bundle bundle = new Bundle();
        bundle.putString("id", String.valueOf(prodctID));

        NavController navController = NavHostFragment.findNavController(HomeFragment.this);
        navController.navigate(R.id.action_homeFragment_to_productDetailsFragment, bundle);
    }

    @Override
    public void onResume() {
        super.onResume();

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav);
        ConstraintLayout clAppbar = requireActivity().findViewById(R.id.clAppbar);

        bottomNavigationView.setVisibility(View.VISIBLE);
        clAppbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPause() {
        super.onPause();

        BottomNavigationView bottomNavigationView = requireActivity().findViewById(R.id.bottom_nav);
        ConstraintLayout clAppbar = requireActivity().findViewById(R.id.clAppbar);

        bottomNavigationView.setVisibility(View.GONE);
        clAppbar.setVisibility(View.GONE);
    }
}