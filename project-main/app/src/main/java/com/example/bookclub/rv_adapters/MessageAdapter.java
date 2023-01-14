package com.example.bookclub.rv_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclub.R;
import com.example.bookclub.parse_models.User_Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>
{
    private Context context;
    private List<User_Message> messages;

    public MessageAdapter(Context c, List<User_Message> m)
    {
        this.context = c;
        this.messages = m;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item_message, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        User_Message m = messages.get(position);
        holder.bind(m);
    }

    @Override
    public int getItemCount()
    {
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_author;
        private ImageView iv_sender_picture;
        private TextView tv_content;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tv_author = itemView.findViewById(R.id.tv_author);
            iv_sender_picture = itemView.findViewById(R.id.iv_sender_picture);
            tv_content = itemView.findViewById(R.id.tv_content);
        }

        public void bind(User_Message m)
        {
            tv_author.setText(m.get_sender().getUsername() + " says:");
            tv_content.setText(m.get_content());
        }
    }
}
