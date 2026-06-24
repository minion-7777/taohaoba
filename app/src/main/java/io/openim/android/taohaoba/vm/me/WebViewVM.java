package io.openim.android.taohaoba.vm.me;

import androidx.lifecycle.MutableLiveData;

import io.openim.android.ouicore.base.BaseViewModel;
import io.openim.android.ouicore.net.RXRetrofit.N;
import io.openim.android.ouicore.net.RXRetrofit.NetObserverThb;
import io.openim.android.ouicore.net.RXRetrofit.Parameter;
import io.openim.android.ouicore.net.bage.BaseTHB;
import io.openim.android.taohaoba.bean.ActivityCenterBean;
import io.openim.android.taohaoba.bean.AgreementBean;
import io.openim.android.taohaoba.bean.ArticlesCategoryBean;
import io.openim.android.taohaoba.bean.ArticlesDetailsBean;
import io.openim.android.taohaoba.bean.ArticlesIndexBean;
import io.openim.android.taohaoba.bean.BannerImgBean;
import io.openim.android.taohaoba.repository.OpenIMService;

public class WebViewVM extends BaseViewModel<WebViewVM.ViewAction> {

    /**
     * 服务协议
     */
    private MutableLiveData<AgreementBean> agreementBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<AgreementBean> getAgreementBeanMutableLiveData() {
        return agreementBeanMutableLiveData;
    }

    public void get_agreement(int category_id) {
        Parameter parameter = new Parameter();
        parameter.add("category_id", category_id);

        N.APIThb(OpenIMService.class)
                .get_agreement(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(AgreementBean.class))
                .subscribe(new NetObserverThb<AgreementBean>(getContext()) {
                    @Override
                    public void onSuccess(AgreementBean it) {
                        agreementBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 文章列表
     */
    private MutableLiveData<ArticlesIndexBean> articlesIndexBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ArticlesIndexBean> getArticlesIndexBeanMutableLiveData() {
        return articlesIndexBeanMutableLiveData;
    }

    public void getArticlesIndex(String page, String category_id, String limit) {

        N.APIThb(OpenIMService.class)
                .getArticlesIndex(page, category_id, limit)
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(ArticlesIndexBean.class))
                .subscribe(new NetObserverThb<ArticlesIndexBean>(getContext()) {
                    @Override
                    public void onSuccess(ArticlesIndexBean it) {
                        articlesIndexBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 文章详情
     */
    private MutableLiveData<ArticlesDetailsBean> articlesDetailsBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ArticlesDetailsBean> getArticlesDetailsBeanMutableLiveData() {
        return articlesDetailsBeanMutableLiveData;
    }

    public void getArticlesDetails(int id) {
        Parameter parameter = new Parameter();
        parameter.add("id", id);

        N.APIThb(OpenIMService.class)
                .getArticlesDetails(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(ArticlesDetailsBean.class))
                .subscribe(new NetObserverThb<ArticlesDetailsBean>(getContext()) {
                    @Override
                    public void onSuccess(ArticlesDetailsBean it) {
                        articlesDetailsBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 文章详情
     */
    private MutableLiveData<BannerImgBean> imgBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<BannerImgBean> getImgBeanMutableLiveData() {
        return imgBeanMutableLiveData;
    }

    public void get_tag_info(String id) {
        Parameter parameter = new Parameter();
        parameter.add("id", Integer.valueOf(id));

        N.APIThb(OpenIMService.class)
                .get_tag_info(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(BannerImgBean.class))
                .subscribe(new NetObserverThb<BannerImgBean>(getContext()) {
                    @Override
                    public void onSuccess(BannerImgBean it) {
                        imgBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 帮助中心分类
     */
    private MutableLiveData<ArticlesCategoryBean> articlesCategoryBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ArticlesCategoryBean> getArticlesCategoryBeanMutableLiveData() {
        return articlesCategoryBeanMutableLiveData;
    }

    public void getArticlesCategory() {

        N.APIThb(OpenIMService.class)
                .getArticlesCategory()
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(ArticlesCategoryBean.class))
                .subscribe(new NetObserverThb<ArticlesCategoryBean>(getContext()) {
                    @Override
                    public void onSuccess(ArticlesCategoryBean it) {
                        articlesCategoryBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }

    /**
     * 活动列表
     */
    private MutableLiveData<ActivityCenterBean> activityCenterBeanMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<ActivityCenterBean> getActivityCenterBeanMutableLiveData() {
        return activityCenterBeanMutableLiveData;
    }

    public void getActivityList(int page, String status) {
        Parameter parameter = new Parameter();
        parameter.add("page", page);
        parameter.add("page_size", 10);
        parameter.add("status", status);// upcoming:未开始 ongoing:进行中 all:全部

        N.APIThb(OpenIMService.class)
                .getActivityList(parameter.buildJsonBody())
                .compose(N.IOMain())
                .map(OpenIMService.turnThb(ActivityCenterBean.class))
                .subscribe(new NetObserverThb<ActivityCenterBean>(getContext()) {
                    @Override
                    public void onSuccess(ActivityCenterBean it) {
                        activityCenterBeanMutableLiveData.postValue(it);
                    }

                    @Override
                    protected void onFailure(BaseTHB baseTHB) {
                        getIView().err(baseTHB.msg);
                    }
                });
    }


    public interface ViewAction extends io.openim.android.ouicore.base.IView {
        void err(String msg);
    }
}
