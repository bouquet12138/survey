package top.systemsec.survey.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import top.systemsec.survey.R;
import top.systemsec.survey.bean.SurveyBean;

public class TempSaveInfoAdapter extends RecyclerView.Adapter<TempSaveInfoAdapter.ViewHolder> {

    private List<SurveyBean> mSurveyBeans;

    public TempSaveInfoAdapter(List<SurveyBean> surveyBeans) {
        mSurveyBeans = surveyBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_temp_save_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        SurveyBean surveyBean = mSurveyBeans.get(position);//得到该位置的surveyBean

        holder.mViewGroup.setOnClickListener((v) -> {
            if (mOnItemClickListener != null)
                mOnItemClickListener.onItemClick(position);//点击监听
        });

        holder.mIndexTextView.setText(position + 1 + "");//编号
        holder.mPointNameText.setText(surveyBean.getPointName());//点名称
        holder.mSaveTimeTextView.setText("保存时间： " + surveyBean.getSaveTime());//保存时间
    }

    @Override
    public int getItemCount() {
        return mSurveyBeans == null ? 0 : mSurveyBeans.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewGroup mViewGroup;
        TextView mIndexTextView;
        TextView mPointNameText;//点名称
        TextView mSaveTimeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mViewGroup = itemView.findViewById(R.id.viewGroup);
            mIndexTextView = itemView.findViewById(R.id.indexTextView);
            mPointNameText = itemView.findViewById(R.id.pointNameText);
            mSaveTimeTextView = itemView.findViewById(R.id.saveTimeTextView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int index);
    }

    private OnItemClickListener mOnItemClickListener;//项目点击

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
