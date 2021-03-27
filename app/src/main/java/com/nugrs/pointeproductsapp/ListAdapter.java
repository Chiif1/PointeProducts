package com.nugrs.pointeproductsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

//Adapter used to fill recyclerview on Homepage
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ProductViewHolder> {
    public Context mContext;
    public ArrayList<ListObjects> mlistObjects;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ListAdapter(Context context, ArrayList<ListObjects> listObjects) {
        mContext = context;
        mlistObjects = listObjects;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.list_items, parent, false);

        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        ListObjects currentItem = mlistObjects.get(position);
        String image = currentItem.getImage();
        String product = currentItem.getProductName();
        String price = currentItem.getProductPrice();
        String description = currentItem.getProductDescription();

        MainActivity mainAct = new MainActivity();
        holder.mTextViewProduct.setText(product);
        holder.mTextViewPrice.setText(mainAct.formatPrice(price));
        File imageFromInternal1 = new File(mContext.getFilesDir(), image);
        Bitmap myBitmap = BitmapFactory.decodeFile(imageFromInternal1.getAbsolutePath());
        holder.mImageView.setImageBitmap(myBitmap);
        holder.mRelativeLayoutList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, XProduct1.class);
                intent.putExtra("IMAGE", image);
                intent.putExtra("PRODUCT", product);
                intent.putExtra("PRICE", price);
                intent.putExtra("DESCRIPTION", description);
                mContext.startActivity(intent);

                Log.d("MyApp", ",,,,,,,,,,,,," + image);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlistObjects.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextViewProduct;
        public TextView mTextViewPrice;
        public TextView mTextViewDescription;
        public RelativeLayout mRelativeLayoutList;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.Image1);
            mTextViewProduct = itemView.findViewById(R.id.productName1);
            mTextViewPrice = itemView.findViewById(R.id.productPrice1);
            mRelativeLayoutList = itemView.findViewById(R.id.productHolder1List);
        }
    }
}

