package com.example.bookclub.rv_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclub.R;
import com.example.bookclub.parse_models.Club;
import com.example.bookclub.parse_models.Club_Message;
import com.example.bookclub.parse_models.User_Message;

import java.util.List;

public class ClubMessageAdapter extends RecyclerView.Adapter<ClubMessageAdapter.ViewHolder>
{
    private Context context;
    private List<Club_Message> club_messages;

    public ClubMessageAdapter(Context c, List<Club_Message> cm)
    {
        this.context = c;
        this.club_messages = cm;
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
        Club_Message cm = club_messages.get(position);
        holder.bind(cm);
    }

    @Override
    public int getItemCount()
    {
        return club_messages.size();
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

        public void bind(Club_Message cm)
        {
            tv_author.setText(cm.get_sender().getUsername() + " says:");
            tv_content.setText(cm.get_message());
        }
    }
}
