package com.mo2a.example.shoppinglist.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.snackbar.Snackbar;
import com.mo2a.example.shoppinglist.ListActivity;
import com.mo2a.example.shoppinglist.R;
import com.mo2a.example.shoppinglist.data.DatabaseHandler;
import com.mo2a.example.shoppinglist.model.Item;

public class Popup {
    private AlertDialog.Builder builder;
    AlertDialog dialog;
    Button saveButton;
    TextView title;
    EditText itemName;
    EditText itemQuantity;
    EditText itemColor;
    EditText itemSize;
    private DatabaseHandler databaseHandler;

    public void createPopupDialog(final Activity context) {
        builder = new AlertDialog.Builder(context);
        View view = context.getLayoutInflater().inflate(R.layout.popup, null);
        title= view.findViewById(R.id.title);
        itemName = view.findViewById(R.id.item);
        itemQuantity = view.findViewById(R.id.itemQuantity);
        itemColor = view.findViewById(R.id.itemColor);
        itemSize = view.findViewById(R.id.itemSize);
        saveButton = view.findViewById(R.id.button_save);
        databaseHandler = new DatabaseHandler(context);


        title.setText("Add Item");

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!itemName.getText().toString().trim().isEmpty()) {
                        saveItem(v, context);
                    } else {
                        Snackbar.make(v, "Name field can not be empty.", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });


        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void saveItem(View view, Activity context) {
        Item item = new Item();
        item.setName(itemName.getText().toString().trim());
        if (itemQuantity.getText().toString().trim().equals("")) {
            item.setQuantity("none entered");
        }
        item.setQuantity(itemQuantity.getText().toString().trim());
        if (itemColor.getText().toString().trim().isEmpty()) {
            item.setColor("none entered");
        }
        item.setColor(itemColor.getText().toString().trim());
        if (itemSize.getText().toString().trim().isEmpty()) {
            item.setSize("none entered");
        }
        item.setSize(itemSize.getText().toString().trim());

        databaseHandler.addItem(item);
        Snackbar.make(view, "Item saved.", Snackbar.LENGTH_SHORT).show();

        moveToDetailsScreen(context);
    }

    private void moveToDetailsScreen(final Activity context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                context.startActivity(new Intent(context, ListActivity.class));
                if (context.getClass().equals(ListActivity.class)) {
                    context.finish();
                }
            }
        }, 1200);
    }
}
