package com.bysj.mbss.fragment;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bysj.mbss.R;
import com.bysj.mbss.adapter.LocalImageHolderView;
import com.bysj.mbss.entity.NewsEntity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

@ContentView(R.layout.fragment_blank)
public class BlankFragment extends BaseFragment implements OnItemClickListener {
    @ViewInject(R.id.banner)
    private com.bigkoo.convenientbanner.ConvenientBanner convenientBanner;

    @Override
    protected void initResource() {

    }

    @Override
    protected void initWidget() {

    }

    /**
     * 根据数据初始化引导页
     */
    private void initConvenineBanner(List<NewsEntity> list) {
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int i) {

    }


    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
        BmobQuery<NewsEntity> query = new BmobQuery<NewsEntity>();
        query.addWhereNotEqualTo("objectId", "");
        query.setLimit(50);
        query.findObjects(getActivity(), new FindListener<NewsEntity>() {
            @Override
            public void onSuccess(List<NewsEntity> list) {
                initConvenineBanner(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }


}
