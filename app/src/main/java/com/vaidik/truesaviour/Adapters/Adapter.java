package com.vaidik.truesaviour.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vaidik.truesaviour.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    int[] actiLogo = new int[]{
            R.drawable.ic_still,
            R.drawable.ic_walking,
            R.drawable.ic_running,
            R.drawable.ic_driving,
            R.drawable.ic_on_bicycle,
            R.drawable.ic_unknown,
    };
    List<String> actiList;
    List<String> timeiList;
    List<String> transiList;


    public Adapter(List<String> myDataset, List<String> myDataset2, List<String> myDataset3) {
        actiList = myDataset;
        if (myDataset2 != null)
            timeiList = myDataset2;
        if (myDataset3 != null)
            transiList = myDataset3;
    }

    public Adapter(List<String> myDataset) {
        actiList = myDataset;
        Log.e("error", "hello ");
    }

    public void add(int position, String item) {
        actiList.add(position, item);
        notifyItemInserted(position);
    }

    private void remove(int position) {
        actiList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                 int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.activity_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String name = actiList.get(position);

        String time = "";
        if (timeiList != null)
            time = timeiList.get(position);

        String trans = "";
        if (transiList != null)
            trans = transiList.get(position);

        holder.acti.setText(name);
        holder.timei.setText(time);
        holder.transi.setText(trans);
        switch (name) {
            case "STILL":
                holder.actiLogo.setImageResource(actiLogo[0]);
                break;
            case "WALKING":
                holder.actiLogo.setImageResource(actiLogo[1]);
                break;
            case "RUNNING":
                holder.actiLogo.setImageResource(actiLogo[2]);
                break;
            case "IN_VEHICLE":
                holder.actiLogo.setImageResource(actiLogo[3]);
                break;
            case "ON_BICYCLE":
                holder.actiLogo.setImageResource(actiLogo[4]);
                break;
            default:
                holder.actiLogo.setImageResource(actiLogo[5]);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return actiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;

        TextView acti;
        TextView timei;
        TextView transi;
        ImageView actiLogo;

        ViewHolder(View v) {
            super(v);
            layout = v;
            acti = v.findViewById(R.id.acti);
            timei = v.findViewById(R.id.timei);
            transi = v.findViewById(R.id.transi);
            actiLogo = v.findViewById(R.id.actiLogo);
        }
    }

}
