/*
 * Copyright 2016 eneim@Eneim Labs, nam@ene.im
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.ene.lab.toro.ext.util;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by eneim on 2/3/16.
 */
public class ViewUtil {

  private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

  // Copy from AOSP

  /**
   * Generate a value suitable for use in {@link View#setId(int)}.
   * This value will not collide with ID values generated at build time by aapt for R.id.
   *
   * @return a generated ID value
   */
  public static int generateViewId() {
    for (; ; ) {
      final int result = sNextGeneratedId.get();
      // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
      int newValue = result + 1;
      if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
      if (sNextGeneratedId.compareAndSet(result, newValue)) {
        return result;
      }
    }
  }

  public static boolean isLargeScreen(Activity activity) {
    return !isMediumScreen(activity) && !isSmallScreen(activity);
  }

  public static boolean isMediumScreen(Activity activity) {
    return !isSmallScreen(activity)
        && activity.getResources().getConfiguration().smallestScreenWidthDp < 600;
  }

  public static boolean isSmallScreen(Activity activity) {
    return activity.getResources().getConfiguration().smallestScreenWidthDp < 410;
  }

  @SuppressWarnings("deprecation")
  public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver,
      ViewTreeObserver.OnGlobalLayoutListener listener) {
    if (viewTreeObserver == null) {
      return;
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
      viewTreeObserver.removeOnGlobalLayoutListener(listener);
    } else {
      viewTreeObserver.removeGlobalOnLayoutListener(listener);
    }
  }
}
