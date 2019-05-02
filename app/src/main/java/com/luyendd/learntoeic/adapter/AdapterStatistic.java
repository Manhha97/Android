package com.luyendd.learntoeic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.luyendd.learntoeic.R;
import com.luyendd.learntoeic.obj.ResultTest;

import java.util.List;

public class AdapterStatistic extends BaseAdapter {
    Context context;
    List<ResultTest> resultTestList;
    ViewHolderStatistic vht;
    public AdapterStatistic(Context context, List<ResultTest> resultTestList) {
        this.context = context;
        this.resultTestList = resultTestList;

    }
    @Override
    public int getCount() {
        return resultTestList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultTestList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater vi =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.adapter_statistic, null);
            vht = new ViewHolderStatistic();
            vht.iv = convertView.findViewById(R.id.image_view);
            vht.tvCorrect = convertView.findViewById(R.id.text_view_correct);
            vht.tvIncorrect = convertView.findViewById(R.id.text_view_incorrect);
            vht.tvDate = convertView.findViewById(R.id.text_view_date);

            convertView.setTag(vht);
        }else{
            vht = (ViewHolderStatistic) convertView.getTag();
        }
        vht.tvDate.setText(resultTestList.get(position).getDate());
        vht.tvCorrect.setText("Correct : " + resultTestList.get(position).getCorrect());
        int incorrect = resultTestList.get(position).getTotal() - resultTestList.get(position).getCorrect();
        vht.tvIncorrect.setText("Incorrect : " + incorrect);

        if(position != resultTestList.size() - 1) {
            if (resultTestList.get(position).getCorrect() > resultTestList.get(position + 1).getCorrect()){
                vht.iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_row_up));
            }else if (resultTestList.get(position).getCorrect() == resultTestList.get(position + 1).getCorrect()) {
                vht.iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_row));
            } else {
                vht.iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_row_down));
            }
        } else {
            vht.iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_row_up));
        }


//        if (position == 0){
//
//        } else {
//            if (resultTestList.get(position).getCorrect() > resultTestList.get(position - 1).getCorrect()){
//                vht.iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_row_up));
//            }else if (resultTestList.get(position).getCorrect() == resultTestList.get(position - 1).getCorrect()) {
//                vht.iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_row));
//            } else {
//                vht.iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_row_down));
//            }
//        }

        return convertView;
    }

    public class ViewHolderStatistic{
        ImageView iv;
        TextView tvCorrect, tvIncorrect, tvDate ;
    }
}

