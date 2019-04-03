package top.systemsec.survey.base;

/**
 * 获取服务器的信息监听
 */
public interface OnGetInfoListener<T> {

    /**
     * 成功
     */
    void onSuccess(T info);

    /**
     * 失败
     */
    void onFail();

    /**
     * 完成
     */
    void onComplete();

}
