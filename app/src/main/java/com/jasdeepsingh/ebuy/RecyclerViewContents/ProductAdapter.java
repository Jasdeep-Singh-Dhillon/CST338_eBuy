package com.jasdeepsingh.ebuy.RecyclerViewContents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jasdeepsingh.ebuy.R;
import com.jasdeepsingh.ebuy.entities.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> mProducts;
    private int mViewType;

    public static final int PRODUCT_VIEW = 1;
    public static final int ORDER_HISTORY_VIEW = 2;
    public static final int SHOPPING_CART_VIEW = 3;
    public static final int EDIT_DELETE_VIEW = 4;

    private OnItemClickListener mListener;


    public interface OnItemClickListener {
        void onBuyClick(int position);

        void onCartClick(int position);

        void onDeleteClick(int position);

        void onEditClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mName;
        public TextView mDescription;
        public TextView mPrice;
        public TextView mQuantity;
        public Button mBuy;
        public Button mAddToCart;
        public ImageView mDelete;
        public ImageView mEdit;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView, OnItemClickListener listener, int viewType) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.product_image);
            mName = itemView.findViewById(R.id.product_name);
            mDescription = itemView.findViewById(R.id.product_description);
            mPrice = itemView.findViewById(R.id.product_price);
            mQuantity = itemView.findViewById(R.id.product_quantity);
            mBuy = itemView.findViewById(R.id.product_buy);
            mAddToCart = itemView.findViewById(R.id.product_add_to_cart);
            mDelete = itemView.findViewById(R.id.product_cart_delete);
            mEdit = itemView.findViewById(R.id.product_edit);

            if(viewType == ProductAdapter.PRODUCT_VIEW) {
                mBuy.setVisibility(View.VISIBLE);
                mAddToCart.setVisibility(View.VISIBLE);
                mBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onBuyClick(position);
                            }
                        }
                    }
                });

                mAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onCartClick(position);
                            }
                        }
                    }
                });
            }

            if(viewType == ProductAdapter.ORDER_HISTORY_VIEW) {
                mBuy.setVisibility(View.GONE);
                mAddToCart.setVisibility(View.GONE);
                mDelete.setVisibility(View.VISIBLE);
                mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onDeleteClick(position);
                            }
                        }
                    }
                });
            }

            if(viewType == ProductAdapter.SHOPPING_CART_VIEW) {
                mBuy.setVisibility(View.GONE);
                mAddToCart.setVisibility(View.GONE);
                mDelete.setVisibility(View.VISIBLE);
                mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onDeleteClick(position);
                            }
                        }
                    }
                });
            }

            if(viewType == ProductAdapter.EDIT_DELETE_VIEW) {
                mBuy.setVisibility(View.GONE);
                mAddToCart.setVisibility(View.GONE);
                mDelete.setVisibility(View.VISIBLE);
                mDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onDeleteClick(position);
                            }
                        }
                    }
                });

                mEdit.setVisibility(View.VISIBLE);
                mEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onEditClick(position);
                            }
                        }
                    }
                });

            }

        }
    }

    public ProductAdapter(List<Product> products, int viewType) {
        mProducts = products;
        mViewType = viewType;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_layout, parent, false);
        return new ViewHolder(view, mListener, mViewType);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ProductAdapter.ViewHolder holder, int position) {
        Product currentItem = mProducts.get(position);

        holder.mImageView.setImageResource(currentItem.getImage());
        holder.mName.setText(currentItem.getName());
        holder.mDescription.setText(currentItem.getDescription());
        holder.mPrice.setText(currentItem.getPrice().toString());
        holder.mQuantity.setText(currentItem.getQuantity().toString());
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public void filterList(List<Product> filteredProducts) {
        mProducts = filteredProducts;
        notifyDataSetChanged();
    }

}
