package com.utils_library.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utils_library.utils.uiutils.ToastUtil;

/**
 * Created by guojiadong
 * on 2017/8/29.
 */

@SuppressWarnings("deprecation")
public abstract class BaseFragment extends Fragment {
    /**
     * 在Fragment中可以使用此Activity对象作为Context使用。<br>
     * <b>注意：禁止使用Fragment的gerRecourse()、getString()等方法，<font color=red>一律使用Activity中的相关资源获取方法</font>。</b>
     */
    protected Activity mActivity;

    /**
     * {@link Fragment#onCreate(Bundle)} 是否已经调用。
     */
    protected boolean isCreated;
    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isVisible;

    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = setContentView(inflater, container, savedInstanceState);
        return mView;
    }
    private void initVariable() {
        isCreated = false;
        isVisible = false;
    }
    /**
     * 直接提示文字
     * @param msg 文字
     */
    public final void showToast(String msg) {
        ToastUtil.showToast(mActivity, msg);
    }
    /**
     * 文字id
     * @param id 文字资源ID
     */
    public final void showToast(int id) {
        ToastUtil.showToast(mActivity, mActivity.getResources().getString(id));
    }

    /**
     * 通过ID找view
     * @param viewId 资源ID
     * @param <VIEW> view类型
     * @return
     */
    public final <VIEW extends View> VIEW findViewById(@IdRes int viewId) {
        return (VIEW) mView.findViewById(viewId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        if (!isCreated && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isVisible = true;
        }
    }
    protected void onFragmentVisibleChange(boolean isVisible) {
    }
    public abstract View setContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    public abstract void initView();
    public abstract void initData();

    @Override
    public void onAttach(Activity activity) {
        this.mActivity = activity;
        super.onAttach(activity);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mView == null) {
            return;
        }
        isCreated = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isVisible = true;
            return;
        }
        if (isVisible) {
            onFragmentVisibleChange(false);
            isVisible = false;
        }
    }
}
