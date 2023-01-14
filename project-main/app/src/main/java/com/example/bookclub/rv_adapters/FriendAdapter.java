package com.example.bookclub.rv_adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclub.MessageActivity;
import com.example.bookclub.R;
import com.example.bookclub.parse_models.User_Friend;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder>
{
    private Context context;
    private List<User_Friend> friends;

    public FriendAdapter(Context c, List<User_Friend> f)
    {
        this.context = c;
        this.friends = f;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        User_Friend f = friends.get(position);
        holder.bind(f);
    }

    @Override
    public int getItemCount()
    {
        return friends.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_friend_name;
        private ImageView iv_friend_picture;
        private Button button_friend_message;
        private User_Friend friend;

        protected void goTo_messageActivity()
        {
            Intent i = new Intent(context, MessageActivity.class);
            i.putExtra("friend_data", friend.get_friend().getUsername());
            context.startActivity(i);
        }

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tv_friend_name = itemView.findViewById(R.id.tv_username);
            iv_friend_picture = itemView.findViewById(R.id.iv_profile);
            button_friend_message = itemView.findViewById(R.id.button_message);
        }

        public void bind(User_Friend f)
        {
            friend = f;
            tv_friend_name.setText(friend.get_friend().getUsername());

            button_friend_message.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    goTo_messageActivity();
                }
            });
        }
    }
}
