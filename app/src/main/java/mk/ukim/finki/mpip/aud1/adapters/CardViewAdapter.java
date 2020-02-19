package mk.ukim.finki.mpip.aud1.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import com.bumptech.glide.Glide;

import java.util.List;

import mk.ukim.finki.mpip.aud1.FlickrItemDetailsActivity;
import mk.ukim.finki.mpip.aud1.R;
import mk.ukim.finki.mpip.aud1.adapters.viewholder.CardItemViewHolder;
import mk.ukim.finki.mpip.aud1.models.FlickrItem;
import mk.ukim.finki.mpip.aud1.models.FlickrResponse;

public class CardViewAdapter extends RecyclerView.Adapter<CardItemViewHolder> {

    private List<FlickrItem> data;
    private Context context;

    public CardViewAdapter(Context context, List<FlickrItem> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CardItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_view_item,viewGroup,false);
        CardItemViewHolder holder = new CardItemViewHolder(view);
        holder.setParent(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardItemViewHolder cardItemViewHolder, int i) {
        FlickrItem entity = data.get(i);
        cardItemViewHolder.bind(entity);
        cardItemViewHolder.getParent().setOnClickListener(v-> {
            Intent intent = new Intent(context,FlickrItemDetailsActivity.class);
            intent.putExtra(context.getString(R.string.str_extra_image_url),entity.getMedia().getPhotoUrl());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (data != null && data!=null) {
            return data.size();
        }
        return 0;
    }

    public void updateData(List<FlickrItem> data) {
        this.data  = data;
        notifyDataSetChanged();
    }
}
