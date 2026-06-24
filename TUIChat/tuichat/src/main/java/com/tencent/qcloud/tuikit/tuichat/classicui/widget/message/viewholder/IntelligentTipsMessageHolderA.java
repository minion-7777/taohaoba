package com.tencent.qcloud.tuikit.tuichat.classicui.widget.message.viewholder;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.tencent.qcloud.tuikit.timcommon.bean.IntelligentTipsMessageABean;
import com.tencent.qcloud.tuikit.timcommon.bean.TUIMessageBean;
import com.tencent.qcloud.tuikit.timcommon.classicui.widget.message.MessageContentHolder;
import com.tencent.qcloud.tuikit.tuichat.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntelligentTipsMessageHolderA extends MessageContentHolder {
    private TextView titlel1;
    private TextView contentl1;
    private TextView contentl2;

    public IntelligentTipsMessageHolderA(View itemView) {
        super(itemView);
        titlel1 = itemView.findViewById(R.id.titlel1);
        contentl1 = itemView.findViewById(R.id.contentl1);
        contentl2 = itemView.findViewById(R.id.contentl2);
    }

    @Override
    public int getVariableLayout() {
        return R.layout.layout_msg_intelligent_card_1;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void layoutVariableViews(TUIMessageBean msg, int position) {
        if (msg instanceof IntelligentTipsMessageABean) {
            IntelligentTipsMessageABean tipsMsg = (IntelligentTipsMessageABean) msg;

            titlel1.setText(tipsMsg.getTitle());
            contentl1.setText(tipsMsg.getContent());

            SpannableString spannable;

            // 1. 正则匹配两种格式（{{***}} 和 [[\$***\$]]）
            Pattern pattern = Pattern.compile("(?:\\{\\{(.*?)\\}\\})|(?:\\[\\[\\$(.*?)\\$\\]\\])"); // 关键修复
            Matcher matcher = pattern.matcher(tipsMsg.getContent());
            List<SpanInfo> spanInfos = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            int lastEnd = 0;

            // 2. 提取内容并替换括号为空格
            while (matcher.find()) {
                sb.append(tipsMsg.getContent(), lastEnd, matcher.start()); // 添加匹配前文本
                sb.append(" "); // 替换左括号为空格
                int start = sb.length();

                // 提取原始内容（group1对应{{***}}，group2对应[[\$***\$]]）
                String originalContent = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
                // 判断是否为[[\$***\$]]格式（group2有值）
                boolean isDollarFormat = matcher.group(2) != null;
                // 显示文本：[[\$***\$]]替换为“加入群聊”，其他格式使用原始内容
                String displayText = isDollarFormat ? "加入群聊" : originalContent;

                sb.append(displayText); // 添加显示文本
                int end = sb.length();
                sb.append(" "); // 替换右括号为空格

                // 保存原始内容到SpanInfo（无论哪种格式）
                spanInfos.add(new SpanInfo(start, end, originalContent));

                lastEnd = matcher.end();
            }
            sb.append(tipsMsg.getContent().substring(lastEnd)); // 添加剩余文本

            // 3. 创建SpannableString并设置样式
            spannable = new SpannableString(sb.toString());
            for (SpanInfo info : spanInfos) {
                // 设置点击事件（复用原有URL跳转逻辑）
                spannable.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        if (!TextUtils.isEmpty(info.text) && (info.text.startsWith("http://") || info.text.startsWith("https://"))) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(info.text));
                            if (browserIntent.resolveActivity(widget.getContext().getPackageManager()) != null) {
                                widget.getContext().startActivity(browserIntent);
                            } else {
                                Toast.makeText(widget.getContext(), "未找到浏览器应用", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(widget.getContext(), "无效的网页链接", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                    }
                }, info.start, info.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                // 直接使用上下文获取资源，避免通过绑定类间接获取可能导致的上下文异常
                int textColor = itemView.getContext().getColor(R.color.color_EACA92);
                spannable.setSpan(new ForegroundColorSpan(textColor),
                        info.start, info.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE); // 修改Span范围模式
            }

            // 4. 设置处理后的文本
            contentl1.setMovementMethod(LinkMovementMethod.getInstance());
            contentl1.setHighlightColor(Color.TRANSPARENT);
            contentl1.setText(spannable);
            // 【修改结束】

            // 关键修复：等待布局测量完成后获取实际行数
            contentl1.post(() -> {
                int actualLineCount = contentl1.getLineCount(); // 获取当前实际行数
                // 若实际行数 >= 15，显示"展开更多"按钮；否则隐藏
                contentl2.setVisibility(actualLineCount >= 15 ? View.VISIBLE : View.GONE);
            });
            contentl2.setOnClickListener(v1->{
                if (contentl1.getMaxLines() == 15){
                    contentl1.setMaxLines(Integer.MAX_VALUE);
                    contentl2.setText("收起");
                }else {
                    contentl1.setMaxLines(15);
                    contentl2.setText("展开更多");
                }
                contentl2.setSelected(!contentl2.isSelected());
            });
        }
    }

    private static class SpanInfo {
        int start;
        int end;
        String text;
        SpanInfo(int start, int end, String text) {
            this.start = start;
            this.end = end;
            this.text = text;
        }
    }
}
