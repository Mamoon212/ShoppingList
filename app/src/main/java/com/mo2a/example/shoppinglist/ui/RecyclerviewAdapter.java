package com.mo2a.example.shoppinglist.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mo2a.example.shoppinglist.R;
import com.mo2a.example.shoppinglist.data.DatabaseHandler;
import com.mo2a.example.shoppinglist.model.Item;


import java.text.MessageFormat;
import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerviewAdapter";
    private Activity context;
    private List<Item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;
    private LayoutInflater inflater;
    private Popup popup = new Popup();

    public RecyclerviewAdapter(Activity context, List<Item> items) {
        this.context = context;
        this.itemList = items;
    }

    @NonNull
    @Override
    public RecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerviewAdapter.ViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemSize.setText(MessageFormat.format("Size: {0}", item.getSize()));
        holder.itemQnty.setText(MessageFormat.format("Quantity: {0}", item.getQuantity()));
        holder.itemClr.setText(MessageFormat.format("Color: {0}", item.getColor()));
        holder.dateAdded.setText(MessageFormat.format("Date added: {0}", item.getDateAdded()));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemName;
        TextView itemClr;
        TextView itemQnty;
        TextView itemSize;
        TextView dateAdded;
        Button edit;
        Button delete;
        public int id;

        ViewHolder(@NonNull View itemView, Activity ctx) {
            super(itemView);
            context = ctx;

            itemName = itemView.findViewById(R.id.item_name);
            itemClr = itemView.findViewById(R.id.item_color);
            itemQnty = itemView.findViewById(R.id.item_quantity);
            itemSize = itemView.findViewById(R.id.item_size);
            dateAdded = itemView.findViewById(R.id.item_date);
            edit = itemView.findViewById(R.id.editButton);
            delete = itemView.findViewById(R.id.deleteButton);

            edit.setOnClickListener(this);
            delete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int pos;
            switch (v.getId()) {
                case R.id.editButton:
                    pos = getAdapterPosition();
                    Item item1 = itemList.get(pos);
                    Log.d(TAG, "onClick: " + v.getId());
                    editItem(item1);
                    break;
                case R.id.deleteButton:
                    pos = getAdapterPosition();
                    Item item2 = itemList.get(pos);
                    deleteItem(item2.getId());
                    break;
                default:
                    break;
            }
        }

        private void editItem(final Item item) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup, null);

            Button saveButton= view.findViewById(R.id.button_save);
            TextView title = view.findViewById(R.id.title);
            final EditText name= view.findViewById(R.id.item);
            final EditText color= view.findViewById(R.id.itemColor);
            final EditText size= view.findViewById(R.id.itemSize);
            final EditText quantity= view.findViewById(R.id.itemQuantity);

            title.setText("Edit Item");
            name.setText(item.getName());
            color.setText(item.getColor());
            size.setText(item.getSize());
            quantity.setText(item.getQuantity());

            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //update our item
                    DatabaseHandler databaseHandler = new DatabaseHandler(context);

                    //update items
                    item.setName(name.getText().toString());
                    item.setColor(color.getText().toString());
                    item.setQuantity(quantity.getText().toString());
                    item.setSize(size.getText().toString());

                    if (!name.getText().toString().isEmpty()) {
                        databaseHandler.updateItem(item);
                        notifyItemChanged(getAdapterPosition(),item); //important!
                    }else {
                        Snackbar.make(view, "Fields Empty",
                                Snackbar.LENGTH_SHORT)
                                .show();
                    }

                    alertDialog.dismiss();

                }
            });


        }

        private void deleteItem(final int id) {
            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation, null);
            Button noButton = view.findViewById(R.id.cancel_btn);
            Button yesButton = view.findViewById(R.id.confirm_btn);
            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.show();

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    alertDialog.dismiss();
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

        }
    }
}
