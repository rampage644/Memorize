import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.util.Log;
import android.widget.TextView;

import com.example.memorize.memorize.R;
import com.example.memorize.memorize.ShowActivity;

/**
 * Created by zvm on 5/23/14.
 */
public class ShowActivityTest extends ActivityInstrumentationTestCase2<ShowActivity> {
    private ShowActivity mActivity;

    public ShowActivityTest() {
        super(ShowActivity.class);

        mActivity = null;
    }

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
    }

    @UiThreadTest
    public void test_start() {
        ShowActivity activity = mActivity;

        assertEquals(activity.getCount(), 100);
        assertNull(activity.getNumbers());
        assertNull(activity.getTime());

        activity.onClick(null);
        assertNotNull(activity.getNumbers());
        assertNotNull(activity.getTime());
        assertEquals(activity.getNumbers().length, activity.getCount());
        assertEquals(activity.getTime().length, activity.getCount());
    }

    @UiThreadTest
    public void test_label() {
        ShowActivity activity = mActivity;

        TextView v = (TextView) activity.findViewById(R.id.textView);
        assertNotNull(v);

        assertEquals(v.getText(), activity.getString(R.string.show_start_text));

        activity.onClick(v);
        assertFalse(v.getText() == activity.getString(R.string.show_start_text));

        for (int i=0;i<activity.getCount();i++) {
            assertEquals(v.getText(), Integer.toString(activity.getNumbers()[i]));
            activity.onClick(v);
        }
        assertEquals(v.getText(), activity.getString(R.string.show_start_text));
    }

    @UiThreadTest
    public void test_label_change_look() {
        //  can't test it for now,
        // have to use custom styles, ignore
    }

    @UiThreadTest
    public void test_measure_time() {
        mActivity.onClick(null);

        for (int i=0;i<mActivity.getCount();i++) {
            SystemClock.sleep(10);
            mActivity.onClick(null);
        }

        for (int i=0;i<mActivity.getCount();i++) {
            assertTrue(mActivity.getTime()[i] >= 10);
        }
     }

    @UiThreadTest
    public void test_next_round() {
        // start
        mActivity.onClick(null);

        for (int i=0;i<mActivity.getCount();i++) {
            // do the round
            mActivity.onClick(null);
        }
        int[] numbers = mActivity.getNumbers();

        // start again
        mActivity.onClick(null);

        // because sequences are random we can't rely on
        // checking every element. So we use another metric: sum all elements.
        // Probability of getting the whole sequence just the same is 0.01^100
        // while probability of getting any element equal to predecessor is 0.01*count
        // with count > 100 it's almost guaranteed that test will fail :)
        int sum1 = 0, sum2 = 0;
        for (int i=0;i<mActivity.getCount();i++) {
            sum1 += numbers[i];
            sum2 += mActivity.getNumbers()[i];
        }

        assertFalse(sum1 == sum2);
    }

    @UiThreadTest
    public void test_start_check_activity() {

    }

}
