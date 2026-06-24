package io.openim.android.taohaoba.ui.adapter;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import io.openim.android.taohaoba.R;
import io.openim.android.taohaoba.bean.GameListBean;
import io.openim.android.taohaoba.widgets.RoundImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameListAdapter extends BaseQuickAdapter<GameListBean.GameDTO, BaseViewHolder> {

    private Map<String, Integer> letterPositionMap = new HashMap<>(); // 存储字母对应的位置

    public GameListAdapter(List<GameListBean.GameDTO> data) {
        super(R.layout.item_game_list, data);
        buildLetterPositionMap();
    }

    // 构建字母与位置的映射
    private void buildLetterPositionMap() {
        String currentLetter = "";
        for (int i = 0; i < this.getData().size(); i++) {
            String letter = this.getData().get(i).getPinyin();
            if (!letter.equals(currentLetter)) {
                letterPositionMap.put(letter, i);
                currentLetter = letter;
            }
        }
    }

    // 根据字母获取位置
    public int getPositionByLetter(String letter) {
        return letterPositionMap.containsKey(letter) ? letterPositionMap.get(letter) : 0;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, GameListBean.GameDTO s) {
        baseViewHolder.setText(R.id.tv_text, s.getName());
        RoundImageView imageView = baseViewHolder.getView(R.id.iv_img);
        Glide.with(baseViewHolder.itemView)
                .load(!TextUtils.isEmpty(s.getImage()) ? s.getImage() : "")
                .apply(new RequestOptions()
                        .transform(new RoundedCorners(10)) // 圆角半径（单位：像素）
                        .placeholder(R.mipmap.ic_default_image)// 加载中的占位图
                        .error(R.mipmap.ic_default_image)// 加载失败的占位图
                        .centerCrop()// 图片裁剪方式
                )
                .into(imageView);
    }
}