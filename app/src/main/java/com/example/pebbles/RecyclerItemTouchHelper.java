package com.example.pebbles;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pebbles.Adapters.INUAdapter;
import com.example.pebbles.Adapters.IUAdapter;
import com.example.pebbles.Adapters.NINUAdapter;
import com.example.pebbles.Adapters.NIUAdapter;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private String currentAdapter = null;
    private IUAdapter iuAdapter;
    private NIUAdapter niuAdapter;
    private INUAdapter inuAdapter;
    private NINUAdapter ninuAdapter;


    public RecyclerItemTouchHelper(IUAdapter adapter) {
        //super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        super(0, ItemTouchHelper.LEFT);
        this.iuAdapter = adapter;
        this.currentAdapter = "iu";
    }
    public RecyclerItemTouchHelper(NIUAdapter adapter) {
        super(0, ItemTouchHelper.LEFT);
        this.niuAdapter = adapter;
        this.currentAdapter = "niu";
    }
    public RecyclerItemTouchHelper(INUAdapter adapter) {
        super(0, ItemTouchHelper.LEFT);
        this.inuAdapter = adapter;
        this.currentAdapter = "inu";
    }
    public RecyclerItemTouchHelper(NINUAdapter adapter) {
        super(0, ItemTouchHelper.LEFT);
        this.ninuAdapter = adapter;
        this.currentAdapter = "ninu";
    }


    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
        final int position = viewHolder.getAdapterPosition();
        if (direction == ItemTouchHelper.LEFT) {
            AlertDialog.Builder builder = null;
            switch (currentAdapter){
                case "iu":
                    builder = new AlertDialog.Builder(iuAdapter.getContext());
                    break;
                case "niu":
                    builder = new AlertDialog.Builder(niuAdapter.getContext());
                    break;
                case "inu":
                    builder = new AlertDialog.Builder(inuAdapter.getContext());
                    break;
                case "ninu":
                    builder = new AlertDialog.Builder(ninuAdapter.getContext());
                    break;
            }
            builder.setTitle("Delete Task");
            builder.setMessage("Are you sure you want to delete this Task?");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (currentAdapter){
                                case "iu": iuAdapter.deleteItem(position);
                                break;
                                case "niu": niuAdapter.deleteItem(position);
                                break;
                                case "inu": inuAdapter.deleteItem(position);
                                    break;
                                case "ninu": ninuAdapter.deleteItem(position);
                                    break;
                            }
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (currentAdapter){
                        case "iu":
                            iuAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            break;
                        case "niu":
                            niuAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            break;
                        case "inu":
                            inuAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            break;
                        case "ninu":
                            ninuAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                            break;
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            //adapter.editItem(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        Drawable icon = null;
        ColorDrawable background = null;

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        if (dX > 0) {
//            System.out.println("kand" + adapter);
            switch (currentAdapter){
                case "iu":
                    icon = ContextCompat.getDrawable(iuAdapter.getContext(), R.drawable.ic_baseline_edit);
                    background = new ColorDrawable(ContextCompat.getColor(iuAdapter.getContext(), R.color.colorPrimaryDark));
                    break;
                case "niu":
                    System.out.println("kand: Im here!");
                    icon = ContextCompat.getDrawable(niuAdapter.getContext(), R.drawable.ic_baseline_edit);
                    background = new ColorDrawable(ContextCompat.getColor(niuAdapter.getContext(), R.color.colorPrimaryDark));
                    break;
                case "inu":
                    System.out.println("kand: Im here!");
                    icon = ContextCompat.getDrawable(inuAdapter.getContext(), R.drawable.ic_baseline_edit);
                    background = new ColorDrawable(ContextCompat.getColor(inuAdapter.getContext(), R.color.colorPrimaryDark));
                    break;case "ninu":
                    System.out.println("kand: Im here!");
                    icon = ContextCompat.getDrawable(ninuAdapter.getContext(), R.drawable.ic_baseline_edit);
                    background = new ColorDrawable(ContextCompat.getColor(ninuAdapter.getContext(), R.color.colorPrimaryDark));
                    break;
            }
        } else {
//            System.out.println("kand" + adapter);
            switch (currentAdapter){
                case "iu":
                    icon = ContextCompat.getDrawable(iuAdapter.getContext(), R.drawable.ic_baseline_delete_24);
                    background = new ColorDrawable(Color.RED);
                    break;
                case "niu":
                    icon = ContextCompat.getDrawable(niuAdapter.getContext(), R.drawable.ic_baseline_delete_24);
                    background = new ColorDrawable(Color.RED);
                    break;
                case "inu":
                    icon = ContextCompat.getDrawable(inuAdapter.getContext(), R.drawable.ic_baseline_delete_24);
                    background = new ColorDrawable(Color.RED);
                    break;
                case "ninu":
                    icon = ContextCompat.getDrawable(ninuAdapter.getContext(), R.drawable.ic_baseline_delete_24);
                    background = new ColorDrawable(Color.RED);
                    break;
            }
        }

        assert icon != null;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

//        if (dX > 0) { // Swiping to the right
//            int iconLeft = itemView.getLeft() + iconMargin;
//            int iconRight = itemView.getLeft() + iconMargin + icon.getIntrinsicWidth();
//            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
//
//            background.setBounds(itemView.getLeft(), itemView.getTop(),
//                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset, itemView.getBottom());
//        }
//        else
            if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        }
        else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }
}