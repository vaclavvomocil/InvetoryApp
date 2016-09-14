package com.example.android.invetoryapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.invetoryapp.data.InventoryContract.InventoryEntry;

import java.sql.Blob;
import java.util.zip.Inflater;


public class InventoryCursorAdapter extends CursorAdapter {

    private String inventoryQuantity;
    private String inventorySale;
    private TextView saleTextView;
    private TextView quantityTextView;
    private Cursor cursor;
    private Context context;
    Toast toast;

    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.price);
        ImageView imageImageView = (ImageView) view.findViewById(R.id.image);
        quantityTextView = (TextView) view.findViewById(R.id.quantity);
        saleTextView = (TextView) view.findViewById(R.id.sale);

        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
        int imageColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_IMAGE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_CURRENT_QUANTITY);
        int saleColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SALE);

        String inventoryName = cursor.getString(nameColumnIndex);
        String inventoryPrice = cursor.getString(priceColumnIndex);
        byte[] inventoryImage = cursor.getBlob(imageColumnIndex);
        inventoryQuantity = cursor.getString(quantityColumnIndex);
        inventorySale = cursor.getString(saleColumnIndex);

        Bitmap inventoryImageBitmap = DbBitmapUtility.getImage(inventoryImage);

        nameTextView.setText("name: " +inventoryName);
        summaryTextView.setText("price: " + inventoryPrice);
        imageImageView.setImageBitmap(inventoryImageBitmap);
        quantityTextView.setText("quantity: " + inventoryQuantity);
        saleTextView.setText("sale: " + inventorySale);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View inflatedView = super.getView(position, convertView, parent);

        inflatedView.findViewById(R.id.saleList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView quantityTextView = (TextView) inflatedView.findViewById(R.id.quantity);
                String[] quantity = quantityTextView.getText().toString().split(" ", 2);
                TextView saleTextView = (TextView) inflatedView.findViewById(R.id.sale);
                String[] sale = saleTextView.getText().toString().split(" ", 2);

                int quantityValue = Integer.parseInt(quantity[1]);
                int saleValue =  Integer.parseInt(sale[1]);
                if (quantityValue > 0 ) {
                    saleTextView.setText("sale: " + ++saleValue);
                    quantityTextView.setText("quantity: " + --quantityValue);
                }
            }
        });
        return inflatedView;
    }


}
