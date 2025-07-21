package com.example.groupproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groupproject.R;
import com.example.groupproject.user.RequestDetailActivity;
import com.example.groupproject.model.Request;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private final List<Request> requestList;

    public RequestAdapter(List<Request> requestList) {
        this.requestList = requestList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvUser, tvItem, tvAddress, tvDate, tvStatus, tvNotes;
        Request currentRequest;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvItem = itemView.findViewById(R.id.tvItem);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvNotes = itemView.findViewById(R.id.tvNotes);

            itemView.setOnClickListener(this); // 👈 handle click
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, RequestDetailActivity.class);
            intent.putExtra("request_id", currentRequest.getRequest_id());
            intent.putExtra("user_id", currentRequest.getUser_id());
            intent.putExtra("item_id", currentRequest.getItem_id());
            intent.putExtra("address", currentRequest.getAddress());
            intent.putExtra("request_date", currentRequest.getRequest_date());
            intent.putExtra("status", currentRequest.getStatus());
            intent.putExtra("notes", currentRequest.getNotes());
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Request req = requestList.get(position);
        holder.tvUser.setText("User ID: " + req.getUser_id());
        holder.tvItem.setText("Item ID: " + req.getItem_id());
        holder.tvAddress.setText("Address: " + req.getAddress());
        holder.tvDate.setText("Date: " + req.getRequest_date());
        holder.tvStatus.setText("Status: " + req.getStatus());
        holder.tvNotes.setText("Notes: " + req.getNotes());
        holder.currentRequest = req; // 👈 save for click
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }
}
