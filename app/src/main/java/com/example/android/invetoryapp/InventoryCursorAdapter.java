package com.example.android.invetoryapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.invetoryapp.data.InventoryContract.InventoryEntry;

import java.sql.Blob;


public class InventoryCursorAdapter extends CursorAdapter {


    public InventoryCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    /**
     * This method binds the pet data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current pet can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        ImageView imageImageView = (ImageView) view.findViewById(R.id.image);

        // Find the columns of pet attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
        int imageColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_IMAGE);

        // Read the pet attributes from the Cursor for the current pet
        String inventoryName = cursor.getString(nameColumnIndex);
        String inventoryPrice = cursor.getString(priceColumnIndex);
        byte[] inventoryImage = cursor.getBlob(imageColumnIndex);

        Bitmap inventoryImageBitmap = DbBitmapUtility.getImage(inventoryImage);


        // Update the TextViews with the attributes for the current pet
        nameTextView.setText(inventoryName);
        summaryTextView.setText(inventoryPrice);
        imageImageView.setImageBitmap(inventoryImageBitmap);
    }

}
