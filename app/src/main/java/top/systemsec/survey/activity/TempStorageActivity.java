package top.systemsec.survey.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import top.systemsec.survey.R;
import top.systemsec.survey.base.MVPBaseActivity;
import top.systemsec.survey.custom_view.MyEditText;
import top.systemsec.survey.presenter.TempStoragePresenter;
import top.systemsec.survey.view.ITempStorageView;

public class TempStorageActivity extends MVPBaseActivity implements View.OnClickListener, ITempStorageView {

    private TempStoragePresenter mTempStoragePresenter;

    private ImageView mBackImage;
    private MyEditText mMyEdit;
    private Button mSearchBt;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_storage);
        initView();
        initListener();

        mTempStoragePresenter = new TempStoragePresenter();
        mTempStoragePresenter.attachView(this);

    }

    private void initView() {
        mBackImage = findViewById(R.id.backImage);
        mMyEdit = findViewById(R.id.myEdit);
        mSearchBt = findViewById(R.id.searchBt);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    private void initListener() {
        mBackImage.setOnClickListener(this);
        mSearchBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backImage:
                finish();//销毁自己
                break;
            case R.id.searchBt:
                break;
        }
    }

    /**
     * 返回用户要查找的数据
     *
     * @return
     */
    @Override
    public String getSearchInfo() {
        return mMyEdit.getMyEditText();
    }

    @Override
    protected void onDestroy() {
        mTempStoragePresenter.detachView();//解除绑定
        super.onDestroy();
    }
}
