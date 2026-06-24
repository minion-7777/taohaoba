package io.openim.android.taohaoba.ui.fragment;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static io.openim.android.taohaoba.utils.StringArrayUtil.convertToArray;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import byc.imagewatcher.ImageWatcher;
import byc.imagewatcher.ImageWatcherHelper;
import io.openim.android.ouicore.base.BaseApp;
import io.openim.android.ouicore.base.BaseFragment;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.databinding.FragmentProductScreenshotBinding;
import io.openim.android.taohaoba.utils.CustomDotIndexProvider;
import io.openim.android.taohaoba.utils.CustomLoadingUIProvider;
import io.openim.android.taohaoba.utils.GlideSimpleLoader;
import io.openim.android.taohaoba.utils.ImgUtils;
import io.openim.android.taohaoba.vm.game.ProductDetailsVM;
import io.openim.android.taohaoba.widgets.RoundImageView;

/**
 * 商品截图
 */
public class ProductScreenshotFragment extends BaseFragment<ProductDetailsVM> implements ProductDetailsVM.ViewAction{

    private FragmentProductScreenshotBinding binding;
    private BaseQuickAdapter baseQuickAdapter;
    private String image;
    private ImageWatcherHelper iwHelper;
    private boolean isTranslucentStatus = false;
    private final SparseArray<ImageView> mVisiblePictureList = new SparseArray<>();

    public ProductScreenshotFragment(String image) {
        this.image = image;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        bindVM(ProductDetailsVM.class);
        BaseApp.inst().putVM(vm);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProductScreenshotBinding.inflate(getLayoutInflater());
        initView();
        return binding.getRoot();
    }

    private void initView() {
        //仿微信预览大图
        // 自定义LoadingUI，并不一定要调用这个API
        iwHelper = ImageWatcherHelper.with(this.getActivity(), new GlideSimpleLoader()) // 一般来讲， ImageWatcher 需要占据全屏的位置
                .setTranslucentStatus(!isTranslucentStatus ? ImgUtils.calcStatusBarHeight(getContext()) : 0) // 如果不是透明状态栏，你需要给ImageWatcher标记 一个偏移值，以修正点击ImageView查看的启动动画的Y轴起点的不正确
                .setErrorImageRes(R.mipmap.ic_default_image) // 配置error图标 如果不介意使用lib自带的图标，并不一定要调用这个API
                .setOnPictureLongPressListener(new ImageWatcher.OnPictureLongPressListener() {
                    @Override
                    public void onPictureLongPress(ImageView v, Uri uri, int pos) {

                    }
                })//长安监听，并不一定要调用这个API
                .setOnStateChangedListener(new ImageWatcher.OnStateChangedListener() {
                    @Override
                    public void onStateChangeUpdate(ImageWatcher imageWatcher, ImageView clicked, int position, Uri uri, float animatedValue, int actionTag) {
                        Log.e("IW", "onStateChangeUpdate [" + position + "][" + uri + "][" + animatedValue + "][" + actionTag + "]");
                    }

                    @Override
                    public void onStateChanged(ImageWatcher imageWatcher, int position, Uri uri, int actionTag) {

                    }
                })
                .setIndexProvider(new CustomDotIndexProvider())//自定义页码指示器（默认数字），并不一定要调用这个API
                .setLoadingUIProvider(new CustomLoadingUIProvider());

        initAdapter();
    }

    private void initAdapter(){

        // 将拆分后的字符串添加到 List 中
        List<String> list = new ArrayList<>();
        List<Uri> Urilist = new ArrayList<>();
        for (String part : convertToArray(image)) {
            list.add(part);
            Urilist.add(Uri.parse(part));
        }

        binding.emptyLayout.setVisibility(list.size() == 0 ? VISIBLE : GONE);
        binding.rcRecycler.setVisibility(list.size() > 0 ? VISIBLE : GONE);

        baseQuickAdapter = new BaseQuickAdapter(R.layout.item_imageview, list) {
            @Override
            protected void convert(@NonNull BaseViewHolder baseViewHolder, Object o) {
                RoundImageView iv_image = baseViewHolder.getView(R.id.iv_image);
                mVisiblePictureList.put(baseViewHolder.getPosition(), iv_image);
                Glide.with(getContext())
                        .load(o.toString())
                        .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) // 关键！
                        .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                                .error(R.mipmap.ic_default_image)// 加载失败的占位图
//                                .fitCenter()// 图片裁剪方式
                        )
                        .into(iv_image);

                baseViewHolder.itemView.setOnClickListener(v->{
                    iwHelper.show(iv_image, mVisiblePictureList, Urilist);
                });
            }
        };
        binding.rcRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rcRecycler.setAdapter(baseQuickAdapter);


    }


    @Override
    public void err(String msg) {

    }
}
