package top.systemsec.survey.view;

import android.content.res.Resources;

import java.util.List;

import top.systemsec.survey.base.IMVPBaseView;
import top.systemsec.survey.bean.ImageUploadState;
import top.systemsec.survey.bean.SurveyBean;

public interface ITempStorageShowView extends IMVPBaseView {


   /* *//**
     * 初始化街道
     *
     * @param streets
     *//*
    void initStreet(String[] streets);

    *//**
     * 初始化街道
     *
     * @param polices
     *//*
    void initPolice(String[] polices);*/

    /**
     * 得到源
     *
     * @return
     */
    Resources getResources();

    /**
     * 站点上传成功
     */
    void uploadOk();

    /**
     *
     * @param imageList
     */
    void sysImageState(List<ImageUploadState> imageList);//同步一下本地图片的状态

}
