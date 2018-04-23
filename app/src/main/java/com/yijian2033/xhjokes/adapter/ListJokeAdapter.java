package com.yijian2033.xhjokes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yijian2033.xhjokes.R;
import com.yijian2033.xhjokes.mode.Data;

import java.util.ArrayList;

/**
 * @author zhangyj
 * @version [XHJokes, 2018-04-19]
 */
public class ListJokeAdapter extends RecyclerView.Adapter<ListJokeAdapter.ListJokeHolder> {

    private Context context;
    private ArrayList<Data> list;
    private OnJokesItemClickListener listener;

    public void setListener(OnJokesItemClickListener listener) {
        this.listener = listener;
    }

    public ListJokeAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public ListJokeAdapter(Context context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ListJokeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_list_joke, parent, false);
        ListJokeHolder listJokeHolder = new ListJokeHolder(inflate);
        return listJokeHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListJokeHolder holder, int position) {
        Data data = list.get(position);
        holder.textView.setText("\u3000\u3000" + data.getContent());
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    public void setDataSource(ArrayList<Data> list, boolean isRefresh) {
        if (isRefresh) {
            this.list.clear();
            this.list = list;
        } else {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public static class ListJokeHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ListJokeHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_content);
        }

    }
}
