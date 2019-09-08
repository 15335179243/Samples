package com.eyecool.visitor.sysinner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eyecool.visitor.sysinner.R;


/**
 * Created date: 2017/6/15
 * Author:  Leslie
 */

public class VisitorInfoViewHolder extends RecyclerView.ViewHolder {

    TextView tvVisitorOrder;
    TextView tvParentName;
    TextView tvStuName;
    TextView tvStuClass;
    TextView tvWeclomeTeacher;
    TextView tvVisitorState;
    TextView tvVisitorTime;
    LinearLayout llInfoItemContainer;
    View greyLineBottom;


    public VisitorInfoViewHolder(View itemView) {
        super(itemView);
        tvVisitorOrder = (TextView) itemView.findViewById(R.id.tv_order);
        tvParentName = (TextView) itemView.findViewById(R.id.tv_parent_name);
        tvStuName = (TextView) itemView.findViewById(R.id.tv_stu_name);
        tvStuClass = (TextView) itemView.findViewById(R.id.tv_stu_class);
        tvWeclomeTeacher = (TextView) itemView.findViewById(R.id.tv_weclome_teacher);
        tvVisitorState = (TextView) itemView.findViewById(R.id.tv_state);
        tvVisitorTime = (TextView) itemView.findViewById(R.id.tv_login_time);
        greyLineBottom = itemView.findViewById(R.id.view_grey_line_bottom);
        llInfoItemContainer = (LinearLayout) itemView.findViewById(R.id.ll_info_item_container);
    }
}
