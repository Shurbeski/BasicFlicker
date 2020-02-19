package mk.ukim.finki.mpip.aud1.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mk.ukim.finki.mpip.aud1.R;
import mk.ukim.finki.mpip.aud1.viewholders.TextViewHolder;

public class TextViewAdapter extends
        RecyclerView.Adapter<TextViewHolder> {


    private Context context;
    private List<String> data;

    public TextViewAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TextViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_text_view, viewGroup, false);
        TextViewHolder textViewHolder = new TextViewHolder(view);
        return textViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TextViewHolder textViewHolder, int i) {
        String dataToShow=data.get(i);
        textViewHolder.getTextView().setText(dataToShow);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void addNewItemToTheList(String item) {
        data.add(item);
        notifyDataSetChanged();
    }
}
