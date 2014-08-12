/**
 * Created by ramp on 8/11/14.
 */
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.EditText;

import com.example.memorize.memorize.CheckActivity;
import com.example.memorize.memorize.R;


public class CheckActivityTest extends ActivityInstrumentationTestCase2<CheckActivity> {
    private CheckActivity mActivity;

    public CheckActivityTest() {
        super(CheckActivity.class);

        mActivity = null;
    }

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mActivity = getActivity();
    }

    @UiThreadTest
    public void test_whole_process() {
        EditText input = (EditText) mActivity.findViewById(R.id.number_input);

        for (int i=0;i<50;++i) {
            assertEquals(input.getText().toString(), "");
            input.setText(Integer.toString(i));
            mActivity.next_btn_click(null);
        }

        assertEquals(mActivity.getIndex(), 50);

        for (int i=0;i<50;++i) {
            assertEquals(mActivity.getNumbers()[i], i);
        }

        for (int i=50;i<mActivity.getNumbers().length;++i) {
            assertEquals(mActivity.getNumbers()[i], CheckActivity.INVALID_NUMBER);
        }
    }

    @UiThreadTest
    public void test_boundaries() {
        assertEquals(mActivity.getIndex(), 0);

        mActivity.prev_btn_click(null);
        mActivity.prev_btn_click(null);
        mActivity.prev_btn_click(null);
        assertEquals(mActivity.getIndex(), 0);

        for (int i=0;i<mActivity.getNumbers().length+1;++i) {
            mActivity.next_btn_click(null);
        }
        assertEquals(mActivity.getIndex(), mActivity.getNumbers().length-1);
    }

    @UiThreadTest
    public void test_shifting() {
        EditText input = (EditText) mActivity.findViewById(R.id.number_input);

        for (int i=0;i<50;++i) {
            input.setText(Integer.toString(i));
            mActivity.next_btn_click(null);
        }

        mActivity.onItemLongClick(null, null, 2, 2);
        assertEquals(mActivity.getNumbers()[2], CheckActivity.INVALID_NUMBER);
        for (int i=3;i<51;++i) {
            assertEquals(mActivity.getNumbers()[i], i-1);
        }
    }


}
