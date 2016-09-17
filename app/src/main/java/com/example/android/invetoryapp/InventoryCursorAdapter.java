
package com.example.android.invetoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.invetoryapp.data.InventoryContract;
import com.example.android.invetoryapp.data.InventoryContract.InventoryEntry;;

public class InventoryCursorAdapter extends CursorAdapter {

    public InventoryCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(final View view, final Context context, final Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.price);
        ImageView imageImageView = (ImageView) view.findViewById(R.id.image);
        TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
        TextView saleTextView = (TextView) view.findViewById(R.id.sale);
        final Button button = (Button) view.findViewById(R.id.saleList);

        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        Log.v("nameColumnIndex", nameColumnIndex + "");
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
        int imageColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_IMAGE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_CURRENT_QUANTITY);
        int saleColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SALE);
        int idColumnIndex = cursor.getColumnIndex(InventoryEntry._ID);
        Log.v("idColumnIndex", idColumnIndex + "");


        String inventoryName = cursor.getString(nameColumnIndex);
        Log.v("inventoryName", inventoryName + "");
        String inventoryPrice = cursor.getString(priceColumnIndex);
        byte[] inventoryImage = cursor.getBlob(imageColumnIndex);
        String inventoryQuantity = cursor.getString(quantityColumnIndex);
        String inventorySale = cursor.getString(saleColumnIndex);
        String inventoryId = cursor.getString(idColumnIndex);
        Log.v("inventoryId", inventoryId + "");

        Bitmap inventoryImageBitmap = DbBitmapUtility.getImage(inventoryImage);

        nameTextView.setText("name: " + inventoryName);
        summaryTextView.setText("price: " + inventoryPrice);
        imageImageView.setImageBitmap(inventoryImageBitmap);
        quantityTextView.setText("quantity: " + inventoryQuantity);
        saleTextView.setText("sale: " + inventorySale);

        button.setTag(inventoryId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView quantityTextView = (TextView) view.findViewById(R.id.quantity);
                String[] quantity = quantityTextView.getText().toString().split(" ", 2);
                TextView saleTextView = (TextView) view.findViewById(R.id.sale);
                String[] sale = saleTextView.getText().toString().split(" ", 2);

                int quantityValue = Integer.parseInt(quantity[1]);
                int saleValue = Integer.parseInt(sale[1]);
                if (quantityValue > 0) {
                    saleTextView.setText("sale: " + +saleValue);
                    quantityTextView.setText("quantity: " + -quantityValue);
                    String row = (String) button.getTag();

                    Uri mCurrentInventoryUri = Uri.withAppendedPath(InventoryContract.BASE_CONTENT_URI, InventoryContract.PATH_INVENTORY);
                    ContentValues values = new ContentValues();
                    values.put(InventoryEntry.COLUMN_CURRENT_QUANTITY, --quantityValue);
                    values.put(InventoryEntry.COLUMN_SALE, ++saleValue);
                    int rowsAffected = context.getContentResolver().update(mCurrentInventoryUri, values, "_id=" + row, null);
                }
            }
        });

    }

}
