package com.eyecool.visitor.sysinner.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eyecool.visitor.sysinner.R;
import com.eyecool.visitor.sysinner.interfaces.Constants;
import com.eyecool.visitorsys.db.VisitorLog;

import java.util.List;

/**
 * Created date: 2017/6/15
 * Author:  Leslie
 */

public class VisitorInfoAdapter extends RecyclerView.Adapter<VisitorInfoViewHolder> implements View.OnClickListener {
    private static final String TAG = "VisitorInfoAdapter";
    private LayoutInflater inflater;
    private Context context;
    private List<VisitorLog> list;
    private onItemClickListener itemClickListener;

    public VisitorInfoAdapter(Context context, List<VisitorLog> list) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);

        setHasStableIds(true);
    }

    public void setItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public VisitorInfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            Log.d(TAG, "onCreateViewHolder: -----------------inflater is null!");
        }
        View view = inflater.inflate(R.layout.info_item, parent, false);
        view.setOnClickListener(this);
        VisitorInfoViewHolder visitorInfoViewHolder = new VisitorInfoViewHolder(view);
        return visitorInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(VisitorInfoViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        holder.itemView.setTag(position);
        VisitorLog itemVisitorLogInfo = list.get(position);
        if (position == list.size() - 1) {
            holder.greyLineBottom.setVisibility(View.VISIBLE);
        } else {
            holder.greyLineBottom.setVisibility(View.GONE);
        }
        if (position == 0) {
            holder.llInfoItemContainer.setBackground(null);
            holder.tvVisitorOrder.setText("序号");
            holder.tvVisitorState.setText("状态");
            holder.tvParentName.setText(itemVisitorLogInfo.getName());
            holder.tvStuName.setText(itemVisitorLogInfo.getVisitor_stu());
            holder.tvStuClass.setText(itemVisitorLogInfo.getSt_class());
            holder.tvWeclomeTeacher.setText(itemVisitorLogInfo.getStu_teacher());
            holder.tvVisitorTime.setText(itemVisitorLogInfo.getMatch_time());
        } else {


            holder.tvVisitorOrder.setText(itemVisitorLogInfo.getId() + "");
            holder.tvParentName.setText(itemVisitorLogInfo.getName());
            holder.tvStuName.setText(itemVisitorLogInfo.getVisitor_stu());
            holder.tvStuClass.setText(itemVisitorLogInfo.getSt_class());
            holder.tvWeclomeTeacher.setText(itemVisitorLogInfo.getStu_teacher());
            //访客类型
            switch (Integer.parseInt(itemVisitorLogInfo.getVisitor_type())) {
                case Constants.USER_TYPE_PARENT:
                    holder.tvVisitorState.setText("通过");
                    holder.tvVisitorState.setTextColor(context.getResources().getColor(R.color.colorGreen1));
                    break;
                case Constants.USER_TYPE_TEACHER:
                    holder.tvVisitorState.setText("教师出入");
                    holder.tvVisitorState.setTextColor(context.getResources().getColor(R.color.colorGreen1));
                    break;
                case Constants.USER_TYPE_STU:
                    holder.tvVisitorState.setText("学生出入");
                    holder.tvVisitorState.setTextColor(context.getResources().getColor(R.color.colorGreen1));
                    break;
                case Constants.USER_TYPE_BLACKLIST:
                    holder.tvVisitorState.setText("黑名单");
                    holder.tvVisitorState.setTextColor(context.getResources().getColor(R.color.colorGrey4));
                    break;
                case Constants.USER_TYPE_REFUSE:
                    holder.tvVisitorState.setText("教师 拒绝");
                    holder.tvVisitorState.setTextColor(context.getResources().getColor(R.color.colorRed2));
                    break;
            }
            holder.tvVisitorTime.setText(itemVisitorLogInfo.getMatch_time());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (this.itemClickListener != null) {
            itemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public static interface onItemClickListener {

        void onItemClick(View view, int position);
    }
}
