package com.example.bookclub.rv_adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookclub.ClubActivity;
import com.example.bookclub.MessageActivity;
import com.example.bookclub.R;
import com.example.bookclub.parse_models.Club;
import com.example.bookclub.parse_models.User_Message;

import java.util.List;

public class ClubAdapter extends RecyclerView.Adapter<ClubAdapter.ViewHolder>
{
    private Context context;
    private List<Club> clubs;

    public ClubAdapter(Context c, List<Club> cl)
    {
        this.context = c;
        this.clubs = cl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item_club, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Club c = clubs.get(position);
        holder.bind(c);
    }

    @Override
    public int getItemCount()
    {
        return clubs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tv_club_name;
        private TextView tv_club_description;
        private ImageView iv_club_picture;
        private Button button_view;
        private Club club;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            tv_club_name = itemView.findViewById(R.id.tv_club_name);
            tv_club_description = itemView.findViewById(R.id.tv_club_description);
            iv_club_picture = itemView.findViewById(R.id.iv_club_picture);
            button_view = itemView.findViewById(R.id.button_view);
        }

        public void bind(Club c)
        {
            club = c;
            tv_club_name.setText(c.get_name());
            tv_club_description.setText(c.get_description());

            button_view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    goTo_clubActivity();
                }
            });
        }

        protected void goTo_clubActivity()
        {
            Intent i = new Intent(context, ClubActivity.class);
            i.putExtra("club_data", club.get_name());
            context.startActivity(i);
        }
    }
}
