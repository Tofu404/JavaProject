// Generated code from Butter Knife. Do not modify!
package com.alltheway.forward;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.pw.view.NativeAdContainer;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view7f07007b;

  private View view7f070077;

  private View view7f0700a1;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.native_btn, "field 'nativeBtn' and method 'clickNativeBtn'");
    target.nativeBtn = Utils.castView(view, R.id.native_btn, "field 'nativeBtn'", Button.class);
    view7f07007b = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickNativeBtn();
      }
    });
    view = Utils.findRequiredView(source, R.id.load_reward_btn, "field 'loadRewardBtn' and method 'clickLoadRewardBtn'");
    target.loadRewardBtn = Utils.castView(view, R.id.load_reward_btn, "field 'loadRewardBtn'", Button.class);
    view7f070077 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickLoadRewardBtn();
      }
    });
    view = Utils.findRequiredView(source, R.id.show_reward_btn, "field 'showRewardBtn' and method 'clickShowRewardBtn'");
    target.showRewardBtn = Utils.castView(view, R.id.show_reward_btn, "field 'showRewardBtn'", Button.class);
    view7f0700a1 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.clickShowRewardBtn();
      }
    });
    target.appIcon = Utils.findRequiredViewAsType(source, R.id.app_icon, "field 'appIcon'", ImageView.class);
    target.title = Utils.findRequiredViewAsType(source, R.id.title, "field 'title'", TextView.class);
    target.description = Utils.findRequiredViewAsType(source, R.id.description, "field 'description'", TextView.class);
    target.layout = Utils.findRequiredViewAsType(source, R.id.layout, "field 'layout'", RelativeLayout.class);
    target.container = Utils.findRequiredViewAsType(source, R.id.container, "field 'container'", NativeAdContainer.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.nativeBtn = null;
    target.loadRewardBtn = null;
    target.showRewardBtn = null;
    target.appIcon = null;
    target.title = null;
    target.description = null;
    target.layout = null;
    target.container = null;

    view7f07007b.setOnClickListener(null);
    view7f07007b = null;
    view7f070077.setOnClickListener(null);
    view7f070077 = null;
    view7f0700a1.setOnClickListener(null);
    view7f0700a1 = null;
  }
}
