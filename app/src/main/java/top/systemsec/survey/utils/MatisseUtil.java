package top.systemsec.survey.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import top.systemsec.survey.R;
import top.systemsec.survey.activity.NewSurveyActivity;

/**
 * 开启matisse工具类
 */
public class MatisseUtil {


    public static void selectImage(Activity activity, int maxImgNum, Resources resources, int resultCode) {
        Matisse.from(activity)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .captureStrategy(
                        new CaptureStrategy(true, "top.systemsec.survey.fileprovider"))
                .maxSelectable(maxImgNum)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .originalEnable(true)
                .maxOriginalSize(10)
                .forResult(resultCode);
    }

}
