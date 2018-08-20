package deal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import top.bubble.bubblesunpim.NotesDetails;
import top.bubble.bubblesunpim.R;

/**
 * Created by Jzhson on 11/8/2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    List<NoteBean> NotesArry;
    Context Homeactivity;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View NotesView;
        TextView notes_title;
        TextView notes_time;
        public ViewHolder(View view){
            super(view);
            NotesView=view;
            notes_title=view.findViewById(R.id.notesitems_title);
            notes_time=view.findViewById(R.id.notesitems_time);
        }
    }
    public NoteAdapter(Context context, ArrayList<NoteBean> notesList){
        Homeactivity=context;
        this.NotesArry=notesList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_items,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.NotesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                NoteBean Notes=NotesArry.get(position);
                int i=Notes.getIds();
                Intent intent=new Intent(v.getContext(),NotesDetails.class);
                intent.putExtra("ids",i);
                v.getContext().startActivity(intent);
                if(Activity.class.isInstance(Homeactivity))
                {
                    // 转化为activity，然后finish就行了
                    Activity activity = (Activity)Homeactivity;
                    activity.finish();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoteBean notes=NotesArry. get(position);
        holder.notes_title.setText(notes.getTitle().toString());
        holder.notes_time.setText(notes.getTimes().toString());
    }

    @Override
    public int getItemCount() {
        return NotesArry.size();
    }




}
