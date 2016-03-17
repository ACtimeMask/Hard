package com.demo.qiushi.hard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/3/14.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{
    private List<Todo> mDatas;
    public TodoAdapter(List<Todo> mDatas,OnItemClickListener onClickListener,OnItemLongClickListener onLongClickListener){
        this.mDatas=mDatas;
        this.onClickListener=onClickListener;
        this.onLongClickListener=onLongClickListener;
    }
   interface OnItemClickListener{
        void onClick(View v);
    }
    interface OnItemLongClickListener{
        void onLongClick(View v);
    }
    private OnItemClickListener onClickListener;
    private OnItemLongClickListener onLongClickListener;
    public  void deleteDate(int pos){
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }
    public void change(int po){
        mDatas.subList(po,po);

    }
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vw= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo,parent,false);
        return new ViewHolder(vw,onClickListener,onLongClickListener) ;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo todo=mDatas.get(position);
        holder.Tv_title.setText(todo.getTitle());
        holder.Tv_content.setText(todo.getContent());
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView Tv_title;
        TextView Tv_content;
        ImageView Tv_tu;
        public ViewHolder(View itemView,final OnItemClickListener onClickListener,final OnItemLongClickListener onLongClickListener){
            super(itemView);
            Tv_title=(TextView)itemView.findViewById(R.id.item_title);
            Tv_content=(TextView)itemView.findViewById(R.id.item_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener!=null){
                        onClickListener.onClick(v);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onLongClickListener!=null){
                        onLongClickListener.onLongClick(v);
                    }
                    return true;
                }
            });
        }

    }
}
