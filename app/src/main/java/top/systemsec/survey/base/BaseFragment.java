package top.systemsec.survey.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 基础碎片
 * Created by xiaohan on 2018/3/4.
 */

public abstract class BaseFragment extends Fragment implements IMVPBaseView {

    public abstract int getContentViewId();

    protected abstract void initAllMembersView(Bundle savedInstanceState);

    protected Context mContext;
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getContentViewId(), container, false);
        this.mContext = getActivity();
        initAllMembersView(savedInstanceState);
        return rootView;
    }

    /**
     * 展示加载进度条
     * @param msg
     */
    @Override
    public void showLoading(String msg) {
        if (getActivity() != null)
        ((MVPBaseActivity) mContext).showLoading(msg);
    }

    /**
     * 隐藏加载进度条
     */
    @Override
    public void hideLoading() {
       if (getActivity() != null)
        ((MVPBaseActivity) mContext).hideLoading();
    }

    /**
     * 弹出信息
     * @param msg
     */
    @Override
    public void showToast(String msg) {
        if (getActivity() != null)
        ((MVPBaseActivity) mContext).showToast(msg);
    }

    /**
     * activity是否被销毁
      * @return
     */
    protected boolean isAttachedContext(){
        return getActivity() != null;
    }

    /**
     * 检查activity连接情况
     */
    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }

    public static class ActivityNotAttachedException extends RuntimeException {
        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity ! - -.");
        }
    }

}
