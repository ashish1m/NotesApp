package com.example.notesapp.ui.note_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.repository.db.entity.Note;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private final LayoutInflater mInflater;
    private OnItemClickListener mListener;
    private List<Note> mNoteList;

    public NoteListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void updateList(List<Note> noteList) {
        mNoteList = noteList;
        notifyDataSetChanged();
    }

    public void addOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.note_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = mNoteList.get(position);
        holder.mNoteTitle.setText(note.getTitle());
        holder.mNoteDescriptionTv.setText(note.getNote());
        holder.mNoteDateTv.setText(String.valueOf(note.getTime()));

        holder.mMainLayoutCv.setTag(note.getId());
    }

    @Override
    public int getItemCount() {
        if (mNoteList != null) {
            return mNoteList.size();
        }
        return 0;
    }

    interface OnItemClickListener {
        void onItemClick(int noteId);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mNoteTitle;
        TextView mNoteDescriptionTv;
        TextView mNoteDateTv;
        CardView mMainLayoutCv;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNoteTitle = itemView.findViewById(R.id.tv_title);
            mNoteDescriptionTv = itemView.findViewById(R.id.tv_description);
            mNoteDateTv = itemView.findViewById(R.id.tv_date);
            mMainLayoutCv = itemView.findViewById(R.id.cv_mainLayout);

            mMainLayoutCv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cv_mainLayout:
                    if (mListener != null) {
                        mListener.onItemClick((int) v.getTag());
                    }
                    break;
            }
        }
    }
}
