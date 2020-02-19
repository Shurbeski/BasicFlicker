package mk.ukim.finki.mpip.aud1.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class TextViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public TextViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = (TextView)itemView;

    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
