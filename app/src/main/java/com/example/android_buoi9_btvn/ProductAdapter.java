package com.example.android_buoi9_btvn;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import java.util.List;
import java.util.Random;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    private Context mContext;
    private List<Product> mListProducts;
    private IClickListener iClickListener;

    public ProductAdapter(List<Product> mListProducts, IClickListener iClickListener) {
        this.mListProducts = mListProducts;
        this.iClickListener = iClickListener;
    }

    public ProductAdapter(List<Product> mListProducts) {
        this.mListProducts = mListProducts;
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mListProducts.get(position);
        Random rand = new Random();
        Glide.with(mContext).load(product.getImages().get(rand.nextInt(product.getImages().size()))).into(holder.imgImage);
        holder.tvTitle.setText(product.getTitle());
        holder.tvPrice.setText(product.getPrice() + "");
        holder.tvRating.setText(product.getRating() + "");
    }

    @Override
    public int getItemCount() {
        return mListProducts != null ? mListProducts.size() : 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgImage;
        TextView tvTitle, tvPrice, tvRating;
        ConstraintLayout clItem;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgImage = itemView.findViewById(R.id.imgImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvRating = itemView.findViewById(R.id.tvRating);

            clItem = itemView.findViewById(R.id.clItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition(); // Lấy vị trí của mục đã nhấp
                    if (position != RecyclerView.NO_POSITION) {
                        int productID = getItemIDAtPosition(position); // Lấy ID của sản phẩm tại vị trí
                        iClickListener.onItemClick(productID); // Gọi phương thức onItemClick và truyền productID
                    }
                }
            });
        }
    }

    private int getItemIDAtPosition(int position) {
        return mListProducts.get(position).getId();
    }
}
