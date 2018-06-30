package gr.redefine.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import gr.redefine.Message;
import gr.redefine.R;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> values;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView messageText;
        public TextView timeText;
        public ImageView profileImg;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            messageText = v.findViewById(R.id.text);
            profileImg = v.findViewById(R.id.profileImage);
            timeText = v.findViewById(R.id.time_posted);
        }
    }

    public void swapItems(List<Message> items) {
        this.values = items;
        notifyDataSetChanged();
    }

    public void add(int position, Message item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessageAdapter(List<Message> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Message text = values.get(position);
        holder.messageText.setText(text.getMessage());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = formatter.format(new Date(text.getTimestamp()));
        holder.timeText.setText(dateString);
        if(text.getImgURL()!=null){
            Picasso.get().load(text.getImgURL()).into(holder.profileImg);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }
}
