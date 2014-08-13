import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.widget.TextView;

import com.example.memorize.memorize.RootActivity;

/**
 * Created by ramp on 8/13/14.
 */
public class RootActivityTest extends ActivityInstrumentationTestCase2<RootActivity> {
    private RootActivity mActivity;

    public RootActivityTest() {
        super(RootActivity.class);

        mActivity = null;
    }

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
    }

    @UiThreadTest
    public void test_new_session() {
        // TODO
    }
}
