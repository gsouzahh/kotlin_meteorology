// Generated by view binder compiler. Do not edit!
package me.guilherme.s.forato.androidApp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;
import me.guilherme.s.forato.androidApp.R;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView dataCurrent;

  @NonNull
  public final ImageView iconHumidity;

  @NonNull
  public final ImageView iconTemp;

  @NonNull
  public final ImageView imageLogo;

  @NonNull
  public final RelativeLayout mainView;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextView testando;

  @NonNull
  public final TextView timeCurrent;

  @NonNull
  public final TextView txtFeelsLike;

  @NonNull
  public final TextView txtHumidity;

  @NonNull
  public final TextView txtInfo;

  @NonNull
  public final TextView txtTemp;

  @NonNull
  public final ViewFlipper viewFlipper;

  private ActivityMainBinding(@NonNull RelativeLayout rootView, @NonNull TextView dataCurrent,
      @NonNull ImageView iconHumidity, @NonNull ImageView iconTemp, @NonNull ImageView imageLogo,
      @NonNull RelativeLayout mainView, @NonNull ProgressBar progressBar,
      @NonNull TextView testando, @NonNull TextView timeCurrent, @NonNull TextView txtFeelsLike,
      @NonNull TextView txtHumidity, @NonNull TextView txtInfo, @NonNull TextView txtTemp,
      @NonNull ViewFlipper viewFlipper) {
    this.rootView = rootView;
    this.dataCurrent = dataCurrent;
    this.iconHumidity = iconHumidity;
    this.iconTemp = iconTemp;
    this.imageLogo = imageLogo;
    this.mainView = mainView;
    this.progressBar = progressBar;
    this.testando = testando;
    this.timeCurrent = timeCurrent;
    this.txtFeelsLike = txtFeelsLike;
    this.txtHumidity = txtHumidity;
    this.txtInfo = txtInfo;
    this.txtTemp = txtTemp;
    this.viewFlipper = viewFlipper;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.dataCurrent;
      TextView dataCurrent = rootView.findViewById(id);
      if (dataCurrent == null) {
        break missingId;
      }

      id = R.id.iconHumidity;
      ImageView iconHumidity = rootView.findViewById(id);
      if (iconHumidity == null) {
        break missingId;
      }

      id = R.id.iconTemp;
      ImageView iconTemp = rootView.findViewById(id);
      if (iconTemp == null) {
        break missingId;
      }

      id = R.id.imageLogo;
      ImageView imageLogo = rootView.findViewById(id);
      if (imageLogo == null) {
        break missingId;
      }

      RelativeLayout mainView = (RelativeLayout) rootView;

      id = R.id.progressBar;
      ProgressBar progressBar = rootView.findViewById(id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.testando;
      TextView testando = rootView.findViewById(id);
      if (testando == null) {
        break missingId;
      }

      id = R.id.timeCurrent;
      TextView timeCurrent = rootView.findViewById(id);
      if (timeCurrent == null) {
        break missingId;
      }

      id = R.id.txt_feels_like;
      TextView txtFeelsLike = rootView.findViewById(id);
      if (txtFeelsLike == null) {
        break missingId;
      }

      id = R.id.txt_humidity;
      TextView txtHumidity = rootView.findViewById(id);
      if (txtHumidity == null) {
        break missingId;
      }

      id = R.id.txt_info;
      TextView txtInfo = rootView.findViewById(id);
      if (txtInfo == null) {
        break missingId;
      }

      id = R.id.txt_temp;
      TextView txtTemp = rootView.findViewById(id);
      if (txtTemp == null) {
        break missingId;
      }

      id = R.id.viewFlipper;
      ViewFlipper viewFlipper = rootView.findViewById(id);
      if (viewFlipper == null) {
        break missingId;
      }

      return new ActivityMainBinding((RelativeLayout) rootView, dataCurrent, iconHumidity, iconTemp,
          imageLogo, mainView, progressBar, testando, timeCurrent, txtFeelsLike, txtHumidity,
          txtInfo, txtTemp, viewFlipper);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
